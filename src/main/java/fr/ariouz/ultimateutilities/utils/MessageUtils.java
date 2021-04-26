package fr.ariouz.ultimateutilities.utils;

import fr.ariouz.ultimateutilities.UltimateUtilities;
import fr.ariouz.ultimateutilities.managers.config.ConfigPaths;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class MessageUtils {

    private final UltimateUtilities ultimateUtilities;

    public MessageUtils(UltimateUtilities ultimateUtilities){
        this.ultimateUtilities = ultimateUtilities;
    }

    public ArrayList<String> getMessages(CommandSender commandSender, ConfigPaths path){
        ArrayList<String> l = new ArrayList<>();
        ultimateUtilities.getMessagesConfig().getList(path.getPath()).forEach(t -> l.add(ChatColor.translateAlternateColorCodes('&', t.toString())));
        return l;
    }

    public ArrayList<String> getMessages(Player player, ConfigPaths path){
        ArrayList<String> l = new ArrayList<>();
        ultimateUtilities.getMessagesConfig().getList(path.getPath()).forEach(t -> l.add(ChatColor.translateAlternateColorCodes('&', PlaceholderAPI.setPlaceholders(player, t.toString()))));
        return l;
    }

}
