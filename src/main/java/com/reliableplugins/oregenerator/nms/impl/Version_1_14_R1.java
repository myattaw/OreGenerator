package com.reliableplugins.oregenerator.nms.impl;

import com.reliableplugins.oregenerator.OreGenerator;
import com.reliableplugins.oregenerator.nms.NMSHandler;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_14_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Version_1_14_R1 implements NMSHandler {

    @Override
    public void setBlock(OreGenerator plugin, World world, int x, int y, int z,  Material material) {

    }

    @Override
    public void breakBlock(Block block, Player player) {

    }

    @Override
    public String getItemName(ItemStack itemStack) {
        return CraftItemStack.asNMSCopy(itemStack).getName().getText();
    }


    @Override
    public String getVersion() {
        return "v1_14_R1";
    }

}
