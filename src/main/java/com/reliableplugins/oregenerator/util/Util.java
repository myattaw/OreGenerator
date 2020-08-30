package com.reliableplugins.oregenerator.util;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Util {

    private static final int[] NUMBERS = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
    private static final String[] LETTERS = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

    public static String intToRoman(int num) {
        StringBuilder roman = new StringBuilder();
        for (int i = 0; i < NUMBERS.length; i++) {
            while (num >= NUMBERS[i]) {
                num -= NUMBERS[i];
                roman.append(LETTERS[i]);
            }
        }
        return roman.toString();
    }


    public static List<String> updateLore(List<String> lore, Map.Entry<String, Object>... placeholders) {
        List<String> newLore = new ArrayList<>();
        for (String line : lore) {
            for (Map.Entry<String, Object> placeholder : placeholders) {
                if (line.toUpperCase().contains(placeholder.getKey().toUpperCase())) {
                    line = line.replace(String.format("[%s]", placeholder.getKey().toUpperCase()), String.valueOf(placeholder.getValue()));
                }
            }
            newLore.add(line);
        }
        return newLore;
    }

    public static String replace(String string, Map.Entry<String, Object>... replacements) {
        for (Map.Entry<String, Object> entry : replacements) {
            string = string.replace(String.format("[%s]", entry.getKey().toUpperCase()), String.valueOf(entry.getValue()).toUpperCase());
        }
        return string;
    }

    public static String color(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static List<String> color(List<String> string) {
        List<String> colored = new ArrayList<>();
        for (String line : string) {
            colored.add(color(line));
        }
        return colored;
    }

    public static ItemStack setName(ItemStack itemStack, String name) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(color(name));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack setLore(ItemStack itemStack, List<String> lore) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setLore(color(lore));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

}