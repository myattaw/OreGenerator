package com.reliableplugins.oregenerator.menu.impl.action;

import com.google.common.primitives.Ints;
import com.reliableplugins.oregenerator.OreGenerator;
import com.reliableplugins.oregenerator.generator.Generator;
import com.reliableplugins.oregenerator.menu.MenuBuilder;
import com.reliableplugins.oregenerator.util.Message;
import com.reliableplugins.oregenerator.util.Util;
import com.reliableplugins.oregenerator.util.XMaterial;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;

import java.util.AbstractMap;
import java.util.Arrays;

public class ConfirmMenu extends MenuBuilder {

    private final int[] LEFT_SIDE = new int[]{0, 1, 2, 3, 9, 10, 11, 12, 18, 19, 20, 21};
    private final int[] CENTER = new int[]{4, 13, 22,};
    private final int[] RIGHT_SIDE = new int[]{5, 6, 7, 8, 14, 15, 16, 17, 23, 24, 25, 26};

    private final ItemStack ACCEPT = Util.setName(XMaterial.GREEN_STAINED_GLASS_PANE.parseItem(), "&a&lYES");
    private final ItemStack BORDER = Util.setName(XMaterial.BLACK_STAINED_GLASS_PANE.parseItem(), " ");
    private final ItemStack DENY = Util.setName(XMaterial.RED_STAINED_GLASS_PANE.parseItem(), "&c&lNO");

    private int cost;
    private int level;
    private Generator generator;
    private MenuBuilder menuBuilder;

    public ConfirmMenu(String title, MenuBuilder menuBuilder, OreGenerator plugin, Generator generator, int level, int cost) {
        super(title, 3, plugin);
        this.menuBuilder = menuBuilder;
        this.cost = cost;
        this.level = level;
        this.generator = generator;
    }

    @Override
    public ConfirmMenu init() {
        Arrays.stream(LEFT_SIDE).forEach(slot -> getInventory().setItem(slot, ACCEPT));
        Arrays.stream(CENTER).forEach(slot -> getInventory().setItem(slot, BORDER));
        Arrays.stream(RIGHT_SIDE).forEach(slot -> getInventory().setItem(slot, DENY));
        return this;
    }

    @Override
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (Ints.contains(LEFT_SIDE, event.getSlot())) {
            getPlugin().getHookManager().getVault().withdrawPlayer(player, cost);
            player.sendMessage(Util.replace(Message.UPGRADE_PURCHASED.getMessage(), new AbstractMap.SimpleEntry<>("cost", cost), new AbstractMap.SimpleEntry<>("name", generator.getName())));
            getPlugin().getPlayerCache().addLevel(player, generator, level);
            getPlugin().getFileManager().getUserData().saveConfig();
            player.closeInventory();
            menuBuilder.init();
        } else if (Ints.contains(RIGHT_SIDE, event.getSlot())) {
            player.closeInventory();
        }
    }

    @Override
    public void onInventoryClose(InventoryCloseEvent event) {

    }

    @Override
    public void onInventoryOpen(InventoryOpenEvent event) {

    }
}
