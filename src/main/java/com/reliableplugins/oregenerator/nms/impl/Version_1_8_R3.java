package com.reliableplugins.oregenerator.nms.impl;

import com.reliableplugins.oregenerator.nms.NMSHandler;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

public class Version_1_8_R3 implements NMSHandler {

    @Override
    public String getItemName(ItemStack itemStack) {
        return CraftItemStack.asNMSCopy(itemStack).getName();
    }

    @Override
    public String getVersion() {
        return "v1_8_R3";
    }
}
