package fr.ariouz.ultimateutilities.commands.utils.world;

import fr.ariouz.ultimateutilities.UltimateUtilities;
import fr.ariouz.ultimateutilities.managers.config.ConfigPaths;
import fr.ariouz.ultimateutilities.utils.MessageUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NightCommand implements CommandExecutor {

    private final UltimateUtilities ultimateUtilities;

    public NightCommand(UltimateUtilities ultimateUtilities){
        this.ultimateUtilities = ultimateUtilities;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(ultimateUtilities.getPluginConfig().getBoolean(ConfigPaths.NIGHT_COMMAND.getEnablePath())){
            if(!(sender instanceof Player)){
                for(String str : new MessageUtils(ultimateUtilities).getMessages(sender, ConfigPaths.ONLY_PLAYER_CAN_USE_COMMAND)){
                    sender.sendMessage(str);
                }
                return false;
            }

            if(!sender.hasPermission("uutils.command.night")){
                for(String str : new MessageUtils(ultimateUtilities).getMessages(sender, ConfigPaths.NO_PERMISSION_MESSAGE)){
                    sender.sendMessage(str);
                }
                return false;
            }

            Player player = (Player) sender;

            int time = ultimateUtilities.getPluginConfig().getInt(ConfigPaths.NIGHT_COMMAND.getPath()+".time");
            player.getWorld().setTime(time);

            for(String str : new MessageUtils(ultimateUtilities).getMessages(player, ConfigPaths.COMMAND_NIGHT_MESSAGE)){
                player.sendMessage(str.replaceAll("%time%", time+""));
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
