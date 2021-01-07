package app.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuPanel extends JPanel {

    private JButton startGame;
    private JButton bestScore;
    private JButton quitGame;


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
                CardLayout cl = (CardLayout) Window.rootPanel.getLayout();
                cl.show(Window.rootPanel, "DIFFICULTY");
            }
        });

        bestScore.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                CardLayout cl = (CardLayout) Window.rootPanel.getLayout();
                cl.show(Window.rootPanel, "BESTSCORE");
            }
        });

        quitGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //TODO save data to file
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
        return temp;
    }
}
