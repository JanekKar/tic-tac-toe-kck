package app.cli;

import com.googlecode.lanterna.*;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;

import java.io.IOException;

import static app.cli.CLI.*;
import static app.cli.Utils.*;

public class PauseMenu {
    public static boolean pauseMenu(TextGraphics tg) throws IOException {
        paused = true;

        drawPausedMenu(tg);

        terminal.flush();
        screen.refresh();

        while (true) {
            KeyStroke keyStroke = terminal.pollInput();
            if (keyStroke != null) {
                if (controls.isEscapeKey(keyStroke)) {
                    break;
                }
                if (controls.isQuitKey(keyStroke)) {
                    play = false;
                    paused = false;
                    return true;
                }
            }
        }
        paused = false;
        return false;
    }

    protected static void drawPausedMenu(TextGraphics tg) {
        TextColor prevColor = tg.getForegroundColor();

        drawWindow(tg, 4, 2);

        tg.setForegroundColor(colorSchema.menuForground);
        tg.setBackgroundColor(colorSchema.menuBackground);
        tg.putString(windowPaddingLeft + 9, windowPaddingTop + 4, "Game Paused", SGR.BLINK, SGR.CIRCLED);
        tg.drawLine(windowPaddingLeft + 8, windowPaddingTop + 5, windowPaddingLeft + columns - 9, windowPaddingTop + 5, Symbols.SINGLE_LINE_HORIZONTAL);
        tg.putString(windowPaddingLeft + 12, windowPaddingTop + 8, "Press Q to quit game", SGR.CIRCLED);
        tg.setForegroundColor(TextColor.ANSI.RED);
        tg.putString(windowPaddingLeft + 12 + "Press ".length(), windowPaddingTop + 8, "Q");
        tg.setForegroundColor(colorSchema.menuForground);
        tg.putString(windowPaddingLeft + 12, windowPaddingTop + 11, "Press ESC to continue", SGR.CIRCLED);
        tg.setForegroundColor(TextColor.ANSI.GREEN);
        tg.putString(windowPaddingLeft + 12 + "Press ".length(), windowPaddingTop + 11, "ESC");

        tg.setForegroundColor(colorSchema.logo[0]);
        drawX(tg, 55, 7);
        tg.setForegroundColor(colorSchema.logo[1]);
        drawO(tg, 55, 14);
        tg.setForegroundColor(colorSchema.logo[2]);
        drawX(tg, 42, 14);
        tg.setForegroundColor(prevColor);
    }
}