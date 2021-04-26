package fr.ariouz.ultimateutilities.managers.display.scoreboard;

import fr.ariouz.ultimateutilities.UltimateUtilities;
import fr.nessydesign.nessyutils.utils.display.scoreboard.FastBoard;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class ScoreboardUpdater implements UScoreboard{

    private final UltimateUtilities ultimateUtilities;
    private final ScoreboardManager scoreboardManager;
    private final List<String> lines = new ArrayList<>();

    public ScoreboardUpdater(UltimateUtilities ultimateUtilities, ScoreboardManager scoreboardManager){
        this.ultimateUtilities = ultimateUtilities;
        this.scoreboardManager = scoreboardManager;
    }

    @Override
    public void updateAll(FastBoard board, int boardChangeTime) {
        if(boardChangeTime >= scoreboardManager.getNeededBoardChangeTime()){
            scoreboardManager.addBoardChangeTime(-boardChangeTime);
            scoreboardManager.addCurrentBoard();
        }

        lines.clear();
        board.updateTitle(ChatColor.translateAlternateColorCodes('&', PlaceholderAPI.setPlaceholders(board.getPlayer(), ultimateUtilities.getScoreboardConfig().getString("boards." + scoreboardManager.getCurrentBoard()+ ".title"))));
        ultimateUtilities.getScoreboardConfig().getList("boards." + scoreboardManager.getCurrentBoard()+ ".lines").forEach(l -> {
            lines.add(ChatColor.translateAlternateColorCodes('&', PlaceholderAPI.setPlaceholders(board.getPlayer(), l.toString())));
        });

        board.updateLines(lines);

        scoreboardManager.addBoardChangeTime(scoreboardManager.getUpdateTime());
    }
}
