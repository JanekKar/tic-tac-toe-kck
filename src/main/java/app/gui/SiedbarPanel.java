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

    String scoreText = "Score:\n";
    String gameNumberText = "Game number: ";
    String nickText = "Player:\n";
    String wonText = "Won: ";
    String lostText = "Lost: ";
    String tieText = "Tie: ";
    String diffText = "Difficulty: ";

    public SiedbarPanel() {
        super();

        LayoutManager lm = new GridLayout(7, 1, 5, 15);
        this.setLayout(lm);
        this.setBackground(new Color(0, 255, 255));
        this.setBorder(new EmptyBorder(0, 15, 0, 15));
        this.setPreferredSize(new Dimension(200, 622));

        difficulty = prepLabel();
        score = prepLabel();
        playerName = prepLabel();
        numberOfGames = prepLabel();
        gamesWon = prepLabel();
        gamesLost = prepLabel();
        gamesTied = prepLabel();

        this.add(difficulty);
        this.add(numberOfGames);
        this.add(playerName);
        this.add(score);
        this.add(gamesWon);
        this.add(gamesLost);
        this.add(gamesTied);
    }

    private JTextArea prepLabel() {
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

    public void setupPanel() {
        playerName.setText(nickText + Main.game.getPlayer().getName());
        score.setText(scoreText + 0);
        gamesTied.setText(tieText + 0);
        gamesLost.setText(lostText + 0);
        gamesWon.setText(wonText + 0);
        numberOfGames.setText(gameNumberText + 0);
        repaint();
    }

    public void updateSidebarData() {
        Player player = Main.game.getPlayer();

        score.setText(scoreText + Main.game.getPlayer().getScore());

        numberOfGames.setText(gameNumberText + Main.game.getGameNo());

        gamesWon.setText(wonText + player.getNumberOfWonGames());

        gamesLost.setText(lostText + player.getNumberOfLostGames());

        gamesTied.setText(tieText + player.getNumberOfTies());

        repaint();

    }
}

