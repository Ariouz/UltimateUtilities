package fr.ariouz.ultimateutilities.commands;

import fr.ariouz.ultimateutilities.UltimateUtilities;
import fr.ariouz.ultimateutilities.managers.Commands;
import fr.ariouz.ultimateutilities.managers.config.ConfigPaths;
import fr.ariouz.ultimateutilities.utils.MessageUtils;
import net.md_5.bungee.api.chat.*;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class UHelpCommand implements CommandExecutor {

    private final UltimateUtilities ultimateUtilities;

    public UHelpCommand(UltimateUtilities ultimateUtilities){
        this.ultimateUtilities = ultimateUtilities;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        ArrayList<TextComponent> cmds = new ArrayList<>();

        int commandPerPage = 4;

        for(Commands commands : Commands.values()){
            boolean enabled = (commands.getEnablePath() == null || ultimateUtilities.getPluginConfig().getBoolean(commands.getEnablePath()));
            BaseComponent[] baseComponents = TextComponent.fromLegacyText(ChatColor.GOLD + commands.getCommand() + ChatColor.GRAY + " » " + commands.getDescription() + ChatColor.BOLD + " Enabled: " + (enabled ? ChatColor.GREEN + "✓" : ChatColor.RED + "✗"));
            TextComponent textComponent = new TextComponent(baseComponents);
            textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GOLD + "Click to execute !").create()));
            textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, commands.getCommandEx()));


            cmds.add(textComponent);
        }

        int totalCommand = cmds.size();
        int pageNumber = ((cmds.size()-(totalCommand%commandPerPage))/commandPerPage)+(cmds.size()%commandPerPage == 0 ? 0 : 1);

        if(args.length == 1){
            if(!ultimateUtilities.isInteger(args[0])){
                for(String str : new MessageUtils(ultimateUtilities).getMessages(sender, ConfigPaths.MUST_BE_AN_INTEGER)){
                    sender.sendMessage(str);
                }
                return false;
            }

            int page = Integer.parseInt(args[0]);

            if(page > pageNumber){
                sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + " UltimateUtilities" + ChatColor.GRAY + "]" + ChatColor.RESET + "" + ChatColor.RED + " Max page is " + pageNumber);
                return false;
            }

            if(page <= 0){
                sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + " UltimateUtilities" + ChatColor.GRAY + "]" + ChatColor.RESET + "" + ChatColor.RED + " Min page is 1");
                return false;
            }

            sendPaginationHelp(sender, cmds, commandPerPage, pageNumber, page);

        }else{
            int page = 1;
            sendPaginationHelp(sender, cmds, commandPerPage, pageNumber, page);
            return true;
        }

        return false;
    }

    private void sendPaginationHelp(CommandSender sender, ArrayList<TextComponent> cmds, int commandPerPage, int pageNumber, int page) {
        int max = page * commandPerPage;
        int min = max - commandPerPage;

        ComponentBuilder componentBuilder = new ComponentBuilder(ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "--------");
        componentBuilder.append(" ");

        if(page-1 >= 1){
            TextComponent previous = new TextComponent(ChatColor.YELLOW + "«");
            componentBuilder.append(previous.getText());
            componentBuilder.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GRAY + "Previous page").create()));
            componentBuilder.event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/uhelp " + (page-1)));
        }

        componentBuilder.append(ChatColor.GOLD + " Page "+ page + "/"+ pageNumber + " ");
        componentBuilder.reset();

        if(page+1 <= pageNumber){
            TextComponent next = new TextComponent(ChatColor.YELLOW + "»");
            componentBuilder.append(next.getText());
            componentBuilder.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GRAY + "Next page").create()));
            componentBuilder.event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/uhelp " + (page+1)));
        }

        componentBuilder.append(" ");
        componentBuilder.append(ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "--------");
        componentBuilder.reset();

        sender.sendMessage(ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "-----" + ChatColor.RED + "" + ChatColor.BOLD + " UltimateUtilities Help Page " + ChatColor.RESET + "" + ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "-----");
        sender.sendMessage("");


        if(sender instanceof Player){
            Player player = (Player) sender;

            for(int i = min+1; i <= max; i++) {
                if (i - 1 >= cmds.size()) {
                    break;
                }
                player.spigot().sendMessage(cmds.get(i-1));
            }

            sender.sendMessage("");
            sender.sendMessage(ChatColor.WHITE + "You can enable or disable commands in the config.yml file.");
            player.spigot().sendMessage(componentBuilder.create());
        }else{

            for(int i = min+1; i <= max; i++) {
                if (i - 1 >= cmds.size()) {
                    break;
                }
                sender.sendMessage(cmds.get(i-1).toLegacyText());
            }

            sender.sendMessage("");
            sender.sendMessage(ChatColor.WHITE + "You can enable or disable commands in the config.yml file.");
            sender.sendMessage(ChatColor.GRAY + "" + ChatColor.STRIKETHROUGH + "---------- Page "+ page + "/"+ pageNumber+ " ----------");
        }
    }
}
