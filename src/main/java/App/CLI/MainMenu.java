package App.CLI;
import com.googlecode.lanterna.*;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;

import java.io.IOException;

import static App.CLI.CLI.*;

public class MainMenu {
    private static int menuPos = 0;
    private static String[] menuItems = {
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
                            return;
                        case 1:
                            break;
                        case 2:
                            break;
                        case 3:
                            run = false;
                            return;
                    }
                }

                highlightMenuItem(tg);

                terminal.flush();
                screen.refresh();
            }
        }
    }

    private static void highlightMenuItem(TextGraphics tg) {
        tg.setForegroundColor(TextColor.ANSI.BLUE);
        tg.putString(10,12+2*menuPos, Symbols.TRIANGLE_RIGHT_POINTING_BLACK+"", SGR.BLINK);
        tg.putString(12,12+2*menuPos, menuItems[menuPos], SGR.UNDERLINE );
        tg.putString(10+menuItems[menuPos].length() + 3,12+2*menuPos, Symbols.TRIANGLE_LEFT_POINTING_BLACK+"", SGR.BLINK);
        tg.setForegroundColor(TextColor.ANSI.DEFAULT);
    }

    private static void unhighlightMenuItem(TextGraphics tg){
        tg.setForegroundColor(TextColor.ANSI.YELLOW);
        tg.putString(10,12+2*menuPos, " ");
        tg.putString(12,12+2*menuPos, menuItems[menuPos]);
        tg.putString(10+menuItems[menuPos].length() + 3,12+2*menuPos, " ");
    }

    protected static void drawMainMenu(TextGraphics tg) {
        tg.setBackgroundColor(TextColor.ANSI.WHITE);
        tg.setForegroundColor(TextColor.ANSI.YELLOW);
        tg.fill(' ');
        drawBorder(tg, 0, 0,columns-1, rows-1);
        tg.fillRectangle(new TerminalPosition(windowPaddingLeft+ 1,windowPaddingTop+1),new TerminalSize(columns-2, rows-2), ' ');
        drawLogo(tg);

        for(int i = 0; i< menuItems.length; i++){
            tg.putString(12,12+2*i, menuItems[i] );
        }

        menuPos = 0;
        highlightMenuItem(tg);
    }

}
