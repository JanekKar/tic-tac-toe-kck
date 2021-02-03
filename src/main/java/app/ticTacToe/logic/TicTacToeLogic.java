package app.ticTacToe.logic;

import app.ticTacToe.TicTacToe;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public abstract class TicTacToeLogic {
    protected final TicTacToe game;
    String ai = "O";
    String player = "X";
    String blank;
    Map<String, Integer> score = new HashMap<String, Integer>();

    protected TicTacToeLogic() {
        this.game = TicTacToe.getInstance();
        this.blank = game.getBlank();

        score.put(player, -10);
        score.put(ai, 10);
        score.put("TIE", 0);
    }

    public abstract Point makeMove();

    protected Point random() {
        Point temp;
        do {
            temp = new Point((int) (Math.random() * 3), (int) (Math.random() * 3));
        } while (!game.checkIfFree(temp));
        return temp;
    }

    public Point bestMove() {
        int bestScore = Integer.MIN_VALUE;
        Point move = null;

        for (int i = 0; i < game.getBoard().length; i++) {
            for (int j = 0; j < game.getBoard()[i].length; j++) {
                if (game.checkIfFree(new Point(i, j))) {
                    game.makeMove(i, j, ai);
                    int score = minimax(false);
                    game.undoMove(i, j);
                    if (score > bestScore) {
                        bestScore = score;
                        move = new Point(i, j);
                    }
                }
            }
        }
        return move;
    }

    protected int minimax(boolean ai_turn) {

        String result = game.checkWinner();
        if (result != null)
            return score.get(result);


        int bestScore;

        if (ai_turn) {
            bestScore = Integer.MIN_VALUE;
            for (int i = 0; i < game.getBoard().length; i++) {
                for (int j = 0; j < game.getBoard()[i].length; j++) {
                    if (game.checkIfFree(new Point(i, j))) {
                        game.makeMove(i, j, ai);
                        int score = minimax(false);
                        game.undoMove(i, j);
                        bestScore = Math.max(score, bestScore);
                    }
                }
            }
        } else {
            bestScore = Integer.MAX_VALUE;
            for (int i = 0; i < game.getBoard().length; i++) {
                for (int j = 0; j < game.getBoard()[i].length; j++) {
                    if (game.checkIfFree(new Point(i, j))) {
                        game.makeMove(i, j, player);
                        int score = minimax(true);
                        game.undoMove(i, j);
                        bestScore = Math.min(score, bestScore);
                    }
                }
            }
        }
        return bestScore;
    }

}

