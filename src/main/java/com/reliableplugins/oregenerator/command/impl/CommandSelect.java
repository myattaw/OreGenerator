package com.reliableplugins.oregenerator.command.impl;

import com.reliableplugins.oregenerator.command.AbstractCommand;
import com.reliableplugins.oregenerator.command.CommandBuilder;
import com.reliableplugins.oregenerator.generator.Generator;
import com.reliableplugins.oregenerator.menu.impl.MainMenu;
import com.reliableplugins.oregenerator.menu.impl.player.SelectorMenu;
import com.reliableplugins.oregenerator.util.Message;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

@CommandBuilder(label = "select", permission = "oregenerator.select", alias = {"pick", "sel"}, description = "select any generator", playerRequired = true)
public class CommandSelect extends AbstractCommand {

    @Override
    public void execute(CommandSender sender, String[] args) {

        Player player = (Player) sender;

        List<Generator> generators = getPlugin().getPlayerCache().getGenerators(player);

        if (generators == null || generators.size() != getPlugin().getGenerators().values().size()) {
            getPlugin().getPlayerCache().addPlayer(player);
        }

        int rows = (int) (1 + Math.ceil((getPlugin().getPlayerCache().getGenerators(player).size() - 1) / 9));
        player.openInventory(new SelectorMenu(player, rows + 2, getPlugin()).init().getInventory());
    }

}
