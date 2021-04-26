package fr.ariouz.ultimateutilities.managers.registers;

import fr.ariouz.ultimateutilities.UltimateUtilities;
import fr.ariouz.ultimateutilities.commands.UHelpCommand;
import fr.ariouz.ultimateutilities.commands.moderation.UReloadCommand;
import fr.ariouz.ultimateutilities.commands.moderation.chat.BroadcastCommand;
import fr.ariouz.ultimateutilities.commands.moderation.MotdCommand;
import fr.ariouz.ultimateutilities.commands.moderation.SudoCommand;
import fr.ariouz.ultimateutilities.commands.moderation.chat.StaffChatCommand;
import fr.ariouz.ultimateutilities.commands.moderation.world.SetSpawnCommand;
import fr.ariouz.ultimateutilities.commands.player.FlyCommand;
import fr.ariouz.ultimateutilities.commands.player.SpawnCommand;
import fr.ariouz.ultimateutilities.commands.utils.FeedCommand;
import fr.ariouz.ultimateutilities.commands.utils.GameModeCommand;
import fr.ariouz.ultimateutilities.commands.utils.messages.*;
import fr.ariouz.ultimateutilities.commands.utils.world.DayCommand;
import fr.ariouz.ultimateutilities.commands.utils.world.NightCommand;

public class CommandManager {

    private final UltimateUtilities ultimateUtilities;

    public CommandManager(UltimateUtilities ultimateUtilities){
        this.ultimateUtilities = ultimateUtilities;
    }

    public void register(){
        ultimateUtilities.getCommand("uhelp").setExecutor(new UHelpCommand(ultimateUtilities));
        ultimateUtilities.getCommand("ureload").setExecutor(new UReloadCommand(ultimateUtilities));

        ultimateUtilities.getCommand("gm0").setExecutor(new GameModeCommand(ultimateUtilities));
        ultimateUtilities.getCommand("gm1").setExecutor(new GameModeCommand(ultimateUtilities));
        ultimateUtilities.getCommand("gm2").setExecutor(new GameModeCommand(ultimateUtilities));
        ultimateUtilities.getCommand("gm3").setExecutor(new GameModeCommand(ultimateUtilities));
        ultimateUtilities.getCommand("feed").setExecutor(new FeedCommand(ultimateUtilities));
        ultimateUtilities.getCommand("day").setExecutor(new DayCommand(ultimateUtilities));
        ultimateUtilities.getCommand("night").setExecutor(new NightCommand(ultimateUtilities));

        ultimateUtilities.getCommand("discord").setExecutor(new DiscordCommand(ultimateUtilities));
        ultimateUtilities.getCommand("website").setExecutor(new WebsiteCommand(ultimateUtilities));
        ultimateUtilities.getCommand("vote").setExecutor(new VoteCommand(ultimateUtilities));
        ultimateUtilities.getCommand("store").setExecutor(new StoreCommand(ultimateUtilities));

        ultimateUtilities.getCommand("fly").setExecutor(new FlyCommand(ultimateUtilities));

        ultimateUtilities.getCommand("spawn").setExecutor(new SpawnCommand(ultimateUtilities));
        ultimateUtilities.getCommand("setspawn").setExecutor(new SetSpawnCommand(ultimateUtilities));


        // ADMIN AND MODERATION
        ultimateUtilities.getCommand("sudo").setExecutor(new SudoCommand(ultimateUtilities));
        ultimateUtilities.getCommand("broadcast").setExecutor(new BroadcastCommand(ultimateUtilities));
        ultimateUtilities.getCommand("motd").setExecutor(new MotdCommand(ultimateUtilities));
        ultimateUtilities.getCommand("staffchat").setExecutor(new StaffChatCommand(ultimateUtilities));
    }

}
