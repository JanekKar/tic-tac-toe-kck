package app.gui;

import app.Main;
import lombok.SneakyThrows;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameField extends JPanel {
    int x, y;

    public GameField(int x, int y) {
        super();
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
                if(Main.game.checkIfFree(new Point(x,y)) && !Window.gameView.disableMoving){
                    markAsX();
                    Main.game.makeMove(new Point(x, y));
                    Main.game.debugPrintBoard();

                    String result = Main.game.checkWinner();
                    if (result != null) {
                        if (!result.equals("TIE"))
                            Window.gameView.highlightWinner(true);
                        else
                            System.out.println("TIE");
                        Window.gameView.nextRound();
                        return;
                    }
                    Window.gameView.AIMove();
                }

            }
        });

    }


    public void reset(){
        this.setBackground(new Color(255, 200, 255));
    }

    public void markAsX(){
        this.setBackground(new Color(180, 180, 120));
    }

    public void markAsO(){
        this.setBackground(new Color( 255,0, 120));
    }

    public void markWinner(){
        this.setBackground(new Color( 100,255, 100));
    }

    public void markLooser(){
        this.setBackground(new Color( 255,40, 20));
    }
}
