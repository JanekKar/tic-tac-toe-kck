package app.gui.panels;

import app.gui.buttons.MenuButton;
import app.gui.MainPanel;
import app.gui.labels.LogoPanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PauseMenuPanel extends JPanel {

    private JButton resumeGame;
    private JButton quitGame;

    public PauseMenuPanel() {
        LayoutManager mainLayout = new GridLayout(2, 1, 20, 20);
        LayoutManager buttonLayout = new GridLayout(2, 1, 20, 20);

        this.setLayout(mainLayout);
        Color backGroundColor = new Color(100, 100, 100);
        this.setBackground(backGroundColor);
        this.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel buttonContainer = new JPanel();
        buttonContainer.setLayout(buttonLayout);
        buttonContainer.setBackground(backGroundColor);

        JPanel buttonContainerWrapper = new JPanel();
        buttonContainerWrapper.setLayout(new FlowLayout());
        buttonContainerWrapper.add(buttonContainer);
        buttonContainerWrapper.setBackground(backGroundColor);

        resumeGame = new MenuButton("Resume game");
        quitGame = new MenuButton("Exit to main menu");

        resumeGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                CardLayout cl = (CardLayout) MainPanel.rootPanel.getLayout();
                cl.show(MainPanel.rootPanel, "GAME");
            }
        });

        quitGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                CardLayout cl = (CardLayout) MainPanel.rootPanel.getLayout();
                cl.show(MainPanel.rootPanel, "MAINMENU");
            }
        });

        buttonContainer.add(resumeGame);
        buttonContainer.add(quitGame);

        this.add(new LogoPanel());
        this.add(buttonContainerWrapper);
    }
}
