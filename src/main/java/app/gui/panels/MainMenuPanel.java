package app.gui.panels;

import app.gui.buttons.MenuButton;
import app.gui.MainPanel;
import app.gui.labels.LogoPanel;
import app.ticTacToe.BestScoreManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuPanel extends JPanel {

    private JButton startGame;
    private JButton bestScore;
    private JButton quitGame;

    private BestScorePanel bestScorePanel = null;

    public MainMenuPanel() {
        LayoutManager mainLayout = new GridLayout(2, 1, 20, 20);
        LayoutManager buttonLayout = new GridLayout(3, 1, 20, 20);

        this.setLayout(mainLayout);
        Color backGroundColor = new Color(100, 100, 100);
        this.setBackground(backGroundColor);
        this.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel buttonContainer = new JPanel();
        buttonContainer.setLayout(buttonLayout);
        buttonContainer.setBackground(backGroundColor);

        JPanel buttonContainerWrapper = new JPanel();
        buttonContainerWrapper.setLayout(new FlowLayout());
        buttonContainerWrapper.setBackground(backGroundColor);

        startGame = new MenuButton("Start new game");
        bestScore = new MenuButton("Best Score");
        quitGame = new MenuButton("Quit");

        startGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                MainPanel.difficultyPanel.clear();
                CardLayout cl = (CardLayout) MainPanel.rootPanel.getLayout();
                cl.show(MainPanel.rootPanel, "DIFFICULTY");
            }
        });

        bestScore.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                CardLayout cl = (CardLayout) MainPanel.rootPanel.getLayout();

                if(bestScorePanel != null){
                    MainPanel.rootPanel.remove(bestScorePanel);
                }
                bestScorePanel = new BestScorePanel();
                MainPanel.rootPanel.add(bestScorePanel, "BESTSCORE");
                cl.show(MainPanel.rootPanel, "BESTSCORE");
            }
        });

        quitGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                BestScoreManager.getInstance().save();
                System.exit(0);
            }
        });

        buttonContainer.add(startGame);
        buttonContainer.add(bestScore);
        buttonContainer.add(quitGame);

        buttonContainerWrapper.add(buttonContainer);

        this.add(new LogoPanel());
        this.add(buttonContainerWrapper);
    }
}
