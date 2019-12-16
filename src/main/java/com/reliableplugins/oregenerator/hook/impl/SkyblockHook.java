package com.reliableplugins.oregenerator.hook.impl;

import com.reliableplugins.oregenerator.OreGenerator;
import com.reliableplugins.oregenerator.hook.PluginHook;
import com.reliableplugins.oregenerator.hook.impl.skyblock.ASkyblockHook;
import org.apache.commons.lang3.NotImplementedException;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.PluginManager;

import java.util.UUID;

public class SkyblockHook implements PluginHook {

    @Override
    public SkyblockHook setup(OreGenerator plugin) {
        PluginManager pluginManager = Bukkit.getPluginManager();
        if (pluginManager.isPluginEnabled("ASkyBlock")) {
            return new ASkyblockHook();
        }
        return this;
    }

    public UUID getIslandOwner(Location location) {
        throw new NotImplementedException("SkyBlock does not exist!");
    }

    @Override
    public String[] getPlugins() {
        return new String[] { "ASkyBlock" };
    }

    @Override
    public String getName() {
        return "SkyBlock";
    }
}
