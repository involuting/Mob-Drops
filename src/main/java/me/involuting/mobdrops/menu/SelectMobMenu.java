package me.involuting.mobdrops.menu;

import me.involuting.mobdrops.mob.MobRegistry;
import me.involuting.mobdrops.mob.category.MobCategory;
import net.j4c0b3y.api.menu.MenuSize;
import net.j4c0b3y.api.menu.button.Button;
import net.j4c0b3y.api.menu.button.ButtonClick;
import net.j4c0b3y.api.menu.layer.impl.BackgroundLayer;
import net.j4c0b3y.api.menu.layer.impl.ForegroundLayer;
import net.j4c0b3y.api.menu.pagination.PaginatedMenu;
import net.j4c0b3y.api.menu.pagination.PaginationSlot;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class SelectMobMenu extends PaginatedMenu {

    private final MobCategory category;
    private final String search;

    public SelectMobMenu(Player player, MobCategory category, String search) {
        super("Select " + (category == null ? "All Mobs" : category.getDisplay()), MenuSize.SIX, player);
        this.category = category == null ? MobCategory.ALL : category;
        this.search = search == null ? "" : search;
    }

    @Override
    public void setup(BackgroundLayer background, ForegroundLayer foreground) {

        background.fill(new PlaceholderButton());

        foreground.center(new PaginationSlot(this));


        foreground.set(53, new Button() {
            @Override
            public void onClick(ButtonClick click) {
                Player player = click.getMenu().getPlayer();
                new SelectCategoryMenu(player, search).open();
            }

            @Override
            public ItemStack getIcon() {
                ItemStack item = new ItemStack(Material.NETHER_STAR);
                ItemMeta meta = item.getItemMeta();

                if (meta != null) {
                    meta.setDisplayName("§6Select Category");
                    meta.setLore(Arrays.asList(
                            "§7Current: §6" + category.getDisplay(),
                            "§7Click to change category"
                    ));
                    item.setItemMeta(meta);
                }

                return item;
            }
        });


        foreground.set(45, new Button() {
            @Override
            public void onClick(ButtonClick click) {

                Player player = click.getMenu().getPlayer();

                player.closeInventory();
                player.sendMessage("§6Type a mob name in chat to search");

                MobSearchSession.start(player, input -> {
                    String newSearch = input == null ? "" : input.toLowerCase(Locale.ROOT);
                    new SelectMobMenu(player, category, newSearch).open();
                });
            }

            @Override
            public ItemStack getIcon() {
                ItemStack item = new ItemStack(Material.COMPASS);
                ItemMeta meta = item.getItemMeta();

                if (meta != null) {
                    meta.setDisplayName("§6Search Mobs");
                    meta.setLore(Arrays.asList(
                            "§7Click and type in chat",
                            "§7to filter mobs"
                    ));
                    item.setItemMeta(meta);
                }

                return item;
            }
        });


        foreground.set(48, new Button() {
            @Override
            public void onClick(ButtonClick click) {
                previousPage();
            }

            @Override
            public ItemStack getIcon() {
                ItemStack item = new ItemStack(Material.ARROW);
                ItemMeta meta = item.getItemMeta();

                if (meta != null) {
                    meta.setDisplayName("§6Previous Page");
                    meta.setLore(List.of("§7Go to the previous page"));
                    item.setItemMeta(meta);
                }

                return item;
            }
        });


        foreground.set(49, new Button() {
            @Override
            public void onClick(ButtonClick click) {}

            @Override
            public ItemStack getIcon() {
                ItemStack item = new ItemStack(Material.PAPER);
                ItemMeta meta = item.getItemMeta();

                if (meta != null) {

                    int entries = getEntries().size();
                    int perPage = 45;
                    int maxPages = Math.max(1, (int) Math.ceil((double) entries / perPage));

                    meta.setDisplayName("§6Page §f" + (getPage() + 1) + "§7/§f" + maxPages);
                    meta.setLore(Arrays.asList(
                            "§7Category: §6" + category.getDisplay(),
                            "§7Search: §6" + (search.isEmpty() ? "None" : search)
                    ));

                    item.setItemMeta(meta);
                }

                return item;
            }
        });


        foreground.set(50, new Button() {
            @Override
            public void onClick(ButtonClick click) {
                nextPage();
            }

            @Override
            public ItemStack getIcon() {
                ItemStack item = new ItemStack(Material.ARROW);
                ItemMeta meta = item.getItemMeta();

                if (meta != null) {
                    meta.setDisplayName("§6Next Page");
                    meta.setLore(List.of("§7Go to the next page"));
                    item.setItemMeta(meta);
                }

                return item;
            }
        });
    }

    @Override
    public List<Button> getEntries() {
        return Arrays.stream(EntityType.values())
                .filter(EntityType::isAlive)
                .filter(type -> type != EntityType.PLAYER)
                .filter(type -> type != EntityType.ARMOR_STAND)
                .filter(type -> type != EntityType.ITEM_FRAME)
                .filter(type -> type != EntityType.GLOW_ITEM_FRAME)
                .filter(type -> type != EntityType.PAINTING)
                .filter(type -> category == null || category.contains(type))
                .filter(type ->
                        search.isEmpty()
                                || type.name().toLowerCase(Locale.ROOT).contains(search)
                )
                .map(MobButton::new)
                .collect(Collectors.toList());
    }

    private static class MobButton extends Button {

        private final EntityType type;

        public MobButton(EntityType type) {
            this.type = type;
        }

        @Override
        public void onClick(ButtonClick click) {
            new MobEditorMenu(click.getMenu().getPlayer(), type).open();
        }

        @Override
        public ItemStack getIcon() {
            ItemStack item = MobRegistry.getIcon(type);

            if (item == null) {
                item = new ItemStack(Material.BARRIER);
            }

            ItemMeta meta = item.getItemMeta();

            if (meta != null) {
                meta.setDisplayName("§6" + format(type));
                meta.setLore(Arrays.asList(
                        "§7Click to select",
                        "§7this mob for drops"
                ));
                item.setItemMeta(meta);
            }

            return item;
        }

        private String format(EntityType type) {
            String name = type.name().toLowerCase().replace("_", " ");
            return Character.toUpperCase(name.charAt(0)) + name.substring(1);
        }
    }
}