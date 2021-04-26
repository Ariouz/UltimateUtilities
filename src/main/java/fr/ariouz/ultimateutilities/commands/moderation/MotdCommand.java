package fr.ariouz.ultimateutilities.commands.moderation;

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

public class MotdCommand implements CommandExecutor {

    private final UltimateUtilities ultimateUtilities;

    public MotdCommand(UltimateUtilities ultimateUtilities){
        this.ultimateUtilities = ultimateUtilities;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(ultimateUtilities.getPluginConfig().getBoolean(ConfigPaths.MOTD_COMMAND.getEnablePath())){
            if(!sender.hasPermission("uutils.admin.command.motd")){
                for(String str : new MessageUtils(ultimateUtilities).getMessages(sender, ConfigPaths.NO_PERMISSION_MESSAGE)){
                    sender.sendMessage(str);
                }
                return false;
            }

            if(args.length == 0){
                for(String str : new MessageUtils(ultimateUtilities).getMessages(sender, ConfigPaths.MOTD_COMMAND_SYNTAX_MESSAGE)){
                    sender.sendMessage(str);
                }
                return false;
            }


            if(!ultimateUtilities.isInteger(args[0])){
                for(String str : new MessageUtils(ultimateUtilities).getMessages(sender, ConfigPaths.MUST_BE_AN_INTEGER)){
                    sender.sendMessage(str);
                }
                return false;
            }

            int line = Integer.parseInt(args[0]);
            if(line <= 0 || line >= 3){
                for(String str : new MessageUtils(ultimateUtilities).getMessages(sender, ConfigPaths.MOTD_COMMAND_SYNTAX_MESSAGE)){
                    sender.sendMessage(str);
                }
                return false;
            }

            String linePath = "";

            if(line == 1){
                linePath = "first";
            }else{
                linePath = "second";
            }

            StringBuilder builder = new StringBuilder();
            for(String str : args){
                builder.append(str).append(" ");
            }

            String message = builder.toString().replaceFirst(args[0]+" ", "");

            ultimateUtilities.getMotdConfig().set("motd.lines."+linePath, message);

            if(!(sender instanceof Player)){
                for(String str : new MessageUtils(ultimateUtilities).getMessages(sender, ConfigPaths.MOTD_COMMAND_MESSAGE)){
                    sender.sendMessage(str.replaceAll("%line%", line+"").replaceAll("%motd%", ChatColor.translateAlternateColorCodes('&', message)));
                }
                return true;
            }

            Player player = (Player) sender;
            for(String str : new MessageUtils(ultimateUtilities).getMessages(player, ConfigPaths.MOTD_COMMAND_MESSAGE)){
                sender.sendMessage(PlaceholderAPI.setPlaceholders(player, str.replaceAll("%line%", line+"").replaceAll("%motd%", ChatColor.translateAlternateColorCodes('&', builder.toString().replaceFirst(args[0]+" ", "")))));
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
