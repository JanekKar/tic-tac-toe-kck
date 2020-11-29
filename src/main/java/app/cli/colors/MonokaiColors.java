package app.cli.colors;

import com.googlecode.lanterna.TextColor;

public class MonokaiColors extends ColorSchema{
    private TextColor blue = new TextColor.RGB(120, 221, 232);
    private TextColor green = new TextColor.RGB(169, 220, 118);
    private TextColor pink = new TextColor.RGB(255, 97, 137);
    private TextColor yellow = new TextColor.RGB(255, 217, 102);
    private TextColor orange = new TextColor.RGB(252, 152, 103);
    private TextColor violet = new TextColor.RGB(171, 157, 242);
    private TextColor gray = new TextColor.RGB(79, 75, 61);

    private static ColorSchema instance;

    public static ColorSchema getInstance(){
        if (instance == null){
            instance = new MonokaiColors();
        }
        return  instance;
    }

    private  MonokaiColors(){
        this.logo = new TextColor[]{blue, green, orange};
        this.menuForground = orange;
        this.menuBackground = gray;
        this.menuHighlight = pink;

        this.borders = yellow;

        this.gameBackground = gray;
        this.gameBoard = TextColor.ANSI.WHITE;
        this.xAndO = TextColor.ANSI.WHITE;

        this.gameSidebarForeground = TextColor.ANSI.WHITE;
        this.gameSidebarBackground = gray;

        this.gameFiledHighlightOk = new TextColor[]{green, gray};
        this.gameFiledHighlightWrong = new TextColor[]{pink, TextColor.ANSI.WHITE};
        this.highlightWinning = new TextColor[]{violet, blue};
        this.highlightLoosing = new TextColor[]{TextColor.ANSI.RED, yellow};

    }
}
