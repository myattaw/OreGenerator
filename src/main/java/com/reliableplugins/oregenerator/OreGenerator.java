package com.reliableplugins.oregenerator;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.reliableplugins.oregenerator.generator.GeneratorItem;
import com.reliableplugins.oregenerator.generator.GeneratorListeners;
import com.reliableplugins.oregenerator.hook.HookManager;
import com.reliableplugins.oregenerator.runnable.GeneratorTask;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class OreGenerator extends JavaPlugin {

    private final ExecutorService executorService = Executors.newSingleThreadExecutor(new ThreadFactoryBuilder().setNameFormat("OreGenerator Thread").build());
    private List<GeneratorItem> generators = new ArrayList<>();

    private HookManager hookManager;

    @Override
    public void onEnable() {

        this.hookManager = new HookManager(this);

        getServer().getPluginManager().registerEvents(new GeneratorListeners(this), this);
        getServer().getScheduler().scheduleSyncRepeatingTask(this, new GeneratorTask(this), 20L, 20L);
    }

    @Override
    public void onDisable() { }

    public HookManager getHookManager() {
        return hookManager;
    }

    public List<GeneratorItem> getGenerators() {
        return generators;
    }
}
