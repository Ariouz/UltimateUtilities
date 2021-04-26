package fr.ariouz.ultimateutilities.commands.moderation;

import fr.ariouz.ultimateutilities.UltimateUtilities;
import fr.ariouz.ultimateutilities.managers.config.ConfigPaths;
import fr.ariouz.ultimateutilities.utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SudoCommand implements CommandExecutor {

    private final UltimateUtilities ultimateUtilities;

    public SudoCommand(UltimateUtilities ultimateUtilities){
        this.ultimateUtilities = ultimateUtilities;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(ultimateUtilities.getPluginConfig().getBoolean(ConfigPaths.SUDO_COMMAND.getEnablePath())){

            if(!sender.hasPermission("uutils.admin.command.sudo")){
                for(String str : new MessageUtils(ultimateUtilities).getMessages(sender, ConfigPaths.NO_PERMISSION_MESSAGE)){
                    sender.sendMessage(str);
                }
                return false;
            }

            if(args.length <= 1){
                for(String str : new MessageUtils(ultimateUtilities).getMessages(sender, ConfigPaths.SUDO_COMMAND_SYNTAX_MESSAGE)){
                    sender.sendMessage(str);
                }
                return false;
            }

            if(Bukkit.getPlayer(args[0]) == null){
                for(String str : new MessageUtils(ultimateUtilities).getMessages(sender, ConfigPaths.PLAYER_NOT_FOUND)){
                    sender.sendMessage(str);
                }
                return false;
            }

            Player target = Bukkit.getPlayer(args[0]);

            StringBuilder builder = new StringBuilder();
            for(String str : args){
                builder.append(str).append(" ");
            }
            String executed = builder.toString().replaceFirst(args[0]+" ", "");

            Bukkit.dispatchCommand(target, executed);
            for(String str : new MessageUtils(ultimateUtilities).getMessages(target, ConfigPaths.SUDO_COMMAND_MESSAGE)){
                sender.sendMessage(str.replaceAll("%command%", executed).replaceAll("%target%", target.getName()));
            }
            return true;

        }else {
            for (String str : new MessageUtils(ultimateUtilities).getMessages(sender, ConfigPaths.COMMAND_NOT_ENABLED)) {
                sender.sendMessage(str);
            }
            return false;
        }

    }
}
