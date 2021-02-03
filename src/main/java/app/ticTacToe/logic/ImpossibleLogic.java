package app.ticTacToe.logic;

import java.awt.*;

public class ImpossibleLogic extends TicTacToeLogic {
    public ImpossibleLogic() {
        super();
        this.game.setBonus(50);
    }

    @Override
    public Point makeMove() {
        return bestMove();
    }
}
