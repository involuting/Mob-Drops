package me.involuting.mobdrops.command;

import me.involuting.mobdrops.menu.SelectMobMenu;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class MobdropCommand implements CommandExecutor {

    public enum Mode {
        ADD,
        REMOVE
    }

    private static Mode mode = Mode.ADD;

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
                mode = Mode.ADD;
                new SelectMobMenu(player).open();
                player.sendMessage("§aSelect a mob to add an drop to.");
            }

            case "remove" -> {
                mode = Mode.REMOVE;
                new SelectMobMenu(player).open();
                player.sendMessage("§cSelect a mob to remove an drop.");
            }

            default -> sendUsage(player);
        }

        return true;
    }

    public static Mode getMode() {
        return mode;
    }

    private void sendUsage(Player player) {
        player.sendMessage(" ");
        player.sendMessage("§eMob Drops:");
        player.sendMessage("§a/mobdrop add §7- Add a new drop");
        player.sendMessage("§c/mobdrop remove §7- Remove a drop");
        player.sendMessage(" ");
    }
}