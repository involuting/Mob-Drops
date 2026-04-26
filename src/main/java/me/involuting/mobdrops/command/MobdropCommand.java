package me.involuting.mobdrops.command;

import me.involuting.mobdrops.menu.DropPreviewMenu;
import me.involuting.mobdrops.menu.SelectMobMenu;
import me.involuting.mobdrops.Mobdrops;
import me.involuting.mobdrops.mob.category.MobCategory;
import org.bukkit.command.*;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MobdropCommand implements CommandExecutor {

    public enum Mode {
        ADD,
        REMOVE
    }

    private static final Map<UUID, Mode> modes = new HashMap<>();

    private final Mobdrops plugin = Mobdrops.getInstance();

    @Override
    public boolean onCommand(@NotNull CommandSender sender,
                             @NotNull Command command,
                             @NotNull String label,
                             @NotNull String[] args) {

        if (!(sender instanceof Player player)) {
            sender.sendMessage("§cOnly players can use this command.");
            return true;
        }

        if (!player.hasPermission("mobdrops.admin")) {
            player.sendMessage("§cNo permission.");
            return true;
        }

        if (args.length == 0) {
            sendUsage(player);
            return true;
        }

        switch (args[0].toLowerCase()) {

            case "add" -> {
                modes.put(player.getUniqueId(), Mode.ADD);
                new SelectMobMenu(player, MobCategory.ALL, "").open();
                player.sendMessage("§aSelect a mob to add a drop.");
            }

            case "remove" -> {
                modes.put(player.getUniqueId(), Mode.REMOVE);
                new SelectMobMenu(player, null, "").open();
                player.sendMessage("§cSelect a mob to remove a drop.");
            }

            case "preview" -> {

                if (args.length < 2) {
                    player.sendMessage("§cUsage: /mobdrop preview <mob>");
                    return true;
                }

                try {
                    EntityType type = EntityType.valueOf(args[1].toUpperCase());
                    new DropPreviewMenu(player, type).open();
                } catch (Exception e) {
                    player.sendMessage("§cInvalid mob type.");
                }
            }

            case "list" -> {

                if (args.length < 2) {
                    player.sendMessage("§cUsage: /mobdrop list <mob>");
                    return true;
                }

                try {
                    EntityType type = EntityType.valueOf(args[1].toUpperCase());

                    var world = player.getWorld();

                    var drops = plugin.getDropManager()
                            .getGlobalDrops(type);

                    player.sendMessage("§eDrops for §a" + type.name() + " §ein §b" + world.getName() + "§e:");

                    if (drops.isEmpty()) {
                        player.sendMessage("§cNo drops found.");
                        return true;
                    }

                    drops.forEach(drop ->
                            player.sendMessage("§7- §f" + drop.getMaterial().name()
                                    + " §7(" + drop.getChance() + "%)")
                    );

                } catch (Exception e) {
                    player.sendMessage("§cInvalid mob type.");
                }
            }

            case "reload" -> {
                plugin.reloadConfig();
                player.sendMessage("§aMobdrops reloaded.");
            }

            default -> sendUsage(player);
        }

        return true;
    }

    public static Mode getMode(Player player) {
        return modes.getOrDefault(player.getUniqueId(), Mode.ADD);
    }

    private void sendUsage(Player player) {
        player.sendMessage(" ");
        player.sendMessage("§eMobdrops Commands:");
        player.sendMessage("§a/mobdrop add");
        player.sendMessage("§c/mobdrop remove");
        player.sendMessage("§e/mobdrop preview (mob)");
        player.sendMessage("§e/mobdrop list (mob)");
        player.sendMessage("§e/mobdrop reload");
        player.sendMessage(" ");
    }
}