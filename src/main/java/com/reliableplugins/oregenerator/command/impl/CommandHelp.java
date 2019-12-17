package com.reliableplugins.oregenerator.command.impl;

import com.reliableplugins.oregenerator.command.AbstractCommand;
import com.reliableplugins.oregenerator.command.BaseCommand;
import com.reliableplugins.oregenerator.command.CommandBuilder;
import com.reliableplugins.oregenerator.util.Util;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandBuilder(label = "help", alias = {"h"}, permission = "oregenerator.help")
public class CommandHelp extends AbstractCommand {

    private BaseCommand baseCommand;

    private String header = "&7&m----------&7[ &2OreGenerator &7]&m----------";

    private String line = "&2/oregenerator %s &2%s";

    private String footer = "&7&oHover to view permissions";

    public CommandHelp(BaseCommand baseCommand) {
        this.baseCommand = baseCommand;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        AbstractCommand[] commands = baseCommand.getCommands().toArray(new AbstractCommand[baseCommand.getCommands().size()]);

        sender.sendMessage(Util.color(header));
        Player player = Bukkit.getPlayer(sender.getName());

        int page = (0 * 5);

        for (int i = page; i < (page + 5); i++) {

            if (i > commands.length - 1) continue;

            AbstractCommand command = commands[i];

            TextComponent message = new TextComponent(Util.color(String.format(line, command.getLabel(), ChatColor.GREEN + command.getDescription())));
            message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GRAY + command.getPermission()).create()));

            if (player != null) {
                player.spigot().sendMessage(message);
            } else {
                sender.sendMessage(message.getText());
            }

        }

        sender.sendMessage(Util.color(footer));
    }
}
