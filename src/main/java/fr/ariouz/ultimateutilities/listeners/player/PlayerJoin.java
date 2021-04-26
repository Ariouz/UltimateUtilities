package fr.ariouz.ultimateutilities.listeners.player;

import fr.ariouz.ultimateutilities.UltimateUtilities;
import fr.ariouz.ultimateutilities.managers.config.ConfigPaths;
import fr.ariouz.ultimateutilities.utils.MessageUtils;
import fr.nessydesign.nessyutils.utils.display.scoreboard.FastBoard;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {

    private final UltimateUtilities ultimateUtilities;

    public PlayerJoin(UltimateUtilities ultimateUtilities){
        this.ultimateUtilities = ultimateUtilities;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        if(ultimateUtilities.getPluginConfig().getBoolean(ConfigPaths.JOIN_MESSAGE.getEnablePath())){
            event.setJoinMessage(null);
            for(String str : new MessageUtils(ultimateUtilities).getMessages(player, ConfigPaths.JOIN_MESSAGE)){
                Bukkit.broadcastMessage(str);
            }

            for(String str : new MessageUtils(ultimateUtilities).getMessages(player, ConfigPaths.PRIVATE_JOIN_MESSAGE)){
                player.sendMessage(str);
            }
        }

        if(ultimateUtilities.getScoreboardConfig().getBoolean(ConfigPaths.SCOREBOARD_ENABLED.getEnablePath())){
            FastBoard board = new FastBoard(player);
            board.updateTitle(ChatColor.translateAlternateColorCodes('&', ChatColor.translateAlternateColorCodes('&', PlaceholderAPI.setPlaceholders(board.getPlayer(), ultimateUtilities.getScoreboardConfig().getString("boards." + ultimateUtilities.getScoreboardManager().getCurrentBoard()+ ".title")))));
            ultimateUtilities.getBoards().put(player.getUniqueId(), board);
        }

    }

}
