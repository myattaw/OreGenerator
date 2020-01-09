package com.reliableplugins.oregenerator.nms;

import com.reliableplugins.oregenerator.OreGenerator;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface NMSHandler {

    void setBlock(OreGenerator plugin, World world, int x, int y, int z, Material material);

    String getItemName(ItemStack itemStack);

    String getVersion();

    void breakBlock(Block block, Player player);

}