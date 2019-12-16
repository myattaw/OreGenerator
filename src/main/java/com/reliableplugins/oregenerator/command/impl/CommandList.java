package com.reliableplugins.oregenerator.command.impl;

import com.reliableplugins.oregenerator.command.AbstractCommand;
import com.reliableplugins.oregenerator.command.CommandBuilder;
import org.bukkit.command.CommandSender;

@CommandBuilder(label = "list", alias = {"l"}, permission = "oregenerator.list", description = "gives a player a generator")
public class CommandList extends AbstractCommand {

    @Override
    public void execute(CommandSender sender, String[] args) {

    }

}
