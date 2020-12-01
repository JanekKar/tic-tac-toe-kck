package app;

import app.cli.Config;
import app.cli.Game;
import app.ticTacToe.PlayerScores;
import app.ticTacToe.Player;
import app.ticTacToe.TicTacToe;
import app.ticTacToe.logic.TicTacToeLogic;
import com.googlecode.lanterna.graphics.TextGraphics;

import java.io.IOException;

public class Main {

    public static TicTacToe game;
    public static TicTacToeLogic logic;

    public static void main(String[] args) throws InterruptedException, IOException {

        PlayerScores files = PlayerScores.getInstance();

        game = TicTacToe.getInstance();
        game.setPlayer(new Player("Janek"));
        logic = null;

        TextGraphics tg = Game.setUpTerminalAndScreen();
        Game.setupGameConfig();

        Game.mainLoop(tg);

        Game.screen.close();
        Game.terminal.close();

        files.addNeBestScore(new Player("best", 12,12,12,12));
        files.save();

        Config.getInstance().saveConfig();

    }

}

