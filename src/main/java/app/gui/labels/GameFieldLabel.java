package app.gui.labels;

import app.Main;
import app.gui.GUIManager;
import app.gui.panels.GamePanel;
import app.gui.utils.GameStyle;
import lombok.SneakyThrows;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameFieldLabel extends JLabel {
    private final JLabel lable;
    int x, y;

    private ImageIcon xField;
    private ImageIcon oField;


    public GameFieldLabel(int x, int y) {
        super();

        this.lable = this;

        this.xField = new ImageIcon(getClass().getResource("/img/x_field.png"));
        this.oField = new ImageIcon(getClass().getResource("/img/o_field.png"));

        this.x = x;
        this.y = y;

        this.setOpaque(true);
        this.setSize(new Dimension(200, 200));
        this.setPreferredSize(new Dimension(200, 200));
        this.setBackground(GameStyle.emptyField);

        this.addMouseListener(new MouseAdapter() {
            @SneakyThrows
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if(Main.game.checkIfFree(new Point(x,y)) && !GUIManager.gameView.disableMoving){
                    markAsX();
                    Main.game.makeMove(new Point(x, y));
                    Main.game.debugPrintBoard();

                    String result = Main.game.checkWinner();
                    if (result != null) {
                        if (!result.equals("TIE"))
                            GUIManager.gameView.highlightWinner(true);
                        else
                            System.out.println("TIE");
                        GUIManager.gameView.nextRound();
                        return;
                    }
                    GUIManager.gameView.AIMove();
                }

            }
        });

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if(!GamePanel.highlightLocked)
                    setBackground(GameStyle.fieldHighlight);
                super.mouseEntered(e);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if(!GamePanel.highlightLocked)
                    setBackground(GameStyle.sidebarFields);
                super.mouseExited(e);
            }
        });

    }


    public void reset(){
        setBackground(GameStyle.emptyField);
        setIcon(null);
        repaint();
        revalidate();
    }

    public void markAsX(){
        this.setIcon(xField);
        repaint();
    }

    public void markAsO(){
        this.setIcon(oField);
        repaint();
    }

    public void markWinner(){
        this.setOpaque(true);
        this.setBackground(GameStyle.winnerFiledHighlight);
    }

    public void markLooser(){
        this.setOpaque(true);
        this.setBackground(GameStyle.looserFieldHIghlight);
    }
}
