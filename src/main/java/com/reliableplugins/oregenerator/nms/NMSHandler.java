package com.reliableplugins.oregenerator.nms;

import com.reliableplugins.oregenerator.OreGenerator;
import org.bukkit.World;

public interface NMSHandler {

    void setBlock(OreGenerator plugin, World world, int x, int y, int z, int id, byte data);

    String getVersion();

}