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

    public static void main(String[] args) throws InterruptedException, IOException {

        BestScoreManager bestScoreManager = BestScoreManager.getInstance();

        game = TicTacToe.getInstance();
        logic = null;

        Game.setupGameConfig();
        Game.mainLoop(Config.tg);

        bestScoreManager.save();

        Config.getInstance().saveConfig();
        Config.closeGame();
        
    }

}

