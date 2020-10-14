package com.reliableplugins.oregenerator;

import com.reliableplugins.oregenerator.generator.Generator;
import com.reliableplugins.oregenerator.util.pair.Pair;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.*;
import java.util.stream.IntStream;

public class PlayerCache implements Listener {

    private Map<UUID, List<Pair<Generator, Integer>>> generators = new HashMap<>();
    private Map<UUID, Pair<Generator, Integer>> selected = new HashMap<>();

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

    public void addPlayer(Player player) {

        List<Pair<Generator, Integer>> generators = new ArrayList<>();
        final int DEFAULT_LEVEL = 1;

        for (Generator generator : plugin.getGenerators().values()) {
            if (player.hasPermission(String.format("oregenerator.use.%s", generator.getName()))) {
                int maxLevel = plugin.getFileManager().getUserData().getConfig().getInt(String.format("%s.%s", player.getUniqueId(), generator.getName()));
                if (generator.getMaxLevel() == 1) {
                    generators.add(Pair.of(generator, DEFAULT_LEVEL));
                } else {
                    IntStream.rangeClosed(DEFAULT_LEVEL, maxLevel).mapToObj(level -> Pair.of(generator, level)).forEach(generators::add);
                }
            }

            if (!selected.containsKey(player.getUniqueId())) {
//                System.out.println("Needs to set default generator");
//                players.put(player.getUniqueId(), Pair.of(plugin.getGenerators().values().iterator().next(), 1));
            }

        }
        this.generators.put(player.getUniqueId(), generators);
    }

    public void addLevel(Player player, Generator generator, int level) {
        this.generators.get(player.getUniqueId()).add(Pair.of(generator, level));
    }

    public void setGenerator(Player player, Generator generator, int level) {
        selected.put(player.getUniqueId(), Pair.of(generator, level));
    }

    public void removePlayer(UUID uuid) {
        selected.remove(uuid);
        generators.remove(uuid);
    }

    public Pair<Generator, Integer> getSelected(Player player) {
        Pair<Generator, Integer> generator = selected.get(player.getUniqueId());
        if (generator == null) {
            return Pair.of(plugin.getGenerators().get("default"), 1);
        }
        return generator;
    }

    public Map<UUID, List<Pair<Generator, Integer>>> getGenerators() {
        return generators;
    }

    public List<Pair<Generator, Integer>> getGenerators(Player player) {
        return generators.get(player.getUniqueId());
    }

}
