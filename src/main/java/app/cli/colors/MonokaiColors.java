package app.cli.colors;

import com.googlecode.lanterna.TextColor;

public class MonokaiColors implements ColorSchema {
    private static final TextColor blue = new TextColor.RGB(120, 221, 232);
    private static final TextColor green = new TextColor.RGB(169, 220, 118);
    private static final TextColor pink = new TextColor.RGB(255, 97, 137);
    private static final TextColor yellow = new TextColor.RGB(255, 217, 102);
    private static final TextColor orange = new TextColor.RGB(252, 152, 103);
    private static final TextColor violet = new TextColor.RGB(171, 157, 242);
    private static final TextColor gray = new TextColor.RGB(79, 75, 61);

    private static ColorSchema instance;

    private final TextColor[] logo = new TextColor[]{blue, green, orange};
    private final TextColor menuForground = orange;
    private final TextColor menuBackground = gray;
    private final TextColor menuHighlight = pink;
    private final TextColor borders = yellow;
    private final TextColor gameBackground = gray;
    private final TextColor gameBoard = TextColor.ANSI.WHITE;
    private final TextColor xAndO = TextColor.ANSI.WHITE;
    private final TextColor gameSidebarForeground = TextColor.ANSI.WHITE;
    private final TextColor gameSidebarBackground = gray;
    private final TextColor[] gameFiledHighlightOk = new TextColor[]{green, gray};
    private final TextColor[] gameFiledHighlightWrong = new TextColor[]{pink, TextColor.ANSI.WHITE};
    private final TextColor[] highlightWinning = new TextColor[]{violet, blue};
    private final TextColor[] highlightLoosing = new TextColor[]{TextColor.ANSI.RED, yellow};


    public static ColorSchema getInstance() {
        if (instance == null) {
            instance = new MonokaiColors();
        }
        return instance;
    }

    @Override
    public String getName() {
        return "monokai";
    }

    @Override
    public TextColor[] getLogo() {
        return logo;
    }

    @Override
    public TextColor getMenuForeground() {
        return menuForground;
    }

    @Override
    public TextColor getMenuBackground() {
        return menuBackground;
    }

    @Override
    public TextColor getMenuHighlight() {
        return menuHighlight;
    }

    @Override
    public TextColor getBorders() {
        return borders;
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
}
