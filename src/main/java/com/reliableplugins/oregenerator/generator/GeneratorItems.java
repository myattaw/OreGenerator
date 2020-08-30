package com.reliableplugins.oregenerator.generator;

import com.reliableplugins.oregenerator.util.XMaterial;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class GeneratorItems {

    private Map<XMaterial, Float> items;

    public GeneratorItems() {
        this.items = new LinkedHashMap<>();
        this.items.put(XMaterial.STONE, 100f);
    }

    public XMaterial generateRandomMaterial() {
        Random rand = new Random();
        float num = rand.nextInt(1000) / 10f;

        for(Map.Entry<XMaterial, Float> entry : items.entrySet())
        {
            if ((num = num - entry.getValue()) < 0) {
                return entry.getKey();
            }
        }
        return XMaterial.COBBLESTONE;
    }

    public void addItem(XMaterial material, float chance)
    {
        this.items.put(material, chance);
    }

    public void removeItem(XMaterial material)
    {
        this.items.remove(material);
    }

    public Map<XMaterial, Float> getItems() {
        return items;
    }

}
