package com.reliableplugins.oregenerator.listeners;

import com.reliableplugins.oregenerator.menu.MenuBuilder;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

public class InventoryListeners implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getInventory().getHolder() instanceof MenuBuilder) {
            MenuBuilder menuBuilder = (MenuBuilder) event.getInventory().getHolder();
            menuBuilder.onInventoryClick(event);
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event) {
        if (event.getInventory().getHolder() instanceof MenuBuilder) {
            MenuBuilder menuBuilder = (MenuBuilder) event.getInventory().getHolder();
            menuBuilder.onInventoryOpen(event);
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (event.getInventory().getHolder() instanceof MenuBuilder) {
            MenuBuilder menuBuilder = (MenuBuilder) event.getInventory().getHolder();
            menuBuilder.onInventoryClose(event);
        }
    }

}
