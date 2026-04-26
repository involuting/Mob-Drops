package me.involuting.mobdrops.manager;

import me.involuting.mobdrops.Mobdrops;
import me.involuting.mobdrops.model.Drop;
import org.bukkit.entity.EntityType;

import java.util.*;

public class DropManager {

    private final Map<EntityType, List<Drop>> drops = new EnumMap<>(EntityType.class);

    public void addDrop(EntityType type, Drop drop) {
        drops.computeIfAbsent(type, k -> new ArrayList<>()).add(drop);
        save();
    }

    public void removeDrop(EntityType type, Drop drop) {
        List<Drop> list = drops.get(type);
        if (list != null) {
            list.remove(drop);

            if (list.isEmpty()) {
                drops.remove(type);
            }
        }
        save();
    }

    public List<Drop> getDrops(EntityType type) {
        return drops.getOrDefault(type, Collections.emptyList());
    }

    public Map<EntityType, List<Drop>> getAll() {
        return Collections.unmodifiableMap(drops);
    }

    public void clear() {
        drops.clear();
    }

    public void save() {
        Mobdrops.getInstance().getDropStorage().save(this);
    }

    // 🔥 IMPORTANT: used ONLY for loading (bypasses auto-save)
    public void addDropInternal(EntityType type, Drop drop) {
        drops.computeIfAbsent(type, k -> new ArrayList<>()).add(drop);
    }
}