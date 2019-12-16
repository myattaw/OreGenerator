package com.reliableplugins.oregenerator.hook;

import com.reliableplugins.oregenerator.OreGenerator;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public class HookManager {

    private OreGenerator plugin;
    private Map<String, PluginHook> pluginMap = new HashMap<>();

    public HookManager(OreGenerator plugin) {
        this.plugin = plugin;
//        hookPlugin(new FactionHook());
    }

    private void hookPlugin(PluginHook pluginHook) {
        if (plugin.getServer().getPluginManager().getPlugin(pluginHook.getName()) == null) {
            return;
        }
        plugin.getServer().getLogger().log(Level.INFO, "Successfully hooked into " + pluginHook.getName());
        pluginMap.put(pluginHook.getName().toLowerCase(), (PluginHook<?>) pluginHook.setup(plugin));
    }

//    public WorldGuardHook getWorldGuard() {
//        if (pluginMap.containsKey("worldguard")) {
//            return (WorldGuardHook) pluginMap.get("worldguard");
//        }
//        return null;
//    }


}
