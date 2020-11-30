package app.cli;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.Symbols;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;

import java.io.IOException;

import static app.cli.Game.screen;
import static app.cli.Game.terminal;
import static app.cli.Utils.*;

public abstract class Submenu {
    private final String[] menuItems;
    private final String menuTitle;
    private final TextGraphics tg;
    private int menuPos;

    private final int itemPaddingTop = 9;
    private final int itemPaddintLeft = 22;

    private boolean show = true;


    public Submenu(TextGraphics tg, String[] items, String title) {
        this.menuItems = items;
        this.menuTitle = title;
        this.tg = tg;
    }

    public void drawSubMenu() {
        int totalItemPaddingTop = itemPaddingTop + windowPaddingTop;
        int totalItemPaddingLeft = itemPaddintLeft + windowPaddingLeft;
        tg.setBackgroundColor(colorSchema.getMenuBackground());
        tg.setForegroundColor(colorSchema.getMenuForeground());
        drawWindow(tg, 15, 4);
        tg.drawLine(totalItemPaddingLeft - 5, totalItemPaddingTop - 2, windowPaddingLeft + columns - 18, windowPaddingTop + 7, Symbols.SINGLE_LINE_HORIZONTAL);

        tg.putString(totalItemPaddingLeft - 5, totalItemPaddingTop - 3, menuTitle);
        for (int i = 0; i < menuItems.length; i++) {
            tg.putString(totalItemPaddingLeft, totalItemPaddingTop + 2 * i, menuItems[i]);
        }


    }

    public void showMenu() throws IOException {
        drawSubMenu();
        highlightMenuItem();

        terminal.flush();
        screen.refresh();

        while (show) {
            KeyStroke keyStroke = terminal.pollInput();
            if (keyStroke != null) {
                unhighlightMenuItem();
                if (controls.isDownKey(keyStroke) && menuPos < menuItems.length - 1)
                    menuPos++;
                else if (controls.isUpKey(keyStroke) && menuPos > 0)
                    menuPos--;
                else if (controls.isAssertKey(keyStroke)) {
                    onEnter(menuPos);
                    drawSubMenu();
                } else if (controls.isEscapeKey(keyStroke))
                    closeMenu();

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

        tg.setForegroundColor(colorSchema.getMenuHighlight());
        tg.putString(totalPaddingLeft - 2, totalPaddingTop + itemNumber, Symbols.TRIANGLE_RIGHT_POINTING_BLACK + "", SGR.BLINK);
        tg.putString(totalPaddingLeft, totalPaddingTop + itemNumber, menuItems[menuPos], SGR.UNDERLINE);
        tg.putString(totalPaddingLeft - 2 + menuItems[menuPos].length() + 3, totalPaddingTop + itemNumber, Symbols.TRIANGLE_LEFT_POINTING_BLACK + "", SGR.BLINK);
        tg.setForegroundColor(prevColor);
    }

    private void unhighlightMenuItem() {
        int itemNumber = 2 * menuPos;
        int totalPaddingTop = this.itemPaddingTop + windowPaddingTop;
        int totalPaddingLeft = this.itemPaddintLeft + windowPaddingLeft;

        tg.setForegroundColor(colorSchema.getMenuForeground());
        tg.putString(totalPaddingLeft - 2, totalPaddingTop + itemNumber, " ");
        tg.putString(totalPaddingLeft, totalPaddingTop + itemNumber, menuItems[menuPos]);
        tg.putString(totalPaddingLeft - 2 + menuItems[menuPos].length() + 3, totalPaddingTop + 2 * menuPos, " ");
    }

    abstract void onEnter(int menuPos) throws IOException;

    public void closeMenu() {
        show = false;
    }

}
