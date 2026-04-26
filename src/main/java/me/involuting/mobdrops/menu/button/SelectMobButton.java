package me.involuting.mobdrops.menu.button;

import me.involuting.mobdrops.menu.MobEditorMenu;
import me.involuting.mobdrops.mob.MobRegistry;
import me.involuting.mobdrops.mob.category.MobCategory;
import net.j4c0b3y.api.menu.button.Button;
import net.j4c0b3y.api.menu.button.ButtonClick;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class SelectMobButton extends Button {

    private final EntityType type;

    public SelectMobButton(EntityType type) {
        this.type = type;
    }

    @Override
    public void onClick(ButtonClick click) {
        new MobEditorMenu(click.getMenu().getPlayer(), type).open();
    }

    @Override
    public ItemStack getIcon() {

        ItemStack item = MobRegistry.getIcon(type);
        ItemMeta meta = item.getItemMeta();

        if (meta == null) return item;

        MobCategory category = MobRegistry.getCategory(type);

       
        meta.setDisplayName(category.getColor() + format(type));

  
        meta.setLore(Arrays.asList(
                "§8Category: " + category.getDisplay(),
                "",
                "§7Click to select this mob",
                "§7for drop configuration"
        ));

        item.setItemMeta(meta);

        
        if (category == MobCategory.Bosses) {
            item = addGlow(item);
        }

        return item;
    }

    private ItemStack addGlow(ItemStack item) {
        ItemMeta meta = item.getItemMeta();

        meta.addEnchant(Enchantment.UNBREAKING, 1, true);
        meta.addItemFlags(org.bukkit.inventory.ItemFlag.HIDE_ENCHANTS);

        item.setItemMeta(meta);
        return item;
    }

    private String format(EntityType type) {
        String name = type.name().toLowerCase().replace("_", " ");
        return Character.toUpperCase(name.charAt(0)) + name.substring(1);
    }
}