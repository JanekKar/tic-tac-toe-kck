package app;

import app.cli.Config;
import app.cli.Game;
import app.cli.menus.Submenus;
import app.ticTacToe.PlayerScores;
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

        Game.setupGameConfig();


        submenus = new Submenus(Config.tg);
        Game.mainLoop(Config.tg);




        files.save();

        Config.getInstance().saveConfig();
        Config.closeGame();


    }

}

