package me.involuting.mobdrops;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
@Getter
public final class MobDrops extends JavaPlugin {
    private MobDrops instance;

    @Override
    public void onEnable() {
        instance = this;


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
