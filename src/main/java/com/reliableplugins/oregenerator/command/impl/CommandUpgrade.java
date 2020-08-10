package com.reliableplugins.oregenerator.command.impl;

import com.reliableplugins.oregenerator.command.AbstractCommand;
import com.reliableplugins.oregenerator.command.CommandBuilder;
import com.reliableplugins.oregenerator.menu.impl.player.UpgradeMenu;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandBuilder(label = "upgrade", permission = "oregenerator.upgrade", alias = {"level"}, description = "select any generator", playerRequired = true)
public class CommandUpgrade extends AbstractCommand {

    @Override
    public void execute(CommandSender sender, String[] args) {

        Player player = (Player) sender;

        int rows = (int) (1 + Math.ceil((getPlugin().getPlayerCache().getGenerators(player).size() - 1) / 9));
//        player.openInventory(new SelectorMenu(player, rows + 3, getPlugin()).init().getInventory());
        player.openInventory(new UpgradeMenu(rows + 3, getPlugin()).init().getInventory());
    }

}