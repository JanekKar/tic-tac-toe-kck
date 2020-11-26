package App.CLI;
import com.googlecode.lanterna.*;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;

import java.io.IOException;

import static App.CLI.Utils.*;
import static App.CLI.CLI.*;


public class MainMenu {
    private static int menuPos = 0;
    private final static String[] menuItems = {
            "Start Game",
            "Settings",
            "Score Board",
            "Quit"};

    private static void drawLogo(TextGraphics tg){

        int totalPaddingLeft = windowPaddingLeft + 10;
        int totalPaddingTop = windowPaddingTop;

        char sym = Symbols.BLOCK_SOLID;
        tg.setForegroundColor(TextColor.ANSI.CYAN);
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
        tg.setForegroundColor(TextColor.ANSI.GREEN);
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
        tg.setForegroundColor(TextColor.ANSI.YELLOW);

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
    }

    protected static void mainMenu(TextGraphics tg) throws IOException {
        drawMainMenu(tg);

        terminal.flush();
        screen.refresh();

        while(true){
            KeyStroke keyStroke = terminal.pollInput();
            if (keyStroke != null) {

                unhighlightMenuItem(tg);

                if (keyStroke.getKeyType() == KeyType.ArrowDown && menuPos < menuItems.length-1){
                    menuPos++;
                }
                if (keyStroke.getKeyType() == KeyType.ArrowUp && menuPos > 0) {
                    menuPos--;
                }
                if (keyStroke.getKeyType() == KeyType.Enter){
                    switch(menuPos){
                        case 0:
                            levelMenu(tg);
                            if(play)
                                return;
                        case 1:
                            break;
                        case 2:
                            break;
                        case 3:
                            run = false;
                            play = false;
                            return;
                    }
                }

                highlightMenuItem(tg);

                terminal.flush();
                screen.refresh();
            }
        }
    }

    private static void drawLevelMenu(TextGraphics tg){
        drawWindow(tg, 15, 5);
    }

    private static void levelMenu(TextGraphics tg) throws IOException {
        drawLevelMenu(tg);

        terminal.flush();
        screen.refresh();
        boolean choosingLevel = true;
        while(choosingLevel){
            KeyStroke keyStroke = terminal.pollInput();
            if (keyStroke != null) {
                if (keyStroke.getKeyType() == KeyType.ArrowDown && menuPos < menuItems.length-1){
//                    menuPos++;
                    continue;
                }
                if (keyStroke.getKeyType() == KeyType.ArrowUp && menuPos > 0) {
//                    menuPos--;
                    continue;
                }
                if (keyStroke.getKeyType() == KeyType.Enter){
                    play = true;
                    switch(menuPos){
                        case 0:
                            if(play)
                                return;
                        case 1:
                            break;
                        case 2:
                            break;
                        case 3:
                            run = false;
                            play = false;
                            return;
                    }
                }
                if (keyStroke.getKeyType() == KeyType.Escape){
                    drawMainMenu(tg);
                    return;
                }

                terminal.flush();
                screen.refresh();
            }
        }
    }

    private static void highlightMenuItem(TextGraphics tg) {
        int itemNumber = 2 * menuPos;
        int menuPaddingTop = windowPaddingTop+12;
        int menuPaddingLeft = windowPaddingLeft+12;

        tg.setForegroundColor(TextColor.ANSI.BLUE);
        tg.putString(windowPaddingLeft+10,menuPaddingTop+itemNumber, Symbols.TRIANGLE_RIGHT_POINTING_BLACK+"", SGR.BLINK);
        tg.putString(menuPaddingLeft,menuPaddingTop+itemNumber, menuItems[menuPos], SGR.UNDERLINE );
        tg.putString(windowPaddingLeft+10+menuItems[menuPos].length() + 3,menuPaddingTop+itemNumber, Symbols.TRIANGLE_LEFT_POINTING_BLACK+"", SGR.BLINK);
        tg.setForegroundColor(TextColor.ANSI.DEFAULT);
    }

    private static void unhighlightMenuItem(TextGraphics tg){
        int itemNumber = 2 * menuPos;
        int menuPaddingTop = windowPaddingTop+12;
        int menuPaddingLeft = windowPaddingLeft+12;

        tg.setForegroundColor(TextColor.ANSI.YELLOW);
        tg.putString(windowPaddingLeft+10,menuPaddingTop+itemNumber, " ");
        tg.putString(menuPaddingLeft,menuPaddingTop+itemNumber, menuItems[menuPos]);
        tg.putString(windowPaddingLeft+10+menuItems[menuPos].length() + 3,menuPaddingTop+2*menuPos, " ");
    }

    protected static void drawMainMenu(TextGraphics tg) {
        int menuPaddingTop = windowPaddingTop+12;
        int menuPaddingLeft = windowPaddingLeft+12;
        tg.setBackgroundColor(TextColor.ANSI.WHITE);
        tg.setForegroundColor(TextColor.ANSI.YELLOW);
        tg.fill(' ');
        drawWindow(tg,0,0);
        drawLogo(tg);

        for(int i = 0; i< menuItems.length; i++){
            tg.putString(menuPaddingLeft,menuPaddingTop+2*i, menuItems[i] );
        }

        menuPos = 0;
        highlightMenuItem(tg);
    }

}
