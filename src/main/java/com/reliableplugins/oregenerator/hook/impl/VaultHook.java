package com.reliableplugins.oregenerator.hook.impl;


import com.reliableplugins.oregenerator.OreGenerator;
import com.reliableplugins.oregenerator.hook.PluginHook;
import com.reliableplugins.oregenerator.util.Message;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

public class VaultHook implements PluginHook {

    private Economy economy;

    @Override
    public VaultHook setup(OreGenerator plugin) {
        RegisteredServiceProvider registration = plugin.getServer().getServicesManager().getRegistration(Economy.class);
        if (registration == null) {
            return null;
        }
        economy = (Economy) registration.getProvider();
        return this;
    }

    public boolean canAfford(Player player, int cost) {
        if (economy.has(player, cost)) {
            return true;
        }
        return false;
    }

    public void withdrawPlayer(Player player, int cost) {
        if (!economy.withdrawPlayer(player, cost).transactionSuccess()) {
            player.sendMessage(Message.ERROR_NOT_ENOUGH_MONEY.getMessage());
        }
    }

    @Override
    public String[] getPlugins() {
        return new String[]{"Vault"};
    }

    @Override
    public String getName() {
        return "vault";
    }

}