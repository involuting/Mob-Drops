package me.involuting.mobdrops.mob.category;

import org.bukkit.entity.EntityType;

import java.util.EnumMap;
import java.util.Map;
import java.util.Set;

public enum MobCategory {

    ALL("§7", "All Mobs", Set.of()),

    Hostile("§c", "Hostile", Set.of(
            EntityType.ZOMBIE,
            EntityType.ZOMBIE_VILLAGER,
            EntityType.HUSK,
            EntityType.DROWNED,
            EntityType.SKELETON,
            EntityType.STRAY,
            EntityType.CREEPER,
            EntityType.SPIDER,
            EntityType.CAVE_SPIDER,
            EntityType.ENDERMAN,
            EntityType.WITCH,
            EntityType.SLIME,
            EntityType.MAGMA_CUBE,
            EntityType.BLAZE,
            EntityType.GHAST,
            EntityType.PIGLIN,
            EntityType.PIGLIN_BRUTE,
            EntityType.ZOMBIFIED_PIGLIN,
            EntityType.HOGLIN,
            EntityType.ZOGLIN,
            EntityType.SHULKER,
            EntityType.VINDICATOR,
            EntityType.EVOKER,
            EntityType.PILLAGER,
            EntityType.RAVAGER
    )),

    Passive("§a", "Passive", Set.of(
            EntityType.COW,
            EntityType.SHEEP,
            EntityType.PIG,
            EntityType.CHICKEN,
            EntityType.RABBIT,
            EntityType.HORSE,
            EntityType.DONKEY,
            EntityType.MULE,
            EntityType.VILLAGER,
            EntityType.WANDERING_TRADER,
            EntityType.CAT,
            EntityType.FOX,
            EntityType.WOLF,
            EntityType.OCELOT,
            EntityType.PARROT,
            EntityType.LLAMA,
            EntityType.TRADER_LLAMA,
            EntityType.PANDA
    )),

    Aquatic("§b", "Aquatic", Set.of(
            EntityType.SQUID,
            EntityType.GLOW_SQUID,
            EntityType.DOLPHIN,
            EntityType.TURTLE,
            EntityType.COD,
            EntityType.SALMON,
            EntityType.PUFFERFISH,
            EntityType.TROPICAL_FISH,
            EntityType.AXOLOTL,
            EntityType.GUARDIAN,
            EntityType.ELDER_GUARDIAN
    )),

    Bosses("§5", "Boss", Set.of(
            EntityType.ENDER_DRAGON,
            EntityType.WITHER,
            EntityType.ELDER_GUARDIAN,
            EntityType.RAVAGER
    ));

    private final String color;
    private final String display;
    private final Set<EntityType> types;

    private static final Map<EntityType, MobCategory> LOOKUP = new EnumMap<>(EntityType.class);

    static {
        for (MobCategory category : values()) {
            for (EntityType type : category.types) {
                LOOKUP.put(type, category);
            }
        }
    }

    MobCategory(String color, String display, Set<EntityType> types) {
        this.color = color;
        this.display = display;
        this.types = types;
    }

    public String getColor() {
        return color;
    }

    public String getDisplay() {
        return display;
    }

    public String getColoredDisplay() {
        return color + display;
    }

    public boolean contains(EntityType type) {
        return this == ALL || types.contains(type);
    }

    public static MobCategory from(EntityType type) {
        return LOOKUP.getOrDefault(type, Hostile);
    }
}