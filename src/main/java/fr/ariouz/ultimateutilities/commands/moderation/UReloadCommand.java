package fr.ariouz.ultimateutilities.commands.moderation;

import fr.ariouz.ultimateutilities.UltimateUtilities;
import fr.ariouz.ultimateutilities.managers.config.ConfigPaths;
import fr.ariouz.ultimateutilities.utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class UReloadCommand implements CommandExecutor {

    private UltimateUtilities ultimateUtilities;

    public UReloadCommand(UltimateUtilities ultimateUtilities){
        this.ultimateUtilities = ultimateUtilities;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("uutils.admin.command.ureload")) {
            for(String str : new MessageUtils(ultimateUtilities).getMessages(sender, ConfigPaths.NO_PERMISSION_MESSAGE)){
                sender.sendMessage(str);
            }
            return false;
        }

        System.out.println("[UltimateUtilities] Reloading plugin...");
        ultimateUtilities.getServer().getPluginManager().disablePlugin(ultimateUtilities);
        ultimateUtilities.getServer().getPluginManager().enablePlugin(ultimateUtilities);
        System.out.println("[UltimateUtilities] Reloading PlaceHolderAPI...");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "papi reload");
        System.out.println("[UltimateUtilities] PlaceHolderAPI reloaded !");
        ultimateUtilities.loadConfigs();

        sender.sendMessage(ChatColor.BOLD + "" + ChatColor.GRAY + "[" + ChatColor.RED + "UltimateUtilities" + ChatColor.GRAY + "] " + ChatColor.GOLD + "Plugin reloaded !");
        return true;
    }
}
