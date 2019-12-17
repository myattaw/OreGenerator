package com.reliableplugins.oregenerator.command.impl;

import com.reliableplugins.oregenerator.OreGenerator;
import com.reliableplugins.oregenerator.command.AbstractCommand;
import com.reliableplugins.oregenerator.command.CommandBuilder;
import com.reliableplugins.oregenerator.generator.Generator;
import com.reliableplugins.oregenerator.util.Message;
import com.reliableplugins.oregenerator.util.Util;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandBuilder(label = "add", alias = {"a"}, permission = "oregenerator.add", playerRequired = true)
public class CommandAdd extends AbstractCommand {

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        OreGenerator plugin = getPlugin();

        if(args.length >= 1) {
            Generator generator = plugin.generatorUtil.getGenerator(args[0]);
            if(generator == null) {
                player.sendMessage(Message.ERROR_INVALID_GENERATOR.getMessage());
                return;
            }

           Material toAdd = player.getItemInHand().getType();
            int percent;
            switch(args.length) {
                // Just add item with 0%
                case 1:
                    generator.addItem(toAdd, 0);
                    break;

                // Add item with given %
                case 2:
                    // Get percent from string
                    try {
                        percent = Integer.parseInt(args[1]);
                    }
                    catch (NumberFormatException e) {
                        player.sendMessage(Message.ERROR_PCTG_INVALID.getMessage());
                        return;
                    }

                    // Percent too high
                    if(percent + generator.getPercentTotal() > 100)
                    {
                        player.sendMessage(Message.ERROR_PCTG_HIGH.getMessage()
                                .replace("{MAX}", Integer.toString(100 - generator.getPercentTotal())));
                        return;
                    }

                    // Percent too low
                    else if(percent < 0)
                    {
                        player.sendMessage(Message.ERROR_PCTG_LOW.getMessage());
                        return;
                    }

                    // Good percent
                    else
                    {
                        generator.addItem(toAdd, percent);
                        player.sendMessage(Message.MATERIAL_ADDED.getMessage()
                                .replace("{MATERIAL}", Util.cleanName(toAdd))
                                .replace("{PROBABILITY}", Integer.toString(percent)));
                    }
                    break;

                default:
                    player.sendMessage(Message.ERROR_TOO_MANY_ARGS.getMessage());
                    return;
            }

            // Save new material into config and load the new config
            plugin.getMaterialsConfig().save();
            plugin.getMaterialsConfig().load();
        }
        else
        {
            player.sendMessage(Message.ERROR_NOT_ENOUGH_ARGS.getMessage());
        }
    }
}
