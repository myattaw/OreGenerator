package com.reliableplugins.oregenerator.menu;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class MenuBuilder<T> implements InventoryHolder, Listener {

    public Inventory inventory;
    private String title;
    private int rows;

    public MenuBuilder(String title, int rows, JavaPlugin plugin) {
        this.title = title;
        this.rows = rows;
        this.inventory = Bukkit.createInventory(this, 9 * rows, getTitle());
        Bukkit.getPluginManager().registerEvents(this, plugin);
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

    @EventHandler
    public abstract void onInventoryClick(InventoryClickEvent event);

    @EventHandler
    public abstract void onInventoryClose(InventoryCloseEvent event);

    @EventHandler
    public abstract void onInventoryOpen(InventoryOpenEvent event);

}