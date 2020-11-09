import java.util.Arrays;

public class TicTacToe {

    /*
    * 0 - null
    * 1 - X
    * 2 - O
    * */


    private byte[][] board = new byte[3][3];

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
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }

    public void put(char c, int posX, int posY) throws WrongCharException {
        c = Character.toLowerCase(c);
        if (c == 'x'){
            this.board[posX][posY] = 1;
        } else if (c == 'o'){
            this.board[posX][posY] = 2;
        } else {
            throw new WrongCharException(c + " is not 'x' or 'o' - case insensitive");
        }
    }

    public void checkForWin(){
        for( byte[] row: this.board ){
//            if row
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

}
