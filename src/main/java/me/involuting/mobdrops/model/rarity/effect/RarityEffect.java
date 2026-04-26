package me.involuting.mobdrops.model.rarity.effect;

import me.involuting.mobdrops.model.rarity.Rarity;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class RarityEffect {
    public static void play(Rarity rarity, Location loc, Player viewer) {

        if (rarity == null) return;
        if (rarity.getParticle() != null) {
            loc.getWorld().spawnParticle(
                    rarity.getParticle(),
                    loc.clone().add(0, 1, 0),
                    15,
                    0.3, 0.3, 0.3,
                    0.05
            );
        }

        if (rarity.getSound() != null) {
            viewer.playSound(loc, rarity.getSound(), 1f, 1f);
        }


        if (rarity.shouldAnnounce()) {
            Bukkit.broadcastMessage(
                    rarity.getColor() + "★ " + rarity.name() + " Drop ★ " +
                            "§7from §eMob Drops"
            );
        }
    }
}

