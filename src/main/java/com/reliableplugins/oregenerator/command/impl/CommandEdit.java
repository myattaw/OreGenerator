package com.reliableplugins.oregenerator.command.impl;

import com.reliableplugins.oregenerator.command.AbstractCommand;
import com.reliableplugins.oregenerator.command.CommandBuilder;
import com.reliableplugins.oregenerator.generator.Generator;
import com.reliableplugins.oregenerator.menu.impl.GeneratorMenu;
import com.reliableplugins.oregenerator.menu.impl.MainMenu;
import com.reliableplugins.oregenerator.util.Message;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandBuilder(label = "edit", alias = {"e"}, permission = "oregenerator.edit", description = "edit generator's properties", playerRequired = true)
public class CommandEdit extends AbstractCommand {

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        Generator generator;

        if (args.length != 0) {

            // If arg is a valid generator, open its menu
            if (getPlugin().getGenerators().containsKey(args[0].toLowerCase())) {
                generator = getPlugin().getGenerators().get(args[0]);

                int rows = (int) (1 + Math.ceil((generator.getItems().size() - 1) / 9));
                getPlugin().setGeneratorMenu(generator, new GeneratorMenu(getPlugin(), generator, rows).init());

            // If arg isn't a valid generator, throw error
            } else {
                player.sendMessage(Message.ERROR_INVALID_GENERATOR.getMessage());
            }

        // Else open main menu
        } else {
            int rows = (int) (1 + Math.ceil((getPlugin().getGenerators().size() - 1) / 9));
            player.openInventory(new MainMenu(rows + 2, getPlugin()).init().getInventory());
        }

    }
}
