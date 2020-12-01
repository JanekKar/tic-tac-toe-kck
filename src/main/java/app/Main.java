package app;

import app.cli.Config;
import app.cli.Game;
import app.cli.Submenus;
import app.ticTacToe.PlayerScores;
import app.ticTacToe.Player;
import app.ticTacToe.TicTacToe;
import app.ticTacToe.logic.TicTacToeLogic;
import com.googlecode.lanterna.graphics.TextGraphics;

import java.io.IOException;

public class Main {

    public static TicTacToe game;
    public static TicTacToeLogic logic;
    public static Submenus submenus;

    public static void main(String[] args) throws InterruptedException, IOException {

        PlayerScores files = PlayerScores.getInstance();

        game = TicTacToe.getInstance();
        logic = null;

        TextGraphics tg = Game.setUpTerminalAndScreen();
        Game.setupGameConfig();


        submenus = new Submenus(tg);
        Game.mainLoop(tg);

        Game.screen.close();
        Game.terminal.close();

        files.save();

        Config.getInstance().saveConfig();

    }

}

