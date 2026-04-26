package me.involuting.mobdrops.session;

import me.involuting.mobdrops.model.Drop;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DropEditSession {

    private static final Map<UUID, Drop> editing = new HashMap<>();
    private static final Map<UUID, EditType> type = new HashMap<>();

    public enum EditType {
        NAME,
        LORE
    }

    public static void start(Player player, Drop drop, EditType editType) {
        editing.put(player.getUniqueId(), drop);
        type.put(player.getUniqueId(), editType);
    }

    public static Drop getDrop(Player player) {
        return editing.get(player.getUniqueId());
    }

    public static EditType getType(Player player) {
        return type.get(player.getUniqueId());
    }

    public static void end(Player player) {
        editing.remove(player.getUniqueId());
        type.remove(player.getUniqueId());
    }

    public static boolean isEditing(Player player) {
        return editing.containsKey(player.getUniqueId());
    }
}