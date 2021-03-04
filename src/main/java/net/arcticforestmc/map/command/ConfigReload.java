package net.arcticforestmc.map.command;

import net.arcticforestmc.map.libs.Yaml;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ConfigReload implements CommandExecutor {
    private final Yaml yaml;

    public ConfigReload(Yaml yaml) {
        this.yaml = yaml;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String Labels, String[] args){
        yaml.reload();

        System.out.println("Map config has been reloaded!");
        return true;
    }
}
