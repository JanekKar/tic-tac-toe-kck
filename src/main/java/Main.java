import TicTacToe.TicTacToe;
import TicTacToe.TicTacToeLogic;
import TicTacToe.Player;
import com.googlecode.lanterna.*;
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

    static Terminal terminal;

    static int rowHeight;
    static int columnWidth;
    static int xWidth = 11;
    static int xHeight = 5;
    static int paddingLeft;
    static int paddingTop = 2;
    static int fieldOffset = 3;
    static int sidebar = 12;
    static int sidedarPaddintTop;
    static int paddingLeftSidebar = 6;

    static int rows;
    static int columns;

    static int prevRows;
    static int prevCols;

    static int highlightX = 1;
    static int highlightY = 1;

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

    private static void drawBoard(TextGraphics tg) {
        for (int j = 0; j < 3; j++) {
            for (int i = 0; i < 3; i++) {
                tg.drawRectangle(
                        new TerminalPosition(paddingLeft - 1 + ((columnWidth) * i), paddingTop-1 + ((rowHeight) * j)),
                        new TerminalSize(columnWidth + 1, rowHeight + 1),
                        Symbols.BLOCK_MIDDLE);
            }
        }

        tg.drawRectangle(
                new TerminalPosition(paddingLeft - 1, paddingTop-1),
                new TerminalSize(columnWidth * 3 + 1, rowHeight * 3 + 1),
                ' ');

    }

    private static void drawSidebar(TextGraphics tg){

        tg.drawLine(sidebar, 0, sidebar, rows, Symbols.DOUBLE_LINE_VERTICAL);

        tg.drawLine(0, sidedarPaddintTop + 0, sidebar, sidedarPaddintTop + 0, Symbols.DOUBLE_LINE_HORIZONTAL);
        tg.putString(sidebar, sidedarPaddintTop + 0, Symbols.DOUBLE_LINE_T_LEFT+"");
        tg.drawLine(0, sidedarPaddintTop + 4, sidebar, sidedarPaddintTop + 4, Symbols.DOUBLE_LINE_HORIZONTAL);
        tg.putString(sidebar, sidedarPaddintTop + 4, Symbols.DOUBLE_LINE_T_LEFT+"");
        tg.drawLine(0, sidedarPaddintTop + 9, sidebar, sidedarPaddintTop + 9, Symbols.DOUBLE_LINE_HORIZONTAL);
        tg.putString(sidebar, sidedarPaddintTop + 9, Symbols.DOUBLE_LINE_T_LEFT+"");
        tg.drawLine(0, sidedarPaddintTop + 13, sidebar, sidedarPaddintTop + 13, Symbols.DOUBLE_LINE_HORIZONTAL);
        tg.putString(sidebar, sidedarPaddintTop + 13, Symbols.DOUBLE_LINE_T_LEFT+"");
        tg.drawLine(0, sidedarPaddintTop + 19, sidebar, sidedarPaddintTop + 19, Symbols.DOUBLE_LINE_HORIZONTAL);
        tg.putString(sidebar, sidedarPaddintTop + 19, Symbols.DOUBLE_LINE_T_LEFT+"");

        drawPlayerInfo(tg);

    }

    private static void drawGame(TextGraphics tg) throws IOException {
        tg.fill(' ');

        drawSidebar(tg);
        drawBoard(tg);
        drawAllMoves(tg);

        highlightField(tg, highlightX, highlightY, TextColor.ANSI.GREEN, TextColor.ANSI.BLACK);
    }

    private static void drawAllMoves(TextGraphics tg) {
        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                drawXorO(tg, i, j);
            }
        }
    }

    private static void drawPlayerInfo(TextGraphics tg){
        tg.putString(1, sidedarPaddintTop + 2, "Score: " + game.getPlayer().getScore(), SGR.BOLD);
        tg.putString(1, sidedarPaddintTop + 6, "Player:", SGR.BOLD);
        tg.putString(1, sidedarPaddintTop + 7, game.getPlayer().getName());

        drawCurrentPlayer(tg);

        tg.putString(1, sidedarPaddintTop + 15, "WIN: "+game.getPlayer().getNumberOfWonGames());
        tg.putString(1, sidedarPaddintTop + 16, "LOST: "+game.getPlayer().getNumberOfLostGames());
        tg.putString(1, sidedarPaddintTop + 17, "TIES: "+game.getPlayer().getNumberOfTies());

    }

    private static void drawCurrentPlayer(TextGraphics tg) {
        if (game.getCurrentPlayer().equals("X")){
            tg.setForegroundColor(TextColor.ANSI.GREEN);
            tg.putString(1,sidedarPaddintTop + 11, "Your Turn", SGR.BLINK, SGR.BOLD);
        }else{
            tg.setForegroundColor(TextColor.ANSI.RED);
            tg.putString(1,sidedarPaddintTop + 11, "AI's Turn", SGR.BLINK, SGR.BOLD);
        }

        tg.setForegroundColor(TextColor.ANSI.DEFAULT);
        tg.setBackgroundColor(TextColor.ANSI.DEFAULT);
    }


    public static void main(String[] args) throws IOException, InterruptedException {

        game = new TicTacToe(new Player("Janek"));
        TicTacToeLogic logic = new TicTacToeLogic(game);

        terminal = new DefaultTerminalFactory().createTerminal();
        terminal.setCursorVisible(true);

        Screen screen = new TerminalScreen(terminal);
        screen.doResizeIfNecessary();

        TextGraphics tg = screen.newTextGraphics();

        terminal.addResizeListener(new TerminalResizeListener() {
            @Override
            public void onResized(Terminal terminal, TerminalSize terminalSize) {
                try {

                    rows = terminal.getTerminalSize().getRows();
                    columns = terminal.getTerminalSize().getColumns();

                    int deltaColumns = columns - prevCols;
                    int deltaRows = rows - prevRows;

                    if(deltaColumns%4==0){
                        prevCols = columns;
                        sidebar+= (deltaColumns/2);
                        paddingLeftSidebar += (deltaColumns/4);
                        paddingLeft = sidebar +  paddingLeftSidebar;
                    }

                    if(deltaRows%2==0){
                        prevRows = rows;
                        sidedarPaddintTop += deltaRows/2;
                        paddingTop += deltaRows/2;
                    }

                    screen.doResizeIfNecessary();

                    drawGame(tg);

                    terminal.flush();
                    screen.refresh();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        screen.startScreen();

        rows = terminal.getTerminalSize().getRows();
        columns = terminal.getTerminalSize().getColumns();

        prevRows = rows;
        prevCols = columns;

        rowHeight = (rows - 1) / 3;
        columnWidth = ((columns - sidebar - 6) / 3) - 1;
        paddingLeft = sidebar + paddingLeftSidebar;
        sidedarPaddintTop = (rows-20)/2;

        TextColor bgColor;
        TextColor fgColor;


        boolean play = true;
        while (play) {
            drawSidebar(tg);
            drawBoard(tg);
            highlightField(tg, highlightX, highlightY, TextColor.ANSI.GREEN, TextColor.ANSI.WHITE);

            terminal.flush();
            screen.refresh();
            screen.startScreen();

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
                                if (result != "TIE")
                                    for (Point point : game.getWinningPositions()) {
                                        highlightField(tg, point.x, point.y, TextColor.ANSI.MAGENTA, TextColor.ANSI.CYAN);
                                    }
                                break;
                            }

                            drawCurrentPlayer(tg);
                            unHighlightField(tg,highlightX, highlightY);
                            terminal.flush();
                            screen.refresh();

                            Thread.sleep(500);

                            drawCurrentPlayer(tg);
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

                            drawCurrentPlayer(tg);

                        } else {
                            bgColor = TextColor.ANSI.RED;
                            fgColor = TextColor.ANSI.WHITE;

                        }

                    }
                    if (keyStroke.getKeyType() == KeyType.Escape) {
                        play = false;
                        break;
                    }

                    highlightField(tg, highlightX, highlightY, bgColor, fgColor);
                    terminal.flush();
                    screen.refresh();
                }
            }

            terminal.flush();
            screen.refresh();

            game.nextGame();
            tg.fill(' ');

            highlightX=1;
            highlightY=1;
            Thread.sleep(2000);

        }

        screen.close();
        terminal.close();

    }



}
