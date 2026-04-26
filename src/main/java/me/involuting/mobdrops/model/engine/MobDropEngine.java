package me.involuting.mobdrops.model.engine;

import me.involuting.mobdrops.manager.DropManager;
import me.involuting.mobdrops.model.Drop;
import me.involuting.mobdrops.model.context.DropContext;
import org.bukkit.World;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class MobDropEngine {

    private final DropManager manager;

    public MobDropEngine(DropManager manager) {
        this.manager = manager;
    }

    public void handleDrops(DropContext ctx, EntityDeathEvent event) {

        World world = ctx.getWorld();
        List<Drop> drops = manager.getRegionDrops(world.getName() , "default", ctx.getMobType());

        if (drops == null || drops.isEmpty()) return;

        double multiplier = DropRuleEngine.getMultiplier(ctx);
        ThreadLocalRandom random = ThreadLocalRandom.current();

        var location = event.getEntity().getLocation();

        for (Drop drop : drops) {

            double chance = drop.getChance() * multiplier;

            if (chance <= 0) continue;

            if (chance > 1.0) chance = 1.0;

            if (random.nextDouble() > chance) continue;

            ItemStack item = drop.buildItem();

            world.dropItemNaturally(location, item);
        }
    }
}