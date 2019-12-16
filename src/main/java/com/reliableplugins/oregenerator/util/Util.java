package com.reliableplugins.oregenerator.util;

import org.bukkit.ChatColor;

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

}