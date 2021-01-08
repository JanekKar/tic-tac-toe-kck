package app.gui;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;

public class MenuButton extends JButton {
    private Color defaultColor = new Color(119, 181, 181);
    private Color hoverColor = new Color(100, 150, 150);

    JButton butoton;

    public MenuButton(String text) {
        super(text);
        butoton = this;
        this.setFont(new Font("Courier", Font.BOLD, 20));
        this.setPreferredSize(new Dimension(400, 50));
        this.setBackground(defaultColor);
        this.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                butoton.setBackground(hoverColor);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                butoton.setBackground(defaultColor);
            }
        });
        this.setBorder(BorderFactory.createRaisedSoftBevelBorder());
    }
}
