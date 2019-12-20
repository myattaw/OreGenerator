package com.reliableplugins.oregenerator.menu.impl;

import com.reliableplugins.oregenerator.OreGenerator;
import com.reliableplugins.oregenerator.generator.Generator;
import com.reliableplugins.oregenerator.menu.MenuBuilder;
import com.reliableplugins.oregenerator.util.Util;
import com.reliableplugins.oregenerator.util.XMaterial;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AddItemMenu extends MenuBuilder {

    private String name;
    private OreGenerator plugin;
    private List<String> lore = new ArrayList<>();

    public AddItemMenu(String name, String title, int rows, OreGenerator plugin) {
        super(title, rows, plugin);
        this.name = name;
        this.plugin = plugin;
        lore.add(ChatColor.GRAY + "Add an item to a generator by clicking");
        lore.add(ChatColor.GRAY +"a material from your inventory.");
        lore.add("");
        lore.add(ChatColor.GRAY + "Remove item from generator by clicking");
        lore.add(ChatColor.GRAY + "on a material inside the menu.");
    }

    @Override
    public AddItemMenu init() {
        Generator generator = plugin.getGenerators().get(name);
        ItemStack border = Util.setName(XMaterial.BLACK_STAINED_GLASS_PANE.parseItem(), " ");
        ItemStack empty = Util.setName(XMaterial.GRAY_STAINED_GLASS_PANE.parseItem(), " ");

        getInventory().clear();

        for (int i = 0; i < ROW_SIZE; i++) {
            if (getInventory().getItem(i) == null) {
                getInventory().setItem(i, border);
            }
        }

        int slot = ROW_SIZE;
        for (Map.Entry<Material, Float> test : generator.getItems().entrySet()) {
            ItemStack itemStack = new ItemStack(test.getKey());
            Util.setLore(itemStack, lore);
            getInventory().setItem(slot++, Util.setName(itemStack, ChatColor.DARK_GREEN + plugin.getNMS().getItemName(itemStack)));
        }

        getInventory().setItem(40, Util.setName(new ItemStack(Material.BARRIER), ChatColor.RED + "Exit"));


        for (int i = slot; i < getInventory().getSize() - ROW_SIZE; i++) {
            getInventory().setItem(i, empty);
        }

        for (int i = getInventory().getSize() - ROW_SIZE; i < getInventory().getSize(); i++) {
            if (getInventory().getItem(i) == null) {
                getInventory().setItem(i, border);
            }
        }

        return this;
    }

    @Override
    public void onInventoryClick(InventoryClickEvent event) {

        // If pressed barrier
        if(event.getSlot() == 40) {
            event.getWhoClicked().closeInventory();
        }

        Inventory inventory = event.getClickedInventory();
        Generator generator = plugin.getGenerators().get(name);
        ItemStack itemStack = event.getCurrentItem();

        // If item in player inventory
        if (inventory.equals(event.getWhoClicked().getInventory())) {
            if (!generator.getItems().containsKey(itemStack.getType()) && itemStack.getType().isSolid()) {
                generator.addItem(itemStack.getType(), 0);
                init();
            }
        } else {
            if (generator.getItems().containsKey(itemStack.getType())) {
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
