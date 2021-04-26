package fr.ariouz.ultimateutilities.commands.utils.messages;

import fr.ariouz.ultimateutilities.UltimateUtilities;
import fr.ariouz.ultimateutilities.managers.config.ConfigPaths;
import fr.ariouz.ultimateutilities.utils.MessageUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WebsiteCommand implements CommandExecutor {

    private final UltimateUtilities ultimateUtilities;

    public WebsiteCommand(UltimateUtilities ultimateUtilities){
        this.ultimateUtilities = ultimateUtilities;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(ultimateUtilities.getPluginConfig().getBoolean(ConfigPaths.WEBSITE_COMMAND.getEnablePath())){
            if(!(sender instanceof Player)){
                for(String str : new MessageUtils(ultimateUtilities).getMessages(sender, ConfigPaths.WEBSITE_COMMAND_MESSAGE)){
                    sender.sendMessage(str);
                }
            }else{
                Player player = (Player) sender;
                for(String str : new MessageUtils(ultimateUtilities).getMessages(player, ConfigPaths.WEBSITE_COMMAND_MESSAGE)){
                    player.sendMessage(str);
                }
            }
            return true;
        }else{
            for(String str : new MessageUtils(ultimateUtilities).getMessages(sender, ConfigPaths.COMMAND_NOT_ENABLED)){
                sender.sendMessage(str);
            }
            return false;
        }

    }

}
