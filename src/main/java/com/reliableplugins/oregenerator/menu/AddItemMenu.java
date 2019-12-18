package com.reliableplugins.oregenerator.menu;

import com.reliableplugins.oregenerator.OreGenerator;
import com.reliableplugins.oregenerator.generator.Generator;
import com.reliableplugins.oregenerator.util.Util;
import com.reliableplugins.oregenerator.util.XMaterial;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class AddItemMenu extends MenuBuilder {

    private String name;
    private OreGenerator plugin;

    public AddItemMenu(String name, String title, int rows, OreGenerator plugin) {
        super(title, rows, plugin);
        this.name = name;
        this.plugin = plugin;
    }

    @Override
    public AddItemMenu init() {

        Generator generator = plugin.getGenerators().get(name);
        ItemStack border = Util.setName(XMaterial.BLACK_STAINED_GLASS_PANE.parseItem(), " ");
        ItemStack empty = Util.setName(XMaterial.GRAY_STAINED_GLASS_PANE.parseItem(), " ");

        getInventory().clear();

        for (int i = 0; i < 9; i++) {
            if (getInventory().getItem(i) == null) {
                getInventory().setItem(i, border);
            }
        }

        int slot = 9;
        for (Map.Entry<Material, Integer> test : generator.getPercents().entrySet()) {
            getInventory().setItem(slot++, new ItemStack(test.getKey()));
        }

        for (int i = slot; i < getInventory().getSize() - 9; i++) {
            getInventory().setItem(i, empty);
        }

        for (int i = getInventory().getSize() - 9; i < getInventory().getSize(); i++) {
            if (getInventory().getItem(i) == null) {
                getInventory().setItem(i, border);
            }
        }

        return this;
    }

    @Override
    public void onInventoryClick(InventoryClickEvent event) {
        Inventory inventory = event.getClickedInventory();
        Generator generator = plugin.getGenerators().get(name);
        ItemStack itemStack = event.getCurrentItem();

        if (inventory == event.getWhoClicked().getInventory()) {
            if (!generator.getPercents().containsKey(itemStack.getType()) && itemStack.getType().isSolid()) {
                generator.addItem(itemStack.getType(), 0);
                init();
            }
        } else {
            //TODO: make it remove item from inventory
            if (generator.getPercents().containsKey(itemStack.getType())) {
                generator.removeItem(itemStack.getType());
                init();
            }
        }
    }

    @Override
    public void onInventoryClose(InventoryCloseEvent event) {

    }

    @Override
    public void onInventoryOpen(InventoryOpenEvent event) {

    }
}
