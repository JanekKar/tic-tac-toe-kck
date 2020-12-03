package app.cli;

import app.cli.colors.*;
import app.cli.controls.*;
import app.cli.menus.NickMenu;
import app.cli.menus.Submenus;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import static app.cli.Game.submenus;
import static app.cli.menus.MainMenu.drawMainMenu;
import static app.cli.menus.NickMenu.drawNickMenu;
import static app.cli.menus.PauseMenu.drawPausedMenu;

public class Config {
    public static int windowPaddingTop = 0;
    public static int windowPaddingLeft = 0;
    public static int boardRowHeight;
    public static int boardColumnWidth;
    public static int boardPaddingTop = 2;
    public static int boardPaddingLeft;
    public static int filedPaddingLeft = 3;
    public static int rowLength;
    public static int columnHeight;
    public static int widthOfX = 11;
    public static int heightOfX = 5;
    public static int spaceSidebarBoard = 6;
    public static int sidebarWidth = 12;
    public static int sidebarPaddingTop;
    public static int prevRowLength;
    public static int prevColumnsHeight;
    public static ColorSchema colorSchema = DefaultColors.getInstance();
    public static Controls controls = DefaultControls.getInstance();
    public static Terminal terminal;
    public static Screen screen;
    public static TextGraphics tg;
    private static Config instance;

    private Config() {
        try {
            File configFile = new File(".ttt_config.cfg");
            String tempColors = null;
            String tempControls = null;

            if (!configFile.createNewFile()) {
                Scanner scanner = new Scanner(configFile);
                if (scanner.hasNext())
                    tempColors = scanner.nextLine();
                if (scanner.hasNext())
                    tempControls = scanner.nextLine();
                scanner.close();
            }

            if (tempColors != null) {
                switch (tempColors.toLowerCase()) {
                    case "monokai":
                        colorSchema = MonokaiColors.getInstance();
                        break;
                    case "ocean":
                        colorSchema = OceanColors.getInstance();
                        break;
                    case "matrix":
                        colorSchema = MatrixColors.getInstance();
                        break;
                    default:
                        colorSchema = DefaultColors.getInstance();
                }
            }


            if (tempControls != null) {
                switch (tempControls.toLowerCase()) {
                    case "wasd":
                        controls = WASDControls.getInstance();
                        break;
                    case "vim":
                        controls = VimControls.getInstance();
                        break;
                    case "ijkl":
                        controls = IJKLControls.getInstance();
                        break;
                    default:
                        controls = DefaultControls.getInstance();
                }
            }

        } catch (
                FileNotFoundException e) {
            e.printStackTrace();
        } catch (
                IOException e) {
            e.printStackTrace();
        }

        try {
            setUpTerminalAndScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Config getInstance() {
        if (instance == null)
            instance = new Config();
        return instance;
    }

    public static void closeGame() throws IOException {
        Config.screen.close();
        Config.terminal.close();
    }

    public static void resizeScreen(TextGraphics tg) {
        try {
            calculatePadding();

            if (Game.play)
                Game.drawGame(tg);
            else {
                drawMainMenu(tg);
                if(submenus.levelMenuOpen){
                    submenus.getLevelMenu().drawSubMenu();
                    if(submenus.nickMenuOpen){
                        drawNickMenu(tg);
                    }
                }

                if (Submenus.bestScoreMenuOpen) {
                    submenus.getScoreInfoMenu(tg, false);
                }
                if (Submenus.settingsMenuOpen) {
                    submenus.getSettingsMenu().drawSubMenu();
                    if (Submenus.controlsMenuOpen)
                        submenus.getControlsMenu().drawSubMenu();
                    if (Submenus.colorsMenuOpen)
                        submenus.getColorsMenu().drawSubMenu();
                    if (Submenus.resetScoreBoardOpen)
                        submenus.drawResetScoreboard(tg);
                }

            }

            if (Game.pauseMenu) {
                drawPausedMenu(tg);
                if (Submenus.bestScoreMenuOpen) {
                    submenus.getScoreInfoMenu(tg, true);
                }
            }

            terminal.flush();
            screen.refresh();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setUpTerminalAndScreen() throws IOException {
        terminal = new DefaultTerminalFactory().createTerminal();
        terminal.setCursorVisible(false);

        screen = new TerminalScreen(terminal);
        screen.doResizeIfNecessary();
        screen.setCursorPosition(null);

        tg = screen.newTextGraphics();

        terminal.addResizeListener((terminal, terminalSize) -> {
            resizeScreen(tg);
        });
        screen.startScreen();

        setDimensions();

        int tempRows = terminal.getTerminalSize().getRows();
        int tempCol = terminal.getTerminalSize().getColumns();

        if (tempRows != rowLength || tempCol != columnHeight) {
            calculatePadding();
        }

    }

    public static void setDimensions() {
        rowLength = 24;
        columnHeight = 80;

        prevRowLength = rowLength;
        prevColumnsHeight = columnHeight;

        boardRowHeight = (rowLength - 1) / 3;
        boardColumnWidth = ((columnHeight - sidebarWidth - 6) / 3) - 1;
        boardPaddingLeft = sidebarWidth + spaceSidebarBoard;
        sidebarPaddingTop = (rowLength - 20) / 2;
    }

    private static void calculatePadding() throws IOException {

        int currRows = terminal.getTerminalSize().getRows();
        int currColumns = terminal.getTerminalSize().getColumns();

        int deltaColumns = currColumns - prevColumnsHeight;
        int deltaRows = currRows - prevRowLength;

        if (deltaColumns % 2 == 0) {
            prevColumnsHeight = currColumns;
            windowPaddingLeft += deltaColumns / 2;
        }

        if (deltaRows % 2 == 0) {
            prevRowLength = currRows;
            windowPaddingTop += deltaRows / 2;
        }

        screen.doResizeIfNecessary();
    }

    public void saveConfig() {
        try {
            File config = new File(".ttt_config.cfg");
            config.createNewFile();
            PrintWriter fileWriter = new PrintWriter(config);

            fileWriter.println(colorSchema.getName());
            fileWriter.println(controls.getName());

            fileWriter.close();
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}

