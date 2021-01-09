package app.gui.utils;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class CustomCellRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        c.setFocusable(false);
        c.setFont(new Font(c.getFont().getName(), Font.BOLD, 15));
        c.setPreferredSize(new Dimension(30, 20));
        c.setBackground(GameStyle.tableCellBackground);
        return c;
    }
}
