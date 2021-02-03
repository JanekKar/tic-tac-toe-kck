package app.gui.utils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class HeaderRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        c.setFocusable(false);
        c.setFont(new Font(GameStyle.fontName, Font.BOLD, 23));
        c.setBackground(GameStyle.buttonBackground);
        setHorizontalAlignment(JLabel.CENTER);
        setBorder(new EmptyBorder(0,0,0,0));
        if(column!=0){
            setBorder(BorderFactory.createMatteBorder(0,2,0,0, GameStyle.buttonBackground));
        }

        return c;
    }
}
