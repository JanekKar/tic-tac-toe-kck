package app.gui.panels;

import app.gui.utils.CustomCellRenderer;
import app.gui.MainPanel;
import app.ticTacToe.BestScoreManager;
import app.ticTacToe.Player;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class BestScorePanel extends JPanel {

    private JTable scoreTable;
    private JButton quitGame;

    public BestScorePanel() {
        LayoutManager mainLayout = new FlowLayout();

        Color backGroundColor = new Color(100, 100, 100);

        this.setLayout(mainLayout);
        this.setBackground(backGroundColor);
        this.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel buttonContainer = new JPanel();
        LayoutManager buttonLayout = new GridLayout(2, 1, 20, 20);
        buttonContainer.setLayout(buttonLayout);
        buttonContainer.setBackground(backGroundColor);

        LayoutManager l1 = new FlowLayout();
        LayoutManager l2 = new FlowLayout();

        JPanel pane1 = new JPanel();
        JPanel pane2 = new JPanel();

        pane1.setLayout(l1);
        pane2.setLayout(l2);

        pane1.setBackground(backGroundColor);
        pane2.setBackground(backGroundColor);



        quitGame = new JButton("Go Back");
        quitGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                CardLayout cl = (CardLayout) MainPanel.rootPanel.getLayout();
                cl.show(MainPanel.rootPanel, "MAINMENU");
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
        scoreTable.setBackground(backGroundColor);
        scoreTable.setDefaultEditor(Object.class, null);
        scoreTable.setCellSelectionEnabled(false);
        scoreTable.setDefaultRenderer(Object.class, new CustomCellRenderer());


        JScrollPane temp = new JScrollPane(scoreTable);
        temp.setPreferredSize(new Dimension(500, 200));
        temp.setBackground(backGroundColor);
        pane1.add(temp);
        pane2.add(quitGame);

        buttonContainer.add(temp);
        buttonContainer.add(pane2);

        this.add(buttonContainer);
    }
}
