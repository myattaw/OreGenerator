package com.reliableplugins.oregenerator.listeners;

import com.reliableplugins.oregenerator.OreGenerator;
import com.reliableplugins.oregenerator.generator.Generator;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockFromToEvent;

import java.util.*;

public class GeneratorListeners implements Listener {

    private OreGenerator plugin;
    private final Set<Material> materials = new HashSet<>(Arrays.asList(Material.LAVA, Material.STATIONARY_LAVA, Material.WATER, Material.STATIONARY_WATER));

    private Map<Location, Generator> generators = new HashMap<>();

    public GeneratorListeners(OreGenerator plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onGenerator(BlockFromToEvent event) {

        //TODO: make this not get called if player breaks block
        if (event.getToBlock().getType() == Material.AIR) {

            Block block = event.getToBlock();
            BlockFace blockFace = event.getFace();

            if (materials.contains(block.getRelative(blockFace).getType()) && materials.contains(block.getRelative(blockFace.getOppositeFace()).getType())) {
                Generator generator = plugin.getGenerators().get("default");
                block.setType(generator.generateRandomMaterial());
                event.setCancelled(true);
                if (!generators.containsKey(block.getLocation())) {
                    generators.put(block.getLocation(), generator);
                }
            }
        }
    }


    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {

        Block block = event.getBlock();

        if (!generators.containsKey(block.getLocation())) return;

        Player player = event.getPlayer();

        event.setCancelled(true);

        Generator selected = plugin.getPlayerCache().getSelected(player);
        if (generators.get(block.getLocation()) != selected) {
            generators.put(block.getLocation(), selected);
        }

        Bukkit.broadcastMessage("generated random");
        block.breakNaturally(player.getItemInHand());

        block.setType(selected.generateRandomMaterial());

    }

}
