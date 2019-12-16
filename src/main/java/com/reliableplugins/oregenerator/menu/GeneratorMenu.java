package com.reliableplugins.oregenerator.menu;

import com.reliableplugins.oregenerator.OreGenerator;
import com.reliableplugins.oregenerator.generator.GeneratorItem;
import com.reliableplugins.oregenerator.util.Util;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class GeneratorMenu extends MenuBuilder {

    private OreGenerator plugin;

    public GeneratorMenu(OreGenerator plugin, String title, int rows) {
        super(title, rows);
        this.plugin = plugin;
    }

    @Override
    public GeneratorMenu init() {
        String title = plugin.getConfig().getString("generator-menu.item-name");
        List<String> lores = plugin.getConfig().getStringList("generator-menu.item-lore");
        for (GeneratorItem generator : plugin.getGenerators()) {
            ItemStack itemStack = new ItemStack(generator.getMaterial());
            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName(Util.color(title.replace("%material%", generator.getMaterial().name())));
            itemMeta.setLore(Util.color(lores));
            itemStack.setItemMeta(itemMeta);
            getInventory().addItem(itemStack);
        }
        return this;
    }

    @Override
    public void onInventoryClick(InventoryClickEvent event) {

    }

    @Override
    public void onInventoryClose(InventoryCloseEvent event) {

    }

    @Override
    public void onInventoryOpen(InventoryOpenEvent event) {

    }

}
