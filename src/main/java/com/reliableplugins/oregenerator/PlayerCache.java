package com.reliableplugins.oregenerator;

import com.reliableplugins.oregenerator.generator.Generator;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.*;

public class PlayerCache {

    private Map<UUID, Generator> players = new HashMap<>();
    private Map<UUID, List<Generator>> generators = new HashMap<>();
    private Map<UUID, Location> locations = new HashMap<>();

    private OreGenerator plugin;

    public PlayerCache(OreGenerator plugin) {
        this.plugin = plugin;
    }

    public void addLocation(UUID uuid, Location location) {
        locations.put(uuid, location);
    }

    public void addPlayer(Player player) {
        List<Generator> generators = new ArrayList<>();
        for (Generator generator : plugin.getGenerators().values()) {
            if (player.hasPermission("oregenerator.use." + generator.getName())) {
                generators.add(generator);
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

}
