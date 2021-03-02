package net.arcticforestmc.map.command;

import net.arcticforestmc.map.Map;
import net.arcticforestmc.map.libs.DataHandler;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class MapCommand implements CommandExecutor {
    private final JavaPlugin main;
    public MapCommand(JavaPlugin main){
        this.main = main;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String Labels, String[] args){
        if(!(sender instanceof Player)){return true;}

        Player player = (Player) sender;

        String world = player.getWorld().getName();
        String creator = main.getConfig().getString("Creator");
        List<String> text = main.getConfig().getStringList("Description");

        Map.dataHandler.setYAMLListField("config", world+".description", text);
        Map.dataHandler.setYAMLStringField("config", world+".creator", creator);

        player.sendMessage(ChatColor.DARK_GREEN + world);

        for(int i = 0; i < text.size(); i++){
            player.sendMessage(ChatColor.GREEN + text.get(i));
        }

        player.sendMessage(ChatColor.DARK_GREEN + creator);

        return true;
    }
}
