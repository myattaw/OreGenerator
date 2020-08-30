package com.reliableplugins.oregenerator.command.impl;

import com.reliableplugins.oregenerator.command.AbstractCommand;
import com.reliableplugins.oregenerator.command.CommandBuilder;
import com.reliableplugins.oregenerator.generator.Generator;
import com.reliableplugins.oregenerator.util.XMaterial;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;
import java.util.Map;

@CommandBuilder(label = "give", alias = {"g"}, permission = "oregenerator.give", description = "basic help command")
public class CommandGive extends AbstractCommand {

    @Override
    public void execute(CommandSender sender, String[] args) {

        Player player = (Player) sender;

        Generator generator = getPlugin().getGenerators().get("default");

        float max = Collections.max(generator.getFirst().getItems().values());

        for (Map.Entry<XMaterial, Float> items : generator.getFirst().getItems().entrySet()) {
            if (items.getValue() == max) {
                player.getInventory().addItem(new ItemStack(items.getKey().parseItem()));
                return;
            }
        }
    }

}
