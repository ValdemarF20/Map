package net.arcticforestmc.map.libs;

import net.arcticforestmc.map.Map;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public final class YamlFactory {
    private final Map plugin;
    private String resourcePath;
    private File directory;
    private String fileName;

    public YamlFactory(Map plugin) {
        this.plugin = plugin;
    }

    public Yaml from(String resourcePath, File directory, String fileName) {
        this.resourcePath = resourcePath;
        this.directory = directory;
        this.fileName = fileName;
        return new Yaml(plugin, resourcePath, directory, fileName);
    }

    public FileConfiguration load(File yamlFile) {
        return YamlConfiguration.loadConfiguration(yamlFile);
    }

    public Yaml setDefaultPathways() {
        return from("config.yml", plugin.getDataFolder(), "config.yml");
    }
}
