package com.reliableplugins.oregenerator.menu.impl.player;

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

import java.util.*;

public class SelectorMenu extends MenuBuilder {

    private OreGenerator plugin;
    private Player player;

    private Map<Integer, Generator> generators = new HashMap<>();

    public SelectorMenu(Player player, int rows, OreGenerator plugin) {
        super("Generator Selector", rows, plugin);
        this.plugin = plugin;
        this.player = player;
        for (int i = 0; i < plugin.getPlayerCache().getGenerators(player).size(); i++) {
            generators.put(ROW_SIZE + i, plugin.getPlayerCache().getGenerators(player).get(i));
        }
    }

    @Override
    public SelectorMenu init() {

        getInventory().clear();

        ItemStack border = Util.setName(XMaterial.BLACK_STAINED_GLASS_PANE.parseItem(), " ");
        ItemStack empty = Util.setName(XMaterial.GRAY_STAINED_GLASS_PANE.parseItem(), " ");

        ItemStack enabled = XMaterial.LIME_STAINED_GLASS_PANE.parseItem();
        ItemStack disabled = XMaterial.RED_STAINED_GLASS_PANE.parseItem();

        for (int i = 0; i < ROW_SIZE; i++) {
            if (getInventory().getItem(i) == null) {
                getInventory().setItem(i, border);
            }
        }

        int slot = ROW_SIZE;

        for (Generator generator : plugin.getPlayerCache().getGenerators(player)) {

            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.GRAY + (ChatColor.ITALIC + "You may only select one generator!"));

            for (Map.Entry<Material, Float> items : generator.getItems().entrySet()) {
                lore.add(Util.color("&a&l* &2" + plugin.getNMS().getItemName(new ItemStack(items.getKey())) + ":&7 " + items.getValue().floatValue() + "%"));
            }

            String name = generator.getName();
            if (plugin.getPlayerCache().getSelected(player) == generator) {
                getInventory().setItem(slot++, Util.setLore(Util.setName(enabled, "&a&l" + name.toUpperCase()), lore));
            } else {
                getInventory().setItem(slot++, Util.setLore(Util.setName(disabled, "&c&l" + name.toUpperCase()), lore));
            }
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
        // add 1 second cooldown

        if (event.getSlot() == (getInventory().getSize() - MID_SLOT)) {
            player.closeInventory();
            return;
        }

        Generator generator = generators.get(event.getSlot());
        if (generator != null) {
            plugin.getPlayerCache().setGenerator(player, generator);
            init();

        }

    }

    @Override
    public void onInventoryClose(InventoryCloseEvent event) {

    }

    @Override
    public void onInventoryOpen(InventoryOpenEvent event) {

    }
}