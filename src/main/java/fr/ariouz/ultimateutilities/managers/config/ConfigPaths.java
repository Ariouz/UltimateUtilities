package fr.ariouz.ultimateutilities.managers.config;

public enum ConfigPaths {

    SCOREBOARD_ENABLED("is_scoreboard_enabled", "is_scoreboard_enabled"),

    JOIN_MESSAGE("join_message", "join_message.enabled"),
    PRIVATE_JOIN_MESSAGE("private_join_message", "private_join_message"),
    QUIT_MESSAGE("quit_message", "quit_message.enabled"),
    FEED_COMMAND("feed_command", "feed_command.enabled"),
    GAMEMODE_COMMAND("gamemode_command", "gamemode_command.enabled"),
    DAY_COMMAND("day_command", "day_command.enabled"),
    NIGHT_COMMAND("night_command", "night_command.enabled"),
    DISCORD_COMMAND("discord_command", "discord_command.enabled"),
    WEBSITE_COMMAND("website_command", "website_command.enabled"),
    VOTE_COMMAND("vote_command", "vote_command.enabled"),
    STORE_COMMAND("store_command", "store_command.enabled"),
    BROADCAST_COMMAND("broadcast_command", "broadcast_command.enabled"),
    FLY_COMMAND("fly_command", "fly_command.enabled"),
    SUDO_COMMAND("sudo_command", "sudo_command.enabled"),
    MOTD_COMMAND("motd_command", "motd_command.enabled"),
    STAFFCHAT_COMMAND("staffchat_command", "staffchat_command.enabled"),
    SPAWN_COMMAND("spawn_command", "spawn_command.enabled"),
    SETSPAWN_COMMAND("setspawn_command", "setspawn_command.enabled"),

    COMMAND_NOT_ENABLED("command_not_enabled", "command_not_enabled"),

    // COMMANDS MESSAGES

    PLAYER_NOT_FOUND("player_not_found_message", "player_not_found_message"),
    MUST_BE_AN_INTEGER("arg_must_be_integer", "arg_must_be_integer"),
    NO_PERMISSION_MESSAGE("no_permission_message", "no_permission_message"),
    ONLY_PLAYER_CAN_USE_COMMAND("only_player_can_use_command", "only_player_can_use_command"),
    COMMAND_GAMEMODE_SENDER_SYNTAX_MESSAGE("command_gamemode_syntax_message", "gamemode_command_syntax_message"),
    COMMAND_GAMEMODE_UPDATED("command_gamemode_updated", "command_gamemode_updated"),
    COMMAND_FEED_MESSAGE("command_feed", "command_feed"),
    COMMAND_FEED_OTHER_MESSAGE("command_feed_other", "command_feed_other"),
    COMMAND_FEED_COOLDOWN_MESSAGE("command_feed_cooldown", "command_feed_cooldown"),
    COMMAND_FEED_SYNTAX_MESSAGE("command_feed_syntax_message", "command_feed_syntax_message"),
    COMMAND_DAY_MESSAGE("command_day_message", "command_day_message"),
    COMMAND_NIGHT_MESSAGE("command_night_message", "command_night_message"),

    DISCORD_COMMAND_MESSAGE("command_discord_message", "command_discord_message"),
    WEBSITE_COMMAND_MESSAGE("command_website_message", "command_website_message"),
    VOTE_COMMAND_MESSAGE("command_vote_message", "command_vote_message"),
    STORE_COMMAND_MESSAGE("command_store_message", "command_store_message"),

    FLY_COMMAND_ENABLE_MESSAGE("command_fly_message_enable", "command_fly_message_enable"),
    FLY_COMMAND_DISABLE_MESSAGE("command_fly_message_disable", "command_fly_message_disable"),
    FLY_COMMAND_SYNTAX_MESSAGE("command_fly_syntax_message", "command_fly_syntax_message"),
    FLY_COMMAND_TARGET_ENABLE_MESSAGE("command_fly_target_message_enable", "command_fly_target_message_enable"),
    FLY_COMMAND_TARGET_DISABLE_MESSAGE("command_fly_target_message_disable", "command_fly_target_message_disable"),

    SPAWN_COMMAND_MESSAGE("command_spawn_message", "command_spawn_message"),
    SETSPAWN_COMMAND_MESSAGE("command_setspawn_message", "command_setspawn_message"),

    // ADMIN AND MODERATION COMMAND MESSAGES

    SUDO_COMMAND_MESSAGE("command_sudo_message", "command_sudo_message"),
    SUDO_COMMAND_SYNTAX_MESSAGE("command_sudo_syntax_message", "command_sudo_syntax_message"),

    BROADCAST_COMMAND_MESSAGE("command_broadcast_message", "command_broadcast_message"),
    BROADCAST_COMMAND_SYNTAX_MESSAGE("command_broadcast_syntax", "command_broadcast_syntax"),

    MOTD_COMMAND_MESSAGE("command_motd_message", "command_motd_message"),
    MOTD_COMMAND_SYNTAX_MESSAGE("command_motd_syntax_message", "command_motd_syntax_message"),

    STAFFCHAT_COMMAND_MESSAGE("command_staffchat_message", "command_staffchat_message"),
    STAFFCHAT_COMMAND_SYNTAX_MESSAGE("command_staffchat_syntax_message", "command_staffchat_syntax_message"),

    ;

    private final String path;
    private final String enablePath;

    ConfigPaths(String path, String enablePath){
        this.path = path;
        this.enablePath = enablePath;
    }

    public String getPath() {
        return path;
    }

    public String getEnablePath() {
        return enablePath;
    }
}
