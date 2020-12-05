package app.cli.menus;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.Symbols;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;

import java.io.IOException;

import static app.cli.Config.*;
import static app.cli.Utils.drawWindow;

public abstract class Submenu {
    private final String[] menuItems;
    private final String menuTitle;
    private final TextGraphics tg;
    private final int itemPaddingTop = 9;
    private final int itemPaddintLeft = 22;
    private int menuPosition;
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
        tg.drawLine(totalItemPaddingLeft - 5, totalItemPaddingTop - 2, windowPaddingLeft + columnHeight - 18, windowPaddingTop + 7, Symbols.SINGLE_LINE_HORIZONTAL);

        TextColor current = tg.getForegroundColor();
        tg.setForegroundColor(colorSchema.getLogo()[0]);
        tg.putString(totalItemPaddingLeft - 5, totalItemPaddingTop - 3, menuTitle);
        tg.setForegroundColor(current);
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
                if (controls.isDownKey(keyStroke) && menuPosition < menuItems.length - 1)
                    menuPosition++;
                else if (controls.isUpKey(keyStroke) && menuPosition > 0)
                    menuPosition--;
                else if (controls.isAssertKey(keyStroke)) {
                    onEnter(menuPosition);
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
        int itemNumber = 2 * menuPosition;
        int totalPaddingTop = this.itemPaddingTop + windowPaddingTop;
        int totalPaddingLeft = this.itemPaddintLeft + windowPaddingLeft;

        tg.setForegroundColor(colorSchema.getMenuHighlight());
        tg.putString(totalPaddingLeft - 2, totalPaddingTop + itemNumber, Symbols.TRIANGLE_RIGHT_POINTING_BLACK + "", SGR.BLINK);
        tg.putString(totalPaddingLeft, totalPaddingTop + itemNumber, menuItems[menuPosition], SGR.UNDERLINE);
        tg.putString(totalPaddingLeft - 2 + menuItems[menuPosition].length() + 3, totalPaddingTop + itemNumber, Symbols.TRIANGLE_LEFT_POINTING_BLACK + "", SGR.BLINK);
        tg.setForegroundColor(prevColor);
    }

    private void unhighlightMenuItem() {
        int itemNumber = 2 * menuPosition;
        int totalPaddingTop = this.itemPaddingTop + windowPaddingTop;
        int totalPaddingLeft = this.itemPaddintLeft + windowPaddingLeft;

        tg.setForegroundColor(colorSchema.getMenuForeground());
        tg.putString(totalPaddingLeft - 2, totalPaddingTop + itemNumber, " ");
        tg.putString(totalPaddingLeft, totalPaddingTop + itemNumber, menuItems[menuPosition]);
        tg.putString(totalPaddingLeft - 2 + menuItems[menuPosition].length() + 3, totalPaddingTop + 2 * menuPosition, " ");
    }

    abstract void onEnter(int menuPos) throws IOException;

    abstract void onClose();

    public void closeMenu() {
        onClose();
        show = false;
    }

}
