package me.involuting.mobdrops.model.service;

import me.involuting.mobdrops.manager.DropManager;
import me.involuting.mobdrops.model.context.DropContext;
import me.involuting.mobdrops.model.engine.MobDropEngine;
import org.bukkit.event.entity.EntityDeathEvent;

public class DropService {

    private final DropManager manager;
    private final MobDropEngine engine;

    public DropService(DropManager manager) {
        this.manager = manager;
        this.engine = new MobDropEngine(manager);
    }

    public void handle(EntityDeathEvent event, DropContext ctx) {
        engine.handleDrops(ctx, event);
    }

    public DropManager getManager() {
        return manager;
    }
}
