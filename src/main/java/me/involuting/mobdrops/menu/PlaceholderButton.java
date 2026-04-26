package me.involuting.mobdrops.menu;

import net.j4c0b3y.api.menu.button.Button;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PlaceholderButton extends Button {
    @Override
    public ItemStack getIcon() {
        ItemStack item = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        item.setDurability((short)0);

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("");

        item.setItemMeta(meta);
        return item;
    }
}
