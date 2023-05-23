package me.alek;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class PluginOptions {

    private final FileConfiguration file;

    public boolean USING_DATABASE;

    public PluginOptions(FileConfiguration file) {
        this.file = file;

    }

    public FileConfiguration getFile() {
        return file;
    }
}
