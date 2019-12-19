package com.reliableplugins.oregenerator.menu;

import com.reliableplugins.oregenerator.OreGenerator;
import com.reliableplugins.oregenerator.generator.Generator;
import com.reliableplugins.oregenerator.util.Util;
import com.reliableplugins.oregenerator.util.XMaterial;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GeneratorMenu extends MenuBuilder {

    private OreGenerator plugin;
    private Generator generator;

    public GeneratorMenu(OreGenerator plugin, Generator generator, String title, int rows) {
        super(title, rows, plugin);
        this.plugin = plugin;
        this.generator = generator;
    }

    @Override
    public GeneratorMenu init() {
        String title = plugin.getConfig().getString("generator-menu.item-name");
        List<String> lores = plugin.getConfig().getStringList("generator-menu.item-lore");

        int index = 0;

        for(String s : lores)
        {
            if(s.contains("%percent%")) break;
            index++;
        }

        for(Map.Entry<Material, Float> entry : generator.getItems().entrySet())
        {
            ItemStack itemStack = new ItemStack(entry.getKey());
            ItemMeta itemMeta = itemStack.getItemMeta();

            // Set probability and lore
            float probability = entry.getValue();
            List<String> itemLores = new ArrayList<>(lores); // Need to copy to not overwrite %percent% permanently
            itemLores.set(index, lores.get(index).replace("%percent%", Float.toString(probability)));
            itemMeta.setLore(Util.color(itemLores));

            // Set name
            String itemName = plugin.getNMS().getItemName(itemStack);
            itemName = Util.color(title.replace("%material%", itemName));
            itemMeta.setDisplayName(itemName);

            // Implement customizations
            itemStack.setItemMeta(itemMeta);
            getInventory().addItem(itemStack);
        }

        ItemStack itemStack = Util.setName(XMaterial.BLACK_STAINED_GLASS_PANE.parseItem(), " ");

        for (int i = 0; i < getInventory().getSize(); i++) {
            if (getInventory().getItem(i) == null) {
                getInventory().setItem(i, itemStack);
            }
        }

        return this;
    }

    @Override
    public void onInventoryClick(InventoryClickEvent event) {

        if(!event.getInventory().equals(this.inventory)) return;
        event.setCancelled(true);

        Player player = (Player) event.getWhoClicked();
        Material clickedMaterial = event.getCurrentItem().getType();

        player.openInventory(new ProbabilityMenu("this", generator, clickedMaterial, plugin).init().getInventory());

    }

    @Override
    public void onInventoryClose(InventoryCloseEvent event) {

    }

    @Override
    public void onInventoryOpen(InventoryOpenEvent event) {

    }

}
