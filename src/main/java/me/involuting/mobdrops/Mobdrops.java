package me.involuting.mobdrops;

import lombok.Getter;
import me.involuting.mobdrops.command.MobdropCommand;
import me.involuting.mobdrops.listener.mob.MobDropListener;
import me.involuting.mobdrops.listener.player.PlayerListener;
import me.involuting.mobdrops.manager.DropManager;
import me.involuting.mobdrops.storage.DropStorage;
import net.j4c0b3y.api.menu.MenuHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class Mobdrops extends JavaPlugin {

    @Getter
    private static Mobdrops instance;

    private DropManager dropManager;
    private DropStorage dropStorage;
    private MenuHandler menuHandler;

    @Override
    public void onEnable() {

        instance = this;
        menuHandler = new MenuHandler(this);


        saveDefaultConfig();

        this.dropManager = new DropManager();
        this.dropStorage = new DropStorage(this);

        Bukkit.getPluginManager().registerEvents(new MobDropListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);

        dropStorage.load(dropManager);

        getCommand("mobdrop").setExecutor(new MobdropCommand());

        getLogger().info("Mobdrops Enabled Successfully.");
    }

    @Override
    public void onDisable() {

        dropStorage.save(dropManager);

        getLogger().info("Mobdrops Saved & Disabled");
    }
}