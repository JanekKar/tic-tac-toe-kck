package app.cli.colors;

import com.googlecode.lanterna.TextColor;

public interface ColorSchema {

    String getName();

    TextColor getRed();

    TextColor getGreen();

    TextColor getMenuBackground();

    TextColor getMenuForeground();

    TextColor getMenuHighlight();

    TextColor getGameBackground();

    TextColor getGameBoard();

    TextColor getXAndO();

    TextColor getGameSidebarForeground();

    TextColor getGameSidebarBackground();

    TextColor getBorders();

    TextColor[] getLogo();

    TextColor[] getGameFiledHighlightOk();

    TextColor[] getGameFiledHighlightWrong();

    TextColor[] getHighlightWinning();

    TextColor[] getHighlightLoosing();

    char[] getLogoSymbols();

}

