package me.involuting.mobdrops.session;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

public class ChatInputSession {
    private static final Map<UUID, Consumer<String>> sessions = new HashMap<>();

    public static void start(Player player, Consumer<String> callback) {
        sessions.put(player.getUniqueId(), callback);
    }

    public static void handle(Player player, String message) {
        Consumer<String> consumer = sessions.remove(player.getUniqueId());
        if (consumer != null) {
            consumer.accept(message);
        }
    }

    public static boolean isWaiting(Player player) {
        return sessions.containsKey(player.getUniqueId());
    }
}

