package app.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PauseMenuWindow extends JPanel {

    private JButton resumeGame;
    private JButton quitGame;

    public PauseMenuWindow() {
        LayoutManager mainLayout = new FlowLayout();
        LayoutManager buttonLayout = new GridLayout(2, 1, 20, 20);

        this.setLayout(mainLayout);
        Color backGroundColor = new Color(100, 100, 100);
        this.setBackground(backGroundColor);
        this.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel buttonContainer = new JPanel();
        buttonContainer.setLayout(buttonLayout);
        buttonContainer.setBackground(backGroundColor);

        resumeGame = new JButton("Resume game");
        quitGame = new JButton("Exit to main menu");

        Dimension buttonDim = new Dimension(200, 50);

        resumeGame.setPreferredSize(buttonDim);
        quitGame.setPreferredSize(buttonDim);

        resumeGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                CardLayout cl = (CardLayout) Window.rootPanel.getLayout();
                cl.show(Window.rootPanel, "GAME");
            }
        });

        buttonContainer.add(resumeGame);
        buttonContainer.add(quitGame);

        this.add(buttonContainer);
    }
}
