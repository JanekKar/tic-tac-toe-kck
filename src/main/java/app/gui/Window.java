package app.gui;

import app.Main;
import app.ticTacToe.Player;
import app.ticTacToe.TicTacToe;
import app.ticTacToe.logic.EasyLogic;
import app.ticTacToe.logic.TicTacToeLogic;

import javax.swing.*;
import java.awt.*;

public class Window {

    public static GamePanel gameView;
    protected static JFrame frame;
    public static JPanel rootPanel;


    static GameField[][] board = new GameField[3][3];


    public static void runWindowMode(){

        //TODO setup player name
        Main.game.setPlayer(new Player("Janek"));

        frame = new JFrame("TicTacToe");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        rootPanel = new JPanel();
        rootPanel.setLayout(new CardLayout());
        gameView = new GamePanel();

        rootPanel.add(gameView, "GAME");
        rootPanel.add(new PauseMenuWindow(), "PAUSE");
        rootPanel.add(new MainMenuPanel(), "MAINMENU");
        rootPanel.add(new MainMenuPanel(), "MAINMENU");

        ((CardLayout)rootPanel.getLayout()).show(rootPanel, "MAINMENU");
        frame.add(rootPanel);

        frame.pack();
        frame.setVisible(true);
    }

}