package app.cli;

import com.googlecode.lanterna.Symbols;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

import static app.cli.Config.*;


public class ACSILogo {
    private static int totalPaddingTop;
    private static int totalPaddingLeft;
    private static char sym = Symbols.BLOCK_SOLID;

    public static void drawLogo(TextGraphics tg, int leftPadding, int topPadding) {
        char[] symbols;

        if (colorSchema.getLogoSymbols() != null) {
            symbols = colorSchema.getLogoSymbols();
        } else {
            symbols = new char[]{Symbols.BLOCK_SOLID, Symbols.BLOCK_DENSE, Symbols.BLOCK_MIDDLE};
        }

        sym = symbols[0];
        TextColor prevColor = tg.getForegroundColor();

        totalPaddingLeft = windowPaddingLeft + leftPadding;
        totalPaddingTop = windowPaddingTop + topPadding;

        tg.setForegroundColor(colorSchema.getLogo()[0]);

        drawT(tg, 0);
        drawI(tg, 6);
        drawC(tg, 8);

        sym = symbols[1];
        tg.setForegroundColor(colorSchema.getLogo()[1]);
        drawT(tg, 17);
        drawA(tg, 23);
        drawC(tg, 29);

        sym = symbols[2];
        tg.setForegroundColor(colorSchema.getLogo()[2]);

        drawT(tg, 37);
        drawO(tg, 43);
        drawE(tg, 49);

        tg.setForegroundColor(prevColor);
        sym = Symbols.BLOCK_SOLID;
    }

    public static void drawTie(TextGraphics tg, int xPos, int yPos) {
        sym = Symbols.BLOCK_SOLID;
        totalPaddingLeft = windowPaddingLeft + xPos;
        totalPaddingTop = windowPaddingTop + yPos;
        tg.setForegroundColor(colorSchema.getLogo()[0]);
        drawT(tg, 0);
        drawI(tg, 6);
        drawE(tg, 8);
    }

    private static void drawA(TextGraphics tg, int xPos) {
        //A
        tg.drawLine(totalPaddingLeft + xPos, totalPaddingTop, totalPaddingLeft + xPos + 4, totalPaddingTop, sym);
        tg.drawLine(totalPaddingLeft + xPos, totalPaddingTop + 2, totalPaddingLeft + xPos + 4, totalPaddingTop + 2, sym);
        tg.drawLine(totalPaddingLeft + xPos, totalPaddingTop, totalPaddingLeft + xPos, totalPaddingTop + 3, sym);
        tg.drawLine(totalPaddingLeft + xPos + 4, totalPaddingTop, totalPaddingLeft + xPos + 4, totalPaddingTop + 3, sym);
    }

    private static void drawC(TextGraphics tg, int xPos) {
        //C
        tg.drawLine(totalPaddingLeft + xPos, totalPaddingTop, totalPaddingLeft + xPos, totalPaddingTop + 3, sym);
        tg.drawLine(totalPaddingLeft + xPos, totalPaddingTop, totalPaddingLeft + xPos + 3, totalPaddingTop, sym);
        tg.drawLine(totalPaddingLeft + xPos, totalPaddingTop + 3, totalPaddingLeft + xPos + 3, totalPaddingTop + 3, sym);
    }

    private static void drawE(TextGraphics tg, int xPos) {
        //E
        tg.drawLine(totalPaddingLeft + xPos, totalPaddingTop, totalPaddingLeft + xPos + 4, totalPaddingTop, sym);
        tg.drawLine(totalPaddingLeft + xPos, totalPaddingTop + 1, totalPaddingLeft + xPos + 4, totalPaddingTop + 1, sym);
        tg.drawLine(totalPaddingLeft + xPos, totalPaddingTop + 3, totalPaddingLeft + xPos + 4, totalPaddingTop + 3, sym);
        tg.drawLine(totalPaddingLeft + xPos, totalPaddingTop, totalPaddingLeft + xPos, totalPaddingTop + 3, sym);
    }

    private static void drawI(TextGraphics tg, int xPos) {
        //I
        tg.drawLine(totalPaddingLeft + xPos, totalPaddingTop, totalPaddingLeft + xPos, totalPaddingTop + 3, sym);
    }

    private static void drawO(TextGraphics tg, int xPos) {
        //O
        tg.drawLine(totalPaddingLeft + xPos, totalPaddingTop, totalPaddingLeft + xPos + 4, totalPaddingTop, sym);
        tg.drawLine(totalPaddingLeft + xPos, totalPaddingTop + 3, totalPaddingLeft + xPos + 4, totalPaddingTop + 3, sym);
        tg.drawLine(totalPaddingLeft + xPos, totalPaddingTop, totalPaddingLeft + xPos, totalPaddingTop + 3, sym);
        tg.drawLine(totalPaddingLeft + xPos + 4, totalPaddingTop, totalPaddingLeft + xPos + 4, totalPaddingTop + 3, sym);
    }

    private static void drawT(TextGraphics tg, int xPos) {
        //T
        tg.drawLine(totalPaddingLeft + xPos, totalPaddingTop, totalPaddingLeft + xPos + 4, totalPaddingTop, sym);
        tg.drawLine(totalPaddingLeft + xPos + 2, totalPaddingTop, totalPaddingLeft + xPos + 2, totalPaddingTop + 3, sym);
    }
}
