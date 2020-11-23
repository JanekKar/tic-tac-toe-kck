package App;

import App.TicTacToe.*;
import App.CLI.*;
import com.googlecode.lanterna.graphics.TextGraphics;

import java.io.IOException;

public class Main{

    public static TicTacToe game;
    public static TicTacToeLogic  logic;

    public static void main(String[] args) throws IOException, InterruptedException, IOException {

        game = TicTacToe.getInstance(new Player("Janek"));
        logic = new TicTacToeLogic(game);

        TextGraphics tg = CLI.setUpTerminalAndScreen();



        CLI.mainLoop(tg);

        CLI.screen.close();
        CLI.terminal.close();

    }

}

