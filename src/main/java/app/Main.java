package app;

import app.cli.Game;
import app.ticTacToe.Player;
import app.ticTacToe.TicTacToe;
import app.ticTacToe.logic.TicTacToeLogic;
import com.googlecode.lanterna.graphics.TextGraphics;

import java.io.IOException;

public class Main {

    public static TicTacToe game;
    public static TicTacToeLogic logic;

    public static void main(String[] args) throws InterruptedException, IOException {

        game = TicTacToe.getInstance();
        game.setPlayer(new Player("Janek"));
        logic = null;

        TextGraphics tg = Game.setUpTerminalAndScreen();

        Game.mainLoop(tg);

        Game.screen.close();
        Game.terminal.close();

    }

}

