package com.reliableplugins.oregenerator.listeners;

import com.reliableplugins.oregenerator.OreGenerator;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListeners {

    private OreGenerator plugin;

    public PlayerListeners(OreGenerator plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        plugin.getPlayerCache().addPlayer(event.getPlayer());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        plugin.getPlayerCache().removePlayer(event.getPlayer().getUniqueId());
    }

}
