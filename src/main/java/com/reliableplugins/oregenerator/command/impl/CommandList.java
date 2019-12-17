package com.reliableplugins.oregenerator.command.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.reliableplugins.oregenerator.OreGenerator;
import com.reliableplugins.oregenerator.command.AbstractCommand;
import com.reliableplugins.oregenerator.command.CommandBuilder;
import com.reliableplugins.oregenerator.generator.Generator;
import com.reliableplugins.oregenerator.util.Message;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

@CommandBuilder(label = "list", alias = {"l"}, permission = "oregenerator.list", description = "Shows list of generators")
public class CommandList extends AbstractCommand {

    private OreGenerator plugin;

    public CommandList(OreGenerator plugin) {
        this.plugin = plugin;
    }
    @Override
    public void execute(CommandSender sender, String[] args) {
        StringBuilder message = new StringBuilder();

        for(Generator g : plugin.getGenerators())
        {
            message.append(g.getName() + ", ");
        }
        message.deleteCharAt(message.lastIndexOf(", "));

        sender.sendMessage(Message.LIST_GENERATORS.getMessage().replace("{LIST}", message.toString()));
    }

}
