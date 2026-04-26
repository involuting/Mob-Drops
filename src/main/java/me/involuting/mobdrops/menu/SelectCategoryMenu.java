package me.involuting.mobdrops.menu;

import me.involuting.mobdrops.mob.category.MobCategory;
import net.j4c0b3y.api.menu.MenuSize;
import net.j4c0b3y.api.menu.button.Button;
import net.j4c0b3y.api.menu.button.ButtonClick;
import net.j4c0b3y.api.menu.layer.impl.BackgroundLayer;
import net.j4c0b3y.api.menu.layer.impl.ForegroundLayer;
import net.j4c0b3y.api.menu.Menu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class SelectCategoryMenu extends Menu {

    private final String search;

    public SelectCategoryMenu(Player player, String search) {
        super("Select Category", MenuSize.THREE, player);
        this.search = search == null ? "" : search;
    }

    @Override
    public void setup(BackgroundLayer background, ForegroundLayer foreground) {

        background.fill(new PlaceholderButton());

        int slot = 10;

        for (MobCategory category : MobCategory.values()) {

            foreground.set(slot++, new Button() {

                @Override
                public void onClick(ButtonClick click) {
                    Player player = click.getMenu().getPlayer();
                    new SelectMobMenu(player, category, search).open();
                }

                @Override
                public ItemStack getIcon() {
                    ItemStack item = new ItemStack(Material.NETHER_STAR);
                    ItemMeta meta = item.getItemMeta();

                    if (meta != null) {
                        meta.setDisplayName(category.getColor() + category.name());

                        meta.setLore(Arrays.asList(
                                "§7Click to select",
                                "§7Mobs: §f" + category.getDisplay()
                        ));

                        item.setItemMeta(meta);
                    }

                    return item;
                }
            });
        }
    }
}