package app.cli;

import app.cli.colors.ColorSchema;
import app.cli.colors.DefaultColors;
import app.cli.controls.Controls;
import app.cli.controls.DefaultControls;
import com.googlecode.lanterna.Symbols;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

import java.awt.*;

import static app.Main.game;

public class Utils {
    static int rowHeight;
    static int columnWidth;
    static int paddingLeft;
    static int paddingTop = 2;
    static int fieldOffset = 3;
    static int sidebar = 12;
    static int sidebarPaddingTop;
    static int paddingLeftSidebar = 6;
    static int windowPaddingTop = 0;
    static int windowPaddingLeft = 0;
    static int rows;
    static int columns;
    static int prevRows;
    static int prevCols;
    static int xWidth = 11;
    static int xHeight = 5;

    public static ColorSchema colorSchema = DefaultColors.getInstance();
    public static Controls controls = DefaultControls.getInstance();

    public static void drawBorder(TextGraphics tg, int startX, int startY, int endX, int endY) {
        TextColor prevColor = tg.getForegroundColor();
        startX += windowPaddingLeft;
        endX += windowPaddingLeft;
        startY += windowPaddingTop;
        endY += windowPaddingTop;
        tg.setForegroundColor(colorSchema.borders);

        tg.drawLine(new TerminalPosition(startX, startY), new TerminalPosition(endX, startY), Symbols.DOUBLE_LINE_HORIZONTAL);
        tg.drawLine(new TerminalPosition(startX, endY), new TerminalPosition(endX, endY), Symbols.DOUBLE_LINE_HORIZONTAL);

        tg.drawLine(new TerminalPosition(startX, startY), new TerminalPosition(startX, endY), Symbols.DOUBLE_LINE_VERTICAL);
        tg.drawLine(new TerminalPosition(endX, startY), new TerminalPosition(endX, endY), Symbols.DOUBLE_LINE_VERTICAL);

        tg.setCharacter(startX, startY, Symbols.DOUBLE_LINE_TOP_LEFT_CORNER);
        tg.setCharacter(startX, endY, Symbols.DOUBLE_LINE_BOTTOM_LEFT_CORNER);
        tg.setCharacter(endX, startY, Symbols.DOUBLE_LINE_TOP_RIGHT_CORNER);
        tg.setCharacter(endX, endY, Symbols.DOUBLE_LINE_BOTTOM_RIGHT_CORNER);

        tg.setForegroundColor(prevColor);
    }

    public static void drawWindow(TextGraphics tg, int startX, int startY, int endX, int endY ){
        drawBorder(tg, startX, startY, endX, endY);
        TextColor prevColor = tg.getBackgroundColor();
        tg.setBackgroundColor(colorSchema.menuBackground);
        tg.fillRectangle(
                new TerminalPosition(windowPaddingLeft+startX+1, windowPaddingTop+startY+1),
                new TerminalSize(endX-startX-1, endY-startY-1),
                ' ');

        tg.setBackgroundColor(prevColor);
    }

    public static void  drawWindow(TextGraphics tg, int marginSide, int marginTop){
        drawWindow(tg, marginSide, marginTop, columns-marginSide-1, rows-marginTop-1);
    }

    public static void drawX(TextGraphics tg, int x, int y) {
        int posX = x + windowPaddingLeft;
        int posY = y + windowPaddingTop;
        tg.drawLine(
                new TerminalPosition(posX, posY),
                new TerminalPosition(posX + xWidth, posY + xHeight),
                'O');

        tg.drawLine(
                new TerminalPosition(posX + xWidth, posY),
                new TerminalPosition(posX, posY + xHeight),
                'O');
    }

    public static void drawXOnBoard(TextGraphics tg, int x, int y) {
        int posX = paddingLeft + fieldOffset + (x * columnWidth);
        int posY = paddingTop + (rowHeight * y);
        drawX(tg, posX, posY);
    }

    public static void drawO(TextGraphics tg, int x, int y) {
        int posX = x + windowPaddingLeft;
        int posY = y + windowPaddingTop;

        // Upper horizontal lines
        tg.drawLine(
                new TerminalPosition(posX + 1, posY + 1),
                new TerminalPosition(posX + 3, posY + 1),
                'X');
        tg.drawLine(
                new TerminalPosition(posX + 4, posY),
                new TerminalPosition(posX + 7, posY),
                'X');
        tg.drawLine(
                new TerminalPosition(posX + 8, posY + 1),
                new TerminalPosition(posX + 10, posY + 1),
                'X');

        // Vertical lines
        tg.drawLine(
                new TerminalPosition(posX, posY + 2),
                new TerminalPosition(posX, posY + 3),
                'X');
        tg.drawLine(
                new TerminalPosition(posX + 11, posY + 2),
                new TerminalPosition(posX + 11, posY + 3),
                'X');

        // Lower horizontal lines
        posY += xHeight;
        tg.drawLine(
                new TerminalPosition(posX + 1, posY - 1),
                new TerminalPosition(posX + 3, posY - 1),
                'X');
        tg.drawLine(
                new TerminalPosition(posX + 4, posY),
                new TerminalPosition(posX + 7, posY),
                'X');
        tg.drawLine(
                new TerminalPosition(posX + 8, posY - 1),
                new TerminalPosition(posX + 10, posY - 1),
                'X');
    }

    public static void drawOOnBoard(TextGraphics tg, int x, int y) {
        int posX = paddingLeft + fieldOffset + (x * columnWidth);
        int posY = paddingTop + (y * rowHeight);

        drawO(tg, posX, posY);

    }

    public static void highlightField(TextGraphics tg, int x, int y, TextColor backgroundColor, TextColor foregroundColor) {
        tg.setBackgroundColor(backgroundColor);
        tg.setForegroundColor(foregroundColor);

        tg.fillRectangle(
                new TerminalPosition(windowPaddingLeft + paddingLeft + (x * columnWidth), windowPaddingTop + paddingTop + (y * rowHeight)),
                new TerminalSize(columnWidth - 1, rowHeight - 1),
                ' ');

        drawXorO(tg, x, y);
    }

    public static void unHighlightField(TextGraphics tg, int x, int y) {
        highlightField(tg, x, y, colorSchema.gameBackground, colorSchema.xAndO);
    }

    protected static void drawXorO(TextGraphics tg, int x, int y) {
//        tg.setBackgroundColor(colorSchema.gameBackground);
//        tg.setForegroundColor(colorSchema.xAndO);

        switch (game.getFieldContent(new Point(x, y))) {
            case "X":
                drawXOnBoard(tg, x, y);
                break;
            case "O":
                drawOOnBoard(tg, x, y);
                break;
            default:
        }
    }

    public static void setDimensions(){
        rows = 24;
        columns = 80;

        prevRows = rows;
        prevCols = columns;

        rowHeight = (rows - 1) / 3;
        columnWidth = ((columns - sidebar - 6) / 3) - 1;
        paddingLeft = sidebar + paddingLeftSidebar;
        sidebarPaddingTop = (rows - 20) / 2;
    }

    public static void setColorSchema(ColorSchema colors){
        colorSchema = colors;
    }
}


