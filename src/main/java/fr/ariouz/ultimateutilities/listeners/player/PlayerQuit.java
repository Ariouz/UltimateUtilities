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
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuit implements Listener {

    private final UltimateUtilities ultimateUtilities;

    public PlayerQuit(UltimateUtilities ultimateUtilities){
        this.ultimateUtilities = ultimateUtilities;
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        Player player = event.getPlayer();
        if(ultimateUtilities.getPluginConfig().getBoolean(ConfigPaths.QUIT_MESSAGE.getEnablePath())){
            event.setQuitMessage(null);
            for(String str : new MessageUtils(ultimateUtilities).getMessages(player, ConfigPaths.QUIT_MESSAGE)){
                Bukkit.broadcastMessage(str);
            }
        }

        if(ultimateUtilities.getScoreboardConfig().getBoolean(ConfigPaths.SCOREBOARD_ENABLED.getEnablePath())) {
            FastBoard board = ultimateUtilities.getBoards().remove(player.getUniqueId());

            if (board != null) {
                board.delete();
            }
        }
    }

}
