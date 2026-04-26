package me.involuting.mobdrops.menu;

import me.involuting.mobdrops.Mobdrops;
import me.involuting.mobdrops.manager.DropManager;
import me.involuting.mobdrops.model.Drop;
import me.involuting.mobdrops.session.ChatInputSession;
import net.j4c0b3y.api.menu.MenuSize;
import net.j4c0b3y.api.menu.button.Button;
import net.j4c0b3y.api.menu.button.ButtonClick;
import net.j4c0b3y.api.menu.layer.impl.BackgroundLayer;
import net.j4c0b3y.api.menu.layer.impl.ForegroundLayer;
import net.j4c0b3y.api.menu.pagination.PaginatedMenu;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class DropEditMenu extends PaginatedMenu {

    private final EntityType type;
    private final Drop drop;

    public DropEditMenu(Player player, EntityType type, Drop drop) {
        super("Edit Drop", MenuSize.THREE, player);
        this.type = type;
        this.drop = drop;
    }

    @Override
    public void setup(BackgroundLayer background, ForegroundLayer foreground) {

        background.fill(new PlaceholderButton());


        foreground.set(18, new Button() {
            @Override
            public ItemStack getIcon() {
                return new ItemStack(Material.ARROW);
            }

            @Override
            public void onClick(ButtonClick click) {
                new MobEditorMenu(click.getMenu().getPlayer(), type).open();
            }
        });


        foreground.set(10, new Button() {
            @Override
            public ItemStack getIcon() {
                return createIcon(Material.NAME_TAG,
                        "§eEdit Name",
                        "§7Click to change display name",
                        "§7Supports & color codes");
            }

            @Override
            public void onClick(ButtonClick click) {
                Player player = click.getMenu().getPlayer();
                player.sendMessage("§eType the new item name in chat (use & for colors).");

                ChatInputSession.start(player, input -> {
                    drop.setName(input.replace("&", "§"));
                    Mobdrops.getInstance().getDropStorage().save(Mobdrops.getInstance().getDropManager());
                    new DropEditMenu(player, type, drop).open();
                });

                player.closeInventory();
            }
        });


        foreground.set(12, new Button() {
            @Override
            public ItemStack getIcon() {
                return createIcon(Material.BOOK,
                        "§bEdit Lore",
                        "§7Use | to separate lines",
                        "§7Supports & color codes");
            }

            @Override
            public void onClick(ButtonClick click) {
                Player player = click.getMenu().getPlayer();
                player.sendMessage("§eType lore lines separated by |");

                ChatInputSession.start(player, input -> {

                    List<String> lore = Arrays.stream(input.split("\\|"))
                            .map(s -> s.replace("&", "§"))
                            .toList();

                    drop.setLore(lore);

                    Mobdrops.getInstance().getDropStorage().save(Mobdrops.getInstance().getDropManager());
                    new DropEditMenu(player, type, drop).open();
                });

                player.closeInventory();
            }
        });


        foreground.set(14, new Button() {
            @Override
            public ItemStack getIcon() {
                return createIcon(Material.GOLD_INGOT,
                        "§aEdit Chance",
                        "§7Current: §e" + drop.getChance() + "%",
                        "§7Click to change");
            }

            @Override
            public void onClick(ButtonClick click) {
                Player player = click.getMenu().getPlayer();
                player.sendMessage("§eType new chance (0-100)");

                ChatInputSession.start(player, input -> {

                    try {
                        double chance = Double.parseDouble(input);
                        drop.setChance(chance);

                        Mobdrops.getInstance().getDropStorage().save(Mobdrops.getInstance().getDropManager());

                        new DropEditMenu(player, type, drop).open();

                    } catch (NumberFormatException e) {
                        player.sendMessage("§cInvalid number.");
                        new DropEditMenu(player, type, drop).open();
                    }
                });

                player.closeInventory();
            }
        });


        foreground.set(16, new Button() {
            @Override
            public ItemStack getIcon() {
                return drop.buildItem();
            }

            @Override
            public void onClick(ButtonClick click) {
                click.getMenu().getPlayer().sendMessage("§7This is a preview item");
            }
        });
    }

    @Override
    public List<Button> getEntries() {
        return List.of();
    }

    private ItemStack createIcon(Material mat, String name, String... lore) {
        ItemStack item = new ItemStack(mat);
        ItemMeta meta = item.getItemMeta();

        if (meta != null) {
            meta.setDisplayName(name);
            meta.setLore(Arrays.asList(lore));
            item.setItemMeta(meta);
        }

        return item;
    }
}