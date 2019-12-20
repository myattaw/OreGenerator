package com.reliableplugins.oregenerator.command.impl;

import com.reliableplugins.oregenerator.command.AbstractCommand;
import com.reliableplugins.oregenerator.command.CommandBuilder;
import com.reliableplugins.oregenerator.generator.Generator;
import com.reliableplugins.oregenerator.menu.impl.MainMenu;
import com.reliableplugins.oregenerator.util.Message;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandBuilder(label = "edit", alias = {"e"}, permission = "oregenerator.edit", description = "edit generator's properties", playerRequired = true)
public class CommandEdit extends AbstractCommand {

    @Override
    public void execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;

        Generator generator = getPlugin().getGenerators().get("default");

        if (args.length != 0) {
            if (getPlugin().getGenerators().containsKey(args[0].toLowerCase())) {
                generator = getPlugin().getGenerators().get(args[0]);
            } else {
                player.sendMessage(Message.ERROR_INVALID_GENERATOR.getMessage());
                return;
            }
        }

//        player.openInventory(new ProbabilityMenu("this", Material.COBBLESTONE, getPlugin()).init().getInventory());
          player.openInventory(new MainMenu("Main Menu", 5, getPlugin()).init().getInventory());
//        player.openInventory(new GeneratorMenu(getPlugin(), generator, getPlugin().getConfig().getString("generator-menu.title"), 1).init().getInventory());
    }
}
