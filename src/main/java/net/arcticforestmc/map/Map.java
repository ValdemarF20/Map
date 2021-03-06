package net.arcticforestmc.map;

import net.arcticforestmc.map.command.ConfigReload;
import net.arcticforestmc.map.command.MapCommand;
import net.arcticforestmc.map.libs.Yaml;
import net.arcticforestmc.map.libs.YamlFactory;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Map extends JavaPlugin {
    protected Yaml yaml;

    @Override
    public void onEnable() {
        Bukkit.getLogger().info("Map plugin has been enabled!");
        yaml = new YamlFactory(JavaPlugin.getPlugin(Map.class)).setDefaultPathways();
        yaml.reload();

        getCommand("map").setExecutor(new MapCommand(yaml));
        getCommand("mapreload").setExecutor(new ConfigReload(yaml));

        saveDefaultConfig();
    }
}
