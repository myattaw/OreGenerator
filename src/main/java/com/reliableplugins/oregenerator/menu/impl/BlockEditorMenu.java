package com.reliableplugins.oregenerator.menu.impl;

import com.reliableplugins.oregenerator.OreGenerator;
import com.reliableplugins.oregenerator.generator.Generator;
import com.reliableplugins.oregenerator.menu.MenuBuilder;
import com.reliableplugins.oregenerator.util.Util;
import com.reliableplugins.oregenerator.util.XMaterial;
import org.bukkit.Bukkit;
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

public class BlockEditorMenu extends MenuBuilder {

    private OreGenerator plugin;
    private List<String> lore = new ArrayList<>();

    private Generator generator;
    private Map<XMaterial, Float> items;

    public BlockEditorMenu(Generator generator, Map<XMaterial, Float> items, int rows, OreGenerator plugin) {
        super("Remove or add blocks", rows, plugin);
        this.generator = generator;
        this.items = items;
        this.plugin = plugin;
        lore.add(ChatColor.GRAY + "[" + ChatColor.GREEN + (ChatColor.BOLD + "+") + ChatColor.GRAY + "]" + ChatColor.ITALIC + " Add an item to a generator by clicking");
        lore.add(ChatColor.GRAY + "a material from your inventory.");
        lore.add("");
        lore.add(ChatColor.GRAY + "[" + ChatColor.RED + (ChatColor.BOLD + "-") + ChatColor.GRAY + "]" + ChatColor.ITALIC + " Remove item from generator by clicking");
        lore.add(ChatColor.GRAY + "on a material inside the menu.");
    }

    @Override
    public BlockEditorMenu init() {

        ItemStack border = Util.setName(XMaterial.BLACK_STAINED_GLASS_PANE.parseItem(), " ");
        ItemStack empty = Util.setName(XMaterial.GRAY_STAINED_GLASS_PANE.parseItem(), " ");

        getInventory().clear();

        for (int i = 0; i < ROW_SIZE; i++) {
            if (getInventory().getItem(i) == null) {
                getInventory().setItem(i, border);
            }
        }

        int slot = ROW_SIZE;
        for (XMaterial material : items.keySet()) {
            ItemStack itemStack = material.parseItem();
            Util.setLore(itemStack, lore);
            getInventory().setItem(slot++, Util.setName(itemStack, ChatColor.DARK_GREEN + plugin.getNMS().getItemName(itemStack)));
        }

        getInventory().setItem(getInventory().getSize() - MID_SLOT, Util.setName(new ItemStack(Material.BARRIER), ChatColor.DARK_RED + "Exit"));

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

        Inventory inventory = event.getClickedInventory();
        ItemStack itemStack = event.getCurrentItem();

        if (itemStack == null || inventory == null) return;

        XMaterial xMaterial = XMaterial.matchXMaterial(itemStack);

        if (inventory == player.getInventory()) {

            if (!items.containsKey(xMaterial) && itemStack.getType().isSolid()) {
                items.put(xMaterial, 0F);
                init();
            }
        } else {
            if (items.containsKey(xMaterial)) {
                items.remove(xMaterial);
                init();
            }
        }
    }

    @Override
    public void onInventoryClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        GeneratorMenu generatorMenu = plugin.getGeneratorMenu(generator).init();
        Bukkit.getScheduler().runTaskLater(plugin, () ->  player.openInventory(generatorMenu.getInventory()), 0);
    }

    @Override
    public void onInventoryOpen(InventoryOpenEvent event) {

    }
}
