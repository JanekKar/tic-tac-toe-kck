import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.awt.*;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) throws IOException {
//        Terminal terminal = new DefaultTerminalFactory().createTerminal();
////        terminal.setCursorVisible(false);
//        Screen screen = new TerminalScreen(terminal);
//
//        TextGraphics tg = screen.newTextGraphics();
//        screen.startScreen();
//
//        tg.drawLine(4, 10, 200, 100 , 'c');
//
//        tg.setForegroundColor(TextColor.ANSI.MAGENTA);
//        tg.setBackgroundColor(TextColor.ANSI.CYAN);
//            /*
//            putString(..) exists in a couple of different flavors but it generally works by taking a string and
//            outputting it somewhere in terminal window. Notice that it doesn't take the current position of the text
//            cursor when doing this.
//             */
//        tg.putString(2, 1, "Lanterna Tutorial 2 - Press ESC to exit", SGR.BOLD);
//        tg.setForegroundColor(TextColor.ANSI.DEFAULT);
//        tg.setBackgroundColor(TextColor.ANSI.DEFAULT);
//        tg.putString(5, 3, "Terminal Size: ", SGR.BOLD);
//        tg.putString(5 + "Terminal Size: ".length(), 3, terminal.getTerminalSize().toString());
//
//
//        terminal.flush();
//
//        screen.refresh();
//
//        screen.readInput();
//        screen.startScreen();

        Scanner scanner = new Scanner(System.in);

        TicTacToe game = new TicTacToe();


        int oTurn = 1;
        boolean play_game = true;

        while (play_game){
            int x,y;
            do {
                if (oTurn == 0)
                    System.out.println("X Moves");
                else
                    System.out.println("O Moves");

                x = scanner.nextInt();
                y = scanner.nextInt();
            }
            while( !game.checkIfFree( new Point(x, y)) );

            game.put(++oTurn, new Point(x, y));

            game.printBoard();

            boolean a = game.checkForWin() , b = game.checkForTie();

            play_game = !(a || b);
            System.out.println("win="+ a + " tie=" + b + " play_game="+ play_game);

            oTurn%=2;
            System.out.println(oTurn);
        }
        System.out.println(Arrays.toString(game.getWinningPositions()));
        System.out.println(game.getWinnerId());
    }



}