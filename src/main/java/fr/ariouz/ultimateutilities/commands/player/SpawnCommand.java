package fr.ariouz.ultimateutilities.commands.player;

import fr.ariouz.ultimateutilities.UltimateUtilities;
import fr.ariouz.ultimateutilities.managers.config.ConfigPaths;
import fr.ariouz.ultimateutilities.utils.MessageUtils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnCommand implements CommandExecutor {

    private final UltimateUtilities ultimateUtilities;

    public SpawnCommand(UltimateUtilities ultimateUtilities){
        this.ultimateUtilities = ultimateUtilities;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(ultimateUtilities.getPluginConfig().getBoolean(ConfigPaths.FLY_COMMAND.getEnablePath())) {
            if (!(sender instanceof Player)) {
                for (String str : new MessageUtils(ultimateUtilities).getMessages(sender, ConfigPaths.ONLY_PLAYER_CAN_USE_COMMAND)) {
                    sender.sendMessage(str);
                }
                return false;
            }

            Location location = ultimateUtilities.getPluginConfig().getLocationFromString(ConfigPaths.SPAWN_COMMAND.getPath()+".coords");
            if(location == null){
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&7[&cUltimateUtilities&7] &rSpawn location is not set. Use /setspawn to set it."));
                return false;
            }

            Player player = (Player) sender;
            player.teleport(location);
            for (String str : new MessageUtils(ultimateUtilities).getMessages(sender, ConfigPaths.SPAWN_COMMAND_MESSAGE)) {
                sender.sendMessage(str);
            }
            return true;
        }else{
            for (String str : new MessageUtils(ultimateUtilities).getMessages(sender, ConfigPaths.COMMAND_NOT_ENABLED)) {
                sender.sendMessage(str);
            }
            return false;
        }
    }
}
