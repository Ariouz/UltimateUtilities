package fr.ariouz.ultimateutilities.listeners.server;

import fr.ariouz.ultimateutilities.UltimateUtilities;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;


public class ServerListPing implements Listener {

    private final UltimateUtilities ultimateUtilities;

    public ServerListPing(UltimateUtilities ultimateUtilities){
        this.ultimateUtilities = ultimateUtilities;
    }

    @EventHandler
    public void onPing(ServerListPingEvent event){
        String first = ultimateUtilities.getMotdConfig().getString("motd.lines.first");
        String second = ultimateUtilities.getMotdConfig().getString("motd.lines.second");
        event.setMotd(ChatColor.translateAlternateColorCodes('&', (first+"\n"+second).replaceAll("%online_count%", event.getNumPlayers()+"").replaceAll("'", " ")));
    }

}
