package app.gui;

import app.Main;
import app.ticTacToe.Player;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class SiedbarPanel extends JPanel {
    JTextArea playerName;
    JTextArea numberOfGames;
    JTextArea gamesWon;
    JTextArea gamesLost;
    JTextArea gamesTied;
    JTextArea score;
    JTextArea difficulty;

    public SiedbarPanel() {
        super();

        LayoutManager lm = new GridLayout(7,1, 5, 15);
        this.setLayout(lm);
        this.setBackground(new Color(0, 255,255));
        this.setBorder(new EmptyBorder(0, 15, 0, 15));
        this.setPreferredSize(new Dimension(200, 622));

        difficulty = prepLabel();

        score = prepLabel();

        playerName = prepLabel();

        numberOfGames = prepLabel();
        numberOfGames.setText("No. "+ 0);

        gamesWon = prepLabel();
        gamesWon.setText("WON: "+ 0);

        gamesLost = prepLabel();
        gamesLost.setText("Lost: "+ 0);

        gamesTied = prepLabel();
        gamesTied.setText("TIE: " + 0);

        this.add(difficulty);
        this.add(numberOfGames);
        this.add(playerName);
        this.add(score);
        this.add(gamesWon);
        this.add(gamesLost);
        this.add(gamesTied);
    }

    private JTextArea prepLabel(){
        JTextArea temp = new JTextArea();
        temp.setOpaque(true);
        temp.setFont(new Font(temp.getFont().getName(), Font.BOLD, 20));
        temp.setBackground(new Color(255, 255, 255));
        temp.setLineWrap(true);
        temp.setWrapStyleWord(true);
        temp.setFocusable(false);
        temp.setEditable(false);
        temp.setAlignmentY(CENTER_ALIGNMENT);
        temp.setAlignmentX(CENTER_ALIGNMENT);
        return temp;
    }

    public void setPlayerName(){
        //TODO not setting name
        score.setText("Score: " + Main.game.getPlayer().getScore());
        repaint();
    }

    public void updateSidebarData(){
        Player player = Main.game.getPlayer();

        score.setText("Score: " + Main.game.getPlayer().getScore());

        numberOfGames.setText("No. "+ Main.game.getGameNo());

        gamesWon.setText("WON: "+ player.getNumberOfWonGames());

        gamesLost.setText("Lost: "+ player.getNumberOfLostGames());

        gamesTied.setText("TIE: " + player.getNumberOfTies());

        repaint();

    }

}
