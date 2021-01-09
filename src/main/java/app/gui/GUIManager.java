package app.gui;

import app.gui.panels.GamePanel;
import app.gui.panels.MainMenuPanel;
import app.gui.panels.NickDifficultyPanel;
import app.gui.panels.PauseMenuPanel;
import app.ticTacToe.BestScoreManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GUIManager {

    public static GamePanel gameView;
    protected static JFrame frame;
    public static JPanel rootPanel;
    public static NickDifficultyPanel difficultyPanel;


    public static void runWindowMode(){


        frame = new JFrame("TicTacToe");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e) {
                BestScoreManager.getInstance().save();
                System.exit(0);
            }
        });

        rootPanel = new JPanel();
        rootPanel.setLayout(new CardLayout());
        gameView = new GamePanel();

        difficultyPanel = new NickDifficultyPanel();

        rootPanel.add(gameView, "GAME");
        rootPanel.add(new PauseMenuPanel(), "PAUSE");
        rootPanel.add(new MainMenuPanel(), "MAINMENU");
        rootPanel.add(difficultyPanel, "DIFFICULTY");

        ((CardLayout)rootPanel.getLayout()).show(rootPanel, "MAINMENU");
        frame.add(rootPanel);

        frame.pack();
        frame.setVisible(true);
    }

}