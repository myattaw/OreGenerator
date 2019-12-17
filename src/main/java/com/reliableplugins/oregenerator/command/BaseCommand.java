package com.reliableplugins.oregenerator.command;

import com.reliableplugins.oregenerator.OreGenerator;
import com.reliableplugins.oregenerator.command.impl.CommandAdd;
import com.reliableplugins.oregenerator.command.impl.CommandEdit;
import com.reliableplugins.oregenerator.command.impl.CommandHelp;
import com.reliableplugins.oregenerator.command.impl.CommandList;
import com.reliableplugins.oregenerator.util.Message;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class BaseCommand implements CommandExecutor {

    public  Map<String, AbstractCommand> subcommands = new HashMap<>();
    private CommandHelp commandHelp;
    private OreGenerator plugin;

    public BaseCommand(OreGenerator plugin) {
        this.plugin = plugin;
        this.commandHelp = new CommandHelp(this);
        addCommand(new CommandAdd(), plugin);
        addCommand(new CommandEdit(), plugin);
        addCommand(new CommandList(plugin), plugin);
        plugin.getCommand("oregenerator").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String string, String[] args) {

        if (args.length == 0) {
            if (commandSender.hasPermission(commandHelp.getPermission())) {
                commandHelp.execute(commandSender, args);
                return true;
            }
            commandSender.sendMessage(Message.ERROR_PERMISSION.getMessage());
        }

        for (AbstractCommand subcommand : subcommands.values()) {

            if (!args[0].equalsIgnoreCase(subcommand.getLabel()) && !subcommand.getAlias().contains(args[0].toLowerCase()))
                continue;

            if (!(commandSender instanceof Player) && subcommand.isPlayerRequired()) {
                commandSender.sendMessage(Message.ERROR_NOT_PLAYER.getMessage());
                return true;
            }

            if (args[0].equalsIgnoreCase(subcommand.getLabel()) || subcommand.getAlias().contains(args[0].toLowerCase())) {
                if (!subcommand.hasPermission() || commandSender.hasPermission(subcommand.getPermission()) || commandSender.isOp()) {
                    subcommand.execute(commandSender, Arrays.copyOfRange(args, 1, args.length));
                } else {
                    commandSender.sendMessage(Message.ERROR_PERMISSION.getMessage());
                    return true;
                }
            }
        }

        return true;
    }

    public void addCommand(AbstractCommand command, OreGenerator plugin) {
        command.setPlugin(plugin);
        this.subcommands.put(command.getLabel().toLowerCase(), command);
    }

    public JavaPlugin getPlugin() {
        return plugin;
    }

    public Collection<AbstractCommand> getCommands() {
        return Collections.unmodifiableCollection(subcommands.values());
    }

}
