package com.reliableplugins.oregenerator.menu.impl;

import com.reliableplugins.oregenerator.OreGenerator;
import com.reliableplugins.oregenerator.generator.Generator;
import com.reliableplugins.oregenerator.menu.MenuBuilder;
import com.reliableplugins.oregenerator.util.Util;
import com.reliableplugins.oregenerator.util.XMaterial;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
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

    private Generator generator;

    public AddItemMenu(String name, String title, int rows, OreGenerator plugin) {
        super(title, rows, plugin);
        this.name = name;
        this.plugin = plugin;
        this.generator = plugin.getGenerators().get(name);
        lore.add(ChatColor.GRAY + "Add an item to a generator by clicking");
        lore.add(ChatColor.GRAY +"a material from your inventory.");
        lore.add("");
        lore.add(ChatColor.GRAY + "Remove item from generator by clicking");
        lore.add(ChatColor.GRAY + "on a material inside the menu.");
    }

    @Override
    public AddItemMenu init() {

        ItemStack border = Util.setName(XMaterial.BLACK_STAINED_GLASS_PANE.parseItem(), " ");
        ItemStack empty = Util.setName(XMaterial.GRAY_STAINED_GLASS_PANE.parseItem(), " ");

        getInventory().clear();

        for (int i = 0; i < ROW_SIZE; i++) {
            if (getInventory().getItem(i) == null) {
                getInventory().setItem(i, border);
            }
        }

        int slot = ROW_SIZE;
        for (Material material : generator.getItems().keySet()) {
            ItemStack itemStack = new ItemStack(material);
            Util.setLore(itemStack, lore);
            getInventory().setItem(slot++, Util.setName(itemStack, ChatColor.DARK_GREEN + plugin.getNMS().getItemName(itemStack)));
        }

        getInventory().setItem(40, Util.setName(new ItemStack(Material.BARRIER), ChatColor.DARK_RED + "Exit"));
        getInventory().setItem(getInventory().getSize() - ROW_SIZE, Util.setName(new ItemStack(Material.ARROW), ChatColor.RED + "Back"));

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
        Player player = (Player) event.getWhoClicked();
        if (event.getSlot() == (getInventory().getSize() - MID_SLOT)) {
            player.closeInventory();
            return;
        }

        if (event.getSlot() == (getInventory().getSize() - ROW_SIZE)) {
            int rows = (int) (1 + Math.ceil((generator.getItems().size() - 1) / ROW_SIZE));
            player.openInventory(new GeneratorMenu(plugin, generator, plugin.getConfig().getString("generator-menu.title"), rows + 2).init().getInventory());
            return;
        }

        Inventory inventory = event.getClickedInventory();
        Generator generator = plugin.getGenerators().get(name);
        ItemStack itemStack = event.getCurrentItem();
        if(itemStack == null || inventory == null) return;

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
