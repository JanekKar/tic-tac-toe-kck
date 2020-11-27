package app.ticTacToe.logic;
import java.awt.*;

public class EasyLogic extends TicTacToeLogic {
    @Override
    public Point makeMove() {
        double chance = Math.random();
        if (chance > 0.5)
            return random();
        else
            return bestMove();
    }
}
