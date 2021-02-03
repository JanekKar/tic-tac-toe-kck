package app.gui.panels;

import app.Main;
import app.gui.utils.GameStyle;
import app.ticTacToe.Player;
import org.ietf.jgss.GSSManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;

public class SiedbarPanel extends JPanel {
    JTextPane playerName;
    JTextPane numberOfGames;
    JTextPane gamesWon;
    JTextPane gamesLost;
    JTextPane gamesTied;
    JTextPane score;
    JTextPane difficulty;

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
        this.setBackground(GameStyle.gameBackground);
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

    private JTextPane prepLabel() {
        JTextPane temp = new JTextPane();
        temp.setOpaque(true);
        temp.setFont(new Font(GameStyle.fontName, Font.BOLD, 20));
        temp.setBackground(GameStyle.sidebarFields);
        temp.setFocusable(false);
        temp.setEditable(false);
        StyledDocument doc = temp.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);
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

