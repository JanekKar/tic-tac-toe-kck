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

import static app.cli.Config.*;
import static app.Main.logic;
import static app.cli.Game.runSession;
import static app.cli.Game.submenus;
import static app.cli.Utils.drawWindow;

public class NickMenu {
    private static String nick = "";

    private static int menuPosition = 0;

    private NickMenu(){};

    public static void nickMenu(TextGraphics tg) throws IOException {
        submenus.nickMenuOpen = true;
        drawNickMenu(tg);

        terminal.flush();
        screen.refresh();

        while (true) {
            KeyStroke keyStroke = terminal.pollInput();
            if (keyStroke != null) {
                if (controls.isEscapeKey(keyStroke)) {
                    runSession = false;
                    submenus.nickMenuOpen = false;
                    return;
                }
                if (keyStroke.getKeyType() == KeyType.Character) {
                    if (nick.length() != 10) {
                        nick += keyStroke.getCharacter();
                        tg.setCharacter(windowPaddingLeft + 29 + 2 * menuPosition, windowPaddingTop + 8 + 4, keyStroke.getCharacter());
                    }
                    if (menuPosition != 9) {
                        menuPosition++;
                    }
                }
                if (keyStroke.getKeyType() == KeyType.Enter && !nick.equals("")) {
                    TicTacToe.getInstance().setPlayer(new Player(nick));
                    if (Config.colorSchema.getName().equals("matrix") && nick.equals("Neo")) {
                        logic = new EasyLogic();
                        TicTacToe.getInstance().setBonus(50);
                    }
                    submenus.nickMenuOpen = false;
                    runSession = true;
                    return;
                }
                if (keyStroke.getKeyType() == KeyType.Backspace && menuPosition > 0) {
                    if (menuPosition == 9 && nick.length() == 10)
                        tg.setCharacter(windowPaddingLeft + 29 + 2 * menuPosition, windowPaddingTop + 8 + 4, '_');
                    else
                        tg.setCharacter(windowPaddingLeft + 29 + 2 * menuPosition--, windowPaddingTop + 8 + 4, '_');
                    nick = nick.substring(0, nick.length() - 1);
                }
                highlightPosition(tg, menuPosition);

                terminal.flush();
                screen.refresh();
            }

        }
    }

    public static void drawNickMenu(TextGraphics tg) throws IOException {
        int totalPaddingLeft = windowPaddingLeft + 20;
        int totalPaddingTop = windowPaddingTop + 8;
        drawWindow(tg, 20, 8);
        tg.putString(totalPaddingLeft + 3, totalPaddingTop + 1, "Enter your name");
        tg.drawLine(totalPaddingLeft + 2, totalPaddingTop + 2, windowPaddingLeft + columnHeight - 23, totalPaddingTop + 2, Symbols.SINGLE_LINE_HORIZONTAL);

        totalPaddingLeft += 9;
        for (int i = 0; i < 10; i++) {
            tg.setCharacter(totalPaddingLeft + 2 * i, totalPaddingTop + 4, '_');
        }
        if(nick.length()>0){
            char[] temp = nick.toCharArray();
            for (int i = 0; i < nick.length(); i++) {
                tg.setCharacter(totalPaddingLeft + 2 * i, totalPaddingTop + 4,  temp[i]);
            }
        }

        highlightPosition(tg, menuPosition);
    }

    private static void highlightPosition(TextGraphics tg, int cursorPosition) {
        if (cursorPosition < 9)
            tg.putString(windowPaddingLeft + 29 + 2 * cursorPosition, windowPaddingTop + 12, "_", SGR.BLINK);
    }
}
