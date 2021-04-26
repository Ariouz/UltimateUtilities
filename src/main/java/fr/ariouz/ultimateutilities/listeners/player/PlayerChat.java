package fr.ariouz.ultimateutilities.listeners.player;

import fr.ariouz.ultimateutilities.UltimateUtilities;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChat implements Listener {

    private final UltimateUtilities ultimateUtilities;

    public PlayerChat(UltimateUtilities ultimateUtilities){
        this.ultimateUtilities = ultimateUtilities;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event){
        Player player  = event.getPlayer();
        if(player.hasPermission("uutils.chatcolor")){
            event.setMessage(ChatColor.translateAlternateColorCodes('&', event.getMessage()));
        }
    }

}
