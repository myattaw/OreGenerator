package com.reliableplugins.oregenerator;

import com.reliableplugins.oregenerator.generator.Generator;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.*;

public class PlayerCache implements Listener {

    private Map<UUID, Generator> players = new HashMap<>();
    private Map<UUID, List<Generator>> generators = new HashMap<>();
    private Map<UUID, Location> locations = new HashMap<>();

    private OreGenerator plugin;

    public PlayerCache(OreGenerator plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        this.plugin = plugin;
    }

    @EventHandler
    private void onJoin(PlayerJoinEvent event) {
        addPlayer(event.getPlayer());
    }

    @EventHandler
    private void onQuit(PlayerQuitEvent event) {
        removePlayer(event.getPlayer().getUniqueId());
    }

    public void addLocation(UUID uuid, Location location) {
        locations.put(uuid, location);
    }

    public void addPlayer(Player player) {
        List<Generator> generators = new ArrayList<>();
        for (Generator generator : plugin.getGenerators().values()) {
            if (player.hasPermission("oregenerator.use." + generator.getName())) {
                generators.add(generator);
                players.put(player.getUniqueId(), generator);
            }

            if (!players.containsKey(player.getUniqueId())) {
                players.put(player.getUniqueId(), plugin.getGenerators().values().iterator().next());
            }

        }
        this.generators.put(player.getUniqueId(), generators);
    }

    public void setGenerator(Player player, Generator generator) {
        players.put(player.getUniqueId(), generator);
    }

    public void removePlayer(UUID uuid) {
        players.remove(uuid);
        generators.remove(uuid);
    }

    public Generator getSelected(Player player) {
        Generator generator = players.get(player.getUniqueId());
        if (generator == null) {
            return plugin.getGenerators().get("default");
        }
        return generator;
    }

    public List<Generator> getGenerators(Player player) {
        return generators.get(player.getUniqueId());
    }

    public Map<UUID, Location> getLocations() {
        return locations;
    }
}
