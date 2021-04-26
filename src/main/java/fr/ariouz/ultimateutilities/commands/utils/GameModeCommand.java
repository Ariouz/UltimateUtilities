package fr.ariouz.ultimateutilities.commands.utils;

import fr.ariouz.ultimateutilities.UltimateUtilities;
import fr.ariouz.ultimateutilities.managers.config.ConfigPaths;
import fr.ariouz.ultimateutilities.utils.MessageUtils;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class GameModeCommand implements CommandExecutor {

    private final UltimateUtilities ultimateUtilities;

    public GameModeCommand(UltimateUtilities ultimateUtilities){
        this.ultimateUtilities = ultimateUtilities;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(ultimateUtilities.getPluginConfig().getBoolean(ConfigPaths.GAMEMODE_COMMAND.getEnablePath())){
            if(label.equalsIgnoreCase("gm0") || label.equalsIgnoreCase("gms")){
                GameMode gameMode = GameMode.SURVIVAL;
                return process(sender, args, gameMode);
            }else if(label.equalsIgnoreCase("gm1") || label.equalsIgnoreCase("gmc")){
                GameMode gameMode = GameMode.CREATIVE;
                return process(sender, args, gameMode);
            }else if(label.equalsIgnoreCase("gm2") || label.equalsIgnoreCase("gma")){
                GameMode gameMode = GameMode.ADVENTURE;
                return process(sender, args, gameMode);
            }else if(label.equalsIgnoreCase("gm3") || label.equalsIgnoreCase("gmspec")){
                GameMode gameMode = GameMode.SPECTATOR;
                return process(sender, args, gameMode);
            }
        }else{
            for(String str : new MessageUtils(ultimateUtilities).getMessages(sender, ConfigPaths.COMMAND_NOT_ENABLED)){
                sender.sendMessage(str);
            }
            return false;
        }

        return false;
    }

    public boolean process(CommandSender sender, String[] args, GameMode gameMode){

        if(!sender.hasPermission("uutils.command.gamemode")){
            for(String str : new MessageUtils(ultimateUtilities).getMessages(sender, ConfigPaths.NO_PERMISSION_MESSAGE)){
                sender.sendMessage(str);
            }
            return false;
        }

        if(args.length >= 2){
            for(Object str : ultimateUtilities.getMessagesConfig().getList(ConfigPaths.COMMAND_GAMEMODE_SENDER_SYNTAX_MESSAGE.getPath())){
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', str.toString()));
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

            Player player = (Player) sender;
            player.setGameMode(gameMode);
            for(String str : new MessageUtils(ultimateUtilities).getMessages(player, ConfigPaths.COMMAND_GAMEMODE_UPDATED)){
                player.sendMessage(str.replaceAll("%target%", player.getName()).replaceAll("%gamemode%", gameMode.toString().toLowerCase()));
            }
            return true;
        }

        if(Bukkit.getPlayer(args[0]) == null){
            for(String str : new MessageUtils(ultimateUtilities).getMessages(sender, ConfigPaths.PLAYER_NOT_FOUND)){
                sender.sendMessage(str.replaceAll("%target%", args[0]));
            }
            return false;
        }

        Player target = (Player) sender;
        target.setGameMode(gameMode);
        for(String str : new MessageUtils(ultimateUtilities).getMessages(sender, ConfigPaths.COMMAND_GAMEMODE_UPDATED)){
            sender.sendMessage(str.replaceAll("%target%", target.getName()).replaceAll("%gamemode%", gameMode.toString().toLowerCase()));
        }
        return true;

    }

}
