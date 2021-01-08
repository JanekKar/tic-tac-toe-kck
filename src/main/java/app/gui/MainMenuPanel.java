package app.gui;

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
                Window.difficultyPanel.clear();
                CardLayout cl = (CardLayout) Window.rootPanel.getLayout();
                cl.show(Window.rootPanel, "DIFFICULTY");
            }
        });

        bestScore.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                CardLayout cl = (CardLayout) Window.rootPanel.getLayout();

                if(bestScorePanel != null){
                    Window.rootPanel.remove(bestScorePanel);
                }
                bestScorePanel = new BestScorePanel();
                Window.rootPanel.add(bestScorePanel, "BESTSCORE");
                cl.show(Window.rootPanel, "BESTSCORE");
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
