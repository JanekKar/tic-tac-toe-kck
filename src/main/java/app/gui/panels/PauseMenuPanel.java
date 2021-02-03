package app.gui.panels;

import app.gui.buttons.MenuButton;
import app.gui.GUIManager;
import app.gui.labels.LogoPanel;
import app.gui.utils.GameStyle;

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
        this.setBackground(GameStyle.menuBackground);
        this.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel buttonContainer = new JPanel();
        buttonContainer.setLayout(buttonLayout);
        buttonContainer.setBackground(GameStyle.menuBackground);

        JPanel buttonContainerWrapper = new JPanel();
        buttonContainerWrapper.setLayout(new FlowLayout());
        buttonContainerWrapper.add(buttonContainer);
        buttonContainerWrapper.setBackground(GameStyle.menuBackground);

        resumeGame = new MenuButton("Resume game");
        quitGame = new MenuButton("Exit to main menu");

        resumeGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                CardLayout cl = (CardLayout) GUIManager.rootPanel.getLayout();
                cl.show(GUIManager.rootPanel, "GAME");
            }
        });

        quitGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                CardLayout cl = (CardLayout) GUIManager.rootPanel.getLayout();
                cl.show(GUIManager.rootPanel, "MAINMENU");
            }
        });

        buttonContainer.add(resumeGame);
        buttonContainer.add(quitGame);

        this.add(new LogoPanel());
        this.add(buttonContainerWrapper);
    }
}
