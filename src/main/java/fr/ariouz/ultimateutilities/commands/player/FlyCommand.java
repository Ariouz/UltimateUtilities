package fr.ariouz.ultimateutilities.commands.player;

import fr.ariouz.ultimateutilities.UltimateUtilities;
import fr.ariouz.ultimateutilities.managers.config.ConfigPaths;
import fr.ariouz.ultimateutilities.utils.MessageUtils;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class FlyCommand implements CommandExecutor {

    private final UltimateUtilities ultimateUtilities;

    private final ArrayList<String> flyingPlayers = new ArrayList<>();

    public FlyCommand(UltimateUtilities ultimateUtilities){
        this.ultimateUtilities = ultimateUtilities;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(ultimateUtilities.getPluginConfig().getBoolean(ConfigPaths.FLY_COMMAND.getEnablePath())){
            if(!sender.hasPermission("uutils.command.fly")){
                for(String str : new MessageUtils(ultimateUtilities).getMessages(sender, ConfigPaths.NO_PERMISSION_MESSAGE)){
                    sender.sendMessage(str);
                }
                return false;
            }

            if(args.length == 0){
                if(!(sender instanceof Player)){
                    for(String str : new MessageUtils(ultimateUtilities).getMessages(sender, ConfigPaths.ONLY_PLAYER_CAN_USE_COMMAND)){
                        sender.sendMessage(str);
                    }
                    return false;
                }

                Player player = (Player)sender;

                return fly(player, player, false);

            }else if(args.length == 1){
                if(Bukkit.getPlayer(args[0]) == null){
                    for(String str : new MessageUtils(ultimateUtilities).getMessages(sender, ConfigPaths.PLAYER_NOT_FOUND)){
                        sender.sendMessage(str);
                    }
                    return false;
                }

                Player target = Bukkit.getPlayer(args[0]);
                target.setAllowFlight(true);
                target.setFlying(true);

                return fly(sender, target, true);
            }else{
                for(String str : new MessageUtils(ultimateUtilities).getMessages(sender, ConfigPaths.FLY_COMMAND_SYNTAX_MESSAGE)){
                    sender.sendMessage(str);
                }
                return false;
            }

        }else{
            for(String str : new MessageUtils(ultimateUtilities).getMessages(sender, ConfigPaths.COMMAND_NOT_ENABLED)){
                sender.sendMessage(str);
            }
            return false;
        }

    }

    private boolean fly(CommandSender sender, Player target, boolean bool) {
        if(flyingPlayers.contains(target.getName())){
            target.setAllowFlight(false);
            target.setFlying(false);
            for(String str : new MessageUtils(ultimateUtilities).getMessages(target, ConfigPaths.FLY_COMMAND_DISABLE_MESSAGE)){
                sender.sendMessage(PlaceholderAPI.setPlaceholders(target, str.replaceAll("%player%", target.getName())));
            }
            if(bool) {
                for (String str : new MessageUtils(ultimateUtilities).getMessages(target, ConfigPaths.FLY_COMMAND_TARGET_DISABLE_MESSAGE)) {
                    target.sendMessage(PlaceholderAPI.setPlaceholders(target, str.replaceAll("%sender%", sender.getName())));
                }
            }
            flyingPlayers.remove(target.getName());
        }else{
            target.setAllowFlight(true);
            target.setFlying(true);
            for(String str : new MessageUtils(ultimateUtilities).getMessages(target, ConfigPaths.FLY_COMMAND_ENABLE_MESSAGE)){
                sender.sendMessage(PlaceholderAPI.setPlaceholders(target, str.replaceAll("%player%", target.getName())));
            }
            if(bool) {
                for (String str : new MessageUtils(ultimateUtilities).getMessages(target, ConfigPaths.FLY_COMMAND_TARGET_ENABLE_MESSAGE)) {
                    target.sendMessage(PlaceholderAPI.setPlaceholders(target, str.replaceAll("%sender%", sender.getName())));
                }
            }
            flyingPlayers.add(target.getName());
        }
        return true;
    }
}
