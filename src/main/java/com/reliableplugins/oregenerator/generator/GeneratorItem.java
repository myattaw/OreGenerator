package com.reliableplugins.oregenerator.generator;


import org.bukkit.Material;

public class GeneratorItem {

    private Material material;
    private int chance;

    public GeneratorItem(Material material, int chance) {
        this.material = material;
        this.chance = chance;
    }

    public Material getMaterial() {
        return material;
    }
    
    public int getChance() {
        return chance;
    }

}
