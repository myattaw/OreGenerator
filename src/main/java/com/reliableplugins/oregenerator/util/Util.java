package com.reliableplugins.oregenerator.util;

import org.bukkit.ChatColor;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;

public class Util {

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

    public static String cleanName(Material material) {
        String name = material.name();
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
        return name;
    }

}