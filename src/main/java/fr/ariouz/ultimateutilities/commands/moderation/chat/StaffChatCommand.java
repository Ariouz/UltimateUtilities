package fr.ariouz.ultimateutilities.commands.moderation.chat;

import fr.ariouz.ultimateutilities.UltimateUtilities;
import fr.ariouz.ultimateutilities.managers.config.ConfigPaths;
import fr.ariouz.ultimateutilities.utils.MessageUtils;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StaffChatCommand implements CommandExecutor {

    private final UltimateUtilities ultimateUtilities;

    public StaffChatCommand(UltimateUtilities ultimateUtilities){
        this.ultimateUtilities = ultimateUtilities;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(ultimateUtilities.getPluginConfig().getBoolean(ConfigPaths.STAFFCHAT_COMMAND.getEnablePath())){
            if(!sender.hasPermission("uutils.admin.command.staffchat")){
                for(String str : new MessageUtils(ultimateUtilities).getMessages(sender, ConfigPaths.NO_PERMISSION_MESSAGE)){
                    sender.sendMessage(str);
                }
                return false;
            }

            if(args.length == 0){
                for(String str : new MessageUtils(ultimateUtilities).getMessages(sender, ConfigPaths.STAFFCHAT_COMMAND_SYNTAX_MESSAGE)){
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
                for(String str : new MessageUtils(ultimateUtilities).getMessages(sender, ConfigPaths.STAFFCHAT_COMMAND_MESSAGE)){
                    Bukkit.getOnlinePlayers().forEach(pls -> {
                        if(pls.hasPermission("uutils.admin.command.staffchat")){
                            pls.sendMessage(str.replaceAll("%sender%", sender.getName()).replaceAll("%message%", message));
                        }
                    });
                    Bukkit.getConsoleSender().sendMessage(str.replaceAll("%sender%", sender.getName()).replaceAll("%message%", message));
                }
                return true;
            }

            Player player = (Player) sender;
            for(String str : new MessageUtils(ultimateUtilities).getMessages(player, ConfigPaths.STAFFCHAT_COMMAND_MESSAGE)){
                Bukkit.getOnlinePlayers().forEach(pls -> {
                    if(pls.hasPermission("uutils.admin.command.staffchat")){
                        pls.sendMessage(PlaceholderAPI.setPlaceholders(player, str.replaceAll("%sender%", player.getName()).replaceAll("%message%", message)));
                    }
                });
                Bukkit.getConsoleSender().sendMessage(PlaceholderAPI.setPlaceholders(player, str.replaceAll("%sender%", player.getName()).replaceAll("%message%", message)));
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
