package app.gui;

import app.Main;
import app.ticTacToe.BestScoreManager;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
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
        LayoutManager mainLayout = new FlowLayout();
        LayoutManager buttonLayout = new GridLayout(3, 1, 20, 20);

        this.setLayout(mainLayout);
        Color backGroundColor = new Color(100, 100, 100);
        this.setBackground(backGroundColor);
        this.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel buttonContainer = new JPanel();
        buttonContainer.setLayout(buttonLayout);
        buttonContainer.setBackground(backGroundColor);

        startGame = prepareButton("Start new game");
        bestScore = prepareButton("Best Score");
        quitGame = prepareButton("Quit");

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

        this.add(buttonContainer);
    }

    private JButton prepareButton(String text){
        JButton temp = new JButton(text);
        temp.setPreferredSize(Window.menuButtonDimensions);
        temp.setBackground(new Color(100, 150, 150));
        temp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                temp.setBackground(Color.GREEN);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                temp.setBackground(new Color(100, 150, 150));
            }
        });
        return temp;
    }
}
