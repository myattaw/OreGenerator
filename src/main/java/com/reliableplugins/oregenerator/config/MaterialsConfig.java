package com.reliableplugins.oregenerator.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.reliableplugins.oregenerator.OreGenerator;
import com.reliableplugins.oregenerator.generator.Generator;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.logging.Level;

public class MaterialsConfig {
    private Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    private OreGenerator plugin;

    public MaterialsConfig(OreGenerator plugin)
    {
        this.plugin = plugin;
    }

    public void save() {
        try (FileWriter writer = new FileWriter(plugin.getDataFolder() + File.separator + "generators.json")) {
            this.gson.toJson(plugin.getGenerators(), writer);
        }
        catch (IOException e) {
            plugin.getLogger().log(Level.SEVERE, "Failed to save generator data!: " + e.getMessage());
        }
    }

    public void load() {
        if (Files.isReadable(Paths.get(plugin.getDataFolder() + File.separator + "generators.json"))) {
            try (Reader reader = new FileReader(plugin.getDataFolder() + File.separator + "generators.json")) {
                 plugin.setGenerators(new Gson().fromJson(reader, new TypeToken<Map<String, Generator>>() {}.getType()));
            }
            catch (IOException e) {
                plugin.getServer().getLogger().log(Level.SEVERE, "Failed to load generators.json!: " + e.getMessage());
            }
        }
    }
}
