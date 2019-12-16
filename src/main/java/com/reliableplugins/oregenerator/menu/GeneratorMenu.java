package com.reliableplugins.oregenerator.menu;

import com.reliableplugins.oregenerator.OreGenerator;
import com.reliableplugins.oregenerator.generator.Generator;
import com.reliableplugins.oregenerator.util.Util;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Map;

public class GeneratorMenu extends MenuBuilder {

    private OreGenerator plugin;
    private Generator generator;

    public GeneratorMenu(OreGenerator plugin, Generator generator, String title, int rows) {
        super(title, rows);
        this.plugin = plugin;
        this.generator = generator;
    }

    @Override
    public GeneratorMenu init() {
        String title = plugin.getConfig().getString("generator-menu.item-name");
        List<String> lores = plugin.getConfig().getStringList("generator-menu.item-lore");
        for(Map.Entry<Material, Integer> entry : generator.getPercents().entrySet())
        {
            ItemStack itemStack = new ItemStack(entry.getKey());
            ItemMeta itemMeta = itemStack.getItemMeta();

            // Clean name format
            String name = entry.getKey().name();
            name = name.toLowerCase();
            name = name.substring(0, 1).toUpperCase() + name.substring(1);
            while(true)
            {
                int index = name.indexOf('_');
                if(index == -1) break;
                name = name.substring(0, index)
                        + " "
                        + name.substring(index + 1, index + 2).toUpperCase()
                        + name.substring(index + 2);
            }

            name = Util.color(title.replace("%material%", name));

            itemMeta.setDisplayName(name);
            itemMeta.setLore(Util.color(lores));
            itemStack.setItemMeta(itemMeta);
            getInventory().addItem(itemStack);
        }
        return this;
    }

    @Override
    public void onInventoryClick(InventoryClickEvent event) {
        event.setCancelled(true);

    }

    @Override
    public void onInventoryClose(InventoryCloseEvent event) {

    }

    @Override
    public void onInventoryOpen(InventoryOpenEvent event) {

    }

}
