import TicTacToe.TicTacToe;
import TicTacToe.TicTacToeLogic;
import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.TerminalResizeListener;

import java.awt.*;
import java.io.IOException;


public class Main {
    static TicTacToe game;
    static int score = 999;
    static String playerName = "Janek";

    static int rowHeight, columnWidth, xWidth, xHeight, paddingLeft, paddingTop, fieldOffset;

    public static void drawX(TextGraphics tg, int x, int y) {
        int posX = paddingLeft + fieldOffset + (x * columnWidth);
        int posY = paddingTop + (rowHeight * y);
        tg.drawLine(
                new TerminalPosition(posX, posY),
                new TerminalPosition(posX + xWidth,posY + xHeight),
                'O');

        tg.drawLine(
                new TerminalPosition(posX + xWidth,posY),
                new TerminalPosition(posX,posY + xHeight),
                'O');
    }


    public static void drawO(TextGraphics tg, int x, int y) {
        int posX = paddingLeft + fieldOffset + (x * columnWidth);
        int posY = paddingTop + (y * rowHeight);

        // Upper horizontal lines
        tg.drawLine(
                new TerminalPosition(posX + 1, posY + 1),
                new TerminalPosition(posX + 3, posY + 1),
                'X');
        tg.drawLine(
                new TerminalPosition(posX + 4, posY),
                new TerminalPosition(posX + 7, posY),
                'X');
        tg.drawLine(
                new TerminalPosition(posX + 8, posY + 1),
                new TerminalPosition(posX + 10, posY + 1),
                'X');

        // Vertical lines
        tg.drawLine(
                new TerminalPosition(posX, posY + 2),
                new TerminalPosition(posX, posY + 3),
                'X');
        tg.drawLine(
                new TerminalPosition(posX + 11, posY + 2),
                new TerminalPosition(posX + 11, posY + 3),
                'X');

        // Lower horizontal lines
        posY += xHeight;
        tg.drawLine(
                new TerminalPosition(posX + 1, posY - 1),
                new TerminalPosition(posX + 3, posY - 1),
                'X');
        tg.drawLine(
                new TerminalPosition(posX + 4, posY),
                new TerminalPosition(posX + 7, posY),
                'X');
        tg.drawLine(
                new TerminalPosition(posX + 8, posY - 1),
                new TerminalPosition(posX + 10, posY - 1),
                'X');
    }


    public static void highlightField(TextGraphics tg, int x, int y, TextColor backgroundColor, TextColor foregroundColor) {
        tg.setBackgroundColor(backgroundColor);
        tg.setForegroundColor(foregroundColor);

        tg.fillRectangle(
                new TerminalPosition(paddingLeft + (x * columnWidth),paddingTop + (y * rowHeight)),
                new TerminalSize(columnWidth - 1, rowHeight - 1),
                ' ');

        drawXorO(tg, x, y);

        tg.setBackgroundColor(TextColor.ANSI.DEFAULT);
        tg.setForegroundColor(TextColor.ANSI.DEFAULT);

    }

    public static void unHighlightField(TextGraphics tg, int x, int y) {
        highlightField(tg, x,y, TextColor.ANSI.DEFAULT, TextColor.ANSI.DEFAULT);
    }

    private static void drawXorO(TextGraphics tg, int x, int y) {
        switch (game.getFieldContent(new Point(x, y))) {
            case "X":
                drawX(tg, x, y);
                break;
            case "O":
                drawO(tg, x, y);
                break;
            default:
        }
    }

    private static void drawBoard(TextGraphics tg) {for (int j = 0; j < 3; j++) {
        for (int i = 0; i < 3; i++) {
            tg.drawRectangle(new TerminalPosition(paddingLeft - 1 + ((columnWidth) * i), 1 + ((rowHeight) * j)), new TerminalSize(columnWidth + 1, rowHeight + 1), '#');
        }
    }

        tg.drawRectangle(new TerminalPosition(paddingLeft - 1, 1), new TerminalSize(columnWidth * 3 + 1, rowHeight * 3 + 1), ' ');

    }

    public static void main(String[] args) throws IOException, InterruptedException {


        game = new TicTacToe();
        TicTacToeLogic logic = new TicTacToeLogic(game);


        Terminal terminal = new DefaultTerminalFactory().createTerminal();
        terminal.setCursorVisible(true);
        
        terminal.addResizeListener(new TerminalResizeListener() {
            @Override
            public void onResized(Terminal terminal, TerminalSize terminalSize) {
                
            }
        });
        
        Screen screen = new TerminalScreen(terminal);
        screen.doResizeIfNecessary();

        TextGraphics tg = screen.newTextGraphics();
        screen.startScreen();

        tg.putString(1, 1, "Score: " + score, SGR.BOLD);
        int sidebar = 12;
        int columns = terminal.getTerminalSize().getColumns();
        int rows = terminal.getTerminalSize().getRows();

        tg.drawLine(sidebar, 0, sidebar, rows, '|');

        tg.drawLine(0, 3, sidebar, 3, '-');

        tg.putString(sidebar, 3, "+");


        tg.putString(1, 5, "Player: ", SGR.BOLD);
        tg.putString(1, 6, playerName);


        tg.setForegroundColor(TextColor.ANSI.MAGENTA);
        tg.setBackgroundColor(TextColor.ANSI.CYAN);


        tg.setForegroundColor(TextColor.ANSI.DEFAULT);
        tg.setBackgroundColor(TextColor.ANSI.DEFAULT);

        rowHeight = (rows - 1) / 3;
        columnWidth = ((columns - sidebar - 6) / 3) - 1;
        xWidth = 11;
        xHeight = 5;
        paddingLeft = sidebar + 6;
        paddingTop = 2;
        fieldOffset = 3;

        drawBoard(tg);

        int x = 0;
        int y = 0;

        highlightField(tg, x, y, TextColor.ANSI.GREEN, TextColor.ANSI.WHITE);


        terminal.flush();

        screen.refresh();

        screen.startScreen();

        TextColor bgColor;
        TextColor fgColor;

        while (true) {
            KeyStroke keyStroke = terminal.pollInput();
            if (keyStroke != null) {
                unHighlightField(tg, x, y);
                bgColor = TextColor.ANSI.GREEN;
                fgColor = TextColor.ANSI.BLACK;
                if (keyStroke.getKeyType() == KeyType.ArrowDown && y < 2)
                    y++;
                if (keyStroke.getKeyType() == KeyType.ArrowUp && y > 0)
                    y--;
                if (keyStroke.getKeyType() == KeyType.ArrowRight && x < 2)
                    x++;
                if (keyStroke.getKeyType() == KeyType.ArrowLeft && x > 0)
                    x--;
                if (keyStroke.getKeyType() == KeyType.Character && keyStroke.getCharacter() == ' ') {
                    if (game.checkIfFree(new Point(x, y))) {
                        game.makeMove(new Point(x, y));
                        drawXorO(tg, x, y);
                        String result = game.checkWinner();
                        if (result != null) {
                            if (result != "TIE")
                                for (Point point : game.getWinningPositions()) {
                                    highlightField(tg, point.x, point.y, TextColor.ANSI.MAGENTA, TextColor.ANSI.CYAN);
                                }
                            break;
                        }

                        Point point = logic.makeMove();
                        game.makeMove(point);

                        drawXorO(tg, point.x, point.y);
                        result = game.checkWinner();
                        if (result != null) {
                            if (result != "TIE")
                                for (Point winningPoint : game.getWinningPositions()) {
                                    highlightField(tg, winningPoint.x, winningPoint.y, TextColor.ANSI.RED, TextColor.ANSI.YELLOW);
                                }
                            break;
                        }
                    } else {
                        bgColor = TextColor.ANSI.RED;
                        fgColor = TextColor.ANSI.WHITE;

                    }

                }
                if (keyStroke.getKeyType() == KeyType.Escape) {
                    break;
                }

                highlightField(tg, x, y, bgColor, fgColor);
                terminal.flush();
                screen.refresh();
            }
        }


        terminal.flush();
        screen.refresh();

        Thread.sleep(4000);
        terminal.close();

    }



}
