package com.reliableplugins.oregenerator.menu.impl;

import com.reliableplugins.oregenerator.OreGenerator;
import com.reliableplugins.oregenerator.generator.Generator;
import com.reliableplugins.oregenerator.menu.MenuBuilder;
import com.reliableplugins.oregenerator.util.Message;
import com.reliableplugins.oregenerator.util.Util;
import com.reliableplugins.oregenerator.util.XMaterial;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class ProbabilityMenu extends MenuBuilder {

    private XMaterial material;
    private Generator generator;
    private OreGenerator plugin;

    private List<String> lore = new ArrayList<>();
    private Map<Integer, Float> slotValue = new HashMap<>();

    public ProbabilityMenu(Generator generator, XMaterial material, OreGenerator plugin) {
        super("Modify percentages", 3, plugin);
        this.material = material;
        this.generator = generator;
        this.plugin = plugin;

        lore.add(ChatColor.GRAY + "Add an item to a generator by clicking");
        lore.add(ChatColor.GRAY + "a material from your inventory.");

        slotValue.put(10, 5.0f);
        slotValue.put(11, 1.0f);
        slotValue.put(12, 0.1f);

        slotValue.put(14, -0.1f);
        slotValue.put(15, -1.0f);
        slotValue.put(16, -5.0f);
    }

    @Override
    public ProbabilityMenu init() {

        getInventory().clear();

        ItemStack itemStack = Util.setName(XMaterial.BLACK_STAINED_GLASS_PANE.parseItem(), " ");

        ItemStack add = XMaterial.LIME_STAINED_GLASS_PANE.parseItem();
        ItemStack rem = XMaterial.RED_STAINED_GLASS_PANE.parseItem();

        ItemStack item = material.parseItem();
        Util.setLore(item, Arrays.asList(ChatColor.GRAY + "Current percent: " + ChatColor.GREEN + generator.getItems().get(material) + "%"));

        getInventory().setItem(10, Util.setName(add, "&7Add &a[+5.0%]"));
        getInventory().setItem(11, Util.setName(add, "&7Add &a[+1.0%]"));
        getInventory().setItem(12, Util.setName(add, "&7Add &a[+0.1%]"));

        getInventory().setItem(13, Util.setName(item, ChatColor.DARK_GREEN + plugin.getNMS().getItemName(item)));

        getInventory().setItem(14, Util.setName(rem, "&7Remove &c[-0.1%]"));
        getInventory().setItem(15, Util.setName(rem, "&7Remove &c[-1.0%]"));
        getInventory().setItem(16, Util.setName(rem, "&7Remove &c[-5.0%]"));

        getInventory().setItem(22, Util.setName(new ItemStack(Material.BARRIER), ChatColor.DARK_RED + "Exit"));

        for (int i = 0; i < getInventory().getSize(); i++) {
            if (getInventory().getItem(i) == null) {
                getInventory().setItem(i, itemStack);
            }
        }

        return this;
    }

    @Override
    public void onInventoryClick(InventoryClickEvent event) {

        float chance = generator.getItems().get(material);

        Player player = (Player) event.getWhoClicked();

        if (event.getSlot() == (getInventory().getSize() - MID_SLOT)) {
            player.closeInventory();
            return;
        }

        if (!slotValue.containsKey(event.getSlot())) return;

        chance += slotValue.get(event.getSlot());
        chance = Math.round(chance * 10f) / 10f;

        // If chance of material will become negative
        if (chance < 0) {
            player.sendMessage(Message.ERROR_PCTG_LOW.getMessage());
            return;
        }

        // If total chances will become above 100
        if (Math.ceil(getPercent() + chance) > 100.0f) {
            player.sendMessage(Message.ERROR_ALREADY_100.getMessage());
            return;
        }

        generator.getItems().put(material, chance);

        // Save new probability into config
        plugin.getMaterialsConfig().save();

        // Update inventory
        init();

    }

    private float getPercent() {
        float percent = 0;
        for (Map.Entry<XMaterial, Float> entry : generator.getItems().entrySet()) {
            if (entry.getKey().equals(material)) continue;
            percent += entry.getValue();
        }
        return percent;
    }

    @Override
    public void onInventoryClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        plugin.getExecutorService().submit(() -> {
            int rows = (int) (1 + Math.ceil((generator.getItems().size() - 1) / ROW_SIZE));
            player.openInventory(new GeneratorMenu(plugin, generator, rows + 2).init().getInventory());
        });
    }

    @Override
    public void onInventoryOpen(InventoryOpenEvent event) {

    }
}
