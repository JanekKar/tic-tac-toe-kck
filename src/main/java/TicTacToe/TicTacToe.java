package TicTacToe;

import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

@Getter
@Setter
public class TicTacToe {


    protected String blank = "";
    private String[] players = {"X", "O"};
    private int currentPlayer;
    private int available;

    protected String[][] board = new String[3][3];

    private Point[] winningPositions;

    public TicTacToe() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = blank;
                available++;
            }
        }

        currentPlayer = 0;
    }


    public boolean equal3(String a, String b, String c) {
        return a.equals(b) && b.equals(c) && !a.equals(blank);
    }

    // null - game on, "TIE" - tie, "X" - x won, "O" - o won
    public String checkWinner() {
        String winner = null;

        // horizontal
        for (int i = 0; i < 3; i++) {
            if (equal3(board[i][0], board[i][1], board[i][2])){
                winner = board[i][0];
                winningPositions = new Point[]{new Point(i, 0), new Point(i, 1), new Point(i, 2)};
            }
        }

        // vertical
        for (int i = 0; i < 3; i++) {
            if (equal3(board[0][i], board[1][i], board[2][i])){
                winner = board[0][i];
                winningPositions = new Point[]{new Point(0, i), new Point(1, i), new Point(2, i)};
            }
        }

        // diagonal
        if (equal3(board[0][0], board[1][1], board[2][2])){
            winner = board[0][0];
            winningPositions = new Point[]{new Point(0, 0), new Point(1, 1), new Point(2, 2)};

        }
        if (equal3(board[2][0], board[1][1], board[0][2])){
            winner = board[2][0];
            winningPositions = new Point[]{new Point(2, 0), new Point(1, 1), new Point(0,2 )};

        }

        if (winner == null && available == 0) {
            winningPositions = null;
            return "TIE";
        } else {
            return winner;
        }
    }

    public boolean checkIfFree(Point point) {
        return board[point.x][point.y].equals(blank);
    }

    public void makeMove(Point point) {
        if (board[point.x][point.y].equals(blank)) {
            available--;
            board[point.x][point.y] = players[currentPlayer];
            currentPlayer++;
            currentPlayer%=2;
        }
    }

    protected void makeMove(int x, int y,  String player ){
        board[x][y] = player;
        available--;
    }

    protected void undoMove(int x, int y){
        board[x][y] = blank;
        available++;
    }

    public Point[] getWinningPositions(){
        return winningPositions;
    }

    public String getFieldContent(Point point){
        return board[point.x][point.y];
    }



    public void printBoard() {
        for (String[] row : this.board) {
            for (String cell : row) {
                if (cell.equals("")) {
                    System.out.print("_");
                } else {
                    System.out.print(cell);
                }
            }
            System.out.println();
        }
    }
}
