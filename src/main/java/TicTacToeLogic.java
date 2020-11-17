import java.awt.*;
import java.util.Arrays;

public class TicTacToeLogic {
    private TicTacToe game;
    byte[][] board;

    // blank = 0
    //player_1 =

    byte blank = 0;
    byte ai_id = 1;
    byte player_id = 2;


    public TicTacToeLogic(TicTacToe game){
        this.game = game;
        this.board = game.board;
    }

    public Point random(){
        Point temp;
        do {
            temp = new Point((int)(Math.random()*3), (int)(Math.random()*3));
        } while(!game.checkIfFree(temp));
        return temp;
    }



    public Point bestMove(){
        int bestScore = Integer.MIN_VALUE;
        Point move = null;

        for (int i=0; i<board.length; i++){
            for(int j=0; j<board[i].length; j++){
                if (board[i][j] == blank){
                    game.put(ai_id, new Point(i,j));
                    int score = minimax(false);
                    game.put(blank, new Point(i,j));
                    if(score > bestScore){
                        bestScore = score;
                        move = new Point(i, j);
                    }
                }
            }
        }
        return move;
    }

    public int minimax(boolean ai_turn){
        int state = game.checkForWin();
        if (state!=0){ // If game ended
            if(state == -1){
                return 0; //TIE
            }
            if( state == ai_id){
                return 1; //AI wins
            }
            return -1; //Player wins
        }

        int bestScore = 0;

        if(ai_turn){
            bestScore = Integer.MIN_VALUE;
            for (int i=0; i<board.length; i++){
                for(int j=0; j<board[i].length; j++){
                    if (board[i][j] == blank){
                        game.put(ai_id, new Point(i,j));
                        int score = minimax(false);
                        game.put(blank, new Point(i,j));
                        bestScore = Math.max(score, bestScore);
                    }
                }
            }
        }else{
            bestScore = Integer.MAX_VALUE;
            for (int i=0; i<board.length; i++){
                for(int j=0; j<board[i].length; j++){
                    if (board[i][j] == blank){
                        game.put(player_id, new Point(i,j));
                        int score = minimax(true);
                        game.put(blank, new Point(i,j));
                        bestScore = Math.min(score, bestScore);
                    }
                }
            }
        }
        return bestScore;
    }
}
