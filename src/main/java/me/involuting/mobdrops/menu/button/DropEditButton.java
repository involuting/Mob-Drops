package me.involuting.mobdrops.menu.button;

import me.involuting.mobdrops.Mobdrops;
import me.involuting.mobdrops.manager.DropManager;
import me.involuting.mobdrops.menu.DropEditMenu;
import me.involuting.mobdrops.menu.MobEditorMenu;
import me.involuting.mobdrops.model.Drop;
import net.j4c0b3y.api.menu.button.Button;
import net.j4c0b3y.api.menu.button.ButtonClick;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class DropEditButton extends Button {

    private final EntityType type;
    private final Drop drop;

    public DropEditButton(EntityType type, Drop drop) {
        this.type = type;
        this.drop = drop;
    }

    @Override
    public void onClick(ButtonClick click) {

        Player player = click.getMenu().getPlayer();


        if (click.getType().isRightClick()) {
            new DropEditMenu(player, type, drop).open();
            return;
        }


        Mobdrops.getInstance()
                .getDropManager()
                .removeDrop(type, drop);

        player.sendMessage("§cRemoved the drop from" + type);
        player.closeInventory();

        new MobEditorMenu(player, type).open();
    }

    @Override
    public ItemStack getIcon() {

        ItemStack item = drop.buildItem(); // IMPORTANT: use builder
        ItemMeta meta = item.getItemMeta();

        if (meta == null) return item;

        List<String> lore = meta.hasLore()
                ? new ArrayList<>(meta.getLore())
                : new ArrayList<>();

        lore.add("");
        lore.add("§7Mob: §e" + type.name());
        lore.add("§7Chance: §a" + drop.getChance() + "%");
        lore.add("§cLeft-click: remove");
        lore.add("§eRight-click to edit name/lore");

        meta.setLore(lore);
        item.setItemMeta(meta);

        return item;
    }
}