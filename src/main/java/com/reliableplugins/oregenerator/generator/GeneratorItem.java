package com.reliableplugins.oregenerator.generator;


import org.bukkit.Material;

public class GeneratorItem {

    private Material material;
    private int chance = 0;

    public GeneratorItem(Material material) {
        this.material = material;
    }

    public Material getMaterial() {
        return material;
    }

    public void setChance(int chance) {
        this.chance = chance;
    }
}
