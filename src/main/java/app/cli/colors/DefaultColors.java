package app.cli.colors;

import com.googlecode.lanterna.TextColor;
import org.w3c.dom.Text;

public class DefaultColors extends ColorSchema {

    private static ColorSchema  instance;

    public static ColorSchema getInstance(){
        if (instance == null){
            instance = new DefaultColors();
        }
        return instance;
    }

    private DefaultColors() {
        this.menuBackground = TextColor.ANSI.WHITE;
        this.menuForground = TextColor.ANSI.YELLOW;
        this.menuHighlight = TextColor.ANSI.BLUE;
        this.gameBackground = TextColor.ANSI.BLACK;
        this.gameBoard = TextColor.ANSI.WHITE;
        this.xAndO = TextColor.ANSI.WHITE;

        this.gameSidebarForeground = TextColor.ANSI.WHITE;
        this.gameSidebarBackground = TextColor.ANSI.BLACK;

        this.borders = TextColor.ANSI.YELLOW;

        this.logo = new TextColor[]{TextColor.ANSI.CYAN, TextColor.ANSI.GREEN, TextColor.ANSI.YELLOW};
        this.gameFiledHighlightOk = new TextColor[]{TextColor.ANSI.GREEN, TextColor.ANSI.BLACK};
        this.gameFiledHighlightWrong = new TextColor[]{TextColor.ANSI.RED, TextColor.ANSI.WHITE};
        this.highlightWinning = new TextColor[]{TextColor.ANSI.MAGENTA, TextColor.ANSI.CYAN};
        this.highlightLoosing = new TextColor[]{TextColor.ANSI.RED, TextColor.ANSI.YELLOW};
    }
}