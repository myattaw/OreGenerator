package com.reliableplugins.oregenerator.util;

import org.bukkit.ChatColor;

public enum Message {
    LIST_GENERATORS(Util.color("&7Generators: {LIST}")),
    ERROR_PERMISSION(Util.color("&cNo permissions")),
    ERROR_NOT_PLAYER(Util.color("&cOnly players may execute this command"));


    private final String text;
    private final String header = Util.color("&8&l(&2&lOreGenerator&8&l)&r ");

    Message(String text) {
        this.text = text;
    }

    public String getMessage() {
        return header + this.text;
    }
}
