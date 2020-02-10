package com.reliableplugins.oregenerator.listeners;

import com.reliableplugins.oregenerator.OreGenerator;
import com.reliableplugins.oregenerator.generator.Generator;
import com.reliableplugins.oregenerator.util.XMaterial;
import org.bukkit.Bukkit;
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
import java.util.concurrent.atomic.AtomicBoolean;

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
        if (event.getToBlock().getType() == Material.AIR || event.getToBlock().getType() == Material.COBBLESTONE) {

            Block block = event.getToBlock();

            if(!isRegenable(block)) return;

            if (generators.containsKey(block.getLocation())) {
                event.setCancelled(true);
                return;
            }

            BlockFace blockFace = event.getFace();
            if (materials.contains(block.getRelative(blockFace).getType()) && materials.contains(block.getRelative(blockFace.getOppositeFace()).getType())) {
                Generator generator = plugin.getGenerators().get("default");
                block.setType(generator.generateRandomMaterial().parseMaterial());
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

        if(!isRegenable(block)) return;

        Player player = event.getPlayer();
        Generator selected = plugin.getPlayerCache().getSelected(player);

        event.setCancelled(true);

        if (generators.get(block.getLocation()) != selected) {
            generators.put(block.getLocation(), selected);
        }

        plugin.getNMS().breakBlock(block, player);

        XMaterial random = selected.generateRandomMaterial();

        plugin.getNMS().setBlock(plugin, block.getWorld(), block.getX(), block.getY(), block.getZ(), random.parseMaterial(), random.getData());

    }

    private boolean isRegenable(Block block) {
        Material northBlock = block.getRelative(BlockFace.NORTH).getType();
        if(northBlock == Material.STATIONARY_LAVA || northBlock == Material.LAVA) {
            Material southBlock = block.getRelative(BlockFace.SOUTH).getType();
            if(southBlock == Material.STATIONARY_WATER || southBlock == Material.WATER) return true;
        }

        if(northBlock == Material.STATIONARY_WATER || northBlock == Material.WATER) {
            Material southBlock = block.getRelative(BlockFace.SOUTH).getType();
            if(southBlock == Material.STATIONARY_LAVA || southBlock == Material.LAVA) return true;
        }

        Material eastBlock = block.getRelative(BlockFace.EAST).getType();
        if(eastBlock == Material.STATIONARY_LAVA || eastBlock == Material.LAVA) {
            Material westBlock = block.getRelative(BlockFace.WEST).getType();
            if(westBlock == Material.STATIONARY_WATER || westBlock == Material.WATER) return true;
        }

        if(eastBlock == Material.STATIONARY_WATER || eastBlock == Material.WATER) {
            Material westBlock = block.getRelative(BlockFace.WEST).getType();
            if(westBlock == Material.STATIONARY_LAVA || westBlock == Material.LAVA) return true;
        }

        return false;
    }
}
