package com.reliableplugins.oregenerator.util;

import com.reliableplugins.oregenerator.OreGenerator;
import com.reliableplugins.oregenerator.generator.Generator;
import org.bukkit.entity.Player;

public class GeneratorUtil {
    private OreGenerator plugin;

    public GeneratorUtil(OreGenerator plugin) {
        this.plugin = plugin;
    }

    public Generator getGenerator(Player player)
    {
        Generator generator = null;
        for(Generator g : plugin.getGenerators())
        {
            if(player.hasPermission("oregenerator." + g.getName()))
            {
                generator = g;
            }
        }
        if(generator == null)
        {
            generator = getGenerator("default");
        }
        return generator;
    }

    public boolean isGenerator(String name) {
        for(Generator g : plugin.getGenerators()) {
            if(name.equalsIgnoreCase(g.getName())) return true;
        }
        return false;
    }

    public Generator getGenerator(String name) {
        for(Generator g : plugin.getGenerators()) {
            if(name.equalsIgnoreCase(g.getName())) return g;
        }
        return null;
    }
}
