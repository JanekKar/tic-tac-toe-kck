package app.cli.colors;

import com.googlecode.lanterna.Symbols;
import com.googlecode.lanterna.TextColor;

public class MatrixColors implements ColorSchema{

    TextColor black = new TextColor.RGB(0,0,0);
    TextColor green = new TextColor.RGB(0, 255, 0);
    TextColor white = new TextColor.RGB(255, 255, 255);

    private static ColorSchema instance;

    public static ColorSchema getInstance() {
        if (instance == null) {
            instance = new MatrixColors();
        }
        return instance;
    }

    @Override
    public String getName() {
        return "matrix";
    }

    @Override
    public TextColor getMenuBackground() {
        return black;
    }

    @Override
    public TextColor getMenuForeground() {
        return green;
    }

    @Override
    public TextColor getMenuHighlight() {
        return white;
    }

    @Override
    public TextColor getGameBackground() {
        return black;
    }

    @Override
    public TextColor getGameBoard() {
        return white;
    }

    @Override
    public TextColor getXAndO() {
        return green;
    }

    @Override
    public TextColor getGameSidebarForeground() {
        return green;
    }

    @Override
    public TextColor getGameSidebarBackground() {
        return black;
    }

    @Override
    public TextColor getBorders() {
        return green;
    }

    @Override
    public TextColor[] getLogo() {
        return new TextColor[]{green, green, green};
    }

    @Override
    public TextColor[] getGameFiledHighlightOk() {
        return new TextColor[]{green, black};
    }

    @Override
    public TextColor[] getGameFiledHighlightWrong() {
        return new TextColor[]{white, black};
    }

    @Override
    public TextColor[] getHighlightWinning() {
        return new TextColor[]{green, white};
    }

    @Override
    public TextColor[] getHighlightLoosing() {
        return new TextColor[]{white, black};
    }

    @Override
    public char[] getLogoSymbols() {
        return new char[]{'N', 'E', 'O'};
    }
}
