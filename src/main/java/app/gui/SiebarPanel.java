package app.gui;

import app.ticTacToe.Player;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class SiebarPanel extends JPanel {
    private final int noOfGames = 0;
    private final int noOfWonGames = 0;
    private final int noOfLostGames = 0;
    private final int noOfTiedGames = 0;
    JLabel playerName;
    JLabel numberOfGames;
    JLabel gamesWon;
    JLabel gamesLost;
    JLabel gamesTied;

    public SiebarPanel() {
        super();

        LayoutManager lm = new GridLayout(5,1, 5, 15);
        this.setLayout(lm);
        this.setBackground(new Color(0, 255,255));
        this.setBorder(new EmptyBorder(0, 15, 0, 15));
        this.setPreferredSize(new Dimension(200, 622));

        playerName = prepLabel();
        playerName.setText("Player: "+ Window.game.getPlayer().getName());

        numberOfGames = prepLabel();
        numberOfGames.setText("No. "+ noOfGames);

        gamesWon = prepLabel();
        gamesWon.setText("WON: "+ noOfWonGames);

        gamesLost = prepLabel();
        gamesLost.setText("Lost: "+ noOfLostGames);

        gamesTied = prepLabel();
        gamesTied.setText("TIE: " + noOfTiedGames);


        this.add(numberOfGames);
        this.add(playerName);
        this.add(gamesWon);
        this.add(gamesLost);
        this.add(gamesTied);
    }

    private JLabel prepLabel(){
        JLabel temp = new JLabel();
        temp.setOpaque(true);
        temp.setFont(new Font(temp.getFont().getName(), Font.BOLD, 20));
        temp.setBackground(new Color(255, 255, 255));
        return temp;
    }

    public void updateSidebarData(){
        Player player = Window.game.getPlayer();
        numberOfGames.setText("No. "+ Window.game.getGameNo());

        gamesWon.setText("WON: "+ player.getNumberOfWonGames());

        gamesLost.setText("Lost: "+ player.getNumberOfLostGames());

        gamesTied.setText("TIE: " + player.getNumberOfTies());

    }

}
