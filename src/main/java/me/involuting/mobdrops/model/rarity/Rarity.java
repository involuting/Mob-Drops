package me.involuting.mobdrops.model.rarity;

import org.bukkit.ChatColor;
import org.bukkit.Particle;
import org.bukkit.Sound;

public enum Rarity {

        COMMON(ChatColor.WHITE, 60, null, null, false),
        UNCOMMON(ChatColor.GREEN, 25, Particle.HAPPY_VILLAGER, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, false),
        RARE(ChatColor.AQUA, 10, Particle.ENCHANTED_HIT, Sound.ENTITY_PLAYER_LEVELUP, true),
        EPIC(ChatColor.LIGHT_PURPLE, 4, Particle.PORTAL, Sound.ENTITY_ENDER_DRAGON_GROWL, true),
        LEGENDARY(ChatColor.GOLD, 1, Particle.FIREWORK, Sound.UI_TOAST_CHALLENGE_COMPLETE, true);

        private final ChatColor color;
        private final int weight;
        private final Particle particle;
        private final Sound sound;
        private final boolean announce;

        Rarity(ChatColor color, int weight, Particle particle, Sound sound, boolean announce) {
            this.color = color;
            this.weight = weight;
            this.particle = particle;
            this.sound = sound;
            this.announce = announce;
        }

        public ChatColor getColor() {
            return color;
        }

        public int getWeight() {
            return weight;
        }

        public Particle getParticle() {
            return particle;
        }

        public Sound getSound() {
            return sound;
        }

        public boolean shouldAnnounce() {
            return announce;
        }
    }

