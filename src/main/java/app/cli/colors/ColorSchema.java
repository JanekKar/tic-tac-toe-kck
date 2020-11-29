package app.cli.colors;

import com.googlecode.lanterna.TextColor;

public abstract class ColorSchema {
    public static TextColor[] logo = new TextColor[3];
    public static TextColor menuBackground;
    public static TextColor menuForground;
    public static TextColor menuHighlight;
    public static TextColor borders;


    public static TextColor gameBackground;
    public static TextColor gameBoard;
    public static TextColor xAndO;
    public static TextColor gameSidebarForeground;
    public static TextColor gameSidebarBackground;

    public static TextColor[] gameFiledHighlightOk;
    public static TextColor[] gameFiledHighlightWrong;
    public static TextColor[] highlightWinning;
    public static TextColor[] highlightLoosing;
}

