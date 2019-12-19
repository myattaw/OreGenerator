package com.reliableplugins.oregenerator.menu;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class MenuBuilder<T> implements InventoryHolder {

    public Inventory inventory;
    private String title;
    private int rows;
    public final int ROW_SIZE = 9;

    public MenuBuilder(String title, int rows, JavaPlugin plugin) {
        this.title = title;
        this.rows = rows;
        this.inventory = Bukkit.createInventory(this, 9 * rows, getTitle());
    }

    public MenuBuilder(String title, JavaPlugin plugin) {
        this.title = title;
        this.inventory = Bukkit.createInventory(this, InventoryType.HOPPER, getTitle());
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

    public abstract T init();

    public Inventory getInventory() {
        return this.inventory;
    }

    public abstract void onInventoryClick(InventoryClickEvent event);

    public abstract void onInventoryClose(InventoryCloseEvent event);

    public abstract void onInventoryOpen(InventoryOpenEvent event);

}