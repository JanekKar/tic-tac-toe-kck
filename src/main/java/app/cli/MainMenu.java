package app.cli;
import app.cli.colors.DefaultColors;
import app.cli.colors.MonokaiColors;
import app.cli.controls.DefaultControls;
import app.cli.controls.JKLIControls;
import app.cli.controls.VimControls;
import app.ticTacToe.logic.EasyLogic;
import app.ticTacToe.logic.HardLogic;
import app.ticTacToe.logic.ImpossibleLogic;
import app.ticTacToe.logic.MediumLogic;
import com.googlecode.lanterna.*;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;

import java.io.IOException;

import static app.cli.Utils.*;
import static app.cli.CLI.*;


public class MainMenu {
    private static int menuPos = 0;
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
            "Default", "Monokai"
    };

    private final static String[] controlsMenuList = {
            "Arrows",
            "WASD",
            "IJKL",
            "Vim"
    };

    private static void drawLogo(TextGraphics tg){

        TextColor prevColor = tg.getForegroundColor();

        int totalPaddingLeft = windowPaddingLeft + 10;
        int totalPaddingTop = windowPaddingTop;

        char sym = Symbols.BLOCK_SOLID;
        tg.setForegroundColor(colorSchema.logo[0]);
        //T
        tg.drawLine(totalPaddingLeft + 3,totalPaddingTop + 3,totalPaddingLeft + 7,totalPaddingTop + 3, sym);
        tg.drawLine(totalPaddingLeft + 5,totalPaddingTop + 3,totalPaddingLeft + 5,totalPaddingTop + 6, sym);
        //I
        tg.drawLine(totalPaddingLeft + 9,totalPaddingTop + 3,totalPaddingLeft + 9,totalPaddingTop + 6, sym);
        //C
        tg.drawLine(totalPaddingLeft + 11,totalPaddingTop + 3,totalPaddingLeft + 11,totalPaddingTop + 6, sym);
        tg.drawLine(totalPaddingLeft + 11,totalPaddingTop + 3,totalPaddingLeft + 14,totalPaddingTop + 3, sym);
        tg.drawLine(totalPaddingLeft + 11,totalPaddingTop + 6,totalPaddingLeft + 14,totalPaddingTop + 6, sym);

        sym = Symbols.BLOCK_DENSE;
        tg.setForegroundColor(colorSchema.logo[1]);
        //T
        tg.drawLine(totalPaddingLeft + 18,totalPaddingTop + 3,totalPaddingLeft + 22,totalPaddingTop + 3, sym);
        tg.drawLine(totalPaddingLeft + 20,totalPaddingTop + 3,totalPaddingLeft + 20,totalPaddingTop + 6, sym);

        //A
        tg.drawLine(totalPaddingLeft + 24, totalPaddingTop + 3, totalPaddingLeft + 28,totalPaddingTop + 3, sym);
        tg.drawLine(totalPaddingLeft + 24, totalPaddingTop + 5, totalPaddingLeft + 28,totalPaddingTop + 5, sym);
        tg.drawLine(totalPaddingLeft + 24,totalPaddingTop + 3,totalPaddingLeft + 24,totalPaddingTop + 6, sym);
        tg.drawLine(totalPaddingLeft + 28,totalPaddingTop + 3,totalPaddingLeft + 28,totalPaddingTop + 6, sym);

        //C
        tg.drawLine(totalPaddingLeft + 30,totalPaddingTop + 3,totalPaddingLeft + 30,totalPaddingTop + 6, sym);
        tg.drawLine(totalPaddingLeft + 30,totalPaddingTop + 3,totalPaddingLeft + 33,totalPaddingTop + 3, sym);
        tg.drawLine(totalPaddingLeft + 30,totalPaddingTop + 6,totalPaddingLeft + 33,totalPaddingTop + 6, sym);

        sym = Symbols.BLOCK_MIDDLE;
        tg.setForegroundColor(colorSchema.logo[2]);

        //T
        tg.drawLine(totalPaddingLeft + 37,totalPaddingTop + 3,totalPaddingLeft + 41,totalPaddingTop + 3, sym);
        tg.drawLine(totalPaddingLeft + 39,totalPaddingTop + 3,totalPaddingLeft + 39,totalPaddingTop + 6, sym);

        //O
        tg.drawLine(totalPaddingLeft + 43, totalPaddingTop + 3, totalPaddingLeft + 47,totalPaddingTop + 3, sym);
        tg.drawLine(totalPaddingLeft + 43, totalPaddingTop + 6, totalPaddingLeft + 47,totalPaddingTop + 6, sym);
        tg.drawLine(totalPaddingLeft + 43,totalPaddingTop + 3,totalPaddingLeft + 43,totalPaddingTop + 6, sym);
        tg.drawLine(totalPaddingLeft + 47,totalPaddingTop + 3,totalPaddingLeft + 47,totalPaddingTop + 6, sym);

        //E
        tg.drawLine(totalPaddingLeft + 49, totalPaddingTop + 3, totalPaddingLeft + 53,totalPaddingTop + 3, sym);
        tg.drawLine(totalPaddingLeft + 49, totalPaddingTop + 4, totalPaddingLeft + 53,totalPaddingTop + 4, sym);
        tg.drawLine(totalPaddingLeft + 49, totalPaddingTop + 6, totalPaddingLeft + 53,totalPaddingTop + 6, sym);
        tg.drawLine(totalPaddingLeft + 49,totalPaddingTop + 3,totalPaddingLeft + 49,totalPaddingTop + 6, sym);

        tg.setForegroundColor(prevColor);
    }

    private static void highlightMenuItem(TextGraphics tg, int menuPos, String[] menuItems, int menuPaddingLeft, int menuPaddingTop) {
        TextColor prevColor = tg.getForegroundColor();
        int itemNumber = 2 * menuPos;
        menuPaddingTop += windowPaddingTop;
        menuPaddingLeft += windowPaddingLeft;

        tg.setForegroundColor(colorSchema.menuHighlight);
        tg.putString(menuPaddingLeft-2,menuPaddingTop+itemNumber, Symbols.TRIANGLE_RIGHT_POINTING_BLACK+"", SGR.BLINK);
        tg.putString(menuPaddingLeft,menuPaddingTop+itemNumber, menuItems[menuPos], SGR.UNDERLINE );
        tg.putString(menuPaddingLeft-2+menuItems[menuPos].length() + 3,menuPaddingTop+itemNumber, Symbols.TRIANGLE_LEFT_POINTING_BLACK+"", SGR.BLINK);
        tg.setForegroundColor(prevColor);
    }

    private static void unhighlightMenuItem(TextGraphics tg, int menuPos, String[] menuItems, int menuPaddingLeft, int menuPaddingTop){
        int itemNumber = 2 * menuPos;
        menuPaddingTop += windowPaddingTop;
        menuPaddingLeft += windowPaddingLeft;

        tg.setForegroundColor(colorSchema.menuForground);
        tg.putString(menuPaddingLeft-2,menuPaddingTop+itemNumber, " ");
        tg.putString(menuPaddingLeft,menuPaddingTop+itemNumber, menuItems[menuPos]);
        tg.putString(menuPaddingLeft-2+menuItems[menuPos].length() + 3,menuPaddingTop+2*menuPos, " ");
    }

    protected static void drawMainMenu(TextGraphics tg) {
        int menuPaddingTop = windowPaddingTop+12;
        int menuPaddingLeft = windowPaddingLeft+12;
        tg.setBackgroundColor(colorSchema.menuBackground);
        tg.setForegroundColor(colorSchema.menuForground);
        tg.fill(' ');
        drawWindow(tg,0,0);
        drawLogo(tg);

        for(int i = 0; i< menuItems.length; i++){
            tg.putString(menuPaddingLeft,menuPaddingTop+2*i, menuItems[i] );
        }

        menuPos = 0;
        highlightMenuItem(tg, menuPos, menuItems, 12, 12);
    }

    protected static void mainMenu(TextGraphics tg) throws IOException {
        drawMainMenu(tg);

        terminal.flush();
        screen.refresh();

        while(true){
            KeyStroke keyStroke = terminal.pollInput();
            if (keyStroke != null) {

                unhighlightMenuItem(tg, menuPos, menuItems, 12, 12);

                if (controls.isDownKey(keyStroke) && menuPos < menuItems.length-1){
                    menuPos++;
                }
                if (controls.isUpKey(keyStroke) && menuPos > 0) {
                    menuPos--;
                }
                if (controls.isAssertKey(keyStroke)){
                    switch(menuPos){
                        case 0:
                            Submenu levelMenu = new Submenu(tg, levelMenuItems, "Choose difficulty level:") {
                                @Override
                                void onEnter(int menuPos) {
                                    switch (menuPos){
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
                            if(play)
                                return;
                            drawMainMenu(tg);
                            break;
                        case 1:
                            Submenu settingsMenu = new Submenu(tg, setteingsCategories, "Settings") {
                                @Override
                                void onEnter(int menuPos) throws IOException {
                                    switch(menuPos){
                                        case 0:
                                            Submenu colorsMenu = new Submenu(tg, colorsSchemas, "Choose color schema") {
                                                @Override
                                                void onEnter(int menuPos) {
                                                    switch(menuPos){
                                                        case 0:
                                                            setColorSchema(DefaultColors.getInstance());
                                                            break;
                                                        case 1:
                                                            setColorSchema(MonokaiColors.getInstance());
                                                            break;
                                                        case 2:
                                                            break;
                                                        case 3:
                                                            break;
                                                    }
                                                    closeMenu();
                                                }
                                            };
                                            colorsMenu.showMenu();
                                            break;
                                        case 1:
                                            Submenu controlsMenu = new Submenu(tg, controlsMenuList, "Choose controls:") {
                                                @Override
                                                void onEnter(int menuPos) throws IOException {
                                                    switch(menuPos){
                                                        case 0:
                                                            controls = DefaultControls.getInstance();
                                                            break;
                                                        case 1:
                                                            break;
                                                        case 2:
                                                            controls = JKLIControls.getInstance();
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
