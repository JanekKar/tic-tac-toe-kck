package App.TicTacToe;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class TicTacToeLogic {
    private TicTacToe game;

    String ai = "O";
    String player = "X";
    String blank;

    Map<String, Integer> score = new HashMap<String, Integer>();

    public TicTacToeLogic(TicTacToe game){
        this.game = game;
        this.blank = game.blank;

        score.put(player, -10);
        score.put(ai, 10);
        score.put("TIE", 0);
    }

    public Point makeMove(){
        return bestMove();
    }

    public Point easyAi(){
        double chance = Math.random();
        if(chance > 0.5)
            return random();
        else
            return bestMove();
    }

    public Point midAi(){
        double chance = Math.random();
        if(chance > 0.8)
            return random();
        else
            return bestMove();
    }

    public Point hardAi(){
        double chance = Math.random();
        if(chance > 0.9)
            return random();
        else
            return bestMove();

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

        for (int i=0; i<game.board.length; i++){
            for(int j=0; j<game.board[i].length; j++){
                if (game.checkIfFree(new Point(i,j))){
                    game.makeMove(i,j,ai);
                    int score = minimax(false);
                    game.undoMove(i,j);
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

        String result = game.checkWinner();
        if(result!=null)
            return score.get(result);


        int bestScore;

        if(ai_turn){
            bestScore = Integer.MIN_VALUE;
            for (int i=0; i<game.board.length; i++){
                for(int j=0; j<game.board[i].length; j++){
                    if (game.checkIfFree(new Point(i,j))){
                        game.makeMove(i,j, ai);
                        int score = minimax(false);
                        game.undoMove(i, j);
                        bestScore = Math.max(score, bestScore);
                    }
                }
            }
        }else{
            bestScore = Integer.MAX_VALUE;
            for (int i=0; i<game.board.length; i++){
                for(int j=0; j<game.board[i].length; j++){
                    if (game.checkIfFree(new Point(i,j))){
                        game.makeMove(i,j,player);
                        int score = minimax(true);
                        game.undoMove(i,j);
                        bestScore = Math.min(score, bestScore);
                    }
                }
            }
        }
        return bestScore;
    }
}
