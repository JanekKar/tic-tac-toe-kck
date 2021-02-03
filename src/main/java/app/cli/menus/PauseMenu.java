package app.cli.menus;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.Symbols;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;

import java.io.IOException;

import static app.cli.Config.*;
import static app.cli.Game.*;
import static app.cli.Utils.*;

public class PauseMenu {
    public static boolean pauseMenu(TextGraphics tg) throws IOException {
        submenus.pauseMenuOpen = true;

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
                    runSession = false;
                    submenus.pauseMenuOpen = false;
                    return true;
                }
            }
        }
        submenus.pauseMenuOpen = false;
        return false;
    }

    public static void drawPausedMenu(TextGraphics tg) {
        TextColor prevColor = tg.getForegroundColor();

        drawWindow(tg, 4, 2);

        tg.setForegroundColor(colorSchema.getMenuForeground());
        tg.setBackgroundColor(colorSchema.getMenuBackground());
        tg.putString(windowPaddingLeft + 9, windowPaddingTop + 4, "Game Paused", SGR.BLINK, SGR.CIRCLED);
        tg.drawLine(windowPaddingLeft + 8, windowPaddingTop + 5, windowPaddingLeft + columnHeight - 9, windowPaddingTop + 5, Symbols.SINGLE_LINE_HORIZONTAL);
        tg.putString(windowPaddingLeft + 12, windowPaddingTop + 8, "Press Q to quit game", SGR.CIRCLED);
        tg.setForegroundColor(colorSchema.getRed());
        tg.putString(windowPaddingLeft + 12 + "Press ".length(), windowPaddingTop + 8, "Q");
        tg.setForegroundColor(colorSchema.getMenuForeground());
        tg.putString(windowPaddingLeft + 12, windowPaddingTop + 11, "Press ESC to continue", SGR.CIRCLED);
        tg.setForegroundColor(colorSchema.getGreen());
        tg.putString(windowPaddingLeft + 12 + "Press ".length(), windowPaddingTop + 11, "ESC");

        tg.setForegroundColor(colorSchema.getLogo()[0]);
        drawX(tg, 55, 7);
        tg.setForegroundColor(colorSchema.getLogo()[1]);
        drawO(tg, 55, 14);
        tg.setForegroundColor(colorSchema.getLogo()[2]);
        drawX(tg, 42, 14);
        tg.setForegroundColor(prevColor);
    }
}
