package fr.ariouz.ultimateutilities.commands.moderation.chat;

import fr.ariouz.ultimateutilities.UltimateUtilities;
import fr.ariouz.ultimateutilities.managers.config.ConfigPaths;
import fr.ariouz.ultimateutilities.utils.MessageUtils;
import gnu.trove.impl.sync.TSynchronizedShortByteMap;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BroadcastCommand implements CommandExecutor {

    private final UltimateUtilities ultimateUtilities;

    public BroadcastCommand(UltimateUtilities ultimateUtilities){
        this.ultimateUtilities = ultimateUtilities;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(ultimateUtilities.getPluginConfig().getBoolean(ConfigPaths.BROADCAST_COMMAND.getEnablePath())){
            if(!sender.hasPermission("uutils.admin.command.broadcast")){
                for(String str : new MessageUtils(ultimateUtilities).getMessages(sender, ConfigPaths.NO_PERMISSION_MESSAGE)){
                    sender.sendMessage(str);
                }
                return false;
            }

            if(args.length == 0){
                for(String str : new MessageUtils(ultimateUtilities).getMessages(sender, ConfigPaths.BROADCAST_COMMAND_SYNTAX_MESSAGE)){
                    sender.sendMessage(str);
                }
                return false;
            }

            StringBuilder builder = new StringBuilder();
            for(String str : args){
                builder.append(str).append(" ");
            }

            String message = ChatColor.translateAlternateColorCodes('&',  builder.toString());

            if(!(sender instanceof Player)){
                for(String str : new MessageUtils(ultimateUtilities).getMessages(sender, ConfigPaths.BROADCAST_COMMAND_MESSAGE)){
                    Bukkit.broadcastMessage(str.replaceAll("%sender%", sender.getName()).replaceAll("%message%", message));
                }
                return true;
            }

            Player player = (Player) sender;
            for(String str : new MessageUtils(ultimateUtilities).getMessages(player, ConfigPaths.BROADCAST_COMMAND_MESSAGE)){
                Bukkit.broadcastMessage(PlaceholderAPI.setPlaceholders(player, str.replaceAll("%sender%", player.getName()).replaceAll("%message%", message)));
            }

        }else{
            for(String str : new MessageUtils(ultimateUtilities).getMessages(sender, ConfigPaths.COMMAND_NOT_ENABLED)){
                sender.sendMessage(str);
            }
            return false;
        }
        return false;
    }
}
