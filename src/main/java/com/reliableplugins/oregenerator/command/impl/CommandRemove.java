package com.reliableplugins.oregenerator.command.impl;

import com.reliableplugins.oregenerator.command.AbstractCommand;
import com.reliableplugins.oregenerator.command.CommandBuilder;
import org.bukkit.command.CommandSender;

@CommandBuilder(label = "remove", alias = {"rem", "remove", "del", "delete"})
public class CommandRemove extends AbstractCommand {

    @Override
    public void execute(CommandSender sender, String[] args) {
        // TODO: make alias system for blocks so player doesn't need to enter ID
    }
}
