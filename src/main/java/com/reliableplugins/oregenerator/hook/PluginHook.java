package com.reliableplugins.oregenerator.hook;

import com.reliableplugins.oregenerator.OreGenerator;

public interface PluginHook<T> {

    T setup(OreGenerator plugin);

    String[] getPlugins();

    String getName();

}
