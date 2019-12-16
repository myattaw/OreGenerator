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
    }

    public String getPermission() {
        return "oregenerator.use." + name;
    }

}
