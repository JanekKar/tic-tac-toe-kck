package app.cli.colors;

import com.googlecode.lanterna.TextColor;

public class OceanColors implements ColorSchema {
    private static final TextColor blue = new TextColor.RGB(37, 106, 217);
    private static final TextColor green = new TextColor.RGB(37, 217, 130);
    private static final TextColor pink = new TextColor.RGB(245, 95, 165);
    private static final TextColor yellow = new TextColor.RGB(255, 186, 3);
    private static final TextColor orange = new TextColor.RGB(252, 119, 3);
    private static final TextColor violet = new TextColor.RGB(171, 88, 219);
    private static final TextColor gray = new TextColor.RGB(126, 130, 156);
    private static final TextColor white = new TextColor.RGB(237, 237, 218);
    private static final TextColor red = new TextColor.RGB(240, 28, 17);
    private static ColorSchema instance;
    private final TextColor[] logo = new TextColor[]{blue, green, orange};
    private final TextColor menuForground = orange;
    private final TextColor menuBackground = gray;
    private final TextColor menuHighlight = pink;
    private final TextColor borders = yellow;
    private final TextColor gameBackground = gray;
    private final TextColor gameBoard = white;
    private final TextColor xAndO = white;
    private final TextColor gameSidebarForeground = white;
    private final TextColor gameSidebarBackground = gray;
    private final TextColor[] gameFiledHighlightOk = new TextColor[]{green, gray};
    private final TextColor[] gameFiledHighlightWrong = new TextColor[]{pink, white};
    private final TextColor[] highlightWinning = new TextColor[]{violet, blue};
    private final TextColor[] highlightLoosing = new TextColor[]{red, yellow};

    private OceanColors() {
    }

    public static ColorSchema getInstance() {
        if (instance == null) {
            instance = new OceanColors();
        }
        return instance;
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
