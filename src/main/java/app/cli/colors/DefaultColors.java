package app.cli.colors;

import com.googlecode.lanterna.TextColor;

public class DefaultColors implements ColorSchema {

    private static ColorSchema instance;

    private final TextColor menuBackground = TextColor.ANSI.WHITE;
    private final TextColor menuForground = TextColor.ANSI.YELLOW;
    private final TextColor menuHighlight = TextColor.ANSI.BLUE;
    private final TextColor gameBackground = new TextColor.RGB(46, 52, 54);
    private final TextColor gameBoard = TextColor.ANSI.WHITE;
    private final TextColor xAndO = TextColor.ANSI.WHITE;

    private final TextColor gameSidebarForeground = TextColor.ANSI.WHITE;
    private final TextColor gameSidebarBackground = new TextColor.RGB(46, 52, 54);

    private final TextColor borders = TextColor.ANSI.YELLOW;

    private final TextColor[] logo = new TextColor[]{TextColor.ANSI.CYAN, TextColor.ANSI.GREEN, TextColor.ANSI.YELLOW};
    private final TextColor[] gameFiledHighlightOk = new TextColor[]{TextColor.ANSI.GREEN, TextColor.ANSI.BLACK};
    private final TextColor[] gameFiledHighlightWrong = new TextColor[]{TextColor.ANSI.RED, TextColor.ANSI.WHITE};
    private final TextColor[] highlightWinning = new TextColor[]{TextColor.ANSI.MAGENTA, TextColor.ANSI.CYAN};
    private final TextColor[] highlightLoosing = new TextColor[]{TextColor.ANSI.RED, TextColor.ANSI.YELLOW};

    private DefaultColors() {
    }

    public static ColorSchema getInstance() {
        if (instance == null) {
            instance = new DefaultColors();
        }
        return instance;
    }

    @Override
    public String getName() {
        return "default";
    }

    @Override
    public TextColor getMenuBackground() {
        return menuBackground;
    }

    @Override
    public TextColor getMenuForeground() {
        return menuForground;
    }

    @Override
    public TextColor getMenuHighlight() {
        return menuHighlight;
    }

    @Override
    public TextColor getGameBackground() {
        return gameBackground;
    }

    @Override
    public TextColor getGameBoard() {
        return gameBoard;
    }

    @Override
    public TextColor getXAndO() {
        return xAndO;
    }

    @Override
    public TextColor getGameSidebarForeground() {
        return gameSidebarForeground;
    }

    @Override
    public TextColor getGameSidebarBackground() {
        return gameSidebarBackground;
    }

    @Override
    public TextColor getBorders() {
        return borders;
    }

    @Override
    public TextColor[] getLogo() {
        return logo;
    }

    @Override
    public TextColor[] getGameFiledHighlightOk() {
        return gameFiledHighlightOk;
    }

    @Override
    public TextColor[] getGameFiledHighlightWrong() {
        return gameFiledHighlightWrong;
    }

    @Override
    public TextColor[] getHighlightWinning() {
        return highlightWinning;
    }

    @Override
    public TextColor[] getHighlightLoosing() {
        return highlightLoosing;
    }

    @Override
    public char[] getLogoSymbols() {
        return null;
    }
}