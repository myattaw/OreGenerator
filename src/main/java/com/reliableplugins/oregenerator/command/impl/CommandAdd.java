package com.reliableplugins.oregenerator.command.impl;

import com.reliableplugins.oregenerator.command.AbstractCommand;
import com.reliableplugins.oregenerator.command.CommandBuilder;
import org.bukkit.command.CommandSender;

@CommandBuilder(label = "add", alias = {"a"}, permission = "oregenerator.add")
public class CommandAdd extends AbstractCommand {

    @Override
    public void execute(CommandSender sender, String[] args) {

    }
}
