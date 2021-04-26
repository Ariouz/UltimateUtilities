package fr.ariouz.ultimateutilities.managers.registers;

import fr.ariouz.ultimateutilities.UltimateUtilities;
import fr.ariouz.ultimateutilities.listeners.player.PlayerChat;
import fr.ariouz.ultimateutilities.listeners.player.PlayerJoin;
import fr.ariouz.ultimateutilities.listeners.player.PlayerQuit;
import fr.ariouz.ultimateutilities.listeners.server.ServerListPing;
import org.bukkit.Bukkit;

public class EventManager {

    private final UltimateUtilities ultimateUtilities;

    public EventManager(UltimateUtilities ultimateUtilities){
        this.ultimateUtilities = ultimateUtilities;
    }

    public void register(){
        Bukkit.getPluginManager().registerEvents(new PlayerJoin(ultimateUtilities), ultimateUtilities);
        Bukkit.getPluginManager().registerEvents(new PlayerQuit(ultimateUtilities), ultimateUtilities);
        Bukkit.getPluginManager().registerEvents(new PlayerChat(ultimateUtilities), ultimateUtilities);
        Bukkit.getPluginManager().registerEvents(new ServerListPing(ultimateUtilities), ultimateUtilities);
    }

}
