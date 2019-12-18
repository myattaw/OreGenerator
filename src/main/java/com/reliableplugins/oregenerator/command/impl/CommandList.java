package com.reliableplugins.oregenerator.command.impl;

import com.reliableplugins.oregenerator.OreGenerator;
import com.reliableplugins.oregenerator.command.AbstractCommand;
import com.reliableplugins.oregenerator.command.CommandBuilder;
import com.reliableplugins.oregenerator.util.Message;
import org.bukkit.command.CommandSender;

@CommandBuilder(label = "list", alias = {"l"}, permission = "oregenerator.list", description = "Shows list of generators")
public class CommandList extends AbstractCommand {

    private OreGenerator plugin;

    public CommandList(OreGenerator plugin) {
        this.plugin = plugin;
    }
    @Override
    public void execute(CommandSender sender, String[] args) {
        StringBuilder message = new StringBuilder();

        for(String name : plugin.getGenerators().keySet())
        {
            message.append(name + ", ");
        }
        message.deleteCharAt(message.lastIndexOf(", "));

        sender.sendMessage(Message.LIST_GENERATORS.getMessage().replace("{LIST}", message.toString()));
    }

}
