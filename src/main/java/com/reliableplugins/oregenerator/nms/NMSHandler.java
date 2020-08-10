package com.reliableplugins.oregenerator.nms;

import com.reliableplugins.oregenerator.OreGenerator;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface NMSHandler {

    String getItemName(ItemStack itemStack);

    String getVersion();

}