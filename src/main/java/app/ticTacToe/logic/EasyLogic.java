package app.ticTacToe.logic;

import java.awt.*;

public class EasyLogic extends TicTacToeLogic {

    public EasyLogic() {
        super();
        this.game.setBonus(10);
    }

    @Override
    public Point makeMove() {
        double chance = Math.random();
        if (chance > 0.5)
            return random();
        else
            return bestMove();
    }
}
