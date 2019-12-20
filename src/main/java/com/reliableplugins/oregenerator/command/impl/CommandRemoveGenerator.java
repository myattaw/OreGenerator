package com.reliableplugins.oregenerator.command.impl;

import com.reliableplugins.oregenerator.command.AbstractCommand;
import com.reliableplugins.oregenerator.command.CommandBuilder;
import com.reliableplugins.oregenerator.util.Message;
import org.bukkit.command.CommandSender;

@CommandBuilder(label = "remove", alias = {"delete", "del", "rem"}, permission = "oregenerator.remove", description = "remove a generator")
public class CommandRemoveGenerator extends AbstractCommand {
    @Override
    public void execute(CommandSender sender, String[] args) {
        // If name not entered
        if(args.length != 1)
        {
            sender.sendMessage(Message.HELP_REMOVE_GENERATOR.getMessage());
            return;
        }

        String name = args[0];

        // If generator doesn't exist
        if(!getPlugin().getGenerators().containsKey(name))
        {
            sender.sendMessage(Message.ERROR_INVALID_GENERATOR.getMessage());
            return;
        }

        getPlugin().getGenerators().remove(name);
        getPlugin().getMaterialsConfig().save();
        sender.sendMessage(Message.GENERATOR_REMOVED.getMessage().replace("{NAME}", name));
    }
}
