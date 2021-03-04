package net.arcticforestmc.map.command;

import net.arcticforestmc.map.Map;
import net.arcticforestmc.map.libs.Yaml;

import org.apache.commons.io.IOUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

public class MapCommand implements CommandExecutor {
    private final Map main;
    private final Yaml yaml;
    private final UUID uuid;

    public MapCommand(Map main, Yaml yaml, UUID uuid){
        this.main = main;
        this.yaml = yaml;
        this.uuid = uuid;
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

        assert creator != null || !creator.equals("UNKNOWN");
        Player uuidToOnlineCreator = Bukkit.getPlayer(UUID.fromString(creator));

        if (uuidToOnlineCreator == null) {
            OfflinePlayer uuidToOfflineCreator = Bukkit.getOfflinePlayer(UUID.fromString(creator));
            player.sendMessage(ChatColor.DARK_GREEN + uuidToOfflineCreator.getName().toString());
        } else {
            player.sendMessage(ChatColor.DARK_GREEN + uuidToOnlineCreator.getName().toString());
        }



        //player.sendMessage(ChatColor.DARK_GREEN + getName(creator));

        try {
            yaml.save();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }
    public static String getName(String uuid) {
        String url = "https://api.mojang.com/user/profiles/"+uuid.replace("-", "")+"/names";

        String nameJson = null;


        try {
            nameJson = IOUtils.toString(new URL(url), Charset.defaultCharset());
        } catch (Exception e) {
            e.printStackTrace();
        }


        JSONArray arr = new JSONArray(nameJson);
        JSONObject latestNameArr = arr.getJSONObject(arr.length()-1);
        String latestName = (String) latestNameArr.get("name");
        return(latestName);

    }
}
