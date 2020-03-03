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

        ItemStack enabled = Util.setName(XMaterial.LIME_STAINED_GLASS_PANE.parseItem(), "&a&lENABLED");
        ItemStack disabled = Util.setName(XMaterial.RED_STAINED_GLASS_PANE.parseItem(), "&c&lDISABLED");

        int slot = ROW_SIZE;

        Generator[] generators = plugin.getPlayerCache().getGenerators(player).stream().toArray(Generator[]::new);

        for (int i = 0; i < ROW_SIZE; i++) {
            if (getInventory().getItem(i) == null) {
                getInventory().setItem(i, border);
            }
        }

        for (int i = 0; i < ROW_SIZE; i++) {

            if (i >= generators.length) {
                getInventory().setItem(slot + ROW_SIZE, empty);
                getInventory().setItem(slot++, empty);
                continue;
            }

            List<String> lore = new ArrayList<>();
            lore.add(ChatColor.GRAY + (ChatColor.ITALIC + "You may only select one generator!"));

            for (Map.Entry<XMaterial, Float> items : generators[i].getItems().entrySet()) {
                if (items.getValue() == 0) continue;
                lore.add(Util.color("&a&l➥ &2" + plugin.getNMS().getItemName(items.getKey().parseItem()) + ":&7 " + items.getValue().floatValue() + "%"));
            }

            Material material = XMaterial.COBBLESTONE.parseMaterial();

            float max = Collections.max(generators[i].getItems().values());

            for (Map.Entry<XMaterial, Float> items : generators[i].getItems().entrySet()) {
                if (items.getValue() == max) {
                    material = items.getKey().parseMaterial();
                }
            }

            String name = generators[i].getName();

            if (plugin.getPlayerCache().getSelected(player) == generators[i]) {
                getInventory().setItem(slot + ROW_SIZE, enabled);
                getInventory().setItem(slot++, Util.setLore(Util.setName(new ItemStack(material), "&a&l" + name.toUpperCase()), lore));
            } else {
                getInventory().setItem(slot + ROW_SIZE, disabled);
                getInventory().setItem(slot++, Util.setLore(Util.setName(new ItemStack(material), "&c&l" + name.toUpperCase()), lore));
            }

        }

        getInventory().setItem(getInventory().getSize() - MID_SLOT, Util.setName(new ItemStack(Material.BARRIER), ChatColor.DARK_RED + "Exit"));

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