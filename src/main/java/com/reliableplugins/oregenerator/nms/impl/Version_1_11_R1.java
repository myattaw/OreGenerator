package com.reliableplugins.oregenerator.nms.impl;

import com.reliableplugins.oregenerator.nms.NMSHandler;
import org.bukkit.craftbukkit.v1_11_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

public class Version_1_11_R1 implements NMSHandler {

    @Override
    public String getItemName(ItemStack itemStack) {
        return CraftItemStack.asNMSCopy(itemStack).getName();
    }

    @Override
    public String getVersion() {
        return "v1_11_R1";
    }

}
