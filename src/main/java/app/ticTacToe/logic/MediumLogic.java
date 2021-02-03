package app.ticTacToe.logic;

import java.awt.*;

public class MediumLogic extends TicTacToeLogic {
    public MediumLogic() {
        super();
        this.game.setBonus(20);
    }

    @Override
    public Point makeMove() {
        double chance = Math.random();
        if (chance > 0.7)
            return random();
        else
            return bestMove();
    }
}
