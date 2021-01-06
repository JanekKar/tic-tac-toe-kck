package app.gui;

import app.ticTacToe.Player;
import app.ticTacToe.TicTacToe;
import app.ticTacToe.logic.EasyLogic;
import app.ticTacToe.logic.TicTacToeLogic;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;


public class GameWindow extends JPanel{

    private JPanel gameBoard;
    private SiebarPanel sideBar;


    protected GameField[][] board = new GameField[3][3];

    public GameWindow(){

        LayoutManager boardLayoutManager = new GridLayout(3,3 ,10, 10);
        LayoutManager gameLayoutManager = new FlowLayout();

        this.setBackground(new Color(0, 255, 255));
        this.setBorder(new EmptyBorder(15, 0, 15, 15));
        this.setLayout(gameLayoutManager);

        this.registerKeyboardAction(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                CardLayout cl = (CardLayout) Window.rootPanel.getLayout();
                cl.show(Window.rootPanel, "PAUSE");
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);


        gameBoard = new JPanel();
        gameBoard.setBorder(new EmptyBorder(0,0,0,0));
        gameBoard.setLayout(boardLayoutManager);

        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                board[i][j] = new GameField(i, j);
                gameBoard.add(board[i][j]);
            }
        }


        sideBar = new SiebarPanel();

        this.add(sideBar);
        this.add(gameBoard);
    }

    public void resetBoard(){
        for(Component component : gameBoard.getComponents()){
            ((GameField) component).reset();
        }
        sideBar.updateSidebarData();
    }
}