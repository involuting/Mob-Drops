package me.involuting.mobdrops.storage;

import me.involuting.mobdrops.Mobdrops;
import me.involuting.mobdrops.manager.DropManager;
import me.involuting.mobdrops.model.Drop;
import me.involuting.mobdrops.model.rarity.Rarity;
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


        manager.clear();

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

                List<String> lore = map.get("lore") instanceof List<?> rawList
                        ? rawList.stream().map(Object::toString).toList()
                        : new ArrayList<>();

                Number amountObj = (Number) map.get("amount");
                Number chanceObj = (Number) map.get("chance");

                String rarityStr = (String) map.get("rarity");

                if (materialName == null || amountObj == null || chanceObj == null) continue;

                try {
                    org.bukkit.Material material = org.bukkit.Material.valueOf(materialName);

                    double chance = chanceObj.doubleValue();
                    int amount = amountObj.intValue();

                    Rarity rarity = rarityStr != null
                            ? Rarity.valueOf(rarityStr)
                            : Rarity.COMMON;

                    manager.addDrop(
                            type,
                            new Drop(material, name, lore, amount, chance, rarity)
                    );

                } catch (Exception ignored) {}
            }
        }
    }


    public void save(DropManager manager) {

        FileConfiguration config = plugin.getConfig();

        config.set("mobs", null);

        for (Map.Entry<EntityType, List<Drop>> entry : manager.getAllGlobal().entrySet()) {

            String mob = entry.getKey().name();

            List<Map<String, Object>> list = new ArrayList<>();

            for (Drop drop : entry.getValue()) {

                Map<String, Object> map = new HashMap<>();

                map.put("material", drop.getMaterial().name());
                map.put("name", drop.getName());
                map.put("lore", drop.getLore());
                map.put("amount", drop.getAmount());
                map.put("chance", drop.getChance());
                map.put("rarity", drop.getRarity().name());

                list.add(map);
            }

            config.set("mobs." + mob + ".drops", list);
        }

        plugin.saveConfig();
    }


    public void reload(DropManager manager) {
        plugin.reloadConfig();
        load(manager);
    }
}