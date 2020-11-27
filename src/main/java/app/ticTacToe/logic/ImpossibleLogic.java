package app.ticTacToe.logic;

import java.awt.*;

public class ImpossibleLogic extends TicTacToeLogic{
    @Override
    public Point makeMove() {
        return bestMove();
    }
}
