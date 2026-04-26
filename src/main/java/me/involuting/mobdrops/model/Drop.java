package me.involuting.mobdrops.model;

import me.involuting.mobdrops.model.rarity.Rarity;
import org.bukkit.ChatColor;
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
    private Rarity rarity;

    public Drop(Material material, String name, List<String> lore, int amount, double chance) {
        this(material, name, lore, amount, chance, Rarity.COMMON);
    }

    public Drop(Material material, String name, List<String> lore, int amount, double chance, Rarity rarity) {
        this.material = material;
        this.name = name;
        this.lore = lore;
        this.amount = amount;
        this.chance = chance;
        this.rarity = rarity;
    }

    public Material getMaterial() {
        return material;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getLore() {
        return lore;
    }

    public void setLore(List<String> lore) {
        this.lore = lore;
    }

    public int getAmount() {
        return amount;
    }

    public double getChance() {
        return chance;
    }

    public void setChance(double chance) {
        this.chance = chance;
    }

    public Rarity getRarity() {
        return rarity;
    }

    public void setRarity(Rarity rarity) {
        this.rarity = rarity;
    }


    public ItemStack buildItem() {

        ItemStack item = new ItemStack(material, amount);
        ItemMeta meta = item.getItemMeta();

        if (meta != null) {

            String prefix = (rarity != null)
                    ? rarity.getColor() + rarity.name() + " §8» §r"
                    : "";

            if (name != null) {
                meta.setDisplayName(prefix + color(name));
            }

            if (lore != null) {
                meta.setLore(colorList(lore));
            }

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
}