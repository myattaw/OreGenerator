package com.reliableplugins.oregenerator.command.impl;

import com.reliableplugins.oregenerator.command.AbstractCommand;
import com.reliableplugins.oregenerator.command.CommandBuilder;
import com.reliableplugins.oregenerator.generator.Generator;
import com.reliableplugins.oregenerator.menu.GeneratorMenu;
import com.reliableplugins.oregenerator.util.Message;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandBuilder(label = "edit", alias = {"e"}, permission = "oregenerator.edit", description = "Shows menu to edit generator properties", playerRequired = true)
public class CommandEdit extends AbstractCommand {

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        if(args.length != 1)
        {
            player.sendMessage(Message.ERROR_NOT_ENOUGH_ARGS.getMessage());
            return;
        }

        for(Generator g : getPlugin().getGenerators()) {
            if(g.getName().equalsIgnoreCase(args[0]))
            {
                player.openInventory(new GeneratorMenu(getPlugin(), g,
                        getPlugin().getConfig().getString("generator-menu.title"), 1)
                        .init()
                        .getInventory());
                return;
            }
        }
        player.sendMessage(Message.ERROR_INVALID_GENERATOR.getMessage());
    }
}
