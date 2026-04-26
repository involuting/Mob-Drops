package me.involuting.mobdrops.model.context;

import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class DropContext {

    private final Player player;
    private final EntityType mobType;
    private final World world;
    private final String region;

    public DropContext(Player player, EntityType mobType, World world, String region) {
        this.player = player;
        this.mobType = mobType;
        this.world = world;
        this.region = region;
    }

    public Player getPlayer() {
        return player;
    }

    public EntityType getMobType() {
        return mobType;
    }

    public World getWorld() {
        return world;
    }

    public String getRegion() {
        return region;
    }
}