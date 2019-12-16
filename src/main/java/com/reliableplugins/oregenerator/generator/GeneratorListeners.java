package com.reliableplugins.oregenerator.generator;

import com.reliableplugins.oregenerator.OreGenerator;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class GeneratorListeners implements Listener {

    private OreGenerator plugin;
    private final Set<Material> materials = new HashSet<>(Arrays.asList(Material.LAVA, Material.STATIONARY_LAVA, Material.WATER, Material.STATIONARY_WATER));

    public GeneratorListeners(OreGenerator plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onGenerator(BlockFromToEvent event) {
        if (event.getToBlock().getType() == Material.AIR) {
            Block block = event.getToBlock();
            BlockFace blockFace = event.getFace();
            if (materials.contains(block.getRelative(blockFace).getType()) && materials.contains(block.getRelative(blockFace.getOppositeFace()).getType())) {

                // TODO: get generator based on player (below)
                Generator generator = plugin.getGenerators().get(0);

                try {
                    block.setType(generator.generateRandomMaterial());
                }
                catch(Exception e) {
                    Bukkit.getConsoleSender().sendMessage(e.getMessage());
                }

                event.setCancelled(true);
            }
        }
    }

}
