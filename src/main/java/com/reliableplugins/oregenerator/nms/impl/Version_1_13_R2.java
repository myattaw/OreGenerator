package com.reliableplugins.oregenerator.nms.impl;

import com.reliableplugins.oregenerator.OreGenerator;
import com.reliableplugins.oregenerator.nms.NMSHandler;
import org.bukkit.World;

public class Version_1_13_R2 implements NMSHandler {

    @Override
    public void setBlock(OreGenerator plugin, World world, int x, int y, int z, int id, byte data) {

    }

    @Override
    public String getVersion() {
        return "v1_13_R2";
    }

}

