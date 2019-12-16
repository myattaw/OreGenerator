package com.reliableplugins.oregenerator.hook.impl.skyblock;

import com.reliableplugins.oregenerator.hook.impl.SkyblockHook;
import com.wasteofplastic.askyblock.ASkyBlockAPI;
import org.bukkit.Location;

import java.util.UUID;

public class ASkyblockHook extends SkyblockHook {

    private final ASkyBlockAPI api = ASkyBlockAPI.getInstance();

    @Override
    public UUID getIslandOwner(Location location) {
        if (api.getIslandAt(location) != null) {
            return api.getIslandAt(location).getOwner();
        }
        return null;
    }

}
