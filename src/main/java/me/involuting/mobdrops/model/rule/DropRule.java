package me.involuting.mobdrops.model.rule;

import me.involuting.mobdrops.model.context.DropContext;

public class DropRule {

    private final String world;
    private final String region;
    private final double multiplier;

    public DropRule(String world, String region, double multiplier) {
        this.world = world;
        this.region = region;
        this.multiplier = multiplier;
    }

    public boolean matches(DropContext ctx) {

        if (world != null && !world.equalsIgnoreCase(ctx.getWorld().getName())) {
            return false;
        }

        if (region != null) {
            if (ctx.getRegion() == null) return false;
            if (!region.equalsIgnoreCase(ctx.getRegion())) return false;
        }

        return true;
    }

    public double getMultiplier() {
        return multiplier;
    }
}