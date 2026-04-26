package me.involuting.mobdrops.mob;

import me.involuting.mobdrops.mob.category.MobCategory;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import java.util.EnumMap;
import java.util.Map;

public class MobRegistry {

    private static final Map<EntityType, Material> ICONS = new EnumMap<>(EntityType.class);

    static {

        // Hostile mobs
        ICONS.put(EntityType.ZOMBIE, Material.ZOMBIE_SPAWN_EGG);
        ICONS.put(EntityType.SKELETON, Material.SKELETON_SPAWN_EGG);
        ICONS.put(EntityType.CREEPER, Material.CREEPER_SPAWN_EGG);
        ICONS.put(EntityType.SPIDER, Material.SPIDER_SPAWN_EGG);
        ICONS.put(EntityType.ENDERMAN, Material.ENDERMAN_SPAWN_EGG);
        ICONS.put(EntityType.BLAZE, Material.BLAZE_SPAWN_EGG);
        ICONS.put(EntityType.GHAST, Material.GHAST_SPAWN_EGG);
        ICONS.put(EntityType.WITCH, Material.WITCH_SPAWN_EGG);
        ICONS.put(EntityType.SLIME, Material.SLIME_SPAWN_EGG);
        ICONS.put(EntityType.MAGMA_CUBE, Material.MAGMA_CUBE_SPAWN_EGG);
        ICONS.put(EntityType.WITHER, Material.WITHER_SKELETON_SPAWN_EGG);
        ICONS.put(EntityType.ENDER_DRAGON, Material.DRAGON_EGG);

        // Passive mobs
        ICONS.put(EntityType.COW, Material.COW_SPAWN_EGG);
        ICONS.put(EntityType.SHEEP, Material.SHEEP_SPAWN_EGG);
        ICONS.put(EntityType.PIG, Material.PIG_SPAWN_EGG);
        ICONS.put(EntityType.CHICKEN, Material.CHICKEN_SPAWN_EGG);
        ICONS.put(EntityType.RABBIT, Material.RABBIT_SPAWN_EGG);
        ICONS.put(EntityType.HORSE, Material.HORSE_SPAWN_EGG);
        ICONS.put(EntityType.VILLAGER, Material.VILLAGER_SPAWN_EGG);
        ICONS.put(EntityType.IRON_GOLEM, Material.IRON_GOLEM_SPAWN_EGG);

        // Aquatic
        ICONS.put(EntityType.SQUID, Material.SQUID_SPAWN_EGG);
        ICONS.put(EntityType.GLOW_SQUID, Material.GLOW_SQUID_SPAWN_EGG);
        ICONS.put(EntityType.DOLPHIN, Material.DOLPHIN_SPAWN_EGG);
        ICONS.put(EntityType.TURTLE, Material.TURTLE_SPAWN_EGG);

        // Modern mobs (if available)
        try { ICONS.put(EntityType.PHANTOM, Material.PHANTOM_SPAWN_EGG); } catch (Exception ignored) {}
        try { ICONS.put(EntityType.PILLAGER, Material.PILLAGER_SPAWN_EGG); } catch (Exception ignored) {}
        try { ICONS.put(EntityType.RAVAGER, Material.RAVAGER_SPAWN_EGG); } catch (Exception ignored) {}
        try { ICONS.put(EntityType.ALLAY, Material.ALLAY_SPAWN_EGG); } catch (Exception ignored) {}
    }

    public static ItemStack getIcon(EntityType type) {
        Material mat = ICONS.get(type);
        if (mat == null) mat = fallback(type);
        return new ItemStack(mat);
    }

    private static Material fallback(EntityType type) {
        try {
            return Material.valueOf(type.name() + "_SPAWN_EGG");
        } catch (Exception ignored) {
            return Material.BARRIER;
        }
    }

    public static MobCategory getCategory(EntityType type) {

        switch (type) {

            case COW:
            case SHEEP:
            case PIG:
            case CHICKEN:
            case VILLAGER:
            case RABBIT:
                return MobCategory.Passive;

            case SQUID:
            case GLOW_SQUID:
            case DOLPHIN:
            case TURTLE:
                return MobCategory.Aquatic;

            case WITHER:
            case ENDER_DRAGON:
            case IRON_GOLEM:
                return MobCategory.Bosses;

            default:
                return MobCategory.Hostile;
        }
    }
}