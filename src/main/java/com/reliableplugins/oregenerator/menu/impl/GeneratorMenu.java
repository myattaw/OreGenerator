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
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class GeneratorMenu extends MenuBuilder {

    private OreGenerator plugin;
    private Generator generator;

    public GeneratorMenu(OreGenerator plugin, Generator generator, int rows) {
        super("Select a block", rows, plugin);
        this.plugin = plugin;
        this.generator = generator;
    }

    @Override
    public GeneratorMenu init() {

        ItemStack border = Util.setName(XMaterial.BLACK_STAINED_GLASS_PANE.parseItem(), " ");
        ItemStack empty = Util.setName(XMaterial.GRAY_STAINED_GLASS_PANE.parseItem(), " ");

        for (int i = 0; i < ROW_SIZE; i++) {
            getInventory().setItem(i, border);
        }

        int slot = ROW_SIZE;
        for (Map.Entry<String, Float> items : generator.getItems().entrySet()) {

            ItemStack item = XMaterial.valueOf(items.getKey()).parseItem();
            List<String> lore = Arrays.asList(ChatColor.GRAY + (ChatColor.ITALIC + "Click to modify percentages"), ChatColor.GRAY + "Current percent: " + ChatColor.GREEN + generator.getItems().get(items.getKey()) + "%");
            Util.setLore(item,  lore);
            getInventory().setItem(slot++, Util.setName(item, ChatColor.DARK_GREEN + plugin.getNMS().getItemName(item)));
        }

        getInventory().setItem(getInventory().getSize() - ROW_SIZE, Util.setName(new ItemStack(Material.ARROW), ChatColor.RED + "Back"));
        getInventory().setItem(getInventory().getSize() - MID_SLOT, Util.setName(new ItemStack(Material.BARRIER), ChatColor.DARK_RED + "Exit"));
        getInventory().setItem(getInventory().getSize() - 1,  Util.setName(XMaterial.WRITABLE_BOOK.parseItem(), ChatColor.GREEN + "Edit blocks"));


        for (int i = 0; i < getInventory().getSize(); i++) {
            if (getInventory().getItem(i) != null) continue;
            if (i < (getInventory().getSize() - ROW_SIZE)) {
                getInventory().setItem(i, empty);
            } else {
                getInventory().setItem(i, border);
            }
        }

        return this;
    }

    @Override
    public void onInventoryClick(InventoryClickEvent event) {

        if (!event.getInventory().equals(this.inventory) || event.getCurrentItem() == null) return;
        event.setCancelled(true);

        Player player = (Player) event.getWhoClicked();

        XMaterial clickedMaterial = XMaterial.matchXMaterial(event.getCurrentItem().getType());

        if (event.getSlot() == (getInventory().getSize() - MID_SLOT)) {
            player.closeInventory();
            return;
        }

        if (event.getSlot() == (getInventory().getSize() - ROW_SIZE)) {
            int rows = (int) (1 + Math.ceil((plugin.getGenerators().size() - 1) / ROW_SIZE));
            player.openInventory(new MainMenu(rows + 2, plugin).init().getInventory());
            return;
        }

        if (event.getSlot() == (getInventory().getSize() - 1)) {
            player.openInventory(new BlockEditorMenu(generator.getName(), 5, plugin).init().getInventory());
            return;
        }

        // If material is already a generator item
        if (generator.getItems().containsKey(clickedMaterial.name())) {
            player.openInventory(new ProbabilityMenu(generator, clickedMaterial, plugin).init().getInventory());
        }
    }

    @Override
    public void onInventoryClose(InventoryCloseEvent event) {
    }

    @Override
    public void onInventoryOpen(InventoryOpenEvent event) {

    }

}
