package com.reliableplugins.oregenerator.listeners;

import com.reliableplugins.oregenerator.OreGenerator;
import com.reliableplugins.oregenerator.generator.Generator;
import com.reliableplugins.oregenerator.menu.impl.player.SelectorMenu;
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
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.player.PlayerInteractEvent;

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

            if (!isCobbleGenerator(XMaterial.matchXMaterial(event.getBlock().getType()), block, false)) {
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

    @EventHandler
    public void onBlockInteract(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {

            Block block = event.getClickedBlock();
            Player player = event.getPlayer();

            if (isCobbleGenerator(XMaterial.matchXMaterial(block.getType()), block, true)) {
                List<Pair<Generator, Integer>> generators = plugin.getPlayerCache().getGenerators(player);

                if (generators == null || generators.size() != plugin.getGenerators().values().size()) {
                    plugin.getPlayerCache().addPlayer(player);
                }

                int rows = (int) (1 + Math.ceil((plugin.getPlayerCache().getGenerators(player).size() - 1) / 9));
                player.openInventory(new SelectorMenu(player, rows + 2, plugin).init().getInventory());
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

    public boolean isCobbleGenerator(XMaterial type, Block block, boolean checkOpposite) {
        XMaterial material = (type == XMaterial.WATER) ? XMaterial.LAVA : XMaterial.WATER;
        XMaterial opposite = (type == XMaterial.WATER) ? XMaterial.WATER : XMaterial.LAVA;
        for (BlockFace face : faces) {
            Block relative = block.getRelative(face, 1);
            if (XMaterial.matchXMaterial(relative.getType()) == material) {
                if (checkOpposite && !(XMaterial.matchXMaterial(block.getRelative(face.getOppositeFace(), 1).getType()) == opposite)) {
                    return false;
                }
                return true;
            }
        }
        return false;
    }

}
