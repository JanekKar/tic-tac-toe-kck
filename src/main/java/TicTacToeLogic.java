import java.awt.*;
import java.util.Arrays;

public class TicTacToeLogic {
    private TicTacToe game;

    // blank = 0
    //player_1 =

    byte blank = 0;
    byte ai_id = 1;
    byte player_id = 2;


    public TicTacToeLogic(TicTacToe game){
        this.game = game;
    }

    public Point random(){
        Point temp;
        do {
            temp = new Point((int)(Math.random()*3), (int)(Math.random()*3));
        } while(!game.checkIfFree(temp));
        return temp;
    }



    public Point bestMove(byte[][] board){
        int bestScore = Integer.MIN_VALUE;
        Point move = null;

        for (int i=0; i<board.length; i++){
            for(int j=0; j<board[i].length; j++){
                if (board[i][j] == blank){
                    board[i][j] = ai_id;
                    int score = minmax(board,false);
                    board[i][j] = blank;
                    if(score > bestScore){
                        bestScore = score;
                        move = new Point(i, j);
                    }
                }
            }
        }
        return move;
    }

    public int minmax(byte[][] board, boolean maximize){
//        game.printBoard(board);
        int state = game.checkForWin();
        if (state!=0){
            if(state == -1)
                return 0;
            else if( state == ai_id){
                return 10;}
            else{
            return -10;}
        }

        if(maximize){
            int bestScore = Integer.MIN_VALUE;
            for (int i=0; i<board.length; i++){
                for(int j=0; j<board[i].length; j++){
                    if (board[i][j] == blank){
                        board[i][j] = ai_id;
                        int score = minmax(board,false);
                        board[i][j] = blank;
                        bestScore = Math.max(bestScore, score);
                    }
                }
            }
            return bestScore;
        }else{
            int bestScore = Integer.MAX_VALUE;
            for (int i=0; i<board.length; i++){
                for(int j=0; j<board[i].length; j++){
                    if (board[i][j] == blank){
                        board[i][j] = player_id;
                        int score = minmax(board,true);
                        board[i][j] = blank;
                        bestScore = Math.min(bestScore, score);
                    }
                }
            }
            return bestScore;
        }

    }

}
