package me.involuting.mobdrops.listener.mob;

import me.involuting.mobdrops.Mobdrops;
import me.involuting.mobdrops.model.Drop;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.List;
import java.util.Random;

public class MobDropListener implements Listener {

    private final Random random = new Random();

    @EventHandler
    public void onKill(EntityDeathEvent event) {

        EntityType type = event.getEntityType();

        List<Drop> drops = Mobdrops.getInstance()
                .getDropManager()
                .getDrops(type);

        if (drops.isEmpty()) return;



        for (Drop drop : drops) {

            if (random.nextDouble(100) <= drop.getChance()) {
                event.getDrops().add(drop.buildItem());
            }
        }
    }
}