package me.involuting.mobdrops.listener.player;

import me.involuting.mobdrops.Mobdrops;
import me.involuting.mobdrops.session.ChatInputSession;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerListener implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {

        Player player = event.getPlayer();

        if (!ChatInputSession.isWaiting(player)) return;

        event.setCancelled(true);

        String message = event.getMessage();

        Bukkit.getScheduler().runTask(Mobdrops.getInstance(), () -> {
            ChatInputSession.handle(player, message);
        });
    }
}
