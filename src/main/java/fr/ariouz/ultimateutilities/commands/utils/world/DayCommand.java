package fr.ariouz.ultimateutilities.commands.utils.world;

import fr.ariouz.ultimateutilities.UltimateUtilities;
import fr.ariouz.ultimateutilities.managers.config.ConfigPaths;
import fr.ariouz.ultimateutilities.utils.MessageUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DayCommand implements CommandExecutor {

    private final UltimateUtilities ultimateUtilities;

    public DayCommand(UltimateUtilities ultimateUtilities){
        this.ultimateUtilities = ultimateUtilities;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(ultimateUtilities.getPluginConfig().getBoolean(ConfigPaths.DAY_COMMAND.getEnablePath())) {
            if (!(sender instanceof Player)) {
                for (String str : new MessageUtils(ultimateUtilities).getMessages(sender, ConfigPaths.ONLY_PLAYER_CAN_USE_COMMAND)) {
                    sender.sendMessage(str);
                }
                return false;
            }

            if (!sender.hasPermission("uutils.command.day")) {
                for (String str : new MessageUtils(ultimateUtilities).getMessages(sender, ConfigPaths.NO_PERMISSION_MESSAGE)) {
                    sender.sendMessage(str);
                }
                return false;
            }

            Player player = (Player) sender;

            int time = ultimateUtilities.getPluginConfig().getInt(ConfigPaths.DAY_COMMAND.getPath() + ".time");
            player.getWorld().setTime(time);

            for (String str : new MessageUtils(ultimateUtilities).getMessages(player, ConfigPaths.COMMAND_DAY_MESSAGE)) {
                player.sendMessage(str.replaceAll("%time%", time + ""));
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
