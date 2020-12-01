package app.cli;

import app.cli.colors.DefaultColors;
import app.cli.colors.MonokaiColors;
import app.cli.colors.OceanColors;
import app.cli.controls.DefaultControls;
import app.cli.controls.IJKLControls;
import app.cli.controls.VimControls;
import app.cli.controls.WASDControls;
import app.ticTacToe.logic.EasyLogic;
import app.ticTacToe.logic.HardLogic;
import app.ticTacToe.logic.ImpossibleLogic;
import app.ticTacToe.logic.MediumLogic;
import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.Symbols;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;

import java.io.IOException;

import static app.cli.ACSILogo.drawLogo;
import static app.cli.Game.*;
import static app.cli.Utils.*;
import static app.cli.Config.*;


public class MainMenu {
    private final static String[] menuItems = {
            "Start Game",
            "Settings",
            "Score Board",
            "Quit"
    };
    private final static String[] levelMenuItems = {
            "EASY",
            "MEDIUM",
            "HARD",
            "IMPOSSIBLE"
    };
    private final static String[] setteingsCategories = {
            "Color schema",
            "Controls"
    };
    private final static String[] colorsSchemas = {
            "Default", "Monokai", "Nord"
    };
    private final static String[] controlsMenuList = {
            "Arrows",
            "WASD",
            "IJKL",
            "Vim"
    };
    private static int menuPos = 0;

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

    protected static void drawMainMenu(TextGraphics tg) {
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

        menuPos = 0;
        highlightMenuItem(tg, menuPos, menuItems, 12, 12);
    }

    protected static void mainMenu(TextGraphics tg) throws IOException {
        drawMainMenu(tg);

        terminal.flush();
        screen.refresh();

        while (true) {
            KeyStroke keyStroke = terminal.pollInput();
            if (keyStroke != null) {

                unhighlightMenuItem(tg, menuPos, menuItems, 12, 12);

                if (controls.isDownKey(keyStroke) && menuPos < menuItems.length - 1) {
                    menuPos++;
                }
                if (controls.isUpKey(keyStroke) && menuPos > 0) {
                    menuPos--;
                }
                if (controls.isAssertKey(keyStroke)) {
                    switch (menuPos) {
                        case 0:
                            Submenu levelMenu = new Submenu(tg, levelMenuItems, "Choose difficulty level:") {
                                @Override
                                void onEnter(int menuPos) {
                                    switch (menuPos) {
                                        case 0:
                                            logic = new EasyLogic();
                                            break;
                                        case 1:
                                            logic = new MediumLogic();
                                            break;
                                        case 2:
                                            logic = new HardLogic();
                                            break;
                                        case 3:
                                            logic = new ImpossibleLogic();
                                            break;
                                    }
                                    play = true;
                                    closeMenu();
                                }
                            };

                            levelMenu.showMenu();
                            if (play)
                                return;
                            drawMainMenu(tg);
                            break;
                        case 1:
                            Submenu settingsMenu = new Submenu(tg, setteingsCategories, "Settings") {
                                @Override
                                void onEnter(int menuPos) throws IOException {
                                    switch (menuPos) {
                                        case 0:
                                            Submenu colorsMenu = new Submenu(tg, colorsSchemas, "Choose color schema") {
                                                @Override
                                                void onEnter(int menuPos) {
                                                    switch (menuPos) {
                                                        case 0:
                                                            colorSchema = DefaultColors.getInstance();
                                                            break;
                                                        case 1:
                                                            colorSchema = MonokaiColors.getInstance();
                                                            break;
                                                        case 2:
                                                            colorSchema = OceanColors.getInstance();
                                                            break;
                                                        case 3:
                                                            break;
                                                    }
                                                    closeMenu();
                                                    drawMainMenu(tg);
                                                }
                                            };
                                            colorsMenu.showMenu();
                                            break;
                                        case 1:
                                            Submenu controlsMenu = new Submenu(tg, controlsMenuList, "Choose controls:") {
                                                @Override
                                                void onEnter(int menuPos) {
                                                    switch (menuPos) {
                                                        case 0:
                                                            controls = DefaultControls.getInstance();
                                                            break;
                                                        case 1:
                                                            controls = WASDControls.getInstance();
                                                            break;
                                                        case 2:
                                                            controls = IJKLControls.getInstance();
                                                            break;
                                                        case 3:
                                                            controls = VimControls.getInstance();
                                                            break;
                                                    }
                                                    closeMenu();
                                                }
                                            };
                                            controlsMenu.showMenu();
                                            break;
                                    }
                                }
                            };
                            settingsMenu.showMenu();
                            drawMainMenu(tg);
                            break;
                        case 2:
                            break;
                        case 3:
                            run = false;
                            play = false;
                            return;
                    }
                }

                highlightMenuItem(tg, menuPos, menuItems, 12, 12);

                terminal.flush();
                screen.refresh();
            }
        }
    }


}
