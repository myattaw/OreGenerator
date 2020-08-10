package com.reliableplugins.oregenerator.nms;

import com.reliableplugins.oregenerator.OreGenerator;
import com.reliableplugins.oregenerator.nms.impl.*;

public class NMSManager {

    private OreGenerator plugin;

    public NMSManager(OreGenerator plugin) {
        this.plugin = plugin;
        switch (getVersion()) {
            case "v1_8_R3":
                addVersion(new Version_1_8_R3());
                break;
            case "v1_11_R1":
                addVersion(new Version_1_11_R1());
                break;
            case "v1_12_R1":
                addVersion(new Version_1_12_R1());
                break;
            case "v1_13_R1":
                addVersion(new Version_1_13_R1());
                break;
            case "v1_13_R2":
                addVersion(new Version_1_13_R2());
                break;
            case "v1_14_R1":
                addVersion(new Version_1_14_R1());
                break;
            case "v1_15_R1":
                addVersion(new Version_1_15_R1());
                break;
            case "v1_16_R1":
                addVersion(new Version_1_16_R1());
                break;
            default:
                addVersion(new Version_1_8_R3());
        }
    }

    private final String getVersion() {
        return plugin.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
    }

    private void addVersion(NMSHandler nms) {
        plugin.setNMS(nms);
    }

}