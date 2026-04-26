package me.involuting.mobdrops.model;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class Drop {

    private final Material material;
    private String name;
    private List<String> lore;
    private final int amount;
    private double chance;

    public Drop(Material material, String name, List<String> lore, int amount, double chance) {
        this.material = material;
        this.name = name;
        this.lore = lore;
        this.amount = amount;
        this.chance = chance;
    }

    public Material getMaterial() { return material; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<String> getLore() { return lore; }
    public void setLore(List<String> lore) { this.lore = lore; }

    public int getAmount() { return amount; }

    public double getChance() { return chance; }

    public ItemStack buildItem() {
        ItemStack item = new ItemStack(material, amount);
        ItemMeta meta = item.getItemMeta();

        if (meta != null) {
            if (name != null) meta.setDisplayName(color(name));
            if (lore != null) meta.setLore(colorList(lore));
            item.setItemMeta(meta);
        }

        return item;
    }

    private String color(String s) {
        return s.replace("&", "§");
    }

    private List<String> colorList(List<String> list) {
        return list.stream()
                .map(s -> s.replace("&", "§"))
                .toList();
    }

    public void setChance(double chance) {
        this.chance = chance;
    }
}