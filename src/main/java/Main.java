import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

import java.io.IOException;
import java.util.Arrays;

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


        TicTacToe game = new TicTacToe();

        try {
            game.put('x', 1,1 );
            game.put('O', 2,1 );
            game.put('X', 0,1 );
            game.put('0', 1,2 );
        } catch (TicTacToe.WrongCharException e) {
            System.out.println(e.getMessage());
        }
        game.printBoard();

    }


}