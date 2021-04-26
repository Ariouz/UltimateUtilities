package fr.ariouz.ultimateutilities.managers.display.scoreboard;

import fr.ariouz.ultimateutilities.UltimateUtilities;
import fr.nessydesign.nessyutils.utils.display.scoreboard.FastBoard;

import java.util.LinkedList;

public class ScoreboardManager {

    private final UltimateUtilities ultimateUtilities;
    private final UScoreboard scoreboard;

    private final LinkedList<String> boards = new LinkedList<>();
    private String currentBoard = "";
    private int currentId = 0;
    private int updateTime = 0;
    private int boardChangeTime = 0;

    public ScoreboardManager(UltimateUtilities ultimateUtilities){
        this.ultimateUtilities = ultimateUtilities;
        this.scoreboard = new ScoreboardUpdater(ultimateUtilities, this);
        loadBoards();
        initCurrentBoard();
        initUpdateTime();
        initBoardChangeTime();
    }

    public void updateBoard(FastBoard board) {
        scoreboard.updateAll(board, boardChangeTime);
    }

    public int getNumberOfBoard(){
        return ultimateUtilities.getScoreboardConfig().getYamlConfiguration().getConfigurationSection("boards").getKeys(false).size();
    }

    public void loadBoards(){
        for(int i = 0; i < getNumberOfBoard(); i++){
            boards.add((ultimateUtilities.getScoreboardConfig().getYamlConfiguration().getConfigurationSection("boards").getKeys(false).toArray()[i]).toString());
        }
    }

    public void addCurrentBoard(){

        if(currentId >= boards.size()-1){
            initCurrentBoard();
            return;
        }

        currentBoard = boards.get(currentId+1);
        currentId++;
    }

    public void initCurrentBoard(){
        this.currentBoard = boards.get(0);
        this.currentId = 0;
    }

    public LinkedList<String> getBoards() {
        return boards;
    }

    private void initUpdateTime() {
        this.updateTime = ultimateUtilities.getScoreboardConfig().getInt("update_time");
    }

    public void initBoardChangeTime(){
        this.boardChangeTime = 0;
    }

    public int getBoardChangeTime() {
        return boardChangeTime;
    }

    public UltimateUtilities getUltimateUtilities() {
        return ultimateUtilities;
    }

    public String getCurrentBoard() {
        return currentBoard;
    }

    public int getUpdateTime() {
        return updateTime;
    }

    public int getNeededBoardChangeTime(){
        return ultimateUtilities.getScoreboardConfig().getInt("board_change_time");
    }

    public void addBoardChangeTime(int time) {
        this.boardChangeTime += time;
    }
}
