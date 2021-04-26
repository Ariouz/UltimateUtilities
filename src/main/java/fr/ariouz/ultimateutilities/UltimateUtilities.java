package fr.ariouz.ultimateutilities;

import de.jeff_media.updatechecker.UpdateChecker;
import de.jeff_media.updatechecker.UserAgentBuilder;
import fr.ariouz.ultimateutilities.managers.config.ConfigPaths;
import fr.ariouz.ultimateutilities.managers.config.ConfigUpdater;
import fr.ariouz.ultimateutilities.managers.display.scoreboard.ScoreboardManager;
import fr.ariouz.ultimateutilities.managers.registers.CommandManager;
import fr.ariouz.ultimateutilities.managers.registers.EventManager;
import fr.ariouz.ultimateutilities.utils.ConfigUtils;
import fr.ariouz.ultimateutilities.utils.Metrics;
import fr.nessydesign.nessyutils.utils.display.scoreboard.FastBoard;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.UUID;

public final class UltimateUtilities extends JavaPlugin {

    private final int metricsId = 11156;
    private static final int SPIGOT_RESOURCE_ID = 91705;

    private EventManager eventManager;
    private CommandManager commandManager;

    private ConfigUtils config;
    private ConfigUtils messagesConfig;
    private ConfigUtils motdConfig;
    private ConfigUtils scoreboardConfig;
    private ConfigUtils updateConfig;

    private ScoreboardManager scoreboardManager;

    private final HashMap<UUID, FastBoard> boards = new HashMap<>();

    @Override
    public void onEnable() {
        long start = System.currentTimeMillis();

        saveDefaultConfig();

        config = new ConfigUtils(this, "config.yml");
        messagesConfig = new ConfigUtils(this, "messages.yml");
        motdConfig = new ConfigUtils(this, "motd.yml");
        scoreboardConfig = new ConfigUtils(this, "scoreboard.yml");
        updateConfig = new ConfigUtils(this, "update.yml");

        if (!this.config.getFile().exists()) {
            saveResource("config.yml", false);
            Bukkit.getLogger().info("config.yml file created !");
        }

        if (!this.messagesConfig.getFile().exists()) {
            saveResource("messages.yml", false);
            Bukkit.getLogger().info("messages.yml file created !");
        }

        if (!this.motdConfig.getFile().exists()) {
            saveResource("motd.yml", false);
            Bukkit.getLogger().info("motd.yml file created !");
        }

        if (!this.scoreboardConfig.getFile().exists()) {
            saveResource("scoreboard.yml", false);
            Bukkit.getLogger().info("scoreboard.yml file created !");
        }

        if (!this.updateConfig.getFile().exists()) {
            saveResource("update.yml", false);
            Bukkit.getLogger().info("update.yml file created !");
        }

        this.eventManager = new EventManager(this);
        this.commandManager = new CommandManager(this);

        this.eventManager.register();
        this.commandManager.register();

        loadConfigs();

        this.scoreboardManager = new ScoreboardManager(this);

        System.out.println("[UltimateUtilities] Loading Metrics...");
        Metrics metrics = new Metrics(this, metricsId);
        System.out.println("[UltimateUtilities] Metrics loaded !");

        if (getScoreboardConfig().getBoolean(ConfigPaths.SCOREBOARD_ENABLED.getEnablePath())) {

            for (Player player : Bukkit.getOnlinePlayers()) {
                FastBoard board = new FastBoard(player);
                board.updateTitle(ChatColor.translateAlternateColorCodes('&', ChatColor.translateAlternateColorCodes('&', PlaceholderAPI.setPlaceholders(board.getPlayer(), getScoreboardConfig().getString("boards." + getScoreboardManager().getCurrentBoard()+ ".title")))));
                boards.put(player.getUniqueId(), board);
            }

            new BukkitRunnable() {
                @Override
                public void run() {
                    for (FastBoard board : boards.values()) {
                        scoreboardManager.updateBoard(board);
                    }
                }
            }.runTaskTimer(this, 0, scoreboardManager.getUpdateTime());

        }

        if(getUpdateConfig().getBoolean("update_checker.check")){
            UpdateChecker.init(this, SPIGOT_RESOURCE_ID)
                    .setDownloadLink("https://www.spigotmc.org/resources/ultimateutilities-powerfull-server-and-admin-utilities.91705/")
                    .setDonationLink("https://paypal.me/cloudskymc")
                    .setNotifyOpsOnJoin(getUpdateConfig().getBoolean("update_checker.sendToOp"))
                    .setUserAgent(new UserAgentBuilder().addPluginNameAndVersion())
                    .checkEveryXHours(6)
                    .checkNow();
        }

        Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "----------------------");
        Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "[Ultimate Utilities] " + ChatColor.WHITE + "Plugin successfully loaded in " + (System.currentTimeMillis()-start) + "ms !");
        Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "----------------------");
    }

    @Override
    public void onDisable() {
        long stop = System.currentTimeMillis();


        Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "----------------------");
        Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "[Ultimate Utilities] " + ChatColor.WHITE + "Plugin successfully unloaded in " + (System.currentTimeMillis()-stop) + "ms !");
        Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "----------------------");
    }

    public void loadConfigs(){
        config.reloadConfiguration();
        messagesConfig.reloadConfiguration();
        motdConfig.reloadConfiguration();
        scoreboardConfig.reloadConfiguration();
        updateConfig.reloadConfiguration();

        System.out.println("[UltimateUtilities] Checking for config update: config.yml");
        new ConfigUpdater(this).update("config.yml");
        System.out.println("[UltimateUtilities] Checking for config update: messages.yml");
        new ConfigUpdater(this).update("messages.yml");
        System.out.println("[UltimateUtilities] Checking for config update: motd.yml");
        new ConfigUpdater(this).update("motd.yml");
        System.out.println("[UltimateUtilities] scoreboard.yml can't be updated automatically");
        new ConfigUpdater(this).update("update.yml");
        System.out.println("[UltimateUtilities] Checking for config update: update.yml");

        config.reloadConfiguration();
        messagesConfig.reloadConfiguration();
        motdConfig.reloadConfiguration();
        updateConfig.reloadConfiguration();
    }

    public EventManager getEventManager() {
        return eventManager;
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }

    public ConfigUtils getPluginConfig() {
        return config;
    }

    public ConfigUtils getMessagesConfig() {
        return messagesConfig;
    }

    public ConfigUtils getMotdConfig() {
        return motdConfig;
    }

    public ConfigUtils getScoreboardConfig() {
        return scoreboardConfig;
    }

    public HashMap<UUID, FastBoard> getBoards() {
        return boards;
    }

    public ScoreboardManager getScoreboardManager() {
        return scoreboardManager;
    }

    public ConfigUtils getUpdateConfig() {
        return updateConfig;
    }

    public boolean isInteger(String s) {
        return isInteger(s,10);
    }

    public boolean isInteger(String s, int radix) {
        if(s.isEmpty()) return false;
        for(int i = 0; i < s.length(); i++) {
            if(i == 0 && s.charAt(i) == '-') {
                if(s.length() == 1) return false;
                else continue;
            }
            if(Character.digit(s.charAt(i),radix) < 0) return false;
        }
        return true;
    }

}
