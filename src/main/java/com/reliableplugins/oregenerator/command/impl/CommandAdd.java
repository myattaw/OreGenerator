package com.reliableplugins.oregenerator.command.impl;

import com.reliableplugins.oregenerator.OreGenerator;
import com.reliableplugins.oregenerator.command.AbstractCommand;
import com.reliableplugins.oregenerator.command.CommandBuilder;
import com.reliableplugins.oregenerator.generator.Generator;
import com.reliableplugins.oregenerator.menu.impl.AddItemMenu;
import com.reliableplugins.oregenerator.util.Message;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandBuilder(label = "add", alias = {"a"}, permission = "oregenerator.add", playerRequired = true)
public class CommandAdd extends AbstractCommand {

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        OreGenerator plugin = getPlugin();

        Generator generator = getPlugin().getGenerators().get("default");

        if (args.length != 0) {
            if (getPlugin().getGenerators().containsKey(args[0].toLowerCase())) {
                generator = getPlugin().getGenerators().get(args[0]);
            } else {
                player.sendMessage(Message.ERROR_INVALID_GENERATOR.getMessage());
                return;
            }
        }

        player.openInventory(new AddItemMenu(generator.getName(), "Click to remove or add", 5, plugin).init().getInventory());
    }

}
