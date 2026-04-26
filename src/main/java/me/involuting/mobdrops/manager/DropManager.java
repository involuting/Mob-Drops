package me.involuting.mobdrops.manager;

import me.involuting.mobdrops.model.Drop;
import org.bukkit.World;
import org.bukkit.entity.EntityType;

import java.util.*;

public class DropManager {

    private final Map<String, Map<String, Map<EntityType, List<Drop>>>> regionDrops = new HashMap<>();
    private final Map<EntityType, List<Drop>> globalDrops = new EnumMap<>(EntityType.class);



    public void addDrop(EntityType type, Drop drop) {
        globalDrops.computeIfAbsent(type, k -> new ArrayList<>()).add(drop);
    }

    public void removeDrop(EntityType type, Drop drop) {
        List<Drop> list = globalDrops.get(type);
        if (list == null) return;

        list.remove(drop);
        if (list.isEmpty()) globalDrops.remove(type);
    }

    public List<Drop> getGlobalDrops(EntityType type) {
        return globalDrops.getOrDefault(type, Collections.emptyList());
    }



    public void addRegionDrop(String world, String region, EntityType type, Drop drop) {
        regionDrops
                .computeIfAbsent(world, w -> new HashMap<>())
                .computeIfAbsent(region, r -> new HashMap<>())
                .computeIfAbsent(type, t -> new ArrayList<>())
                .add(drop);
    }

    public void removeRegionDrop(String world, String region, EntityType type, Drop drop) {

        Map<String, Map<EntityType, List<Drop>>> worldMap = regionDrops.get(world);
        if (worldMap == null) return;

        Map<EntityType, List<Drop>> regionMap = worldMap.get(region);
        if (regionMap == null) return;

        List<Drop> list = regionMap.get(type);
        if (list == null) return;

        list.remove(drop);

        if (list.isEmpty()) regionMap.remove(type);
        if (regionMap.isEmpty()) worldMap.remove(region);
        if (worldMap.isEmpty()) regionDrops.remove(world);
    }

    public List<Drop> getRegionDrops(String world, String region, EntityType type) {
        return regionDrops
                .getOrDefault(world, Collections.emptyMap())
                .getOrDefault(region, Collections.emptyMap())
                .getOrDefault(type, Collections.emptyList());
    }



    public List<Drop> resolveDrops(World world, String region, EntityType type) {

        List<Drop> regionList = getRegionDrops(world.getName(), region, type);
        if (!regionList.isEmpty()) {
            return regionList;
        }

        return getGlobalDrops(type);
    }

    public List<Drop> resolveDrops(World world, EntityType type) {
        return resolveDrops(world, "default", type);
    }



    public Map<EntityType, List<Drop>> getAllGlobal() {
        return Collections.unmodifiableMap(globalDrops);
    }

    public void clear() {
        globalDrops.clear();
        regionDrops.clear();
    }
}