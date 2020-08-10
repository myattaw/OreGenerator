package com.reliableplugins.oregenerator.nms.impl;

import com.reliableplugins.oregenerator.nms.NMSHandler;
import net.minecraft.server.v1_16_R1.LocaleLanguage;
import org.bukkit.craftbukkit.v1_16_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

public class Version_1_16_R1 implements NMSHandler {

    @Override
    public String getItemName(ItemStack itemStack) {
        return LocaleLanguage.a().a(CraftItemStack.asNMSCopy(itemStack).getItem().getName());
    }


    @Override
    public String getVersion() {
        return "v1_16_R1";
    }

}
