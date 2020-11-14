import java.awt.*;
import java.util.Arrays;

public class TicTacToeLogic {
    private TicTacToe game;

    // blank = 0
    //player_1 =

    byte blank = 0;
    int ai_id;

    public TicTacToeLogic(TicTacToe game, int players_id){
        this.game = game;
        this.ai_id = players_id;
    }

    public Point random(){
        Point temp;
        do {
            temp = new Point((int)(Math.random()*3), (int)(Math.random()*3));
        } while(!game.checkIfFree(temp));
        return temp;
    }


    public int minmax(byte[][] board, byte player){
        return this.minmax(board, player, true);
    }

    public int minmax(byte[][] board, byte player, boolean maximize){
        if (game.checkForWin()){
            if (game.getWinnerId() == ai_id){
                return 1;
            }else{
                return -1;
            }
        }
        if (game.checkForTie()){
            return 0;
        }

        if(maximize){
            
        }

        Point move = null;
        int score = Integer.MIN_VALUE;

        for (int i=0; i<board.length; i++){
            for(int j=0; j<board[i].length; j++){
                if (board[i][j] == blank){
                    byte[][] nextMove = copyBoard(board);
                    nextMove[i][j] = player;
                    int scoreForMove = minmax(nextMove, (byte)((player%2)+1), !maximize);
                    if(scoreForMove > score){
                        score = scoreForMove;
                        move = new Point(i, j);
                    }
                }
            }
        }
        if (move == null){
            return 0;
        }
        System.out.println(move);
        return score;
    }

    public byte[][]copyBoard(byte[][] board){
        byte[][] temp = new byte[3][3];
        for(int i = 0; i<board.length; i++){
            for(int j=0; j<board[i].length; j++){
                temp[i][j]=board[i][j];
            }
        }
        return temp;
    }
}
