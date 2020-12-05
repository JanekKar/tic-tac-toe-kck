package app;

import app.cli.Config;
import app.cli.Game;
import app.ticTacToe.BestScoreManager;
import app.ticTacToe.TicTacToe;
import app.ticTacToe.logic.TicTacToeLogic;

import java.io.IOException;

public class Main {

    public static TicTacToe game;
    public static TicTacToeLogic logic;

    public static void main(String[] args){

        BestScoreManager bestScoreManager = BestScoreManager.getInstance();

        game = TicTacToe.getInstance();
        logic = null;

        Game.setupGameConfig();
        try {
            Game.mainLoop(Config.tg);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        bestScoreManager.save();

        Config.getInstance().saveConfig();
        try {
            Config.closeGame();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}

