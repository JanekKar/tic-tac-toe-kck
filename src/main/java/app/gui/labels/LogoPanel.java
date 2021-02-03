package app.gui.labels;

import javax.swing.*;
import java.awt.*;

public class LogoPanel extends JLabel {
    public LogoPanel() {
        super("", CENTER);
        setPreferredSize(new Dimension(600, 200));
        ImageIcon logo = new ImageIcon(getClass().getResource("/img/logo.png"));
        setIcon(logo);
    }
}
