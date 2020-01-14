package com.reliableplugins.oregenerator;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.reliableplugins.oregenerator.command.BaseCommand;
import com.reliableplugins.oregenerator.config.MaterialsConfig;
import com.reliableplugins.oregenerator.generator.Generator;
import com.reliableplugins.oregenerator.hook.HookManager;
import com.reliableplugins.oregenerator.listeners.GeneratorListeners;
import com.reliableplugins.oregenerator.listeners.InventoryListeners;
import com.reliableplugins.oregenerator.nms.NMSHandler;
import com.reliableplugins.oregenerator.nms.NMSManager;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class OreGenerator extends JavaPlugin implements Listener {

    private final ExecutorService executorService = Executors.newSingleThreadExecutor(new ThreadFactoryBuilder().setNameFormat("OreGenerator Thread").build());

    private Map<String, Generator> generators = new HashMap<>();
    private PlayerCache playerCache;

    private HookManager hookManager;
    private NMSManager nmsManager;
    private MaterialsConfig materialsConfig;
    private NMSHandler nmsHandler;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
//        this.hookManager = new HookManager(this);
        this.nmsManager = new NMSManager(this);
        this.playerCache = new PlayerCache(this);

        getServer().getPluginManager().registerEvents(new GeneratorListeners(this), this);
        getServer().getPluginManager().registerEvents(new InventoryListeners(), this);

        new BaseCommand(this);

        generators.put("default", new Generator("default"));

        materialsConfig = new MaterialsConfig(this);
        materialsConfig.load();
        materialsConfig.save();
    }

    @Override
    public void onDisable() {
        materialsConfig.save();
    }


    public PlayerCache getPlayerCache() {
        return playerCache;
    }

    public HookManager getHookManager() {
        return hookManager;
    }

    public NMSManager getNmsManager() {
        return nmsManager;
    }

    public NMSHandler getNMS() {
        return nmsHandler;
    }

    public void setNMS(NMSHandler nmsHandler) {
        this.nmsHandler = nmsHandler;
    }

    public Map<String, Generator> getGenerators() {
        return generators;
    }

    public void setGenerators(Map<String, Generator> generators) {
        this.generators = generators;
    }

    public ExecutorService getExecutorService() {
        return executorService;
    }

    public MaterialsConfig getMaterialsConfig() {
        return materialsConfig;
    }
}
