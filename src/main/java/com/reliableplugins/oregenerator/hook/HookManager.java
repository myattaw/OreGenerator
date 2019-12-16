package com.reliableplugins.oregenerator.hook;

import com.reliableplugins.oregenerator.OreGenerator;
import com.reliableplugins.oregenerator.hook.impl.SkyblockHook;
import com.reliableplugins.oregenerator.hook.impl.skyblock.ASkyblockHook;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public class HookManager {

    private OreGenerator plugin;
    private Map<String, PluginHook> pluginMap = new HashMap<>();

    public HookManager(OreGenerator plugin) {
        this.plugin = plugin;
        hookPlugin(new ASkyblockHook());
    }

    private void hookPlugin(PluginHook pluginHook) {
        for (String name : pluginHook.getPlugins()) {
            if (plugin.getServer().getPluginManager().getPlugin(name) == null) continue;
            plugin.getServer().getLogger().log(Level.INFO, "Successfully hooked into " + name);
            pluginMap.put(pluginHook.getName().toLowerCase(), (PluginHook<?>) pluginHook.setup(plugin));
        }
    }

    public SkyblockHook getSkyBlock() {
        if (pluginMap.containsKey("skyblock")) {
            return (SkyblockHook) pluginMap.get("skyblock");
        }
        return null;
    }


}
