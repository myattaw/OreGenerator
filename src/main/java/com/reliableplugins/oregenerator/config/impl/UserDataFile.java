package com.reliableplugins.oregenerator.config.impl;

import com.reliableplugins.oregenerator.OreGenerator;
import com.reliableplugins.oregenerator.config.CustomFile;
import com.reliableplugins.oregenerator.generator.Generator;
import com.reliableplugins.oregenerator.util.pair.Pair;
import org.bukkit.Bukkit;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class UserDataFile extends CustomFile {

    public UserDataFile(OreGenerator plugin, String name) {
        super(plugin, name);
    }

    @Override
    public void init() {

    }

    @Override
    public void load() {

    }

    @Override
    public void saveConfig() {
        for (Map.Entry<UUID, List<Pair<Generator, Integer>>> playerData : getPlugin().getPlayerCache().getGenerators().entrySet()) {
            for (Pair<Generator, Integer> generatorLevels : playerData.getValue()) {
                if (generatorLevels.getValue() == 1) continue;

                String path = String.format("%s.%s", playerData.getKey(), generatorLevels.getKey().getName());

                if (getConfig().contains(path)) {
                    getConfig().set(path, generatorLevels.getValue());
                } else {
                    getConfig().addDefault(path, generatorLevels.getValue());
                }
            }
        }
        getConfig().options().copyDefaults(true);
        super.saveConfig();
    }
}
