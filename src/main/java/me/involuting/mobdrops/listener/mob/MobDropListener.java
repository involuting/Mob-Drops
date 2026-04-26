package me.involuting.mobdrops.listener.mob;

import me.involuting.mobdrops.Mobdrops;
import me.involuting.mobdrops.model.Drop;
import me.involuting.mobdrops.model.rarity.effect.RarityEffect;
import me.involuting.mobdrops.model.weight.WeightedLoot;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
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

        if (drops == null || drops.isEmpty()) return;

        Drop chosen = WeightedLoot.pick(drops);

        if (chosen == null) return;

        event.getDrops().add(chosen.buildItem());

        Player killer = event.getEntity().getKiller();

        if (killer != null) {
            RarityEffect.play(
                    chosen.getRarity(),
                    event.getEntity().getLocation(),
                    killer
            );
        }
    }
}