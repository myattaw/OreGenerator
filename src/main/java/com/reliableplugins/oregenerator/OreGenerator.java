package com.reliableplugins.oregenerator;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.reliableplugins.oregenerator.command.BaseCommand;
import com.reliableplugins.oregenerator.config.CustomFile;
import com.reliableplugins.oregenerator.config.FileManager;
import com.reliableplugins.oregenerator.config.MaterialsConfig;
import com.reliableplugins.oregenerator.generator.Generator;
import com.reliableplugins.oregenerator.hook.HookManager;
import com.reliableplugins.oregenerator.listeners.GeneratorListeners;
import com.reliableplugins.oregenerator.listeners.InventoryListeners;
import com.reliableplugins.oregenerator.menu.impl.GeneratorMenu;
import com.reliableplugins.oregenerator.nms.NMSHandler;
import com.reliableplugins.oregenerator.nms.NMSManager;
import com.reliableplugins.oregenerator.util.XMaterial;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class OreGenerator extends JavaPlugin {

    private Map<String, Generator> generators = new HashMap<>();
    private PlayerCache playerCache;

    private MaterialsConfig materialsConfig;

    private NMSHandler nmsHandler;

    private NMSManager nmsManager;
    private FileManager fileManager;
    private HookManager hookManager;

    private Map<Generator, GeneratorMenu> generatorMenus = new HashMap<>();

    @Override
    public void onEnable() {
        this.saveDefaultConfig();

        this.hookManager = new HookManager(this);
        this.nmsManager = new NMSManager(this);
        this.fileManager = new FileManager(this);

        GeneratorListeners generatorListeners = new GeneratorListeners(this);

        getServer().getPluginManager().registerEvents(generatorListeners, this);
        getServer().getPluginManager().registerEvents(new InventoryListeners(), this);

        new BaseCommand(this);

        Generator defaultGenerator = new Generator("default");
        generators.put("default", defaultGenerator);

        materialsConfig = new MaterialsConfig(this);
        materialsConfig.load();

        this.playerCache = new PlayerCache(this);

        for (Player player : this.getServer().getOnlinePlayers()) {
            playerCache.addPlayer(player);
        }

    }

    @Override
    public void onDisable() {
        materialsConfig.save();
        fileManager.getFiles().forEach(CustomFile::saveConfig);
    }

    public void setGeneratorMenu(Generator generator, GeneratorMenu generatorMenu) {
        GeneratorMenu menu = generatorMenus.get(generator);
        if (menu != null) {
            generatorMenus.remove(generator);
        }
        generatorMenus.put(generator, generatorMenu);
    }

    public FileManager getFileManager() {
        return fileManager;
    }

    public GeneratorMenu getGeneratorMenu(Generator generator) {
        return generatorMenus.get(generator);
    }

    public HookManager getHookManager() {
        return hookManager;
    }

    public PlayerCache getPlayerCache() {
        return playerCache;
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

    public MaterialsConfig getMaterialsConfig() {
        return materialsConfig;
    }
}
