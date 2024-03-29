package com.reliableplugins.oregenerator.menu.impl.player;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.reliableplugins.oregenerator.OreGenerator;
import com.reliableplugins.oregenerator.generator.Generator;
import com.reliableplugins.oregenerator.menu.MenuBuilder;
import com.reliableplugins.oregenerator.menu.impl.action.ConfirmMenu;
import com.reliableplugins.oregenerator.util.Message;
import com.reliableplugins.oregenerator.util.Util;
import com.reliableplugins.oregenerator.util.XMaterial;
import com.reliableplugins.oregenerator.util.pair.Pair;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.lang.reflect.Field;
import java.util.*;

public class SelectorMenu extends MenuBuilder {

    private OreGenerator plugin;
    private Player player;

    private Map<Integer, Pair<Generator, Integer>> generators = new HashMap<>();

    private String DISABLED = ChatColor.RED + ChatColor.BOLD.toString();
    private String ENABLED = ChatColor.GREEN + ChatColor.BOLD.toString();

    private final int DEFAULT_LEVEL = 1;

    public SelectorMenu(Player player, int rows, OreGenerator plugin) {
        super("Generator Selector", rows, plugin);
        this.plugin = plugin;
        this.player = player;
    }

    @Override
    public SelectorMenu init() {

        int slot = ROW_SIZE;

        getInventory().clear();
        this.generators.clear();

        for (Pair<Generator, Integer> generator : plugin.getPlayerCache().getGenerators(player)) {
            if (generator.getValue() == 1) {
                for (int level = 1; level <= generator.getKey().getMaxLevel(); level++) {
                    this.generators.put(slot++, Pair.of(generator.getKey(), level));
                }
            }
        }

        ItemStack border = Util.setName(XMaterial.BLACK_STAINED_GLASS_PANE.parseItem(), " ");
        ItemStack empty = Util.setName(XMaterial.GRAY_STAINED_GLASS_PANE.parseItem(), " ");

        ItemStack enabled = Util.setName(XMaterial.LIME_STAINED_GLASS_PANE.parseItem(), ENABLED + "ENABLED");
        ItemStack disabled = Util.setName(XMaterial.ORANGE_STAINED_GLASS_PANE.parseItem(), DISABLED + "DISABLED");

        for (int i = 0; i < ROW_SIZE; i++) {
            if (getInventory().getItem(i) == null) {
                getInventory().setItem(i, border);
            }
        }

        Material material = XMaterial.COBBLESTONE.parseMaterial();

        for (Map.Entry<Integer, Pair<Generator, Integer>> generators : this.generators.entrySet()) {

            Generator generator = generators.getValue().getKey();

            int level = generators.getValue().getValue();
            int playerLevel = plugin.getFileManager().getUserData().getConfig().getInt(String.format("%s.%s", player.getUniqueId(), generator.getName()));

            float max = Collections.max(generator.getByLevel(level).getItems().values());

            for (Map.Entry<XMaterial, Float> items : generator.getByLevel(level).getItems().entrySet()) {
                if (items.getValue() == max) {
                    material = items.getKey().parseMaterial();
                }
            }

            List<String> lore = new ArrayList<>();

            Pair<Generator, Integer> generatorLevel = plugin.getPlayerCache().getSelected(player);

            ItemStack itemStack;
            String name = Util.replace(plugin.getConfig().getString("select-menu.item-name"), Pair.of("name", generator.getName().toUpperCase()), Pair.of("level", Util.intToRoman(level)));
            List<String> statusLore;

            if (generatorLevel.getKey() == generator && generatorLevel.getValue() == level) {
                itemStack = Util.setName(new ItemStack(material), name);
                statusLore = plugin.getConfig().getStringList("select-menu.status.selected");
            } else if (level <= playerLevel || level == DEFAULT_LEVEL) {
                itemStack = Util.setName(enabled, name);
                statusLore = plugin.getConfig().getStringList("select-menu.status.disabled");
            } else {
                itemStack = Util.setName(disabled, name);
                statusLore = plugin.getConfig().getStringList("select-menu.status.locked");
            }

            for (String line : plugin.getConfig().getStringList("select-menu.item-lore")) {
                if (line.contains("[ITEMS]")) {
                    for (Map.Entry<XMaterial, Float> items : generator.getByLevel(level).getItems().entrySet()) {
                        if (items.getValue() == 0) continue;
                        lore.add(Util.replace(plugin.getConfig().getString("select-menu.item-line"), Pair.of("percent", items.getValue().floatValue()), Pair.of("block", plugin.getNMS().getItemName(items.getKey().parseItem()))));
                    }
                } else if (line.contains("[STATUS]")){
                    int cost = plugin.getConfig().getInt(String.format("level-cost.%s.%d", generator.getName().toLowerCase(), level));
                    for (String status : statusLore) {
                        lore.add(Util.replace(status, Pair.of("cost", cost), Pair.of("status", status)));
                    }
                } else {
                    lore.add(line);
                }
            }

            getInventory().setItem(generators.getKey(), Util.setLore(itemStack, lore));

        }

        getInventory().setItem(getInventory().getSize() - MID_SLOT, Util.setName(new ItemStack(Material.BARRIER), ChatColor.DARK_RED + "Exit"));

//        getInventory().setItem(getInventory().getSize() - ROW_SIZE, getSkull("http://textures.minecraft.net/texture/bb0f6e8af46ac6faf88914191ab66f261d6726a7999c637cf2e4159fe1fc477"));
//        getInventory().setItem(getInventory().getSize() - 1, getSkull("http://textures.minecraft.net/texture/f2f3a2dfce0c3dab7ee10db385e5229f1a39534a8ba2646178e37c4fa93b"));

        for (int i = ROW_SIZE; i < getInventory().getSize() - ROW_SIZE; i++) {
            if (getInventory().getItem(i) == null) {
                getInventory().setItem(i, empty);
            }
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

        Pair<Generator, Integer> generator = generators.get(event.getSlot());
        if (generator != null) {

            int playerLevel = plugin.getFileManager().getUserData().getConfig().getInt(String.format("%s.%s", player.getUniqueId(), generator.getKey().getName()));

            if (generator.getValue() > playerLevel && generator.getValue() != DEFAULT_LEVEL) {

                //TODO: check if player has previous generator unlocked.
                int cost = plugin.getConfig().getInt(String.format("level-cost.%s.%d", generator.getKey().getName().toLowerCase(), generator.getValue()));

                if (generator.getValue() == playerLevel + DEFAULT_LEVEL) {

                    if (plugin.getHookManager().getVault().canAfford(player, cost)) {
                        String title = Util.replace(Message.UPGRADE_CONFIRM_TITLE.getText(), Pair.of("cost", cost));
                        player.openInventory(new ConfirmMenu(title, this, plugin, generator.getKey(), generator.getValue(), cost).init().getInventory());
                    } else {
                        player.sendMessage(Message.ERROR_NOT_ENOUGH_MONEY.getMessage());
                    }
                } else {
                    player.sendMessage(Message.UPGRADE_PREVIOUS.getMessage());
                }

            } else {
                plugin.getPlayerCache().setGenerator(player, generator.getKey(), generator.getValue());
                init();
            }
        }

    }

    @Override
    public void onInventoryClose(InventoryCloseEvent event) {

    }

    @Override
    public void onInventoryOpen(InventoryOpenEvent event) {

    }

    public ItemStack getSkull(final String url) {
        ItemStack skull = XMaterial.PLAYER_HEAD.parseItem();
        if (url == null || url.isEmpty()) {
            return skull;
        }
        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        String encodedData = Base64Coder.encodeString(String.format("{textures:{SKIN:{url:\"%s\"}}}", url));
        profile.getProperties().put("textures", new Property("textures", encodedData));
        Field profileField = null;
        try {
            profileField = skullMeta.getClass().getDeclaredField("profile");
        } catch (NoSuchFieldException | SecurityException e) {
            e.printStackTrace();
        }
        profileField.setAccessible(true);
        try {
            profileField.set(skullMeta, profile);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
        skull.setItemMeta(skullMeta);
        return skull;
    }

}