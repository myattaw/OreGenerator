package com.reliableplugins.oregenerator.util;

import com.reliableplugins.oregenerator.OreGenerator;
import com.reliableplugins.oregenerator.generator.Generator;

public class GeneratorUtil {
    private OreGenerator plugin;

    public GeneratorUtil(OreGenerator plugin) {
        this.plugin = plugin;
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
