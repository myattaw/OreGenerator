package com.reliableplugins.oregenerator.generator;

import com.reliableplugins.oregenerator.config.MaterialsConfig;
import org.bukkit.Bukkit;
import org.bukkit.Material;

import java.util.*;

public class Generator {

    private Map<Material, Integer> percents;
    private String name;

    public Generator(String name) {
        this.name = name;
        this.percents = new LinkedHashMap<>();
        this.percents.put(Material.REDSTONE_ORE, 30);
        this.percents.put(Material.DIAMOND_ORE, 2);
        this.percents.put(Material.COAL_ORE, 3);
        this.percents.put(Material.STONE, 15);
        this.percents.put(Material.EMERALD_ORE, 50);
    }

    public Material generateRandomMaterial() throws Exception {
        Random rand = new Random();
        int randInt = rand.nextInt(100);

        for(Map.Entry<Material, Integer> entry : percents.entrySet())
        {
            if ((randInt = randInt - entry.getValue()) < 0) {
                return entry.getKey();
            }
        }

        throw new Exception("Invalid probability settings");
    }

    public void setPercents(Map<Material, Integer> percents) {
        this.percents = percents;
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
