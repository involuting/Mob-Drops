package me.involuting.mobdrops.menu.button;



import me.involuting.mobdrops.chance.ChanceEditorMenu;
import net.j4c0b3y.api.menu.button.Button;
import net.j4c0b3y.api.menu.button.ButtonClick;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class AddDropEditorButton extends Button {


    private final EntityType type;

    public AddDropEditorButton(EntityType type) {
        this.type = type;
    }

    @Override
    public void onClick(ButtonClick click) {
        new ChanceEditorMenu(click.getMenu().getPlayer(), type).open();
    }

    @Override
    public ItemStack getIcon() {
        ItemStack item = new ItemStack(Material.CHEST);

        ItemMeta meta = item.getItemMeta();
        if (meta == null) return item;

        meta.setDisplayName("§aAdd Drop");
        meta.setLore(Arrays.asList("§7Click to add new mob drop"));

        item.setItemMeta(meta);
        return item;
    }
}