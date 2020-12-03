package app.cli.menus;

import app.cli.Config;
import app.cli.colors.DefaultColors;
import app.cli.colors.MatrixColors;
import app.cli.colors.MonokaiColors;
import app.cli.colors.OceanColors;
import app.cli.controls.DefaultControls;
import app.cli.controls.IJKLControls;
import app.cli.controls.VimControls;
import app.cli.controls.WASDControls;
import app.cli.menus.Submenu;
import app.ticTacToe.Player;
import app.ticTacToe.PlayerScores;
import app.ticTacToe.logic.EasyLogic;
import app.ticTacToe.logic.HardLogic;
import app.ticTacToe.logic.ImpossibleLogic;
import app.ticTacToe.logic.MediumLogic;
import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.Symbols;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;

import java.io.IOException;
import java.util.List;

import static app.Main.submenus;
import static app.cli.Config.colorSchema;
import static app.cli.Config.controls;
import static app.cli.Game.*;
import static app.cli.menus.NickMenu.drawNickMenu;
import static app.cli.Utils.*;


public class Submenus {

    public static boolean  levelMenuOpen = false;
    public static boolean settingsMenuOpen = false;
    public static boolean controlsMenuOpen = false;
    public static boolean colorsMenuOpen = false;
    public static boolean bestScoreMenuOpen = false;
    private final String[] levelMenuItems = {
            "EASY",
            "MEDIUM",
            "HARD",
            "IMPOSSIBLE"
    };
    private final String[] setteingsCategories = {
            "Color schema",
            "Controls"
    };
    private final String[] colorsSchemas = {
            "Default", "Monokai", "Ocean", "Matrix"
    };
    private final String[] controlsMenuList = {
            "Arrows",
            "WASD",
            "IJKL",
            "Vim"
    };
//
//    private Submenu levelMenu;
//    private Submenu settingsMenu;
//    private Submenu colorsMenu;
//    private Submenu controlsMenu;

    private TextGraphics tg;
    public Submenus(TextGraphics tg) {
        this.tg = tg;
    }

    public void getScoreInfoMenu(TextGraphics tg, boolean noInput) throws IOException {
        bestScoreMenuOpen = true;
        int totalPaddingLeft = windowPaddingLeft + 19;
        int totalPaddingTop = windowPaddingTop;
        drawWindow(tg, 15, 3);
        tg.drawLine(totalPaddingLeft-2,totalPaddingTop + 6,windowPaddingLeft+columns-18, totalPaddingTop + 6, Symbols.SINGLE_LINE_HORIZONTAL);
        tg.drawLine(totalPaddingLeft,totalPaddingTop + 8,windowPaddingLeft+columns-19, totalPaddingTop + 8, Symbols.SINGLE_LINE_HORIZONTAL);
        TextColor current = tg.getForegroundColor();
        tg.setForegroundColor(colorSchema.getLogo()[0]);
        tg.putString( totalPaddingLeft-1, totalPaddingTop + 5,"BEST Players", SGR.BOLD);
        tg.setForegroundColor(current);
        tg.putString( totalPaddingLeft, totalPaddingTop + 7,"Name");
        List<Player> bestPlayers = PlayerScores.getInstance().getPlayersList();
        int maxLength = 0;

        totalPaddingTop += 10;

        for(int i=0; i<bestPlayers.size(); i++){
            tg.putString(totalPaddingLeft, totalPaddingTop+i*2, bestPlayers.get(i).getName());
            if(bestPlayers.get(i).getName().length() > maxLength){
                maxLength = bestPlayers.get(i).getName().length();
            }
        }

        totalPaddingLeft += maxLength + 2;

        tg.putString( totalPaddingLeft, totalPaddingTop -3,"Score");
        for (int i = 0; i < bestPlayers.size(); i++) {
            tg.putString(totalPaddingLeft, totalPaddingTop+i*2, bestPlayers.get(i).getScore()+"");
        }

        totalPaddingLeft += 12;

        tg.putString( totalPaddingLeft, totalPaddingTop -3,"WON");
        for (int i = 0; i < bestPlayers.size(); i++) {
            tg.putString(totalPaddingLeft, totalPaddingTop+i*2, bestPlayers.get(i).getNumberOfWonGames()+"");
        }
        totalPaddingLeft += 5;
        tg.putString( totalPaddingLeft, totalPaddingTop -3,"LOST");
        for (int i = 0; i < bestPlayers.size(); i++) {
            tg.putString(totalPaddingLeft, totalPaddingTop+i*2, bestPlayers.get(i).getNumberOfLostGames()+"");
        }

        totalPaddingLeft += 5;
        tg.putString( totalPaddingLeft+5, totalPaddingTop -3,"TIE");
        for (int i = 0; i < bestPlayers.size(); i++) {
            tg.putString(totalPaddingLeft+5, totalPaddingTop+i*2, bestPlayers.get(i).getNumberOfTies()+"");
        }

        Config.terminal.flush();
        Config.screen.refresh();
        while(!noInput){
            KeyStroke keyStroke = Config.terminal.pollInput();
            if(keyStroke!=null){
                if(controls.isEscapeKey(keyStroke));
                break;
            }
        }
        bestScoreMenuOpen = false;
    }

    public Submenu getLevelMenu() {
        levelMenuOpen = true;
        return new Submenu(tg, levelMenuItems, "Choose difficulty level:") {
            @Override
            void onClose() {
                levelMenuOpen = false;
            }

            @Override
            void onEnter(int menuPos) throws IOException {
                switch (menuPos) {
                    case 0:
                        logic = new EasyLogic();
                        break;
                    case 1:
                        logic = new MediumLogic();
                        break;
                    case 2:
                        logic = new HardLogic();
                        break;
                    case 3:
                        logic = new ImpossibleLogic();
                        break;
                }
                play = true;
                drawNickMenu(tg);
                closeMenu();
            }
        };
    }

    public Submenu getSettingsMenu() {
        settingsMenuOpen = true;
        return new Submenu(tg, setteingsCategories, "Settings") {
            @Override
            void onClose() {
                settingsMenuOpen = false;
            }

            @Override
            void onEnter(int menuPos) throws IOException {
                switch (menuPos) {
                    case 0:
                        submenus.getColorsMenu().showMenu();
                        break;
                    case 1:

                        submenus.getControlsMenu().showMenu();
                        break;
                }
            }
        };
    }

    public Submenu getColorsMenu() {
        colorsMenuOpen = true;
        return new Submenu(tg, colorsSchemas, "Choose color schema") {
            @Override
            void onClose() {
                colorsMenuOpen = false;
            }

            @Override
            void onEnter(int menuPos) {
                switch (menuPos) {
                    case 0:
                        colorSchema = DefaultColors.getInstance();
                        break;
                    case 1:
                        colorSchema = MonokaiColors.getInstance();
                        break;
                    case 2:
                        colorSchema = OceanColors.getInstance();
                        break;
                    case 3:
                        colorSchema = MatrixColors.getInstance();
                        break;
                }
                closeMenu();
            }
        };
    }

    public Submenu getControlsMenu() {
        controlsMenuOpen = true;
        return new Submenu(tg, controlsMenuList, "Choose controls:") {
            @Override
            void onClose() {
                controlsMenuOpen = false;
            }

            @Override
            void onEnter(int menuPos) {
                switch (menuPos) {
                    case 0:
                        controls = DefaultControls.getInstance();
                        break;
                    case 1:
                        controls = WASDControls.getInstance();
                        break;
                    case 2:
                        controls = IJKLControls.getInstance();
                        break;
                    case 3:
                        controls = VimControls.getInstance();
                        break;
                }
                closeMenu();
            }
        };
    }
}
