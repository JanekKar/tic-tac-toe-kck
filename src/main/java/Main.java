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
    static TextGraphics tg;
    private static Screen screen;

    static int rowHeight;
    static int columnWidth;
    static int xWidth = 11;
    static int xHeight = 5;
    static int paddingLeft;
    static int paddingTop = 2;
    static int fieldOffset = 3;
    static int sidebar = 12;
    static int sidebarPaddingTop;
    static int paddingLeftSidebar = 6;

    static int windowPaddingTop = 0;
    static int windowPaddingLeft = 0;

    static int rows;
    static int columns;

    static int prevRows;
    static int prevCols;

    static int highlightX = 1;
    static int highlightY = 1;

    static boolean play = true;
    private static boolean paused = false;

    public static void drawX(TextGraphics tg, int x, int y){
        int posX = x + windowPaddingLeft;
        int posY = y + windowPaddingTop;
        tg.drawLine(
                new TerminalPosition(posX, posY),
                new TerminalPosition(posX + xWidth,posY + xHeight),
                'O');

        tg.drawLine(
                new TerminalPosition(posX + xWidth,posY),
                new TerminalPosition(posX,posY + xHeight),
                'O');
    }

    public static void drawXOnBoard(TextGraphics tg, int x, int y) {
        int posX = paddingLeft + fieldOffset + (x * columnWidth);
        int posY = paddingTop + (rowHeight * y);
        drawX(tg, posX, posY);
    }


    public static void drawO(TextGraphics tg, int x, int y){
        int posX = x  + windowPaddingLeft;
        int posY = y + windowPaddingTop;

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

    public static void drawOOnBoard(TextGraphics tg, int x, int y) {
        int posX = paddingLeft + fieldOffset + (x * columnWidth);
        int posY = paddingTop + (y * rowHeight);

        drawO(tg, posX, posY);

    }

    public static void highlightField(TextGraphics tg, int x, int y, TextColor backgroundColor, TextColor foregroundColor) {
        tg.setBackgroundColor(backgroundColor);
        tg.setForegroundColor(foregroundColor);

        tg.fillRectangle(
                new TerminalPosition(windowPaddingLeft + paddingLeft + (x * columnWidth),windowPaddingTop + paddingTop + (y * rowHeight)),
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
                drawXOnBoard(tg, x, y);
                break;
            case "O":
                drawOOnBoard(tg, x, y);
                break;
            default:
        }
    }

    private static void drawBoard(TextGraphics tg) {
        for (int j = 0; j < 3; j++) {
            for (int i = 0; i < 3; i++) {
                tg.drawRectangle(
                        new TerminalPosition(windowPaddingLeft + paddingLeft - 1 + ((columnWidth) * i), windowPaddingTop + paddingTop-1 + ((rowHeight) * j)),
                        new TerminalSize( columnWidth + 1, rowHeight + 1),
                        Symbols.BLOCK_MIDDLE);
            }
        }

        tg.drawRectangle(
                new TerminalPosition(windowPaddingLeft + paddingLeft - 1, windowPaddingTop+paddingTop-1),
                new TerminalSize(columnWidth * 3 + 1, rowHeight * 3 + 1),
                ' ');

    }

    private static void drawSidebar(TextGraphics tg){

        tg.drawLine(windowPaddingLeft + sidebar, windowPaddingTop, windowPaddingLeft +sidebar, windowPaddingTop + rows-1, Symbols.DOUBLE_LINE_VERTICAL);

        tg.drawLine(windowPaddingLeft, windowPaddingTop + sidebarPaddingTop,windowPaddingLeft +  sidebar, windowPaddingTop + sidebarPaddingTop, Symbols.DOUBLE_LINE_HORIZONTAL);
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
        tg.setCharacter(windowPaddingLeft + sidebar, windowPaddingTop + rows-1, Symbols.DOUBLE_LINE_T_UP);



        drawPlayerInfo(tg);

    }

    private static void drawGame(TextGraphics tg){
        tg.fill(' ');
        drawBorder(tg,0,0, columns-1, rows-1);
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
        tg.putString(windowPaddingLeft + 1, windowPaddingTop + sidebarPaddingTop + 2, "Score: " + game.getPlayer().getScore(), SGR.BOLD);
        tg.putString(windowPaddingLeft + 1, windowPaddingTop + sidebarPaddingTop + 6, "Player:", SGR.BOLD);
        tg.putString(windowPaddingLeft + 1, windowPaddingTop + sidebarPaddingTop + 7, game.getPlayer().getName());

        drawCurrentPlayer(tg);

        tg.putString(windowPaddingLeft + 1, windowPaddingTop + sidebarPaddingTop + 15, "WIN: "+game.getPlayer().getNumberOfWonGames());
        tg.putString(windowPaddingLeft + 1, windowPaddingTop + sidebarPaddingTop + 16, "LOST: "+game.getPlayer().getNumberOfLostGames());
        tg.putString(windowPaddingLeft + 1, windowPaddingTop + sidebarPaddingTop + 17, "TIES: "+game.getPlayer().getNumberOfTies());

    }

    private static void drawCurrentPlayer(TextGraphics tg) {
        if (game.getCurrentPlayer().equals("X")){
            tg.setForegroundColor(TextColor.ANSI.GREEN);
            tg.putString(windowPaddingLeft + 1,windowPaddingTop + sidebarPaddingTop + 11, "Your Turn", SGR.BLINK, SGR.BOLD);
        }else{
            tg.setForegroundColor(TextColor.ANSI.RED);
            tg.putString(windowPaddingLeft + 1,windowPaddingTop + sidebarPaddingTop + 11, "AI's Turn", SGR.BLINK, SGR.BOLD);
        }

        tg.setForegroundColor(TextColor.ANSI.DEFAULT);
        tg.setBackgroundColor(TextColor.ANSI.DEFAULT);
    }

    public static void drawBorder(TextGraphics tg, int startX, int startY, int endX, int endY){
        startX += windowPaddingLeft;
        endX += windowPaddingLeft;
        startY += windowPaddingTop;
        endY += windowPaddingTop;

        tg.drawLine(new TerminalPosition(startX, startY), new TerminalPosition(endX, startY), Symbols.DOUBLE_LINE_HORIZONTAL);
        tg.drawLine(new TerminalPosition(startX, endY), new TerminalPosition(endX, endY), Symbols.DOUBLE_LINE_HORIZONTAL);

        tg.drawLine(new TerminalPosition(startX, startY), new TerminalPosition(startX, endY), Symbols.DOUBLE_LINE_VERTICAL);
        tg.drawLine(new TerminalPosition(endX, startY), new TerminalPosition(endX, endY), Symbols.DOUBLE_LINE_VERTICAL);

        tg.setCharacter(startX, startY, Symbols.DOUBLE_LINE_TOP_LEFT_CORNER);
        tg.setCharacter(startX, endY, Symbols.DOUBLE_LINE_BOTTOM_LEFT_CORNER);
        tg.setCharacter(endX, startY, Symbols.DOUBLE_LINE_TOP_RIGHT_CORNER);
        tg.setCharacter(endX, endY, Symbols.DOUBLE_LINE_BOTTOM_RIGHT_CORNER);
    }


    public static boolean pauseMenu(TextGraphics tg) throws IOException {
        paused = true;

        drawPausedMenu(tg);

        terminal.flush();
        screen.refresh();

        while(true){
            KeyStroke keyStroke = terminal.pollInput();
            if(keyStroke != null){
                if (keyStroke.getKeyType() == KeyType.Escape){
                    break;
                }
                if (keyStroke.getKeyType() == KeyType.Character && keyStroke.getCharacter() == 'q'){
                    play = false;
                    return true;
                }
            }
        }
        paused = false;
        return false;
    }

    private static void drawPausedMenu(TextGraphics tg) {
        tg.setForegroundColor(TextColor.ANSI.YELLOW);
        drawBorder(tg,4, 2, columns-5, rows-3);
        tg.setForegroundColor(TextColor.ANSI.DEFAULT);
        tg.fillRectangle(new TerminalPosition(windowPaddingLeft+5,windowPaddingTop+3), new TerminalSize(columns-10, rows-6), ' ');

        tg.putString(windowPaddingLeft+7,windowPaddingTop+4,"Game Paused", SGR.BLINK, SGR.CIRCLED );
        tg.drawLine(windowPaddingLeft+8, windowPaddingTop+5,windowPaddingLeft+columns-8, windowPaddingTop+5, Symbols.SINGLE_LINE_HORIZONTAL);
        tg.putString(windowPaddingLeft+12,windowPaddingTop+8,"Press Q to quit game", SGR.CIRCLED );
        tg.setForegroundColor(TextColor.ANSI.RED);
        tg.putString(windowPaddingLeft+12+"Press ".length(), windowPaddingTop+8, "Q");
        tg.setForegroundColor(TextColor.ANSI.DEFAULT);
        tg.putString(windowPaddingLeft+12,windowPaddingTop+11,"Press ESC to continue", SGR.CIRCLED );
        tg.setForegroundColor(TextColor.ANSI.GREEN);
        tg.putString(windowPaddingLeft+12+"Press ".length(), windowPaddingTop+11, "ESC");
        tg.setForegroundColor(TextColor.ANSI.DEFAULT);

        drawX(tg, 55,7);
        drawO(tg, 55,14);
        drawX(tg, 42,14);
    }


    public static void main(String[] args) throws IOException, InterruptedException {

        game = new TicTacToe(new Player("Janek"));
        TicTacToeLogic logic = new TicTacToeLogic(game);

        setUpTerminalAndScreen();

        rows = terminal.getTerminalSize().getRows();
        columns = terminal.getTerminalSize().getColumns();

        prevRows = rows;
        prevCols = columns;

        rowHeight = (rows - 1) / 3;
        columnWidth = ((columns - sidebar - 6) / 3) - 1;
        paddingLeft = sidebar + paddingLeftSidebar;
        sidebarPaddingTop = (rows-20)/2;

        TextColor bgColor;
        TextColor fgColor;

        while (play) {
            drawBorder(tg, 0,0,columns-1, rows-1);
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
                                if (!result.equals("TIE"))
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

    private static void setUpTerminalAndScreen() throws IOException {
        terminal = new DefaultTerminalFactory().createTerminal();
        terminal.setCursorVisible(false);

        screen = new TerminalScreen(terminal);
        screen.doResizeIfNecessary();
        screen.setCursorPosition(null);

        tg = screen.newTextGraphics();

        terminal.addResizeListener((terminal, terminalSize) -> {
            try {

                int currRows = terminal.getTerminalSize().getRows();
                int currColumns = terminal.getTerminalSize().getColumns();

                int deltaColumns = currColumns - prevCols;
                int deltaRows = currRows - prevRows;

                if(deltaColumns%2==0){
                    prevCols = currColumns;
                    windowPaddingLeft+=deltaColumns/2;
                }

                if(deltaRows%2==0){
                    prevRows = currRows;
                    windowPaddingTop+=deltaRows/2;
                }

                screen.doResizeIfNecessary();

                drawGame(tg);

                if (paused)
                    drawPausedMenu(tg);

                terminal.flush();
                screen.refresh();

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        screen.startScreen();
    }


}
