package com.reliableplugins.oregenerator.menu.impl;

import com.reliableplugins.oregenerator.OreGenerator;
import com.reliableplugins.oregenerator.generator.Generator;
import com.reliableplugins.oregenerator.generator.GeneratorItems;
import com.reliableplugins.oregenerator.menu.MenuBuilder;
import com.reliableplugins.oregenerator.util.Util;
import com.reliableplugins.oregenerator.util.XMaterial;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class MainMenu extends MenuBuilder {

    private OreGenerator plugin;

    private Map<Integer, Map<XMaterial, Float>> slotItems = new HashMap<>();

    public MainMenu(int rows, OreGenerator plugin) {
        super(plugin.getConfig().getString("gui-menus.titles.main-menu"), rows, plugin);
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
        Material material = XMaterial.LIME_STAINED_GLASS_PANE.parseMaterial();

        for (Map.Entry<String, Generator> entry : plugin.getGenerators().entrySet()) {

            Generator generator = entry.getValue();

            for (int level = 1; level <= generator.getMaxLevel(); level++) {
                float max = Collections.max(generator.getByLevel(level).getItems().values());

                for (Map.Entry<XMaterial, Float> items : generator.getByLevel(level).getItems().entrySet()) {
                    if (items.getValue() == max) {
                        material = items.getKey().parseMaterial();
                    }
                }

                String itemName = Util.replace(plugin.getConfig().getString("main-menu.generator.name"), new AbstractMap.SimpleEntry<>("name", generator.getName()), new AbstractMap.SimpleEntry<>("level", Util.intToRoman(level)));
                ItemStack itemStack = Util.setName(new ItemStack(material), itemName);

                List<String> lore = plugin.getConfig().getStringList("main-menu.generator.lore");
                slotItems.put(slot, generator.getByLevel(level).getItems());
                getInventory().setItem(slot++, Util.setLore(itemStack, Util.updateLore(lore, new AbstractMap.SimpleEntry<>("max", generator.getMaxLevel()), new AbstractMap.SimpleEntry<>("name", generator.getName()))));
            }

        }

        getInventory().setItem(getInventory().getSize() - MID_SLOT, Util.setName(new ItemStack(Material.BARRIER), plugin.getConfig().getString("gui-menus.buttons.exit-name")));

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

        Map<XMaterial, Float> items = slotItems.get(event.getSlot());

        if (items != null) {
            Generator generator = plugin.getGenerators().get(itemName);
            int rows = (int) (1 + Math.ceil((items.size() - 1) / ROW_SIZE));
            plugin.setGeneratorMenu(generator, new GeneratorMenu(plugin, generator, items, rows + 2).init());
            player.openInventory(plugin.getGeneratorMenu(generator).getInventory());
        }

    }

    @Override
    public void onInventoryClose(InventoryCloseEvent event) {

    }

    @Override
    public void onInventoryOpen(InventoryOpenEvent event) {

    }
}
