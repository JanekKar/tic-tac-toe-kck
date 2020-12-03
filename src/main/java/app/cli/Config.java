package app.cli;

import app.cli.colors.*;
import app.cli.controls.*;
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

import static app.Main.submenus;
//import static app.cli.Game.*;
import static app.cli.Utils.*;
import static app.cli.menus.MainMenu.drawMainMenu;
import static app.cli.menus.PauseMenu.drawPausedMenu;

public class Config {

    private static Config instance;

    public static ColorSchema colorSchema = DefaultColors.getInstance();
    public static Controls controls = DefaultControls.getInstance();

    public static Terminal terminal;
    public static Screen screen;

    public static TextGraphics tg;

    private Config() {
        try {
            File config = new File(".ttt_config.cfg");
            String tempColors = null, tempControls = null;
            if (!config.createNewFile()){
                Scanner scanner = new Scanner(config);
                if (scanner.hasNext())
                    tempColors = scanner.nextLine();
                if (scanner.hasNext())
                    tempControls = scanner.nextLine();
                scanner.close();
            }

            if(tempColors != null){
                switch (tempColors.toLowerCase()){
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


            if (tempControls != null){
                switch (tempControls.toLowerCase()){
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
        if(instance == null)
            instance = new Config();
        return instance;
    }

    public static void closeGame() throws IOException {
        Config.screen.close();
        Config.terminal.close();
    }


    public void saveConfig(){
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

    public static void resizeScreen(TextGraphics tg) {
        try {
            calculatePadding();

            if (Game.play)
                Game.drawGame(tg);
            else{
                drawMainMenu(tg);
                if(submenus.bestScoreMenuOpen){
                    submenus.getScoreInfoMenu(tg, false);
                }
                if(submenus.settingsMenuOpen){
                    submenus.getSettingsMenu().drawSubMenu();
                    if(submenus.controlsMenuOpen)
                        submenus.getControlsMenu().drawSubMenu();
                    if(submenus.colorsMenuOpen)
                        submenus.getColorsMenu().drawSubMenu();
                }

            }

            if (Game.pauseMenu){
                drawPausedMenu(tg);
                if(submenus.bestScoreMenuOpen){
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

        if (tempRows != rows || tempCol != columns) {
            calculatePadding();
        }

    }

    private static void calculatePadding() throws IOException {

        int currRows = terminal.getTerminalSize().getRows();
        int currColumns = terminal.getTerminalSize().getColumns();

        int deltaColumns = currColumns - prevCols;
        int deltaRows = currRows - prevRows;

        if (deltaColumns % 2 == 0) {
            prevCols = currColumns;
            windowPaddingLeft += deltaColumns / 2;
        }

        if (deltaRows % 2 == 0) {
            prevRows = currRows;
            windowPaddingTop += deltaRows / 2;
        }

        screen.doResizeIfNecessary();
    }
}

