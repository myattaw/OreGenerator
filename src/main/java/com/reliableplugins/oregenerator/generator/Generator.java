package com.reliableplugins.oregenerator.generator;

import org.bukkit.Material;

import java.util.HashMap;
import java.util.Map;

public class Generator {

    private Map<Material, Integer> percents;
    private String name;

    public Generator(String name) {
        this.name = name;
        this.percents = new HashMap<>();
        this.percents.put(Material.STONE, 100);
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
