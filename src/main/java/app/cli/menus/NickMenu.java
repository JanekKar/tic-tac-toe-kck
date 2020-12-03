package app.cli.menus;

import app.cli.Config;
import app.ticTacToe.Player;
import app.ticTacToe.TicTacToe;
import app.ticTacToe.logic.EasyLogic;
import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.Symbols;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;

import java.io.IOException;

import static app.cli.Game.*;
import static app.cli.Utils.*;
import static app.cli.Config.*;

public class NickMenu {

    private static String nick = "";

    public static void drawNickMenu(TextGraphics tg) throws IOException {
        int cursorPosition = 0;;
        int totalPaddingLeft = windowPaddingLeft + 20;
        int totalPaddingTop = windowPaddingTop + 8;
        drawWindow(tg, 20, 8);
        tg.putString(totalPaddingLeft+3, totalPaddingTop+1, "Enter your name");
        tg.drawLine(totalPaddingLeft+2, totalPaddingTop+2, windowPaddingLeft+columns - 23, totalPaddingTop+2, Symbols.SINGLE_LINE_HORIZONTAL);

        totalPaddingLeft += 9;
        for (int i = 0; i < 10; i++) {
            tg.setCharacter(totalPaddingLeft + 2 * i, totalPaddingTop+4, '_');
        }
        highlightPosition(tg, cursorPosition);
        terminal.flush();
        screen.refresh();
        while (true) {
            KeyStroke keyStroke = terminal.pollInput();
            if (keyStroke != null) {
                if(controls.isEscapeKey(keyStroke)){
                    play = false;
                    return;
                }

                if (keyStroke.getKeyType() == KeyType.Character) {
                    if(nick.length()!=10){
                        nick += keyStroke.getCharacter();
                        tg.setCharacter(totalPaddingLeft + 2 * cursorPosition, totalPaddingTop+4, keyStroke.getCharacter());
                    }
                    if (cursorPosition != 9) {
                        cursorPosition++;
                    }
                }
                if (keyStroke.getKeyType() == KeyType.Enter && !nick.equals("")) {
                    TicTacToe.getInstance().setPlayer(new Player(nick));
                    if(Config.colorSchema.getName().equals("matrix") && nick.equals("Neo")){
                        logic = new EasyLogic();
                        TicTacToe.getInstance().setBonus(50);
                    }
                    return;
                }
                if (keyStroke.getKeyType() == KeyType.Backspace && cursorPosition > 0) {
                    if (cursorPosition == 9 && nick.length() == 10)
                        tg.setCharacter(totalPaddingLeft + 2 * cursorPosition, totalPaddingTop+4, '_');
                    else
                        tg.setCharacter(totalPaddingLeft + 2 * cursorPosition--, totalPaddingTop+4, '_');
                    nick = nick.substring(0, nick.length()-1);
                }
                highlightPosition(tg, cursorPosition);

                terminal.flush();
                screen.refresh();
            }

        }
    }

    private static void highlightPosition(TextGraphics tg, int cursorPosition) {
        if(cursorPosition < 9)
            tg.putString(windowPaddingLeft + 29 + 2 * cursorPosition, windowPaddingTop + 12, "_", SGR.BLINK);
    }
}
