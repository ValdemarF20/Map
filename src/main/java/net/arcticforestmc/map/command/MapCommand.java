package net.arcticforestmc.map.command;

import net.arcticforestmc.map.Map;
import net.arcticforestmc.map.libs.Yaml;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

public class MapCommand implements CommandExecutor {
    private final Map main;
    private final Yaml yaml;

    public MapCommand(Map main, Yaml yaml){
        this.main = main;
        this.yaml = yaml;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String Labels, String[] args){
        if(!(sender instanceof Player)){return true;}

        Player player = (Player) sender;

        String world = player.getWorld().getName();

        ConfigurationSection section = (yaml.getConfigurationSection(world) != null) ? yaml.getConfigurationSection(world) : yaml.createSection(world);
        String creator = section.getString("creator");
        List<String> text = section.getStringList("description");

        if (creator == null) {
            section.set("creator", "UNKNOWN");
        }

        if (text == null || text.size() == 0) {
            section.set("description", new ArrayList<String>(){{
                add("REPLACE_ME");
            }});
        }

        assert text != null;

        player.sendMessage(ChatColor.DARK_GREEN + world);

        StringBuilder sb = new StringBuilder();
        text.forEach(a -> sb.append(ChatColor.GREEN)
                .append(a)
                .append("\n"));

        player.sendMessage(sb.toString());

        player.sendMessage(ChatColor.DARK_GREEN + creator);

        try {
            yaml.save();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }
}
