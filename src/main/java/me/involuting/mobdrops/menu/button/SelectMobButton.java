package me.involuting.mobdrops.menu.button;

import me.involuting.mobdrops.menu.MobEditorMenu;
import net.j4c0b3y.api.menu.button.Button;
import net.j4c0b3y.api.menu.button.ButtonClick;
import org.bukkit.Material;
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

        Material material;

        switch (type) {
            case BLAZE -> material = Material.BLAZE_POWDER;
            case ZOMBIE -> material = Material.ZOMBIE_HEAD;
            case SKELETON -> material = Material.SKELETON_SKULL;
            case CREEPER -> material = Material.CREEPER_HEAD;
            case SPIDER -> material = Material.SPIDER_EYE;
            case ENDERMAN -> material = Material.ENDER_PEARL;
            case IRON_GOLEM -> material = Material.IRON_INGOT;
            case VILLAGER -> material = Material.EMERALD;
            default -> material = Material.EGG;
        }

        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName("§e" + type.name());

        meta.setLore(Arrays.asList(
                "§7Click to select",
                "§7this mob for drops"
        ));

        item.setItemMeta(meta);

        return item;
    }
}