import lombok.Getter;
import lombok.Setter;

import java.awt.*;

@Getter
@Setter
public class TicTacToe {
    /*
    * 0 - empty
    * 1 - X
    * 2 - O
    * */

    public byte[][] board = new byte[3][3];

    private Point[] winningPositions = new Point[3];

    int num_of_available_moves = 9;

    public byte getWinnerId() {
        return winnerId;
    }

    private byte winnerId = 0;

    public TicTacToe(){
        for( byte[] row : this.board){
            for (byte cell : row){
                cell = 0;
            }
        }
    }

    public void printBoard(){
        for(byte[] row: this.board){
            for (byte cell: row) {
                if (cell == 1) {
                    System.out.print("X");
                } else if (cell == 2) {
                    System.out.print("O");
                } else {
                    System.out.print("_");
                }
            }
            System.out.println();
        }
    }

    public void put(int playerId, Point point){

        this.board[point.x][point.y] = (byte)playerId;

//        if (isInRange(point)){
//            c = Character.toLowerCase(c);
//            if (c == 'x'){
//                this.board[point.x][point.y] = 1;
//                num_of_available_moves--;
//
//            } else if (c == 'o'){
//                this.board[point.x][point.y] = 2;
//                num_of_available_moves--;
//
//            } else {
//                throw new WrongCharException(c + " is not 'x' or 'o' - case insensitive");
//            }
//        }
    }

    public boolean checkIfFree(Point point) {
        if (isInRange(point))
            if(board[point.x][point.y] == 0)
                return true;
        return false;
//        throw new WrongPositionException(point.x + " " + point.y + " must be between 0, 1 or 2");
    }

    private boolean isInRange(Point point){
        if (point.x >=0 && point.y <= 2)
            return true;
        return false;
    }

    public boolean checkForWin(){
        for (int i =0; i<board.length; i++){
            if(board[i][0]==board[i][1] && board[i][1]==board[i][2] && board[i][0] != 0){
                winningPositions[0] = new Point(i,0);
                winningPositions[1] = new Point(i,1);
                winningPositions[2] = new Point(i,2);

                winnerId = board[i][0];

                System.out.println(board[i][0] + " won!");
                return true;

            }
            if(board[0][i]==board[1][i] && board[1][i]==board[2][i] && board[0][i] != 0) {
                winningPositions[0] = new Point(0,i);
                winningPositions[1] = new Point(1,i);
                winningPositions[2] = new Point(2,i);

                winnerId = board[0][i];

                System.out.println(board[0][i] + " won!");
                return true;
            }
        }
        if(board[0][0]==board[1][1] && board[1][1]==board[2][2] && board[0][0] != 0){
            winningPositions[0] = new Point(0,0);
            winningPositions[1] = new Point(1,1);
            winningPositions[2] = new Point(2,2);

            winnerId = board[0][0];

            System.out.println(board[0][0] + " won!");
            return true;
        }

        if(board[0][2]==board[1][1] && board[1][1]==board[2][0] && board[2][0] != 0){
            winningPositions[0] = new Point(2,0);
            winningPositions[1] = new Point(1,1);
            winningPositions[2] = new Point(0,2);

            winnerId = board[0][2];

            System.out.println(board[0][2] + " won!");
            return true;
        }
        return false;
    }

    public boolean checkForTie(){
        if (num_of_available_moves == 0)
            return true;
        else
            return false;

    }

    public Point[] getWinningPositions() {
        return winningPositions;
    }

    class WrongPositionException extends Exception{
        public WrongPositionException() {
            super();
        }

        public WrongPositionException(String message) {
            super(message);
        }
    }

    class WrongCharException extends Exception{
        public WrongCharException() {
            super();
        }

        public WrongCharException(String message) {
            super(message);
        }
    }

    class NotEmptyPosition extends Exception {
        public NotEmptyPosition() {
            super();
        }

        public NotEmptyPosition(String message) {
            super(message);
        }
    }

}
