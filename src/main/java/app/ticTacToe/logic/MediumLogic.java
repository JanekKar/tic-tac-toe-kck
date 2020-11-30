package app.ticTacToe.logic;

import java.awt.*;

public class MediumLogic extends TicTacToeLogic {
    @Override
    public Point makeMove() {
        double chance = Math.random();
        if (chance > 0.8)
            return random();
        else
            return bestMove();
    }
}
