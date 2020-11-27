package app.cli;

import app.Main;
import app.ticTacToe.TicTacToe;
import app.ticTacToe.logic.TicTacToeLogic;
import com.googlecode.lanterna.*;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.awt.*;
import java.io.IOException;


import static app.cli.PauseMenu.*;
import static app.cli.Utils.*;
import static app.cli.MainMenu.*;



public class CLI {
    private static TicTacToe game = Main.game;
    protected static TicTacToeLogic logic = Main.logic;
    public static Terminal terminal;
    public static Screen screen;
    static int highlightX = 1;
    static int highlightY = 1;
    static boolean play = false;
    protected static boolean paused = false;
    protected static boolean run = true;

    private static void drawBoard(TextGraphics tg) {
        for (int j = 0; j < 3; j++) {
            for (int i = 0; i < 3; i++) {
                tg.drawRectangle(
                        new TerminalPosition(windowPaddingLeft + paddingLeft - 1 + ((columnWidth) * i), windowPaddingTop + paddingTop - 1 + ((rowHeight) * j)),
                        new TerminalSize(columnWidth + 1, rowHeight + 1),
                        Symbols.BLOCK_MIDDLE);
            }
        }

        tg.drawRectangle(
                new TerminalPosition(windowPaddingLeft + paddingLeft - 1, windowPaddingTop + paddingTop - 1),
                new TerminalSize(columnWidth * 3 + 1, rowHeight * 3 + 1),
                ' ');

    }

    private static void drawSidebar(TextGraphics tg) {

        tg.drawLine(windowPaddingLeft + sidebar, windowPaddingTop, windowPaddingLeft + sidebar, windowPaddingTop + rows - 1, Symbols.DOUBLE_LINE_VERTICAL);

        tg.drawLine(windowPaddingLeft, windowPaddingTop + sidebarPaddingTop, windowPaddingLeft + sidebar, windowPaddingTop + sidebarPaddingTop, Symbols.DOUBLE_LINE_HORIZONTAL);
        tg.setCharacter(windowPaddingLeft + sidebar, windowPaddingTop + sidebarPaddingTop, Symbols.DOUBLE_LINE_T_LEFT);
        tg.setCharacter(windowPaddingLeft, windowPaddingTop + sidebarPaddingTop, Symbols.DOUBLE_LINE_T_RIGHT);

        tg.drawLine(windowPaddingLeft, windowPaddingTop + sidebarPaddingTop + 4, windowPaddingLeft + sidebar, windowPaddingTop + sidebarPaddingTop + 4, Symbols.DOUBLE_LINE_HORIZONTAL);
        tg.setCharacter(windowPaddingLeft + sidebar, windowPaddingTop + sidebarPaddingTop + 4, Symbols.DOUBLE_LINE_T_LEFT);
        tg.setCharacter(windowPaddingLeft, windowPaddingTop + sidebarPaddingTop + 4, Symbols.DOUBLE_LINE_T_RIGHT);

        tg.drawLine(windowPaddingLeft, windowPaddingTop + sidebarPaddingTop + 9, windowPaddingLeft + sidebar, windowPaddingTop + sidebarPaddingTop + 9, Symbols.DOUBLE_LINE_HORIZONTAL);
        tg.setCharacter(windowPaddingLeft + sidebar, windowPaddingTop + sidebarPaddingTop + 9, Symbols.DOUBLE_LINE_T_LEFT);
        tg.setCharacter(windowPaddingLeft, windowPaddingTop + sidebarPaddingTop + 9, Symbols.DOUBLE_LINE_T_RIGHT);

        tg.drawLine(windowPaddingLeft, windowPaddingTop + sidebarPaddingTop + 13, windowPaddingLeft + sidebar, windowPaddingTop + sidebarPaddingTop + 13, Symbols.DOUBLE_LINE_HORIZONTAL);
        tg.setCharacter(windowPaddingLeft + sidebar, windowPaddingTop + sidebarPaddingTop + 13, Symbols.DOUBLE_LINE_T_LEFT);
        tg.setCharacter(windowPaddingLeft, windowPaddingTop + sidebarPaddingTop + 13, Symbols.DOUBLE_LINE_T_RIGHT);

        tg.drawLine(windowPaddingLeft, windowPaddingTop + sidebarPaddingTop + 19, windowPaddingLeft + sidebar, windowPaddingTop + sidebarPaddingTop + 19, Symbols.DOUBLE_LINE_HORIZONTAL);
        tg.setCharacter(windowPaddingLeft + sidebar, windowPaddingTop + sidebarPaddingTop + 19, Symbols.DOUBLE_LINE_T_LEFT);
        tg.setCharacter(windowPaddingLeft, windowPaddingTop + sidebarPaddingTop + 19, Symbols.DOUBLE_LINE_T_RIGHT);


        tg.setCharacter(windowPaddingLeft + sidebar, windowPaddingTop, Symbols.DOUBLE_LINE_T_DOWN);
        tg.setCharacter(windowPaddingLeft + sidebar, windowPaddingTop + rows - 1, Symbols.DOUBLE_LINE_T_UP);


        drawPlayerInfo(tg);

    }

    private static void drawGame(TextGraphics tg) {
        tg.fill(' ');
        drawBorder(tg, 0, 0, columns - 1, rows - 1);
        drawSidebar(tg);
        drawBoard(tg);
        drawAllMoves(tg);

        highlightField(tg, highlightX, highlightY, TextColor.ANSI.GREEN, TextColor.ANSI.BLACK);
    }

    private static void drawAllMoves(TextGraphics tg) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                drawXorO(tg, i, j);
            }
        }
    }

    private static void drawPlayerInfo(TextGraphics tg) {
        tg.putString(windowPaddingLeft + 1, windowPaddingTop + sidebarPaddingTop + 2, "Score: " + game.getPlayer().getScore(), SGR.BOLD);
        tg.putString(windowPaddingLeft + 1, windowPaddingTop + sidebarPaddingTop + 6, "Player:", SGR.BOLD);
        tg.putString(windowPaddingLeft + 1, windowPaddingTop + sidebarPaddingTop + 7, game.getPlayer().getName());

        drawCurrentPlayer(tg);

        tg.putString(windowPaddingLeft + 1, windowPaddingTop + sidebarPaddingTop + 15, "WIN: " + game.getPlayer().getNumberOfWonGames());
        tg.putString(windowPaddingLeft + 1, windowPaddingTop + sidebarPaddingTop + 16, "LOST: " + game.getPlayer().getNumberOfLostGames());
        tg.putString(windowPaddingLeft + 1, windowPaddingTop + sidebarPaddingTop + 17, "TIES: " + game.getPlayer().getNumberOfTies());

    }

    private static void drawCurrentPlayer(TextGraphics tg) {
        if (game.getCurrentPlayer().equals("X")) {
            tg.setForegroundColor(TextColor.ANSI.GREEN);
            tg.putString(windowPaddingLeft + 1, windowPaddingTop + sidebarPaddingTop + 11, "Your Turn", SGR.BLINK, SGR.BOLD);
        } else {
            tg.setForegroundColor(TextColor.ANSI.RED);
            tg.putString(windowPaddingLeft + 1, windowPaddingTop + sidebarPaddingTop + 11, "AI's Turn", SGR.BLINK, SGR.BOLD);
        }

        tg.setForegroundColor(TextColor.ANSI.DEFAULT);
        tg.setBackgroundColor(TextColor.ANSI.DEFAULT);
    }


    public static void mainLoop(TextGraphics tg) throws IOException, InterruptedException {

        while(run){
            mainMenu(tg);
            tg.setBackgroundColor(TextColor.ANSI.DEFAULT);
            tg.setForegroundColor(TextColor.ANSI.DEFAULT);
            tg.fill(' ');

            terminal.flush();
            screen.refresh();
            while (play) {
                drawBorder(tg, 0, 0, columns - 1, rows - 1);
                drawSidebar(tg);
                drawBoard(tg);
                highlightField(tg, highlightX, highlightY, TextColor.ANSI.GREEN, TextColor.ANSI.WHITE);

                terminal.flush();
                screen.refresh();
                screen.startScreen();

                gameLoop(tg);

                terminal.flush();
                screen.refresh();

                game.nextGame();
                tg.fill(' ');

                highlightX = 1;
                highlightY = 1;
                Thread.sleep(1500);
            }
        }

    }

    private static void gameLoop(TextGraphics tg) throws IOException, InterruptedException {
        TextColor bgColor;
        TextColor fgColor;
        while (true) {
            KeyStroke keyStroke = terminal.pollInput();
            if (keyStroke != null) {
                unHighlightField(tg, highlightX, highlightY);
                bgColor = TextColor.ANSI.GREEN;
                fgColor = TextColor.ANSI.BLACK;
                if (keyStroke.getKeyType() == KeyType.ArrowDown && highlightY < 2)
                    highlightY++;
                if (keyStroke.getKeyType() == KeyType.ArrowUp && highlightY > 0)
                    highlightY--;
                if (keyStroke.getKeyType() == KeyType.ArrowRight && highlightX < 2)
                    highlightX++;
                if (keyStroke.getKeyType() == KeyType.ArrowLeft && highlightX > 0)
                    highlightX--;
                if (keyStroke.getKeyType() == KeyType.Character && keyStroke.getCharacter() == ' ') {
                    if (game.checkIfFree(new Point(highlightX, highlightY))) {
                        game.makeMove(new Point(highlightX, highlightY));
                        drawXorO(tg, highlightX, highlightY);
                        String result = game.checkWinner();
                        if (result != null) {
                            if (!result.equals("TIE"))
                                for (Point point : game.getWinningPositions()) {
                                    highlightField(tg, point.x, point.y, TextColor.ANSI.MAGENTA, TextColor.ANSI.CYAN);
                                }
                            break;
                        }

                        drawCurrentPlayer(tg);
                        unHighlightField(tg, highlightX, highlightY);
                        terminal.flush();
                        screen.refresh();

                        Thread.sleep(500);

                        drawCurrentPlayer(tg);
                        Point point = logic.makeMove();
                        game.makeMove(point);

                        drawXorO(tg, point.x, point.y);
                        result = game.checkWinner();
                        if (result != null) {
                            if (!result.equals("TIE"))
                                for (Point winningPoint : game.getWinningPositions()) {
                                    highlightField(tg, winningPoint.x, winningPoint.y, TextColor.ANSI.RED, TextColor.ANSI.YELLOW);
                                }
                            break;
                        }

                        drawCurrentPlayer(tg);

                    } else {
                        bgColor = TextColor.ANSI.RED;
                        fgColor = TextColor.ANSI.WHITE;
                    }

                }
                if (keyStroke.getKeyType() == KeyType.Escape) {
                    if (pauseMenu(tg))
                        break;
                    tg.fill(' ');
                    drawGame(tg);
                }

                highlightField(tg, highlightX, highlightY, bgColor, fgColor);
                terminal.flush();
                screen.refresh();
            }
        }
    }

    public static void calculatePadding() throws IOException {

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

    public static void resizeScreen(TextGraphics tg){
        try {

            calculatePadding();

            if (play)
                drawGame(tg);
            else
                drawMainMenu(tg);

            if (paused)
                drawPausedMenu(tg);

            terminal.flush();
            screen.refresh();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static TextGraphics setUpTerminalAndScreen() throws IOException {
        terminal = new DefaultTerminalFactory().createTerminal();
        terminal.setCursorVisible(false);

        screen = new TerminalScreen(terminal);
        screen.doResizeIfNecessary();
        screen.setCursorPosition(null);

        TextGraphics tg = screen.newTextGraphics();

        terminal.addResizeListener((terminal, terminalSize) -> {
           resizeScreen(tg);
        });
        screen.startScreen();

        setDimensions();

        int tempRows = terminal.getTerminalSize().getRows();
        int tempCol = terminal.getTerminalSize().getColumns();

        if(tempRows != rows || tempCol != columns){
            calculatePadding();
        }

        return tg;
    }




}
