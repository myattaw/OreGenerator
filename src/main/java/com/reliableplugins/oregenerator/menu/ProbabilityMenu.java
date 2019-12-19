package com.reliableplugins.oregenerator.menu;

import com.reliableplugins.oregenerator.util.Util;
import com.reliableplugins.oregenerator.util.XMaterial;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class ProbabilityMenu extends MenuBuilder {

    private Material material;

    public ProbabilityMenu(String title, Material material, JavaPlugin plugin) {
        super(title, 1, plugin);
        this.material = material;
    }

    @Override
    public ProbabilityMenu init() {

        ItemStack itemStack = Util.setName(XMaterial.BLACK_STAINED_GLASS_PANE.parseItem(), " ");

        ItemStack add = XMaterial.LIME_STAINED_GLASS_PANE.parseItem();
        ItemStack remove = XMaterial.RED_STAINED_GLASS_PANE.parseItem();

        ItemStack item = new ItemStack(material);

        getInventory().setItem(0, Util.setName(add, "&7Add &a[+5.0%]"));
        getInventory().setItem(1, Util.setName(add, "&7Add &a[+1.0%]"));
        getInventory().setItem(2, Util.setName(add, "&7Add &a[+0.1%]"));

        getInventory().setItem(4, Util.setName(item, ChatColor.GREEN + CraftItemStack.asNMSCopy(item).getName()));

        getInventory().setItem(6, Util.setName(remove, "&7Remove &c[+5.0%]"));
        getInventory().setItem(7, Util.setName(remove, "&7Remove &c[+1.0%]"));
        getInventory().setItem(8, Util.setName(remove, "&7Remove &c[+0.1%]"));

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
