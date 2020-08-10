package com.reliableplugins.oregenerator.generator;

import com.reliableplugins.oregenerator.util.XMaterial;
import org.bukkit.Material;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

public class Generator {

    private Map<String, Float> items;
    private String name;

    public Generator(String name) {
        this.name = name;
        this.items = new LinkedHashMap<>();
        this.items.put("STONE", 100f);
    }

    public XMaterial generateRandomMaterial() {
        Random rand = new Random();
        float num = rand.nextInt(1000) / 10f;

        for(Map.Entry<String, Float> entry : items.entrySet())
        {
            if ((num = num - entry.getValue()) < 0) {
                return XMaterial.valueOf(entry.getKey());
            }
        }

        return XMaterial.COBBLESTONE;
    }

    public void addItem(XMaterial material, float chance)
    {
        this.items.put(material.name(), chance);
    }

    public void removeItem(XMaterial material)
    {
        this.items.remove(material.name());
    }

    public String getPermission() {
        return "oregenerator.use." + name;
    }

    public Map<String, Float> getItems() {
        return items;
    }

    public String getName() {
        return name;
    }

}
