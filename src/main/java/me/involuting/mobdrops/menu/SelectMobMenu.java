package me.involuting.mobdrops.menu;

import me.involuting.mobdrops.mob.MobRegistry;
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
import java.util.stream.Collectors;

public class SelectMobMenu extends PaginatedMenu {

    public SelectMobMenu(Player player) {
        super("Select Mob", MenuSize.SIX, player);
    }

    @Override
    public void setup(BackgroundLayer background, ForegroundLayer foreground) {
        background.fill(new PlaceholderButton());


        foreground.center(new PaginationSlot(this));
    }

    @Override
    public List<Button> getEntries() {
        return Arrays.stream(EntityType.values())
                .filter(EntityType::isSpawnable)
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
            if (item == null) item = new ItemStack(Material.BARRIER);

            ItemMeta meta = item.getItemMeta();
            if (meta == null) return item;

            meta.setDisplayName("§e" + format(type));
            meta.setLore(Arrays.asList(
                    "§7Click to select",
                    "§7this mob for drops"
            ));

            item.setItemMeta(meta);
            return item;
        }

        private String format(EntityType type) {
            String name = type.name().toLowerCase().replace("_", " ");
            return Character.toUpperCase(name.charAt(0)) + name.substring(1);
        }
    }
}