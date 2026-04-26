package me.involuting.mobdrops.mob;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;

public class MobIcon {

    private final EntityType type;
    private final Material icon;

    public MobIcon(EntityType type, Material icon) {
        this.type = type;
        this.icon = icon;
    }

    public EntityType getType() {
        return type;
    }

    public Material getIcon() {
        return icon;
    }
}