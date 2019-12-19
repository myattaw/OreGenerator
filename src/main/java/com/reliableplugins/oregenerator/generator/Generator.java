package com.reliableplugins.oregenerator.generator;

import org.bukkit.Material;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

public class Generator {

    private Map<Material, Float> items;
    private String name;

    public Generator(String name) {
        this.name = name;
        this.items = new LinkedHashMap<>();
        this.items.put(Material.STONE, 100f);
    }

    public Material generateRandomMaterial() {
        Random rand = new Random();
        float num = rand.nextInt(1000) / 10f;

        for(Map.Entry<Material, Float> entry : items.entrySet())
        {
            if ((num = num - entry.getValue()) < 0) {
                return entry.getKey();
            }
        }

        return Material.COBBLESTONE;
    }

    public void addItem(Material material, float chance)
    {
        this.items.put(material, chance);
    }

    public void removeItem(Material material)
    {
        this.items.remove(material);
    }

    public String getPermission() {
        return "oregenerator.use." + name;
    }

    public Map<Material, Float> getItems() {
        return items;
    }

    public String getName() {
        return name;
    }

}
