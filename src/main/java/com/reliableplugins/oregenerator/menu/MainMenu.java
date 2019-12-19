package com.reliableplugins.oregenerator.menu;

import com.reliableplugins.oregenerator.OreGenerator;
import com.reliableplugins.oregenerator.generator.Generator;
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
            ItemStack itemStack = Util.setName(XMaterial.GREEN_STAINED_GLASS_PANE.parseItem(), ChatColor.DARK_GREEN + generators.getKey());
            List<String> lore = new ArrayList<>();
            for (Map.Entry<Material, Float> percents : generators.getValue().getItems().entrySet()) {
                lore.add(ChatColor.GRAY + plugin.getNMS().getItemName(new ItemStack(percents.getKey())) + ": " + ChatColor.GREEN + percents.getValue().floatValue() + "%");
            }
            lore.add("");
            lore.add(ChatColor.GRAY + "Click to modify block percentages.");
            getInventory().setItem(slot++,  Util.setLore(itemStack, lore));
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

        ItemStack itemStack = event.getCurrentItem();
        if (!itemStack.hasItemMeta() || !itemStack.getItemMeta().hasDisplayName()) return;

        Player player = (Player) event.getWhoClicked();

        if (itemStack.getType() == Material.BARRIER) {
            player.closeInventory();
            return;
        }

        String itemName = ChatColor.stripColor(itemStack.getItemMeta().getDisplayName());

        if (plugin.getGenerators().containsKey(itemName)) {
            Generator generator = plugin.getGenerators().get(itemName);
            int rows = (int) (1 + Math.ceil((generator.getItems().size() - 1) / 9));
            player.openInventory(new GeneratorMenu(plugin, generator, plugin.getConfig().getString("generator-menu.title"), rows).init().getInventory());
        }

    }

    @Override
    public void onInventoryClose(InventoryCloseEvent event) {

    }

    @Override
    public void onInventoryOpen(InventoryOpenEvent event) {

    }
}
