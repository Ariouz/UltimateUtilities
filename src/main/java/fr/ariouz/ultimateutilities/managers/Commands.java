package fr.ariouz.ultimateutilities.managers;

import fr.ariouz.ultimateutilities.managers.config.ConfigPaths;

public enum Commands {

    GAMEMODE_SURVIVAL("/gm0 (<player>) or /gms (<player>)", "Set the player's gamemode to survival.", ConfigPaths.GAMEMODE_COMMAND.getEnablePath(), "/gm0"),
    GAMEMODE_CREATIVE("/gm1 (<player>) or /gmc (<player>)", "Set the player's gamemode to creative.", ConfigPaths.GAMEMODE_COMMAND.getEnablePath(), "/gm1"),
    GAMEMODE_ADVENTURE("/gm2 (<player>) or /gma (<player>)", "Set the player's gamemode to adventure.", ConfigPaths.GAMEMODE_COMMAND.getEnablePath(), "gm2"),
    GAMEMODE_SPECTATOR("/gm3 (<player>) or /gmspec (<player>)", "Set the player's gamemode to spectator.", ConfigPaths.GAMEMODE_COMMAND.getEnablePath(), "/gm3"),

    FEED("/feed (<player>)", "Set the player's food level and saturation to max.", ConfigPaths.FEED_COMMAND.getEnablePath(), "/feed"),
    DAY("/day", "Set the time to day.", ConfigPaths.DAY_COMMAND.getEnablePath(), "/day"),
    NIGHT("/night", "Set the time to night.", ConfigPaths.NIGHT_COMMAND.getEnablePath(), "/night"),

    DISCORD("/discord", "Get the server's discord link.", ConfigPaths.DISCORD_COMMAND.getEnablePath(), "/discord"),
    WEBSITE("/website", "Get the server's website link.", ConfigPaths.WEBSITE_COMMAND.getEnablePath(), "/website"),
    VOTE("/vote", "Get the server's vote link.", ConfigPaths.VOTE_COMMAND.getEnablePath(), "/vote"),
    STORE("/store", "Get the server's store link.", ConfigPaths.STORE_COMMAND.getEnablePath(), "/store"),

    FLY("/fly (<player>)", "Enable or disable fly for a player.", ConfigPaths.FLY_COMMAND.getEnablePath(), "/fly"),

    SPAWN("/spawn", "Teleport the player to the spawn.", ConfigPaths.SPAWN_COMMAND.getEnablePath(), "/spawn"),
    SETSPAWN("/setspawn", "Set the server's spawn to the player location.", ConfigPaths.SETSPAWN_COMMAND.getEnablePath(), "/setspawn"),

    // ADMIN COMMANDS

    URELOAD("/ureload", "Reload plugin.", null, "/ureload"),
    SUDO("/sudo <player>", "Force player to execute a command.", ConfigPaths.SUDO_COMMAND.getEnablePath(), "/sudo <player>"),
    BROADCAST("/broadcast <message> or /bc <message>", "Send a broadcast message.", ConfigPaths.BROADCAST_COMMAND.getEnablePath(), "/broadcast <message>"),
    MOTD("/motd <1/2> <message>", "Set the server motd in one command.", ConfigPaths.MOTD_COMMAND.getEnablePath(), "/motd <1/2> <message>"),
    STAFFCHAT("/staffchat <message> or /sc <message>", "Chat with staff players.", ConfigPaths.STAFFCHAT_COMMAND.getEnablePath(), "/staffchat <message>"),

    ;

    private final String command;
    private final String description;
    private final String enablePath;
    private final String commandEx;

    Commands(String command, String description, String enablePath, String commandEx){
        this.command = command;
        this.description = description;
        this.enablePath = enablePath;
        this.commandEx = commandEx;
    }

    public String getCommand() {
        return command;
    }

    public String getDescription() {
        return description;
    }

    public String getEnablePath() {
        return enablePath;
    }

    public String getCommandEx() {
        return commandEx;
    }
}
