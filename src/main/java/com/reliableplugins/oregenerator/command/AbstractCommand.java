package com.reliableplugins.oregenerator.command;

import com.google.common.collect.Sets;
import com.reliableplugins.oregenerator.OreGenerator;
import org.bukkit.command.CommandSender;

import java.util.Set;

public abstract class AbstractCommand {

    private String label;
    private String[] alias;
    private String permission;
    private String description;
    private boolean playerRequired;

    private OreGenerator plugin;

    public AbstractCommand() {
        this.label = getClass().getAnnotation(CommandBuilder.class).label();
        this.alias = getClass().getAnnotation(CommandBuilder.class).alias();
        this.permission = getClass().getAnnotation(CommandBuilder.class).permission();
        this.description = getClass().getAnnotation(CommandBuilder.class).description();
        this.playerRequired = getClass().getAnnotation(CommandBuilder.class).playerRequired();
    }

    public abstract void execute(CommandSender sender, String[] args);

    public String getLabel() {
        return label;
    }

    public Set<String> getAlias() {
        return Sets.newHashSet(alias);
    }

    public String getPermission() {
        return permission;
    }

    public String getDescription() {
        return description;
    }

    public void setPlugin(OreGenerator plugin) {
        this.plugin = plugin;
    }

    public OreGenerator getPlugin() {
        return plugin;
    }

    public boolean hasPermission() {
        return permission.length() != 0;
    }

    public boolean isPlayerRequired() {
        return playerRequired;
    }
}