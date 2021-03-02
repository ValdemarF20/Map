package net.arcticforestmc.map.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class ConfigReload implements CommandExecutor {
    private final JavaPlugin main;
    public ConfigReload(JavaPlugin main) {
        this.main = main;
    }
    public boolean onCommand(CommandSender sender, Command cmd, String Labels, String[] args){
        main.reloadConfig();
        System.out.println("Map config has been reloaded!");
        return true;
    }
}
