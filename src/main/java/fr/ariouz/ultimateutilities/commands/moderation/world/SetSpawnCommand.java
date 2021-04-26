package fr.ariouz.ultimateutilities.commands.moderation.world;

import fr.ariouz.ultimateutilities.UltimateUtilities;
import fr.ariouz.ultimateutilities.managers.config.ConfigPaths;
import fr.ariouz.ultimateutilities.utils.MessageUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetSpawnCommand implements CommandExecutor {

    private final UltimateUtilities ultimateUtilities;

    public SetSpawnCommand(UltimateUtilities ultimateUtilities){
        this.ultimateUtilities = ultimateUtilities;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(ultimateUtilities.getPluginConfig().getBoolean(ConfigPaths.SETSPAWN_COMMAND.getEnablePath())) {
            if (!(sender instanceof Player)) {
                for (String str : new MessageUtils(ultimateUtilities).getMessages(sender, ConfigPaths.ONLY_PLAYER_CAN_USE_COMMAND)) {
                    sender.sendMessage(str);
                }
                return false;
            }

            Player player = (Player) sender;
            if(!player.hasPermission("uutils.admin.commands.setspawn")){
                for (String str : new MessageUtils(ultimateUtilities).getMessages(sender, ConfigPaths.NO_PERMISSION_MESSAGE)) {
                    sender.sendMessage(str);
                }
                return false;
            }

            ultimateUtilities.getPluginConfig().saveLocation(ConfigPaths.SPAWN_COMMAND.getPath()+".coords", player.getLocation());
            for (String str : new MessageUtils(ultimateUtilities).getMessages(sender, ConfigPaths.SETSPAWN_COMMAND_MESSAGE)) {
                sender.sendMessage(str.replaceAll("%coords%", ultimateUtilities.getPluginConfig().getString(ConfigPaths.SPAWN_COMMAND.getPath()+".coords").replaceAll(":", " ")));
            }

            return true;
        }
        return false;
    }
}
