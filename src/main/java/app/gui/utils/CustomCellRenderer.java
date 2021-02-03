package app.gui.utils;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class CustomCellRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        c.setFocusable(false);
        c.setFont(new Font(GameStyle.fontName, Font.BOLD, 20));
        c.setBackground(GameStyle.tableCellBackground);
        setHorizontalAlignment(JLabel.CENTER);
        if(column!=0){
            setBorder(BorderFactory.createMatteBorder(0,2,0,0, GameStyle.buttonBackground));
        }

        if(row%2==1)
            c.setBackground(GameStyle.tableCellBackgroundLighter);

    return c;
    }
}
