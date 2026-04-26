package me.involuting.mobdrops.chance;

import me.involuting.mobdrops.Mobdrops;
import me.involuting.mobdrops.menu.MobEditorMenu;
import me.involuting.mobdrops.menu.PlaceholderButton;
import me.involuting.mobdrops.model.Drop;
import net.j4c0b3y.api.menu.Menu;
import net.j4c0b3y.api.menu.MenuSize;
import net.j4c0b3y.api.menu.button.Button;
import net.j4c0b3y.api.menu.button.ButtonClick;
import net.j4c0b3y.api.menu.layer.impl.BackgroundLayer;
import net.j4c0b3y.api.menu.layer.impl.ForegroundLayer;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;

public class ChanceEditorMenu extends Menu {

    private final EntityType type;
    private final double chance;

    public ChanceEditorMenu(Player player, EntityType type) {
        this(player, type, 5.0);
    }

    public ChanceEditorMenu(Player player, EntityType type, double chance) {
        super("Chance Editor", MenuSize.THREE, player);
        this.type = type;
        this.chance = chance;
    }

    @Override
    public void setup(BackgroundLayer background, ForegroundLayer foreground) {

        background.fill(new PlaceholderButton());

        foreground.set(13, display());

        foreground.set(10, change(-5));
        foreground.set(11, change(-1));

        foreground.set(15, change(1));
        foreground.set(16, change(5));

        foreground.set(22, confirm());
    }

    private Button change(double amount) {
        return new Button() {
            @Override
            public void onClick(ButtonClick click) {

                double newChance = Math.max(0, Math.min(100, chance + amount));

                new ChanceEditorMenu(
                        click.getMenu().getPlayer(),
                        type,
                        newChance
                ).open();
            }

            @Override
            public ItemStack getIcon() {
                return new ItemStack(Material.ARROW);
            }
        };
    }

    private Button display() {
        return new Button() {
            @Override
            public void onClick(ButtonClick click) {}

            @Override
            public ItemStack getIcon() {
                ItemStack item = new ItemStack(Material.PAPER);
                ItemMeta meta = item.getItemMeta();

                if (meta != null) {
                    meta.setDisplayName("§eChance: §a" + chance + "%");
                    meta.setLore(Collections.singletonList("§7Adjust drop chance"));
                    item.setItemMeta(meta);
                }

                return item;
            }
        };
    }

    private Button confirm() {
        return new Button() {
            @Override
            public void onClick(ButtonClick click) {

                Player p = click.getMenu().getPlayer();
                ItemStack hand = p.getInventory().getItemInMainHand();

                if (hand == null || hand.getType().isAir()) {
                    p.sendMessage("§cYou must be holding an item!");
                    return;
                }

                ItemMeta meta = hand.getItemMeta();

                String name = meta != null && meta.hasDisplayName()
                        ? meta.getDisplayName()
                        : null;

                java.util.List<String> lore = meta != null && meta.hasLore()
                        ? meta.getLore()
                        : null;

                Drop drop = new Drop(
                        hand.getType(),
                        name,
                        lore,
                        hand.getAmount(),
                        chance
                );

                Mobdrops.getInstance()
                        .getDropManager()
                        .addDrop(type, drop);

                p.getInventory().setItemInMainHand(null);

                p.sendMessage("§aDrop added with " + chance + "% chance!");

                new MobEditorMenu(p, type).open();
            }

            @Override
            public ItemStack getIcon() {
                ItemStack item = new ItemStack(Material.LIME_WOOL);
                ItemMeta meta = item.getItemMeta();

                if (meta != null) {
                    meta.setDisplayName("§a§lConfirm Drop");
                    meta.setLore(Collections.singletonList("§7Click to save this drop"));
                    item.setItemMeta(meta);
                }

                return item;
            }
        };
    }
}