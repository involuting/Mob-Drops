package me.involuting.mobdrops.util;

import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class ItemSerializer {

    public static String serialize(ItemStack item) {
        return item.serialize().toString();
    }

    public static ItemStack deserialize(String data) {
        return ItemStack.deserialize(ConfigurationSerialization.deserializeObject(
                Map.of()
        ).serialize());
    }
}