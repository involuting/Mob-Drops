package me.involuting.mobdrops.menu;


import me.involuting.mobdrops.Mobdrops;

import me.involuting.mobdrops.menu.button.AddDropEditorButton;
import me.involuting.mobdrops.menu.button.DropEditButton;
import me.involuting.mobdrops.model.Drop;
import net.j4c0b3y.api.menu.Menu;
import net.j4c0b3y.api.menu.MenuSize;
import net.j4c0b3y.api.menu.layer.impl.BackgroundLayer;
import net.j4c0b3y.api.menu.layer.impl.ForegroundLayer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.List;

public class MobEditorMenu extends Menu {

    private final EntityType type;

    public MobEditorMenu(Player player, EntityType type) {
        super("Editor: " + type.name(), MenuSize.SIX , player);
        this.type = type;
    }

    @Override
    public void setup(BackgroundLayer background, ForegroundLayer foreground) {

        background.fill(new PlaceholderButton());

        List<Drop> drops = Mobdrops.getInstance()
                .getDropManager()
                .getGlobalDrops(type);

        int slot = 10;

        for (Drop drop : drops) {
            if (slot >= 26) break;

            foreground.set(slot++, new DropEditButton(type, drop));
        }


        foreground.set(49, new AddDropEditorButton(type));
    }
}