package com.reliableplugins.oregenerator.listeners;

import com.reliableplugins.oregenerator.OreGenerator;
import com.reliableplugins.oregenerator.generator.Generator;
import com.reliableplugins.oregenerator.util.XMaterial;
import com.reliableplugins.oregenerator.util.pair.Pair;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockFromToEvent;

import java.util.*;

public class GeneratorListeners implements Listener {

    private OreGenerator plugin;

    private Map<Location, Generator> generators = new HashMap<>();
    private Map<Location, XMaterial> blocks = new HashMap<>();

    public GeneratorListeners(OreGenerator plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onGenerator(BlockFromToEvent event) {

        //TODO: make this not get called if player breaks block
        Block block = event.getToBlock();

        if (block.getType() == Material.AIR || block.getType() == Material.COBBLESTONE || block.getType() == Material.STONE) {

            if (!isCobbleGenerator(XMaterial.matchXMaterial(event.getBlock().getType()), block)) {
                return;
            }

            Generator generator;


            if (plugin.getGenerators().containsKey("default")) {
                generator = plugin.getGenerators().get("default");
            } else {
                generator = plugin.getGenerators().values().iterator().next();
            }

            XMaterial material = generator.getFirst().generateRandomMaterial();

            if (blocks.containsKey(block.getLocation())) {
                material = blocks.get(block.getLocation());
            }


            block.setType(material.parseMaterial());

            event.setCancelled(true);
            if (!generators.containsKey(block.getLocation())) {
                generators.put(block.getLocation(), generator);
            }
        }

    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onBlockBreak(BlockBreakEvent event) {

        Block block = event.getBlock();

        if (!generators.containsKey(block.getLocation())) return;

        Player player = event.getPlayer();
        Pair<Generator, Integer> selected = plugin.getPlayerCache().getSelected(player);

        if (generators.get(block.getLocation()) != selected.getKey()) {
            generators.put(block.getLocation(), selected.getKey());
        }

        XMaterial random = selected.getKey().getByLevel(selected.getValue()).generateRandomMaterial();
        blocks.put(block.getLocation(), random);
    }

    private final BlockFace[] faces = new BlockFace[]{
            BlockFace.UP,
            BlockFace.DOWN,
            BlockFace.NORTH,
            BlockFace.EAST,
            BlockFace.SOUTH,
            BlockFace.WEST
    };

    public boolean isCobbleGenerator(XMaterial type, Block block) {
        XMaterial material = (type == XMaterial.WATER) ? XMaterial.LAVA : XMaterial.WATER;
        for (BlockFace face : faces) {
            Block relative = block.getRelative(face, 1);
            if (XMaterial.matchXMaterial(relative.getType()) == material) {
                return true;
            }
        }
        return false;
    }

}
