package com.reliableplugins.oregenerator;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.reliableplugins.oregenerator.command.BaseCommand;
import com.reliableplugins.oregenerator.config.MaterialsConfig;
import com.reliableplugins.oregenerator.generator.Generator;
import com.reliableplugins.oregenerator.generator.GeneratorListeners;
import com.reliableplugins.oregenerator.hook.HookManager;
import com.reliableplugins.oregenerator.runnable.GeneratorTask;
import org.bukkit.plugin.java.JavaPlugin;

import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class OreGenerator extends JavaPlugin {

    private final ExecutorService executorService = Executors.newSingleThreadExecutor(new ThreadFactoryBuilder().setNameFormat("OreGenerator Thread").build());
    private List<Generator> generators = new ArrayList<>();
    private HookManager hookManager;
    private MaterialsConfig materialsConfig;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        this.hookManager = new HookManager(this);

        getServer().getPluginManager().registerEvents(new GeneratorListeners(this), this);
        getServer().getScheduler().scheduleSyncRepeatingTask(this, new GeneratorTask(this), 20L, 20L);
        new BaseCommand(this);

        generators.add(new Generator("default"));
        generators.add(new Generator("default2"));
        materialsConfig = new MaterialsConfig(this);
        materialsConfig.load();
        materialsConfig.save();
    }

    @Override
    public void onDisable() {
        materialsConfig.save();
    }

    public HookManager getHookManager() {
        return hookManager;
    }

    public void setGenerators(List<Generator> generators) {
        this.generators = generators;
    }

    public List<Generator> getGenerators() {
        return generators;
    }

    public MaterialsConfig getMaterialsConfig() {
        return materialsConfig;
    }
}
