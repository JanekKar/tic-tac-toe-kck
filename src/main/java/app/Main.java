package app;

import app.ticTacToe.*;
import app.cli.*;
import app.ticTacToe.logic.TicTacToeLogic;
import com.googlecode.lanterna.graphics.TextGraphics;

import java.io.IOException;

public class Main{

    public static TicTacToe game;
    public static TicTacToeLogic logic;

    public static void main(String[] args) throws IOException, InterruptedException, IOException {

        game = TicTacToe.getInstance();
        game.setPlayer(new Player("Janek"));
        logic = null;

        TextGraphics tg = CLI.setUpTerminalAndScreen();

        CLI.mainLoop(tg);

        CLI.screen.close();
        CLI.terminal.close();

    }

}

