package app.gui;

import app.ticTacToe.Player;
import app.ticTacToe.TicTacToe;
import app.ticTacToe.logic.EasyLogic;
import app.ticTacToe.logic.TicTacToeLogic;

import javax.swing.*;
import java.awt.*;

public class Window {


    public static TicTacToe game;
    public static TicTacToeLogic logic;

    public static GameWindow gameView;
    protected static JFrame frame;
    public static JPanel rootPanel;


    static GameField[][] board = new GameField[3][3];

    public static void main(String[] args) throws InterruptedException {

        game = TicTacToe.getInstance();
        game.setPlayer(new Player("Janek"));
        logic = new EasyLogic();

        frame = new JFrame("TicTacToe");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        rootPanel = new JPanel();
        rootPanel.setLayout(new CardLayout());
        gameView = new GameWindow();

        rootPanel.add(gameView, "GAMEPANEL");
        rootPanel.add(new JPanel().add(new JLabel("Test")), "PAUSE");
        frame.add(rootPanel);

        frame.pack();
        frame.setVisible(true);
    }
}