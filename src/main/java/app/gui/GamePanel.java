package app.gui;

import app.Main;
import com.googlecode.lanterna.SGR;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import static app.Main.game;
import static app.cli.Config.*;
import static app.cli.Utils.drawWindow;


public class GamePanel extends JPanel{

    private JLabel gameBoard;
    private SiedbarPanel sideBar;

    protected GameField[][] board = new GameField[3][3];

    public static boolean disableMoving = false;


    public static Color gameBacgroundColor = new Color(100, 100, 120);

    public GamePanel(){

        LayoutManager boardLayoutManager = new GridLayout(3,3 ,10, 10);
        LayoutManager gameLayoutManager = new FlowLayout();

        this.setBackground(gameBacgroundColor);
        this.setBorder(new EmptyBorder(15, 0, 15, 15));
        this.setLayout(gameLayoutManager);

        this.registerKeyboardAction(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                CardLayout cl = (CardLayout) Window.rootPanel.getLayout();
                cl.show(Window.rootPanel, "PAUSE");
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);


        gameBoard = new JLabel();
//        gameBoard.setIcon(new ImageIcon(getClass().getResource("/img/board.png")));
        gameBoard.setPreferredSize(new Dimension(620, 620));
        gameBoard.setBorder(new EmptyBorder(0,0,0,0));
        gameBoard.setLayout(boardLayoutManager);
        gameBoard.setBackground(Color.black);
        gameBoard.setOpaque(true);

        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                board[i][j] = new GameField(i, j);
                gameBoard.add(board[i][j]);
            }
        }


        sideBar = new SiedbarPanel();

        this.add(sideBar);
        this.add(gameBoard);
    }

    public void resetBoard(){
        for(Component component : gameBoard.getComponents()){
            ((GameField) component).reset();
        }
        sideBar.updateSidebarData();
    }

    public void AIMove(){
        disableMoving = true;

        Timer timer = new Timer(500, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                Point point = Main.logic.makeMove();
                Main.game.makeMove(point);
                board[point.x][point.y].markAsO();

                String result = Main.game.checkWinner();
                if (result != null) {
                    if (!result.equals("TIE"))
                        highlightWinner(false);
                    else
                        System.out.println("TIE");
                    nextRound();
                }
                disableMoving = false;
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    public void nextRound(){

        disableMoving = true;
        Timer timer = new Timer(500, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                Main.game.nextGame();
                if(!Main.game.isEndOfSession()){
                    resetBoard();
                }else{
                    if (game.isNewBest()) {
                        //TODO info about best score
                    }
                    game.endSession();
                    resetBoard();
                    CardLayout cl = (CardLayout) Window.rootPanel.getLayout();
                    cl.show(Window.rootPanel, "MAINMENU");
                }
                disableMoving = false;
            }
        });
        timer.setRepeats(false);
        timer.start();
    }


    public void highlightWinner(boolean winner){
        for(Point point : Main.game.getWinningPositions()){
            if(winner)
                board[point.x][point.y].markWinner();
            else
                board[point.x][point.y].markLooser();
        }
    }

    public SiedbarPanel getSidebar() {
        return sideBar;
    }
}