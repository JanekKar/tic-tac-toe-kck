package app.cli;

import app.cli.menus.Submenus;
import com.googlecode.lanterna.*;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;

import java.awt.*;
import java.io.IOException;

import static app.Main.game;
import static app.Main.logic;
import static app.cli.ACSILogo.drawTie;
import static app.cli.Config.*;
import static app.cli.Utils.*;
import static app.cli.menus.MainMenu.mainMenu;
import static app.cli.menus.PauseMenu.pauseMenu;


public class Game {

    public static boolean runGame = true;
    public static boolean runSession = false;
    public static Submenus submenus;
    static int highlightX = 1;
    static int highlightY = 1;

    private static void drawBoard(TextGraphics tg) {
        tg.setForegroundColor(colorSchema.getGameBoard());
        tg.setBackgroundColor(colorSchema.getGameBackground());
        for (int j = 0; j < 3; j++) {
            for (int i = 0; i < 3; i++) {
                tg.drawRectangle(
                        new TerminalPosition(windowPaddingLeft + boardPaddingLeft - 1 + ((boardColumnWidth) * i), windowPaddingTop + boardPaddingTop - 1 + ((boardRowHeight) * j)),
                        new TerminalSize(boardColumnWidth + 1, boardRowHeight + 1),
                        Symbols.BLOCK_MIDDLE);
            }
        }

        tg.drawRectangle(
                new TerminalPosition(windowPaddingLeft + boardPaddingLeft - 1, windowPaddingTop + boardPaddingTop - 1),
                new TerminalSize(boardColumnWidth * 3 + 1, boardRowHeight * 3 + 1),
                ' ');

    }

    private static void drawSidebar(TextGraphics tg) {

        tg.setForegroundColor(colorSchema.getBorders());

        tg.drawLine(windowPaddingLeft + sidebarWidth, windowPaddingTop, windowPaddingLeft + sidebarWidth, windowPaddingTop + rowLength - 1, Symbols.DOUBLE_LINE_VERTICAL);

        tg.drawLine(windowPaddingLeft, windowPaddingTop + sidebarPaddingTop, windowPaddingLeft + sidebarWidth, windowPaddingTop + sidebarPaddingTop, Symbols.DOUBLE_LINE_HORIZONTAL);
        tg.setCharacter(windowPaddingLeft + sidebarWidth, windowPaddingTop + sidebarPaddingTop, Symbols.DOUBLE_LINE_T_LEFT);
        tg.setCharacter(windowPaddingLeft, windowPaddingTop + sidebarPaddingTop, Symbols.DOUBLE_LINE_T_RIGHT);

        tg.drawLine(windowPaddingLeft, windowPaddingTop + sidebarPaddingTop + 4, windowPaddingLeft + sidebarWidth, windowPaddingTop + sidebarPaddingTop + 4, Symbols.DOUBLE_LINE_HORIZONTAL);
        tg.setCharacter(windowPaddingLeft + sidebarWidth, windowPaddingTop + sidebarPaddingTop + 4, Symbols.DOUBLE_LINE_T_LEFT);
        tg.setCharacter(windowPaddingLeft, windowPaddingTop + sidebarPaddingTop + 4, Symbols.DOUBLE_LINE_T_RIGHT);

        tg.drawLine(windowPaddingLeft, windowPaddingTop + sidebarPaddingTop + 9, windowPaddingLeft + sidebarWidth, windowPaddingTop + sidebarPaddingTop + 9, Symbols.DOUBLE_LINE_HORIZONTAL);
        tg.setCharacter(windowPaddingLeft + sidebarWidth, windowPaddingTop + sidebarPaddingTop + 9, Symbols.DOUBLE_LINE_T_LEFT);
        tg.setCharacter(windowPaddingLeft, windowPaddingTop + sidebarPaddingTop + 9, Symbols.DOUBLE_LINE_T_RIGHT);

        tg.drawLine(windowPaddingLeft, windowPaddingTop + sidebarPaddingTop + 13, windowPaddingLeft + sidebarWidth, windowPaddingTop + sidebarPaddingTop + 13, Symbols.DOUBLE_LINE_HORIZONTAL);
        tg.setCharacter(windowPaddingLeft + sidebarWidth, windowPaddingTop + sidebarPaddingTop + 13, Symbols.DOUBLE_LINE_T_LEFT);
        tg.setCharacter(windowPaddingLeft, windowPaddingTop + sidebarPaddingTop + 13, Symbols.DOUBLE_LINE_T_RIGHT);

        tg.drawLine(windowPaddingLeft, windowPaddingTop + sidebarPaddingTop + 19, windowPaddingLeft + sidebarWidth, windowPaddingTop + sidebarPaddingTop + 19, Symbols.DOUBLE_LINE_HORIZONTAL);
        tg.setCharacter(windowPaddingLeft + sidebarWidth, windowPaddingTop + sidebarPaddingTop + 19, Symbols.DOUBLE_LINE_T_LEFT);
        tg.setCharacter(windowPaddingLeft, windowPaddingTop + sidebarPaddingTop + 19, Symbols.DOUBLE_LINE_T_RIGHT);


        tg.setCharacter(windowPaddingLeft + sidebarWidth, windowPaddingTop, Symbols.DOUBLE_LINE_T_DOWN);
        tg.setCharacter(windowPaddingLeft + sidebarWidth, windowPaddingTop + rowLength - 1, Symbols.DOUBLE_LINE_T_UP);

        drawPlayerInfo(tg);

    }

    protected static void drawGame(TextGraphics tg) {
        tg.setBackgroundColor(colorSchema.getGameBackground());
//        tg.fill(' ');
//        drawBorder(tg, 0, 0, columnHeight - 1, rowLength - 1);
        drawWindow(tg, 0, 0);
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
        tg.setBackgroundColor(colorSchema.getGameSidebarBackground());
        tg.setForegroundColor(colorSchema.getGameSidebarForeground());

        tg.putString(windowPaddingLeft + 1, windowPaddingTop + sidebarPaddingTop - 1, "Game No: " + (game.getGameNo() + 1));
        drawScore(tg);
        tg.putString(windowPaddingLeft + 1, windowPaddingTop + sidebarPaddingTop + 6, "Player:", SGR.BOLD);
        tg.putString(windowPaddingLeft + 1, windowPaddingTop + sidebarPaddingTop + 7, game.getPlayer().getName());
        drawCurrentPlayer(tg);
        tg.putString(windowPaddingLeft + 1, windowPaddingTop + sidebarPaddingTop + 15, "WIN: " + game.getPlayer().getNumberOfWonGames());
        tg.putString(windowPaddingLeft + 1, windowPaddingTop + sidebarPaddingTop + 16, "LOST: " + game.getPlayer().getNumberOfLostGames());
        tg.putString(windowPaddingLeft + 1, windowPaddingTop + sidebarPaddingTop + 17, "TIES: " + game.getPlayer().getNumberOfTies());

    }

    private static void drawScore(TextGraphics tg) {
        tg.setForegroundColor(colorSchema.getGameSidebarForeground());
        tg.setBackgroundColor(colorSchema.getGameSidebarBackground());
        tg.putString(windowPaddingLeft + 1, windowPaddingTop + sidebarPaddingTop + 2, "Score: " + game.getPlayer().getScore(), SGR.BOLD);
    }

    private static void drawCurrentPlayer(TextGraphics tg) {
        if (game.getCurrentPlayer().equals("X")) {
            tg.setForegroundColor(TextColor.ANSI.GREEN);
            tg.putString(windowPaddingLeft + 1, windowPaddingTop + sidebarPaddingTop + 11, "Your Turn", SGR.BLINK, SGR.BOLD);
        } else {
            tg.setForegroundColor(TextColor.ANSI.RED);
            tg.putString(windowPaddingLeft + 1, windowPaddingTop + sidebarPaddingTop + 11, "AI's Turn", SGR.BLINK, SGR.BOLD);
        }
        tg.setForegroundColor(colorSchema.getXAndO());

    }


    public static void mainLoop(TextGraphics tg) throws IOException, InterruptedException {

        while (runGame) {
            mainMenu(tg);
            tg.setBackgroundColor(colorSchema.getGameBackground());
            tg.setForegroundColor(colorSchema.getGameBoard());
            tg.fill(' ');

            terminal.flush();
            screen.refresh();
            while (runSession) {
                drawBorder(tg, 0, 0, columnHeight - 1, rowLength - 1);
                drawSidebar(tg);
                drawBoard(tg);
                highlightField(tg, highlightX, highlightY, colorSchema.getGameFiledHighlightOk()[0], colorSchema.getGameFiledHighlightOk()[1]);

                terminal.flush();
                screen.refresh();
                screen.startScreen();

                gameLoop(tg);

                terminal.flush();
                screen.refresh();

                game.nextGame();
                if (game.isEndOfSession()) {
                    runSession = false;
                    if (game.isNewBest()) {
                        tg.setBackgroundColor(colorSchema.getMenuBackground());
                        tg.setForegroundColor(colorSchema.getMenuForeground());
                        drawWindow(tg, 14, 1);
                        tg.putString(windowPaddingLeft + 17, windowPaddingTop + 2, "NEW TOP 5 SCORE", SGR.BOLD, SGR.BLINK);
                        tg.putString(windowPaddingLeft + 17, windowPaddingTop + 21, "ESC to exit", SGR.BOLD, SGR.BLINK);
                        submenus.getScoreInfoMenu(tg, false);

                        terminal.flush();
                        screen.refresh();
                    }
                } else {
                    Thread.sleep(500);
                }
                tg.setBackgroundColor(colorSchema.getGameBackground());
                tg.fill(' ');

                highlightX = 1;
                highlightY = 1;
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
                bgColor = colorSchema.getGameFiledHighlightOk()[0];
                fgColor = colorSchema.getGameFiledHighlightOk()[1];
                if (controls.isDownKey(keyStroke) && highlightY < 2)
                    highlightY++;
                if (controls.isUpKey(keyStroke) && highlightY > 0)
                    highlightY--;
                if (controls.isRightKey(keyStroke) && highlightX < 2)
                    highlightX++;
                if (controls.isLeftKey(keyStroke) && highlightX > 0)
                    highlightX--;
                if (controls.isPlaceKey(keyStroke)) {
                    if (game.checkIfFree(new Point(highlightX, highlightY))) {
                        game.makeMove(new Point(highlightX, highlightY));
                        drawXorO(tg, highlightX, highlightY);
                        String result = game.checkWinner();
                        if (result != null) {
                            if (!result.equals("TIE"))
                                for (Point point : game.getWinningPositions()) {
                                    highlightField(tg, point.x, point.y, colorSchema.getHighlightWinning()[0], colorSchema.getHighlightWinning()[1]);
                                }
                            else
                                drawTieWindow(tg);
                            break;
                        }

                        drawCurrentPlayer(tg);
                        unHighlightField(tg, highlightX, highlightY);
                        terminal.flush();
                        screen.refresh();

                        Thread.sleep(500);

                        Point point = logic.makeMove();
                        game.makeMove(point);

                        drawXorO(tg, point.x, point.y);
                        result = game.checkWinner();
                        if (result != null) {
                            if (!result.equals("TIE"))
                                for (Point winningPoint : game.getWinningPositions()) {
                                    highlightField(tg, winningPoint.x, winningPoint.y, colorSchema.getHighlightLoosing()[0], colorSchema.getHighlightLoosing()[1]);
                                }
                            else
                                drawTieWindow(tg);
                            break;
                        }
                        drawCurrentPlayer(tg);
                    } else {
                        bgColor = colorSchema.getGameFiledHighlightWrong()[0];
                        fgColor = colorSchema.getGameFiledHighlightWrong()[1];
                    }

                }
                if (controls.isPauseGameKey(keyStroke)) {
                    if (pauseMenu(tg))
                        break;
                    tg.fill(' ');
                    drawGame(tg);
                }

                drawScore(tg);
                highlightField(tg, highlightX, highlightY, bgColor, fgColor);
                terminal.flush();
                screen.refresh();
            }
        }
    }

    private static void drawTieWindow(TextGraphics tg) throws IOException, InterruptedException {
        windowPaddingLeft += sidebarWidth / 2;
        tg.setBackgroundColor(colorSchema.getMenuBackground());
        tg.setForegroundColor(colorSchema.getBorders());
        drawWindow(tg, 25, 8);
        drawTie(tg, 33, 10);
        terminal.flush();
        screen.refresh();
        windowPaddingLeft -= sidebarWidth / 2;
        Thread.sleep(500);
    }


    public static void setupGameConfig() {
        Config.getInstance();
        submenus = new Submenus(Config.tg);
    }
}
