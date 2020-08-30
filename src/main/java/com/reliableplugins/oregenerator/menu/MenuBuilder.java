package com.reliableplugins.oregenerator.menu;

import com.reliableplugins.oregenerator.OreGenerator;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public abstract class MenuBuilder<T> implements InventoryHolder {

    public Inventory inventory;
    private String title;
    private int rows;

    public final int MID_SLOT = 5;
    public final int ROW_SIZE = 9;

    private OreGenerator plugin;

    public MenuBuilder(String title, int rows, OreGenerator plugin) {
        this.title = title;
        this.rows = rows;
        this.inventory = Bukkit.createInventory(this, 9 * rows, getTitle());
        this.plugin = plugin;
    }

    public MenuBuilder(String title, OreGenerator plugin) {
        this.title = title;
        this.inventory = Bukkit.createInventory(this, InventoryType.HOPPER, getTitle());
        this.plugin = plugin;
    }


    public String getTitle() {
        return ChatColor.translateAlternateColorCodes('&', title);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public OreGenerator getPlugin() {
        return plugin;
    }

    public abstract T init();

    public Inventory getInventory() {
        return this.inventory;
    }

    public abstract void onInventoryClick(InventoryClickEvent event);

    public abstract void onInventoryClose(InventoryCloseEvent event);

    public abstract void onInventoryOpen(InventoryOpenEvent event);

}