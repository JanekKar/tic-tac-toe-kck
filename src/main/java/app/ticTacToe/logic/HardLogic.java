package app.ticTacToe.logic;

import java.awt.*;

public class HardLogic extends TicTacToeLogic {
    @Override
    public Point makeMove() {
        double chance = Math.random();
        if (chance > 0.9)
            return random();
        else
            return bestMove();
    }
}
