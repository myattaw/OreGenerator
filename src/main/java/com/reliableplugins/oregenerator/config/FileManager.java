package com.reliableplugins.oregenerator.config;

import com.reliableplugins.oregenerator.OreGenerator;
import com.reliableplugins.oregenerator.config.impl.UserDataFile;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class FileManager {

    private OreGenerator plugin;
    private List<CustomFile> files = new ArrayList<>();

    private UserDataFile userData;

    public FileManager(OreGenerator plugin) {
        this.plugin = plugin;
        addFile(userData = new UserDataFile(plugin, "user-data"));
    }

    private void addFile(CustomFile file) {
        files.add(file);
        plugin.getLogger().log(Level.INFO, file.getName() + " has initialized.");
        file.init();
    }

    public UserDataFile getUserData() {
        return userData;
    }

    public List<CustomFile> getFiles() {
        return files;
    }

}
