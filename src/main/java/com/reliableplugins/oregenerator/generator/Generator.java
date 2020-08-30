package com.reliableplugins.oregenerator.generator;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

public class Generator {

    private String name;

    @SerializedName("level")
    private Map<Integer, GeneratorItems> levelMap = new HashMap<>();

    private int maxLevel;

    public Generator(String name) {
        this.name = name;
        this.levelMap.put(1, new GeneratorItems());
    }

    public GeneratorItems getFirst() {
        return levelMap.get(1);
    }

    public GeneratorItems getByLevel(int level) { return levelMap.get(level); }

    public void addLevel() {
        setMaxLevel(maxLevel + 1);
        levelMap.put(this.maxLevel, new GeneratorItems());
    }

    public void removeLevel() {
        levelMap.remove(getMaxLevel());
        setMaxLevel(maxLevel - 1);
    }

    public void setMaxLevel(int maxLevel) {
        this.maxLevel = maxLevel;
    }

    public int getMaxLevel() {
        return levelMap.size();
    }

    public String getPermission() {
        return "oregenerator.use." + name;
    }

    public String getName() {
        return name;
    }

}
