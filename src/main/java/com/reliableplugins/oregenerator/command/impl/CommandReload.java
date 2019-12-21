package com.reliableplugins.oregenerator.command.impl;

import com.reliableplugins.oregenerator.command.AbstractCommand;
import com.reliableplugins.oregenerator.command.CommandBuilder;
import com.reliableplugins.oregenerator.util.Message;
import org.bukkit.command.CommandSender;

@CommandBuilder(label = "reload", alias = {}, permission = "oregenerator.reload", description = "reloads config")
public class CommandReload extends AbstractCommand {
    @Override
    public void execute(CommandSender sender, String[] args) {
        getPlugin().getMaterialsConfig().load();
        getPlugin().reloadConfig();
        sender.sendMessage(Message.RELOADED.getMessage());
    }
}
