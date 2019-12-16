package com.reliableplugins.oregenerator.command.impl;

import com.reliableplugins.oregenerator.command.AbstractCommand;
import com.reliableplugins.oregenerator.command.CommandBuilder;
import com.reliableplugins.oregenerator.menu.GeneratorMenu;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandBuilder(label = "edit", alias = {"e"}, permission = "oregenerator.edit", description = "gives a player a generator", playerRequired = true)
public class CommandEdit extends AbstractCommand {


    @Override
    public void execute(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        player.openInventory(new GeneratorMenu(getPlugin(), getPlugin().getConfig().getString("generator-menu.title"), 1).init().getInventory());

    }

}
