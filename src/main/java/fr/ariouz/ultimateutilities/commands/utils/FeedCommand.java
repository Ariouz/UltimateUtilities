package fr.ariouz.ultimateutilities.commands.utils;

import fr.ariouz.ultimateutilities.UltimateUtilities;
import fr.ariouz.ultimateutilities.managers.config.ConfigPaths;
import fr.ariouz.ultimateutilities.utils.CooldownUtils;
import fr.ariouz.ultimateutilities.utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class FeedCommand implements CommandExecutor {

    private final UltimateUtilities ultimateUtilities;

    private final HashMap<String, Long> cooldown = new HashMap<>();

    public FeedCommand(UltimateUtilities ultimateUtilities) {
        this.ultimateUtilities = ultimateUtilities;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(ultimateUtilities.getPluginConfig().getBoolean(ConfigPaths.FEED_COMMAND.getEnablePath())){
            if(args.length == 0){
                if(!(sender instanceof Player)){
                    for(String str : new MessageUtils(ultimateUtilities).getMessages(sender, ConfigPaths.ONLY_PLAYER_CAN_USE_COMMAND)){
                        sender.sendMessage(str);
                    }
                    return false;
                }

                Player player = (Player) sender;

                int cooldownTime = ultimateUtilities.getPluginConfig().getInt(ConfigPaths.FEED_COMMAND.getPath()+".cooldown");

                if(player.hasPermission("uutils.command.feed")){
                    if(!new CooldownUtils().isOnCooldown(player, cooldown)){
                        player.setFoodLevel(20);
                        player.setSaturation(20);
                        for(String str : new MessageUtils(ultimateUtilities).getMessages(sender, ConfigPaths.COMMAND_FEED_MESSAGE)){
                            sender.sendMessage(str);
                        }
                        if(!player.isOp() || !ultimateUtilities.getPluginConfig().getBoolean(ConfigPaths.FEED_COMMAND.getPath() + ".op-no-cooldown")){
                            cooldown.put(player.getName(), System.currentTimeMillis() + (cooldownTime*1000));
                        }

                        return true;
                    }else{

                        long hours, minutes, seconds;

                        seconds = (new CooldownUtils().getCooldown(player, cooldown) - System.currentTimeMillis())/1000;
                        hours = 0;
                        minutes = 0;

                        while(seconds >= 60){
                            seconds-=60;
                            minutes++;
                        }

                        while(minutes >=60){
                            minutes-=60;
                            hours++;
                        }

                        for(String str : new MessageUtils(ultimateUtilities).getMessages(player, ConfigPaths.COMMAND_FEED_COOLDOWN_MESSAGE)){
                            player.sendMessage(str.replaceAll("%hours%", hours+"").replaceAll("%minutes%", minutes+"").replaceAll("%seconds%", seconds+""));
                        }
                        return false;
                    }
                }else{
                    for(String str : new MessageUtils(ultimateUtilities).getMessages(sender, ConfigPaths.NO_PERMISSION_MESSAGE)){
                        sender.sendMessage(str);
                    }
                    return false;
                }
            }else if(args.length == 1){
                if(sender.isOp()){
                    if(Bukkit.getPlayer(args[0]) != null){
                        Player target = Bukkit.getPlayer(args[0]);
                        target.setFoodLevel(20);
                        target.setSaturation(20);
                        for(String str : new MessageUtils(ultimateUtilities).getMessages(sender, ConfigPaths.COMMAND_FEED_OTHER_MESSAGE)){
                            sender.sendMessage(str.replaceAll("%target%", target.getName()));
                        }
                    }else{
                        for(String str : new MessageUtils(ultimateUtilities).getMessages(sender, ConfigPaths.PLAYER_NOT_FOUND)){
                            sender.sendMessage(str.replaceAll("%target%", args[0]));
                        }
                        return false;
                    }
                }else{
                    for(String str : new MessageUtils(ultimateUtilities).getMessages(sender, ConfigPaths.NO_PERMISSION_MESSAGE)){
                        sender.sendMessage(str);
                    }
                    return false;
                }
            }else{
                for(String str : new MessageUtils(ultimateUtilities).getMessages(sender, ConfigPaths.COMMAND_FEED_SYNTAX_MESSAGE)){
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

        return false;
    }
}
