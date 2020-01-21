package com.reliableplugins.oregenerator.command.impl;

import com.reliableplugins.oregenerator.command.AbstractCommand;
import com.reliableplugins.oregenerator.command.CommandBuilder;
import com.reliableplugins.oregenerator.generator.Generator;
import com.reliableplugins.oregenerator.util.Message;
import org.bukkit.command.CommandSender;

@CommandBuilder(label = "create", permission = "oregenerator.create", alias = {"c", "make"}, description = "creates a new generator")
public class CommandCreate extends AbstractCommand {

    @Override
    public void execute(CommandSender sender, String[] args) {
        // If didn't enter a generator name, throw error
        if(args.length != 1) {
            sender.sendMessage(Message.HELP_CREATE_GENERATOR.getMessage());
            return;
        }

        String name = args[0].toLowerCase();

        // If generator already exists, throw error
        if(getPlugin().getGenerators().containsKey(name)) {
            sender.sendMessage(Message.ERROR_GENERATOR_EXISTS.getMessage());
            return;
        }

        Generator newGenerator = new Generator(name);
        getPlugin().getGenerators().put(name, newGenerator);
        getPlugin().getMaterialsConfig().save();
        sender.sendMessage(Message.GENERATOR_CREATED.getMessage().replace("{NAME}", name));
    }
}
