package net.arcticforestmc.map;

import net.arcticforestmc.map.command.ConfigReload;
import net.arcticforestmc.map.command.MapCommand;
import net.arcticforestmc.map.libs.Yaml;
import net.arcticforestmc.map.libs.YamlFactory;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public final class Map extends JavaPlugin {
    protected Yaml yaml;
    protected UUID uuid;

    @Override
    public void onEnable() {
        System.out.println("Map plugin has been enabled!");
        yaml = new YamlFactory(JavaPlugin.getPlugin(Map.class)).setDefaultPathways();
        yaml.reload();

        getCommand("map").setExecutor(new MapCommand(this, yaml, uuid));
        getCommand("mapreload").setExecutor(new ConfigReload(yaml));

        saveDefaultConfig();
    }
}
