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
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainMenu extends MenuBuilder {

    private OreGenerator plugin;

    public MainMenu( String title, int rows, OreGenerator plugin) {
        super(title, rows, plugin);
        this.plugin = plugin;
    }

    @Override
    public MainMenu init() {
        ItemStack border = Util.setName(XMaterial.BLACK_STAINED_GLASS_PANE.parseItem(), " ");
        ItemStack empty = Util.setName(XMaterial.GRAY_STAINED_GLASS_PANE.parseItem(), " ");

        getInventory().clear();

        for (int i = 0; i < ROW_SIZE; i++) {
            if (getInventory().getItem(i) == null) {
                getInventory().setItem(i, border);
            }
        }

        int slot = ROW_SIZE;
        for (Map.Entry<String, Generator> generators : plugin.getGenerators().entrySet()) {
            ItemStack itemStack = Util.setName(XMaterial.GREEN_STAINED_GLASS_PANE.parseItem(), ChatColor.DARK_GREEN + (ChatColor.BOLD + generators.getKey().toUpperCase()));
            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.GRAY + (ChatColor.ITALIC + "Click to modify generator."));
            for (Map.Entry<Material, Float> percents : generators.getValue().getItems().entrySet()) {
                lore.add(Util.color("&a&l* &2" + plugin.getNMS().getItemName(new ItemStack(percents.getKey())) + ":&7 " + percents.getValue().floatValue() + "%"));
            }
            lore.add("");
            lore.add(ChatColor.DARK_GREEN + "Permission:");
            lore.add(ChatColor.GRAY + "oregenerator.use." + generators.getKey().toLowerCase());
            getInventory().setItem(slot++,  Util.setLore(itemStack, lore));
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

        ItemStack itemStack = event.getCurrentItem();
        if (itemStack == null || !itemStack.hasItemMeta() || !itemStack.getItemMeta().hasDisplayName()) return;

        Player player = (Player) event.getWhoClicked();

        if (event.getSlot() == (getInventory().getSize() - MID_SLOT)) {
            player.closeInventory();
            return;
        }

        String itemName = ChatColor.stripColor(itemStack.getItemMeta().getDisplayName().toLowerCase());

        if (plugin.getGenerators().containsKey(itemName)) {
            Generator generator = plugin.getGenerators().get(itemName);
            int rows = (int) (1 + Math.ceil((generator.getItems().size() - 1) / ROW_SIZE));
            player.openInventory(new GeneratorMenu(plugin, generator, plugin.getConfig().getString("generator-menu.title"), rows + 2).init().getInventory());
        }

    }

    @Override
    public void onInventoryClose(InventoryCloseEvent event) {

    }

    @Override
    public void onInventoryOpen(InventoryOpenEvent event) {

    }
}
