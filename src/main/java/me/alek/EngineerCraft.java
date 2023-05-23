package me.alek;

import me.alek.hub.HubListeners;
import me.alek.hub.HubManager;
import me.alek.mechanics.UnitLibrary;
import me.alek.storage.SQLStorage;
import me.alek.storage.StorageAdapter;
import me.alek.storage.YamlStorage;
import org.bukkit.plugin.java.JavaPlugin;

public final class EngineerCraft extends JavaPlugin {

    private static EngineerCraft instance;
    private static PluginOptions options;
    private static StorageAdapter storage;

    @Override
    public void onEnable() {
        getCommand("create").setExecutor(new StartCommand());
        getServer().getPluginManager().registerEvents(new HubListeners(), this);
        instance = this;

        if (!getDataFolder().exists()) {
            getDataFolder().mkdirs();
        }
        getConfig().options().copyDefaults(true);
        options = new PluginOptions(getConfig());

        if (options.USING_DATABASE) {
            storage = new SQLStorage();
        } else {
            storage = new YamlStorage();
        }
        storage.init();

        UnitLibrary.setup();
        HubManager.loadHubs();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static EngineerCraft getInstance() {
        return instance;
    }

    public static StorageAdapter getStorageAdapter() {
        return storage;
    }

    public static PluginOptions getOptions() {
        return options;
    }
}
