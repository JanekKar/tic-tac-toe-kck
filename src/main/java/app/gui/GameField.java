package app.gui;

import app.cli.Game;
import lombok.SneakyThrows;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameField extends JPanel {
    int x, y;

    JPanel panel;

    boolean aiMoved = false;
    public GameField(int x, int y) {
        super();
        panel = this;
        this.x = x;
        this.y = y;
        this.setSize(new Dimension(200, 200));
        this.setPreferredSize(new Dimension(200, 200));
        this.setBackground(new Color(255, 200, 255));

        this.addMouseListener(new MouseAdapter() {
            @SneakyThrows
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                gameMechanic();

            }
        });

    }

    public void reset(){
        this.setBackground(new Color(255, 200, 255));
    }

    private void gameMechanic(){
        if(Window.game.checkIfFree(new Point(x,y))){
            panel.setBackground(new Color(180, 180, 120));
            Window.game.makeMove(new Point(x, y));
            Window.game.debugPrintBoard();

            String result = Window.game.checkWinner();
            if (result != null) {
                if (!result.equals("TIE"))
                    System.out.println("Player won");
                else
                    System.out.println("TIE");
                nextRound();
                return;
            }
            Timer timer = new Timer(500, new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent arg0) {
                    System.out.println("Test 1");
                    Point point = Window.logic.makeMove();
                    Window.game.makeMove(point);
                    Window.gameView.board[point.x][point.y].setBackground(new Color( 255,0, 120));

                    String result = Window.game.checkWinner();
                    if (result != null) {
                        if (!result.equals("TIE"))
                            System.out.println("AI won");
                        else
                            System.out.println("TIE");
                        nextRound();
                    }
                }
            });
            timer.setRepeats(false);
            timer.start();
        }
    }


    private void nextRound(){
        Timer timer = new Timer(500, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                Window.game.nextGame();
                Window.gameView.resetBoard();
            }
        });
        timer.setRepeats(false);
        timer.start();
    }
}
