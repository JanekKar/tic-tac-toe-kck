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

    private Point[] winningPositions;

    int num_of_available_moves = 9;

    private byte winnerId = 0;

    public TicTacToe(){
        for( byte[] row : this.board){
            for (byte cell : row){
                cell = 0;
            }
        }

        winningPositions = new Point[3];

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
        System.out.println();
    }

    public void put(int playerId, Point point){
        this.board[point.x][point.y] = (byte)playerId;
        num_of_available_moves--;
    }

    public boolean checkIfFree(Point point) {
        if (isInRange(point))
            if(board[point.x][point.y] == 0)
                return true;
        return false;
//        throw new WrongPositionException(point.x + " " + point.y + " must be between 0, 1 or 2");
    }

    private boolean isInRange(Point point){
        if (point.x >=0 && point.x <= 2 && point.y >=0 && point.y<=2)
            return true;
        return false;
    }


    // Return 0 - game on, -1 - tie, 1 - player 1 won, 2 - player 2 won;
    public int checkForWin() {

        for (int i =0; i<board.length; i++){
            if(board[i][0]==board[i][1] && board[i][1]==board[i][2] && board[i][0] != 0){
                winningPositions[0] = new Point(i,0);
                winningPositions[1] = new Point(i,1);
                winningPositions[2] = new Point(i,2);

                return board[i][0];

            }
            if(board[0][i]==board[1][i] && board[1][i]==board[2][i] && board[0][i] != 0) {
                winningPositions[0] = new Point(0,i);
                winningPositions[1] = new Point(1,i);
                winningPositions[2] = new Point(2,i);

                return board[0][i];
            }
        }
        if(board[0][0]==board[1][1] && board[1][1]==board[2][2] && board[0][0] != 0){
            winningPositions[0] = new Point(0,0);
            winningPositions[1] = new Point(1,1);
            winningPositions[2] = new Point(2,2);

            return board[0][0];
        }

        if(board[0][2]==board[1][1] && board[1][1]==board[2][0] && board[2][0] != 0){
            winningPositions[0] = new Point(2,0);
            winningPositions[1] = new Point(1,1);
            winningPositions[2] = new Point(0,2);

            return  board[0][2];
        }
        if (num_of_available_moves == 0)
            return -1;
        return 0;
    }

    public Point[] getWinningPositions() {
        return winningPositions;
    }

    public byte getWinnerId() {
        return winnerId;
    }

    public void resetGame(){
        for( byte[] row : this.board){
            for (byte cell : row){
                cell = 0;
            }
        }
        winningPositions = new Point[3];
        winnerId = 0;
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
