package com.reliableplugins.oregenerator.generator;

import org.bukkit.Material;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

public class Generator {

    private Map<Material, Integer> items;
    private String name;

    public Generator(String name) {
        this.name = name;
        this.items = new LinkedHashMap<>();
        this.items.put(Material.STONE, 100);
    }

    public Material generateRandomMaterial() {
        Random rand = new Random();
        int randInt = rand.nextInt(100);

        for(Map.Entry<Material, Integer> entry : items.entrySet())
        {
            if ((randInt = randInt - entry.getValue()) < 0) {
                return entry.getKey();
            }
        }

        return Material.COBBLESTONE;
    }

    public void addItem(Material material, int chance)
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

    public Map<Material, Integer> getItems() {
        return items;
    }

    public String getName() {
        return name;
    }

}
