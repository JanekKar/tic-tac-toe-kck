package app.gui.panels;

import app.cli.Game;
import app.gui.buttons.MenuButton;
import app.gui.utils.CustomCellRenderer;
import app.gui.GUIManager;
import app.gui.utils.GameStyle;
import app.gui.utils.HeaderRenderer;
import app.ticTacToe.BestScoreManager;
import app.ticTacToe.Player;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import java.util.List;

public class BestScorePanel extends JPanel {

    private JTable scoreTable;
    private JButton quitGame;

    public BestScorePanel() {
        this.setBackground(GameStyle.menuBackground);
        this.setBorder(new EmptyBorder(100, 20, 20, 20));

        JPanel buttonContainer = new JPanel();
        LayoutManager buttonLayout = new GridLayout(2, 1, 20, 30);
        buttonContainer.setLayout(buttonLayout);
        buttonContainer.setBackground(GameStyle.menuBackground);

        JPanel pane1 = new JPanel();
        JPanel pane2 = new JPanel();
        pane1.setLayout(new FlowLayout());
        pane2.setLayout(new FlowLayout());

        pane1.setBackground(GameStyle.menuBackground);
        pane2.setBackground(GameStyle.menuBackground);


        quitGame = new MenuButton("Go Back");
        quitGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                CardLayout cl = (CardLayout) GUIManager.rootPanel.getLayout();
                cl.show(GUIManager.rootPanel, "MAINMENU");
            }
        });

        List<Player> scoreList = BestScoreManager.getInstance().getBestScoreList();
        String[][] data = new String[scoreList.size()][5];

        for (int i=0; i < scoreList.size(); i++){
            data[i][0] = scoreList.get(i).getName();
            data[i][1] = scoreList.get(i).getScore()+"";
            data[i][2] = scoreList.get(i).getNumberOfWonGames()+"";
            data[i][3] = scoreList.get(i).getNumberOfLostGames()+"";
            data[i][4] = scoreList.get(i).getNumberOfTies()+"";
        }

        String[] headers = {"Name", "Score", "Won", "Lost", "Tie"};

        scoreTable = new JTable(data, headers);
        scoreTable.setBackground(GameStyle.menuBackground);
        scoreTable.setDefaultEditor(Object.class, null);
        scoreTable.setCellSelectionEnabled(false);
        scoreTable.setDefaultRenderer(Object.class, new CustomCellRenderer());
        scoreTable.setBorder(null);
        scoreTable.setGridColor(GameStyle.menuBackground);
        scoreTable.setShowGrid(false);
        scoreTable.setShowVerticalLines(true);
        scoreTable.setIntercellSpacing(new Dimension(0, 0));

        scoreTable.getTableHeader().setOpaque(false);
        scoreTable.getTableHeader().setPreferredSize(new Dimension(20,40));
        scoreTable.getTableHeader().setBackground(GameStyle.menuBackground);
        scoreTable.getTableHeader().setDefaultRenderer(new HeaderRenderer());

        scoreTable.setRowHeight(30);
        TableColumnModel cm = scoreTable.getColumnModel();
        cm.getColumn(0).setPreferredWidth(100);
        cm.getColumn(2).setPreferredWidth(25);
        cm.getColumn(3).setPreferredWidth(25);
        cm.getColumn(4).setPreferredWidth(25);
        Enumeration<TableColumn> cols = cm.getColumns();
        while(cols.hasMoreElements()){
            TableColumn el = cols.nextElement();
            el.setResizable(false);
        }


        JScrollPane temp = new JScrollPane(scoreTable);
        temp.setPreferredSize(new Dimension(500, 200));
        temp.setBorder(null);
        temp.setBackground(GameStyle.menuBackground);
        temp.setBorder(new LineBorder(GameStyle.menuBackground, 5));
        pane1.add(temp);
        pane2.add(quitGame);

        buttonContainer.add(temp);
        buttonContainer.add(pane2);

        this.add(buttonContainer);
    }
}
