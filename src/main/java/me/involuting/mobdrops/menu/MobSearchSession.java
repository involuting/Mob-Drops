package me.involuting.mobdrops.menu;

import me.involuting.mobdrops.Mobdrops;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

public class MobSearchSession implements Listener {

    private static final Map<UUID, Consumer<String>> sessions = new HashMap<>();

    public static void start(Player player, Consumer<String> callback) {
        sessions.put(player.getUniqueId(), callback);
    }

    public static void stop(Player player) {
        sessions.remove(player.getUniqueId());
    }

    public static boolean isSearching(Player player) {
        return sessions.containsKey(player.getUniqueId());
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {

        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();

        if (!sessions.containsKey(uuid)) return;

        event.setCancelled(true);

        String message = event.getMessage();

        Consumer<String> callback = sessions.remove(uuid);

        if (callback != null) {
            player.getServer().getScheduler().runTask(
                    Mobdrops.getInstance(),
                    () -> callback.accept(message)
            );
        }
    }
}