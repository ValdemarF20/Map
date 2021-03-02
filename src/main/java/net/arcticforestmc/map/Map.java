package net.arcticforestmc.map;

import net.arcticforestmc.map.command.ConfigReload;
import net.arcticforestmc.map.command.MapCommand;
import net.arcticforestmc.map.libs.DataHandler;
import org.bukkit.plugin.java.JavaPlugin;

public final class Map extends JavaPlugin {
    public static DataHandler dataHandler;
    @Override
    public void onEnable() {
        System.out.println("Map plugin has been enabled!");

        dataHandler = new DataHandler(this);
        dataHandler.createDirectoryIfMissing("plugins/ArcticValentines");
        dataHandler.copyTemplateIfMissing("valentinesKey.yml", "plugins/ArcticValentines/valentinesKey.yml");
        dataHandler.addFile("key", "plugins/ArcticValentines/valentinesKey.yml");
        dataHandler.loadFileYAML("key");

        getCommand("map").setExecutor(new MapCommand(this));
        getCommand("mapreload").setExecutor(new ConfigReload(this));

        saveDefaultConfig();
    }
}
