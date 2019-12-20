package com.reliableplugins.oregenerator.listeners;

import com.reliableplugins.oregenerator.OreGenerator;
import com.reliableplugins.oregenerator.generator.Generator;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFromToEvent;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class GeneratorListeners implements Listener{

    private OreGenerator plugin;
    private final Set<Material> materials = new HashSet<>(Arrays.asList(Material.LAVA, Material.STATIONARY_LAVA, Material.WATER, Material.STATIONARY_WATER));

    public GeneratorListeners(OreGenerator plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onGenerator(BlockFromToEvent event) {

        if (event.getToBlock().getType() == Material.AIR) {

            Block block = event.getToBlock();
            World world = block.getWorld();
            BlockFace blockFace = event.getFace();

            if (materials.contains(block.getRelative(blockFace).getType()) && materials.contains(block.getRelative(blockFace.getOppositeFace()).getType())) {
                block.setType(plugin.getGenerators().get("default").generateRandomMaterial());
                world.playSound(block.getLocation(), Sound.FIZZ, 1.0f, 2.0f);
                world.playEffect(block.getLocation(), Effect.LAVA_POP, 0);
                event.setCancelled(true);
            }

        }

    }



}
