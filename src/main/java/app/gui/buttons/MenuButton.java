package app.gui.buttons;

import app.gui.utils.GameStyle;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;

public class MenuButton extends JButton {

    JButton butoton;

    public MenuButton(String text) {
        super(text);
        butoton = this;
        this.setFont(new Font(GameStyle.fontName, Font.BOLD, 20));
        this.setPreferredSize(new Dimension(400, 50));
        this.setBackground(GameStyle.buttonBackground);
        this.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                butoton.setBackground(GameStyle.buttonHover);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                butoton.setBackground(GameStyle.buttonBackground);
            }
        });
        this.setBorder(BorderFactory.createRaisedSoftBevelBorder());
    }
}
