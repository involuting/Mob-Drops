package me.involuting.mobdrops.menu;

import me.involuting.mobdrops.Mobdrops;
import me.involuting.mobdrops.model.Drop;
import net.j4c0b3y.api.menu.MenuSize;
import net.j4c0b3y.api.menu.button.Button;
import net.j4c0b3y.api.menu.layer.impl.BackgroundLayer;
import net.j4c0b3y.api.menu.layer.impl.ForegroundLayer;
import net.j4c0b3y.api.menu.pagination.PaginatedMenu;
import net.j4c0b3y.api.menu.pagination.PaginationSlot;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class DropPreviewMenu extends PaginatedMenu {

    private final EntityType type;

    public DropPreviewMenu(Player player, EntityType type) {
        super("Drop Preview: " + type.name(), MenuSize.SIX, player);
        this.type = type;
    }

    @Override
    public void setup(BackgroundLayer background, ForegroundLayer foreground) {

        background.fill(new PlaceholderButton());

        foreground.center(new PaginationSlot(this));
    }

    @Override
    public List<Button> getEntries() {

        List<Drop> drops = Mobdrops.getInstance()
                .getDropManager()
                .getGlobalDrops(type);

        return drops.stream()
                .map(DropPreviewButton::new)
                .map(b -> (Button) b)
                .toList();
    }


    private static class DropPreviewButton extends Button {

        private final Drop drop;

        public DropPreviewButton(Drop drop) {
            this.drop = drop;
        }

        @Override
        public void onClick(net.j4c0b3y.api.menu.button.ButtonClick click) {

        }

        @Override
        public ItemStack getIcon() {

            ItemStack item = drop.buildItem();
            ItemMeta meta = item.getItemMeta();

            if (meta != null) {

                meta.setLore(List.of(
                        "§7Chance: §a" + drop.getChance() + "%",
                        "§7Rarity: " + drop.getRarity().getColor() + drop.getRarity().name(),
                        "",
                        "§8Preview Only"
                ));

                item.setItemMeta(meta);
            }

            return item;
        }
    }
}