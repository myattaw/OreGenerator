package com.reliableplugins.oregenerator.config;

import com.reliableplugins.oregenerator.OreGenerator;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;

public abstract class CustomFile {

    private YamlConfiguration config;
    private File configFile;
    private String name;

    private OreGenerator plugin;

    public CustomFile(OreGenerator plugin, String name) {
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
        }

        configFile = new File(plugin.getDataFolder(), name + ".yml");

        try {
            configFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        reloadConfig();
        this.plugin = plugin;
        this.name = name;
    }

    public abstract void init();

    public abstract void load();

    public void reloadConfig() {
        config = YamlConfiguration.loadConfiguration(configFile);
    }

    public void saveConfig() {
        try {
            config.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public YamlConfiguration getConfig() {
        return config;
    }

    public String getName() {
        return name;
    }

    public OreGenerator getPlugin() {
        return plugin;
    }
}