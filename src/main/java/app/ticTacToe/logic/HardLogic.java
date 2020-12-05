package app.ticTacToe.logic;

import java.awt.*;

public class HardLogic extends TicTacToeLogic {
    public HardLogic() {
        super();
        this.game.setBonus(30);
    }

    @Override
    public Point makeMove() {
        double chance = Math.random();
        if (chance > 0.8)
            return random();
        else
            return bestMove();
    }
}
