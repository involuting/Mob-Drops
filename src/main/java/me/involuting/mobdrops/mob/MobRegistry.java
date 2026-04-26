package me.involuting.mobdrops.mob;


import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class MobRegistry {

    private static final Map<EntityType, MobIcon> mobs = new HashMap<>();

    public static ItemStack getIcon(EntityType type) {

        Material mat = switch (type) {
            case BLAZE -> Material.BLAZE_POWDER;
            case ZOMBIE -> Material.ZOMBIE_HEAD;
            case SKELETON -> Material.SKELETON_SKULL;
            case CREEPER -> Material.CREEPER_HEAD;
            case SPIDER -> Material.SPIDER_EYE;
            case ENDERMAN -> Material.ENDER_PEARL;
            case IRON_GOLEM -> Material.IRON_INGOT;
            case VILLAGER -> Material.EMERALD;
            default -> Material.BARRIER;
        };

        return new ItemStack(mat);
    }

    public static void register(EntityType type, Material icon) {
        mobs.put(type, new MobIcon(type, icon));
    }

    public static MobIcon get(EntityType type) {
        return mobs.getOrDefault(type, new MobIcon(type, Material.BARRIER));
    }

    public static Collection<MobIcon> getAll() {
        return mobs.values();
    }


}