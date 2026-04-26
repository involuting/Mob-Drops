package me.involuting.mobdrops.listener.mob;

import me.involuting.mobdrops.Mobdrops;
import me.involuting.mobdrops.model.Drop;
import me.involuting.mobdrops.model.context.DropContext;
import me.involuting.mobdrops.model.engine.MobDropEngine;
import me.involuting.mobdrops.model.rarity.effect.RarityEffect;
import me.involuting.mobdrops.model.service.DropService;
import me.involuting.mobdrops.model.weight.WeightedLoot;
import me.involuting.mobdrops.util.RegionUtil;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.List;
import java.util.Random;

public class MobDropListener implements Listener {

    private final DropService dropService;

    private final Random random = new Random();

    public MobDropListener(DropService dropService) {
        this.dropService = dropService;
    }

    @EventHandler
    public void onMobKill(EntityDeathEvent event) {

        if (event.getEntity().getKiller() == null) return;

        Player player = event.getEntity().getKiller();
        EntityType type = event.getEntityType();

        World world = player.getWorld();
        String region = RegionUtil.getRegion(player);

        DropContext context = new DropContext(player, type , world , region);

        dropService.handle(event, context);
    }
}