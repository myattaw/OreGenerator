package com.reliableplugins.oregenerator.generator;

import com.reliableplugins.oregenerator.config.MaterialsConfig;
import org.bukkit.Material;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

public class Generator {

    private Map<Material, Integer> percents;
    private String name;

    public Generator(String name) {
        this.name = name;
        this.percents = new LinkedHashMap<>();
        addItem(Material.REDSTONE_ORE, 30);
        addItem(Material.DIAMOND_ORE, 2);
        addItem(Material.COAL_ORE, 3);
        addItem(Material.STONE, 15);
        addItem(Material.EMERALD_ORE, 50);
    }

    public Material generateRandomMaterial() {
        Random rand = new Random();
        int randInt = rand.nextInt(100);

        for(Map.Entry<Material, Integer> entry : percents.entrySet())
        {
            if ((randInt = randInt - entry.getValue()) < 0) {
                return entry.getKey();
            }
        }

        return Material.COBBLESTONE;
    }

    public void addItem(Material material, int chance)
    {
        this.percents.put(material, chance);
    }

    public void removeItem(Material material)
    {
        this.percents.remove(material);
    }

    public void setPercents(Map<Material, Integer> percents) {
        this.percents = percents;
    }

    public int getPercentTotal()
    {
        int total = 0;
        for(int i : percents.values())
        {
            total += i;
        }
        return total;
    }

    public String getPermission() {
        return "oregenerator.use." + name;
    }

    public Map<Material, Integer> getPercents() {
        return percents;
    }

    public String getName() {
        return name;
    }

}
