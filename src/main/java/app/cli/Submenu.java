package app.cli;

import com.googlecode.lanterna.*;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;

import java.io.IOException;

import static app.cli.CLI.*;
import static app.cli.CLI.screen;
import static app.cli.Utils.*;
import static app.cli.Utils.windowPaddingTop;

public abstract class Submenu {
    private String[] menuItems;
    private String menuTitle;
    private TextGraphics tg;
    private int menuPos;

    private int itemPaddingTop = 9;
    private int itemPaddintLeft = 22;

    private boolean show = true;


    public Submenu(TextGraphics tg, String[] items, String title){
        this.menuItems = items;
        this.menuTitle = title;
        this.tg = tg;
    }

    public void drawSubMenu(){
        int totalItemPaddingTop = itemPaddingTop + windowPaddingTop;
        int totalItemPaddingLeft = itemPaddintLeft + windowPaddingLeft;
        tg.setBackgroundColor(colorSchema.menuBackground);
        tg.setForegroundColor(colorSchema.menuForground);
        drawWindow(tg, 15, 4);
        tg.putString(totalItemPaddingLeft -5, totalItemPaddingTop-3, menuTitle);
        tg.drawLine(totalItemPaddingLeft -5, totalItemPaddingTop-2, windowPaddingLeft + columns - 18, windowPaddingTop + 7, Symbols.SINGLE_LINE_HORIZONTAL);

        for(int i=0; i<menuItems.length; i++){
            tg.putString(totalItemPaddingLeft, totalItemPaddingTop +2*i, menuItems[i]);
        }

    }

    public void showMenu() throws IOException {
        drawSubMenu();
        highlightMenuItem();

        terminal.flush();
        screen.refresh();

        while(show){
            KeyStroke keyStroke = terminal.pollInput();
            if (keyStroke != null) {
                unhighlightMenuItem();
                switch(keyStroke.getKeyType()) {
                    case ArrowDown:
                        if (menuPos < menuItems.length - 1)
                            menuPos++;
                        break;
                    case ArrowUp:
                        if (menuPos > 0)
                            menuPos--;
                        break;
                    case Enter:
                        onEnter(menuPos);
                        drawSubMenu();
                        break;
                    case Escape:
                        closeMenu();
                        break;
                }
                highlightMenuItem();
                terminal.flush();
                screen.refresh();

            }
        }
    }

    private void highlightMenuItem() {
        TextColor prevColor = tg.getForegroundColor();
        int itemNumber = 2 * menuPos;
        int totalPaddingTop = this.itemPaddingTop + windowPaddingTop;
        int totalPaddingLeft = this.itemPaddintLeft + windowPaddingLeft;

        tg.setForegroundColor(colorSchema.menuHighlight);
        tg.putString(totalPaddingLeft-2,totalPaddingTop+itemNumber, Symbols.TRIANGLE_RIGHT_POINTING_BLACK+"", SGR.BLINK);
        tg.putString(totalPaddingLeft,totalPaddingTop+itemNumber, menuItems[menuPos], SGR.UNDERLINE );
        tg.putString(totalPaddingLeft-2+menuItems[menuPos].length() + 3,totalPaddingTop+itemNumber, Symbols.TRIANGLE_LEFT_POINTING_BLACK+"", SGR.BLINK);
        tg.setForegroundColor(prevColor);
    }

    private void unhighlightMenuItem(){
        int itemNumber = 2 * menuPos;
        int totalPaddingTop = this.itemPaddingTop + windowPaddingTop;
        int totalPaddingLeft = this.itemPaddintLeft + windowPaddingLeft;

        tg.setForegroundColor(colorSchema.menuForground);
        tg.putString(totalPaddingLeft-2,totalPaddingTop+itemNumber, " ");
        tg.putString(totalPaddingLeft,totalPaddingTop+itemNumber, menuItems[menuPos]);
        tg.putString(totalPaddingLeft-2+menuItems[menuPos].length() + 3,totalPaddingTop+2*menuPos, " ");
    }

    abstract void onEnter(int menuPos) throws IOException;

    public void closeMenu(){
        show = false;
    }

}
