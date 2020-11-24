package App.CLI;

import com.googlecode.lanterna.*;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;

import java.io.IOException;

import static App.CLI.CLI.*;
import static App.CLI.Utils.*;

public class PauseMenu {
    public static boolean pauseMenu(TextGraphics tg) throws IOException {
        paused = true;

        drawPausedMenu(tg);

        terminal.flush();
        screen.refresh();

        while (true) {
            KeyStroke keyStroke = terminal.pollInput();
            if (keyStroke != null) {
                if (keyStroke.getKeyType() == KeyType.Escape) {
                    break;
                }
                if (keyStroke.getKeyType() == KeyType.Character && keyStroke.getCharacter() == 'q') {
                    play = false;
                    return true;
                }
            }
        }
        paused = false;
        return false;
    }

    protected static void drawPausedMenu(TextGraphics tg) {
        tg.setForegroundColor(TextColor.ANSI.YELLOW);
        drawBorder(tg, 4, 2, columns - 5, rows - 3);
        tg.setForegroundColor(TextColor.ANSI.DEFAULT);
        tg.fillRectangle(new TerminalPosition(windowPaddingLeft + 5, windowPaddingTop + 3), new TerminalSize(columns - 10, rows - 6), ' ');

        tg.putString(windowPaddingLeft + 9, windowPaddingTop + 4, "Game Paused", SGR.BLINK, SGR.CIRCLED);
        tg.drawLine(windowPaddingLeft + 8, windowPaddingTop + 5, windowPaddingLeft + columns - 9, windowPaddingTop + 5, Symbols.SINGLE_LINE_HORIZONTAL);
        tg.putString(windowPaddingLeft + 12, windowPaddingTop + 8, "Press Q to quit game", SGR.CIRCLED);
        tg.setForegroundColor(TextColor.ANSI.RED);
        tg.putString(windowPaddingLeft + 12 + "Press ".length(), windowPaddingTop + 8, "Q");
        tg.setForegroundColor(TextColor.ANSI.DEFAULT);
        tg.putString(windowPaddingLeft + 12, windowPaddingTop + 11, "Press ESC to continue", SGR.CIRCLED);
        tg.setForegroundColor(TextColor.ANSI.GREEN);
        tg.putString(windowPaddingLeft + 12 + "Press ".length(), windowPaddingTop + 11, "ESC");

        drawX(tg, 55, 7);
        tg.setForegroundColor(TextColor.ANSI.MAGENTA);
        drawO(tg, 55, 14);
        tg.setForegroundColor(TextColor.ANSI.CYAN);
        drawX(tg, 42, 14);
        tg.setForegroundColor(TextColor.ANSI.DEFAULT);
    }
}
