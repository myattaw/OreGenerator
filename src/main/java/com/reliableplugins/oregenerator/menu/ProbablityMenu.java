package com.reliableplugins.oregenerator.menu;

import com.reliableplugins.oregenerator.util.Util;
import com.reliableplugins.oregenerator.util.XMaterial;
import net.minecraft.server.v1_8_R3.Items;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class ProbablityMenu extends MenuBuilder {


    public ProbablityMenu(String title, JavaPlugin plugin) {
        super(title, plugin);
    }

    @Override
    public ProbablityMenu init() {

        ItemStack itemStack = XMaterial.BLACK_STAINED_GLASS_PANE.parseItem();

        ItemStack add = XMaterial.LIME_STAINED_GLASS_PANE.parseItem();
        ItemStack exit = XMaterial.BARRIER.parseItem();
        ItemStack remove = XMaterial.RED_STAINED_GLASS_PANE.parseItem();

        getInventory().setItem(0, Util.setName(add, "&7Add &a[+1]"));
        getInventory().setItem(0, Util.setName(exit, "&cExit"));
        getInventory().setItem(0, Util.setName(remove, "&7Remove &c[+1]"));

        for (int i = 0; i < getInventory().getSize(); i++) {
            if (getInventory().getItem(i) == null) {
                getInventory().setItem(i, itemStack);
            }
        }

        return this;
    }

    @Override
    public void onInventoryClick(InventoryClickEvent event) {

    }

    @Override
    public void onInventoryClose(InventoryCloseEvent event) {

    }

    @Override
    public void onInventoryOpen(InventoryOpenEvent event) {

    }
}
