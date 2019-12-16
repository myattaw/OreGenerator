package com.reliableplugins.oregenerator.menu;

import com.reliableplugins.oregenerator.OreGenerator;
import com.reliableplugins.oregenerator.generator.Generator;
import com.reliableplugins.oregenerator.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GeneratorMenu extends MenuBuilder implements Listener {

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

        for(Map.Entry<Material, Integer> entry : generator.getPercents().entrySet())
        {
            ItemStack itemStack = new ItemStack(entry.getKey());
            ItemMeta itemMeta = itemStack.getItemMeta();

            // Set probability and lore
            int probability = entry.getValue();
            List<String> itemLores = new ArrayList<>(lores); // Need to copy to not overwrite %percent% permanently
            itemLores.set(index, lores.get(index).replace("%percent%", Integer.toString(probability)));
            itemMeta.setLore(Util.color(itemLores));

            // Set name
            String itemName = Util.cleanName(entry.getKey());
            itemName = Util.color(title.replace("%material%", itemName));
            itemMeta.setDisplayName(itemName);

            // Implement customizations
            itemStack.setItemMeta(itemMeta);
            getInventory().addItem(itemStack);
        }
        return this;
    }

    @Override
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        event.setCancelled(true);

        Player player = (Player) event.getWhoClicked();
        Material clickedMaterial = event.getCurrentItem().getType();
        int chance = generator.getPercents().get(clickedMaterial);

        switch(event.getClick()) {
            case LEFT:
                chance = chance - 1;
                break;

            case RIGHT:
                chance = chance + 1;
                break;

            default:
                break;
        }

        // If chance is between 0 and 100 inclusive, update chance
        if(chance >= 0 && chance <= 100) {
            generator.getPercents().put(clickedMaterial, chance);

            // Save new probability into config
            plugin.getMaterialsConfig().save();

            // Update inventory
            player.openInventory(new GeneratorMenu(plugin, generator,
                    plugin.getConfig().getString("generator-menu.title"), 1)
                    .init()
                    .getInventory());

            // Kill this listener
            InventoryClickEvent.getHandlerList().unregister(this);
        }
    }

    @Override
    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {

    }

    @Override
    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event) {

    }

}
