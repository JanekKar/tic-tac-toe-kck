package app.cli.menus;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.Symbols;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;

import java.io.IOException;

import static app.cli.ACSILogo.drawLogo;
import static app.cli.Config.*;
import static app.cli.Game.*;
import static app.cli.Utils.*;


public class MainMenu {

    private final static String[] menuItems = {
            "Start Game",
            "Settings",
            "Score Board",
            "Quit"
    };

    private MainMenu(){};

    private static int menuPosition = 0;

    private static void highlightMenuItem(TextGraphics tg, int menuPos, String[] menuItems, int menuPaddingLeft, int menuPaddingTop) {
        TextColor prevColor = tg.getForegroundColor();
        int itemNumber = 2 * menuPos;
        menuPaddingTop += windowPaddingTop;
        menuPaddingLeft += windowPaddingLeft;

        tg.setForegroundColor(colorSchema.getMenuHighlight());
        tg.putString(menuPaddingLeft - 2, menuPaddingTop + itemNumber, Symbols.TRIANGLE_RIGHT_POINTING_BLACK + "", SGR.BLINK);
        tg.putString(menuPaddingLeft, menuPaddingTop + itemNumber, menuItems[menuPos], SGR.UNDERLINE, SGR.BOLD);
        tg.putString(menuPaddingLeft - 2 + menuItems[menuPos].length() + 3, menuPaddingTop + itemNumber, Symbols.TRIANGLE_LEFT_POINTING_BLACK + "", SGR.BLINK);
        tg.setForegroundColor(prevColor);
    }

    private static void unhighlightMenuItem(TextGraphics tg, int menuPos, String[] menuItems, int menuPaddingLeft, int menuPaddingTop) {
        int itemNumber = 2 * menuPos;
        menuPaddingTop += windowPaddingTop;
        menuPaddingLeft += windowPaddingLeft;

        tg.setForegroundColor(colorSchema.getMenuForeground());
        tg.putString(menuPaddingLeft - 2, menuPaddingTop + itemNumber, " ");
        tg.putString(menuPaddingLeft, menuPaddingTop + itemNumber, menuItems[menuPos], SGR.BOLD);
        tg.putString(menuPaddingLeft - 2 + menuItems[menuPos].length() + 3, menuPaddingTop + 2 * menuPos, " ");
    }

    public static void drawMainMenu(TextGraphics tg) {
        int menuPaddingTop = windowPaddingTop + 12;
        int menuPaddingLeft = windowPaddingLeft + 12;
        tg.setBackgroundColor(colorSchema.getMenuBackground());
        tg.setForegroundColor(colorSchema.getMenuForeground());
        tg.fill(' ');
        drawWindow(tg, 0, 0);
        drawLogo(tg, 11, 4);

        for (int i = 0; i < menuItems.length; i++) {
            tg.putString(menuPaddingLeft, menuPaddingTop + 2 * i, menuItems[i], SGR.BOLD);
        }

        drawX(tg, 40, 15);
        drawO(tg, 53, 15);
        drawO(tg, 53, 9);

        menuPosition = 0;
        highlightMenuItem(tg, menuPosition, menuItems, 12, 12);
    }

    public static void mainMenu(TextGraphics tg) throws IOException {
        drawMainMenu(tg);

        terminal.flush();
        screen.refresh();

        while (true) {
            KeyStroke keyStroke = terminal.pollInput();
            if (keyStroke != null) {
                unhighlightMenuItem(tg, menuPosition, menuItems, 12, 12);
                if (controls.isDownKey(keyStroke) && menuPosition < menuItems.length - 1) {
                    menuPosition++;
                }
                if (controls.isUpKey(keyStroke) && menuPosition > 0) {
                    menuPosition--;
                }
                if (controls.isAssertKey(keyStroke)) {
                    switch (menuPosition) {
                        case 0:
                            submenus.getLevelMenu().showMenu();
                            if (runSession)
                                return;
                            drawMainMenu(tg);
                            break;
                        case 1:
                            submenus.getSettingsMenu().showMenu();
                            break;
                        case 2:
                            submenus.getScoreInfoMenu(tg, false);
                            break;
                        case 3:
                            runGame = false;
                            runSession = false;
                            return;
                    }
                    drawMainMenu(tg);
                }

                highlightMenuItem(tg, menuPosition, menuItems, 12, 12);

                terminal.flush();
                screen.refresh();
            }
        }
    }


}
