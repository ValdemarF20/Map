package net.arcticforestmc.map.command;

import net.arcticforestmc.map.libs.Yaml;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

public class MapCommand implements CommandExecutor {
    private final Yaml yaml;

    public MapCommand(Yaml yaml){
        this.yaml = yaml;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String Labels, String[] args){
        if(!(sender instanceof Player)){return true;}

        //Gets the player and the world
        Player player = (Player) sender;
        String world = player.getWorld().getName();

        //Gets the information from the config.yml file
        ConfigurationSection section = (yaml.getConfigurationSection(world) != null) ? yaml.getConfigurationSection(world) : yaml.createSection(world);
        String creator = section.getString("creator");
        List<String> text = section.getStringList("description");

        //Makes sure that creator will not be null
        if (creator == null)  section.set("creator", "UNKNOWN");

        //Makes sure that text will not be null
        if (text == null || text.size() == 0) {
            section.set("description", new ArrayList<String>(){{
                add("REPLACE_ME");
            }});
        }

        player.sendMessage(ChatColor.DARK_GREEN + world);

        //Makes sure that text is not null
        assert text != null;
        StringBuilder sb = new StringBuilder();
        text.forEach(a -> sb.append(ChatColor.GREEN)
                .append(a)
                .append("\n"));

        player.sendMessage(sb.toString());

        //Sends the value of creator to the player, depending on what the value is in the config.
        assert creator != null;
        if(creator.equals("UNKNOWN")){
            player.sendMessage(ChatColor.RED + "There is no set creator of this map");
        }else {
            Player uuidToOnlineCreator = Bukkit.getPlayer(UUID.fromString(creator));

            if (uuidToOnlineCreator == null) {
                OfflinePlayer uuidToOfflineCreator = Bukkit.getOfflinePlayer(UUID.fromString(creator));
                player.sendMessage(ChatColor.DARK_GREEN + uuidToOfflineCreator.getName());
            } else {
                player.sendMessage(ChatColor.DARK_GREEN + uuidToOnlineCreator.getName());
            }
        }

        try {
            yaml.save();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }
}
