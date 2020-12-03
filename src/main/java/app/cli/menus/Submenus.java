package app.cli.menus;

import app.Main;
import app.cli.Config;
import app.cli.colors.DefaultColors;
import app.cli.colors.MatrixColors;
import app.cli.colors.MonokaiColors;
import app.cli.colors.OceanColors;
import app.cli.controls.DefaultControls;
import app.cli.controls.IJKLControls;
import app.cli.controls.VimControls;
import app.cli.controls.WASDControls;
import app.ticTacToe.BestScoreManager;
import app.ticTacToe.Player;
import app.ticTacToe.logic.EasyLogic;
import app.ticTacToe.logic.HardLogic;
import app.ticTacToe.logic.ImpossibleLogic;
import app.ticTacToe.logic.MediumLogic;
import com.googlecode.lanterna.*;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;

import java.io.IOException;
import java.util.List;

import static app.cli.Config.*;
import static app.cli.Game.*;
import static app.cli.Utils.drawWindow;
import static app.cli.menus.NickMenu.nickMenu;


public class Submenus {

    public static boolean levelMenuOpen = false;
    public static boolean settingsMenuOpen = false;
    public static boolean controlsMenuOpen = false;
    public static boolean colorsMenuOpen = false;
    public static boolean bestScoreMenuOpen = false;
    public static boolean pauseMenuOpen = false;
    public static boolean nickMenuOpen = false;
    public static boolean resetScoreBoardOpen = false;

    private final String[] levelMenuItems = {
            "EASY",
            "MEDIUM",
            "HARD",
            "IMPOSSIBLE"
    };
    private final String[] setteingsCategories = {
            "Color schema",
            "Controls",
            "Reset scoreboard"
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

    private final TextGraphics tg;

    public Submenus(TextGraphics tg) {
        this.tg = tg;
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
                         Main.logic = new EasyLogic();
                        break;
                    case 1:
                        Main.logic = new MediumLogic();
                        break;
                    case 2:
                        Main.logic = new HardLogic();
                        break;
                    case 3:
                        Main.logic = new ImpossibleLogic();
                        break;
                }
                nickMenu(tg);
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
                    case 2:
                        submenus.resetScoreboard();
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

    public void getScoreInfoMenu(TextGraphics tg, boolean noInput) throws IOException {
        bestScoreMenuOpen = true;
        int totalPaddingLeft = windowPaddingLeft + 19;
        int totalPaddingTop = windowPaddingTop;
        drawWindow(tg, 15, 3);
        tg.drawLine(totalPaddingLeft - 2, totalPaddingTop + 6, windowPaddingLeft + columnHeight - 18, totalPaddingTop + 6, Symbols.SINGLE_LINE_HORIZONTAL);
        tg.drawLine(totalPaddingLeft, totalPaddingTop + 8, windowPaddingLeft + columnHeight - 19, totalPaddingTop + 8, Symbols.SINGLE_LINE_HORIZONTAL);
        TextColor current = tg.getForegroundColor();
        tg.setForegroundColor(colorSchema.getLogo()[0]);
        tg.putString(totalPaddingLeft - 1, totalPaddingTop + 5, "BEST Players", SGR.BOLD);
        tg.setForegroundColor(current);
        tg.putString(totalPaddingLeft, totalPaddingTop + 7, "Name");
        List<Player> bestPlayers = BestScoreManager.getInstance().getPlayersList();
        int maxLength = 0;

        totalPaddingTop += 10;

        for (int i = 0; i < bestPlayers.size(); i++) {
            tg.putString(totalPaddingLeft, totalPaddingTop + i * 2, bestPlayers.get(i).getName());
            if (bestPlayers.get(i).getName().length() > maxLength) {
                maxLength = bestPlayers.get(i).getName().length();
            }
        }

        totalPaddingLeft += maxLength + 2;

        tg.putString(totalPaddingLeft, totalPaddingTop - 3, "Score");
        for (int i = 0; i < bestPlayers.size(); i++) {
            tg.putString(totalPaddingLeft, totalPaddingTop + i * 2, bestPlayers.get(i).getScore() + "");
        }

        totalPaddingLeft += 12;

        tg.putString(totalPaddingLeft, totalPaddingTop - 3, "WON");
        for (int i = 0; i < bestPlayers.size(); i++) {
            tg.putString(totalPaddingLeft, totalPaddingTop + i * 2, bestPlayers.get(i).getNumberOfWonGames() + "");
        }
        totalPaddingLeft += 5;
        tg.putString(totalPaddingLeft, totalPaddingTop - 3, "LOST");
        for (int i = 0; i < bestPlayers.size(); i++) {
            tg.putString(totalPaddingLeft, totalPaddingTop + i * 2, bestPlayers.get(i).getNumberOfLostGames() + "");
        }

        totalPaddingLeft += 5;
        tg.putString(totalPaddingLeft + 5, totalPaddingTop - 3, "TIE");
        for (int i = 0; i < bestPlayers.size(); i++) {
            tg.putString(totalPaddingLeft + 5, totalPaddingTop + i * 2, bestPlayers.get(i).getNumberOfTies() + "");
        }

        Config.terminal.flush();
        Config.screen.refresh();
        while (!noInput) {
            KeyStroke keyStroke = Config.terminal.pollInput();
            if (keyStroke != null) {
                if (controls.isEscapeKey(keyStroke)) ;
                break;
            }
        }
        bestScoreMenuOpen = false;
    }

    public void drawResetScoreboard(TextGraphics tg) throws IOException {
        drawWindow(tg, 26, 8);
        tg.drawLine(27, 10, columnHeight-28, 10, Symbols.SINGLE_LINE_HORIZONTAL);
        tg.setForegroundColor(TextColor.ANSI.RED);
        tg.putString(28, 9, "Reset ALL score data?", SGR.BOLD);

        tg.putString(30, 12, "[Y]es", SGR.BOLD);
        tg.setForegroundColor(colorSchema.getMenuForeground());
        tg.putString(30, 13, "[N]o", SGR.BOLD);

        terminal.flush();
        screen.refresh();
    }

    private void resetScoreboard() throws IOException {
        resetScoreBoardOpen = true;

        drawResetScoreboard(tg);

        while(true){
            KeyStroke keyStroke = terminal.pollInput();
            if (keyStroke != null) {
                if(keyStroke.getKeyType() == KeyType.Character){
                    if (keyStroke.getCharacter() == 'y'){
                        BestScoreManager.getInstance().clearPlayerList();
                        tg.fillRectangle(new TerminalPosition(30,12), new TerminalSize(2, 1), ' ');
                        tg.putString(32, 12, "All data deleted", SGR.BLINK);
                        tg.putString(32, 13, " ESC to go back");
                        terminal.flush();
                        screen.refresh();
                    }
                    if (keyStroke.getCharacter() == 'n'){
                        break;
                    }
                }
                if (keyStroke.getKeyType() == KeyType.Escape){
                    break;
                }

            }

        }

        resetScoreBoardOpen = false;
    }
}
