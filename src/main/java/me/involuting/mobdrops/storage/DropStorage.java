package me.involuting.mobdrops.storage;

import me.involuting.mobdrops.Mobdrops;
import me.involuting.mobdrops.manager.DropManager;
import me.involuting.mobdrops.model.Drop;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;

import java.util.*;

public class DropStorage {

    private final Mobdrops plugin;

    public DropStorage(Mobdrops plugin) {
        this.plugin = plugin;
    }


    public void load(DropManager manager) {

        FileConfiguration config = plugin.getConfig();

        if (!config.contains("mobs")) return;

        var section = config.getConfigurationSection("mobs");
        if (section == null) return;

        for (String mob : section.getKeys(false)) {

            EntityType type;

            try {
                type = EntityType.valueOf(mob);
            } catch (Exception e) {
                continue;
            }

            List<Map<?, ?>> list = config.getMapList("mobs." + mob + ".drops");

            for (Map<?, ?> map : list) {

                String materialName = (String) map.get("material");
                String name = (String) map.get("name");
                List<String> lore = (List<String>) map.get("lore");

                Number amountObj = (Number) map.get("amount");
                Number chanceObj = (Number) map.get("chance");

                if (materialName == null || amountObj == null || chanceObj == null) continue;

                try {
                    org.bukkit.Material material = org.bukkit.Material.valueOf(materialName);

                    double chance = chanceObj.doubleValue();
                    int amount = amountObj.intValue();

                    manager.addDropInternal(
                            type,
                            new Drop(material, name, lore, amount, chance)
                    );

                } catch (Exception ignored) {}
            }
        }
    }


    public void save(DropManager manager) {

        // ❌ DO NOT run async (Bukkit config is NOT thread-safe)
        FileConfiguration config = plugin.getConfig();
        config.set("mobs", null);

        for (Map.Entry<EntityType, List<Drop>> entry : manager.getAll().entrySet()) {

            String mob = entry.getKey().name();

            List<Map<String, Object>> list = new ArrayList<>();

            for (Drop drop : entry.getValue()) {

                Map<String, Object> map = new HashMap<>();

                map.put("material", drop.getMaterial().name());
                map.put("name", drop.getName());
                map.put("lore", drop.getLore());
                map.put("amount", drop.getAmount());
                map.put("chance", drop.getChance());

                list.add(map);
            }

            config.set("mobs." + mob + ".drops", list);
        }

        plugin.saveConfig();
    }
}