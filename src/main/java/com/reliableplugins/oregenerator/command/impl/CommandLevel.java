package com.reliableplugins.oregenerator.command.impl;

import com.reliableplugins.oregenerator.command.AbstractCommand;
import com.reliableplugins.oregenerator.command.CommandBuilder;
import com.reliableplugins.oregenerator.generator.Generator;
import com.reliableplugins.oregenerator.util.Message;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

@CommandBuilder(label = "level", alias = {"tier"}, permission = "oregenerator.level", description = "add or remove tiers from generator")
public class CommandLevel extends AbstractCommand {

    @Override
    public void execute(CommandSender sender, String[] args) {

        if(args.length != 2)
        {
            sender.sendMessage(Message.LEVEL_WRONG_ARGS.getMessage());
            return;
        }

        Generator generator = getPlugin().getGenerators().get(args[1].toLowerCase());
        if (generator == null) {
            sender.sendMessage(Message.ERROR_INVALID_GENERATOR.getMessage());
            return;
        }

        if (args[0].toLowerCase().startsWith("add")) {
            generator.addLevel();
            sender.sendMessage(String.format(Message.LEVEL_ADD.getMessage(), generator.getName()));
        } else if (args[0].toLowerCase().startsWith("rem")) {
            generator.removeLevel();
            sender.sendMessage(String.format(Message.LEVEL_REM.getMessage(), generator.getName()));
        }

    }

}
