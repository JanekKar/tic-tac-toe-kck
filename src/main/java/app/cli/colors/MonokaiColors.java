package app.cli.colors;

import com.googlecode.lanterna.TextColor;

public class MonokaiColors extends ColorSchema{
    public MonokaiColors(){
        this.menuBackground = new TextColor.RGB(51,54,43);
        this.menuForground = TextColor.ANSI.WHITE;
        this.menuHighlight = TextColor.ANSI.MAGENTA;
        this.gameBackground = TextColor.ANSI.BLACK;
        this.gameBoard = TextColor.ANSI.WHITE;
        this.xAndO = TextColor.ANSI.WHITE;
        this.gameSidebarForeground = TextColor.ANSI.BLACK;
        this.gameSidebarBackground = TextColor.ANSI.WHITE;
        this.borders = TextColor.ANSI.YELLOW;

        this.logo = new TextColor[]{TextColor.ANSI.CYAN, TextColor.ANSI.GREEN, TextColor.ANSI.YELLOW};
       // this.gameFiledHighlight = new TextColor[]{TextColor.ANSI.GREEN, TextColor.ANSI.BLACK, TextColor.ANSI.RED, TextColor.ANSI.WHITE};
        this.highlightWinning = new TextColor[]{TextColor.ANSI.MAGENTA, TextColor.ANSI.CYAN};
        this.highlightLoosing = new TextColor[]{TextColor.ANSI.RED, TextColor.ANSI.YELLOW};

    }
}
