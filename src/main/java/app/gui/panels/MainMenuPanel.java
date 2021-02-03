package app.gui.panels;

import app.gui.buttons.MenuButton;
import app.gui.GUIManager;
import app.gui.labels.LogoPanel;
import app.gui.utils.GameStyle;
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
        this.setBackground(GameStyle.menuBackground);
        this.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel buttonContainer = new JPanel();
        buttonContainer.setLayout(buttonLayout);
        buttonContainer.setBackground(GameStyle.menuBackground);

        JPanel buttonContainerWrapper = new JPanel();
        buttonContainerWrapper.setLayout(new FlowLayout());
        buttonContainerWrapper.setBackground(GameStyle.menuBackground);

        startGame = new MenuButton("Start new game");
        bestScore = new MenuButton("Best Score");
        quitGame = new MenuButton("Quit");

        startGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                GUIManager.difficultyPanel.clear();
                CardLayout cl = (CardLayout) GUIManager.rootPanel.getLayout();
                cl.show(GUIManager.rootPanel, "DIFFICULTY");
            }
        });

        bestScore.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                CardLayout cl = (CardLayout) GUIManager.rootPanel.getLayout();

                if(bestScorePanel != null){
                    GUIManager.rootPanel.remove(bestScorePanel);
                }
                bestScorePanel = new BestScorePanel();
                GUIManager.rootPanel.add(bestScorePanel, "BESTSCORE");
                cl.show(GUIManager.rootPanel, "BESTSCORE");
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
