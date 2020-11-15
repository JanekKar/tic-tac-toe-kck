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
        TicTacToeLogic logic = new TicTacToeLogic(game, 1);

//        game.put(1, new Point(1,2));

        logic.minmax(game.board, (byte) 2);


        int oTurn = 1;
        boolean play_game = false;

        while (play_game){
            /*
            int x,y;
            do {
                if (oTurn == 0)
                    System.out.println("X Moves");
                else
                    System.out.println("O Moves");

//                x = scanner.nextInt();
//                y = scanner.nextInt();
            }
            while( !game.checkIfFree( new Point(x, y)) );
            */
            game.put(++oTurn, logic.random());

            game.printBoard();

            boolean a = game.checkForWin() , b = game.checkForTie();

            play_game = !(a || b);
            System.out.println("win="+ a + " tie=" + b + " play_game="+ play_game);

            oTurn%=2;
        }
        System.out.println(Arrays.toString(game.getWinningPositions()));
        System.out.println(game.getWinnerId());
    }



}

//
//import java.nio.charset.Charset;
//
//import com.googlecode.lanterna.TerminalFacade;
//import com.googlecode.lanterna.gui.*;
//import com.googlecode.lanterna.gui.Component.Alignment;
//import com.googlecode.lanterna.gui.component.*;
//import com.googlecode.lanterna.gui.component.Panel.Orientation;
//import com.googlecode.lanterna.gui.layout.LinearLayout;
//import com.googlecode.lanterna.gui.layout.VerticalLayout;
//import com.googlecode.lanterna.screen.Screen;
//import com.googlecode.lanterna.screen.ScreenCharacterStyle;
//import com.googlecode.lanterna.terminal.Terminal;
//import com.googlecode.lanterna.terminal.TerminalSize;
//
//public class Main {
//    public static void test1() {
////final GUIScreen guiScreen = TerminalFacade.createGUIScreen();
//        Terminal terminal = TerminalFacade.createTerminal(Charset.forName("UTF8"));
//        terminal.enterPrivateMode();
//        terminal.moveCursor(10, 10);
//        terminal.putCharacter('W');
//        terminal.putCharacter('i');
//        terminal.putCharacter('t');
//        terminal.putCharacter('a');
//        terminal.putCharacter('m');
//        terminal.putCharacter(' ');
//        terminal.putCharacter('s');
//        terminal.putCharacter('t');
//        terminal.putCharacter('u');
//        terminal.putCharacter('d');
//        terminal.putCharacter('e');
//        terminal.putCharacter('n');
//        terminal.putCharacter('t');
//        terminal.putCharacter('a');
//        terminal.putCharacter('!');
//        terminal.moveCursor(0, 0);
//        Thread.currentThread();
//        while(terminal.readInput()==null){
//            try {
//                Thread.sleep(100);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//        terminal.exitPrivateMode();
//    }
//
//    public static void test2() {
////Terminal terminal = TerminalFacade.createTerminal(Charset.forName("UTF8"));
//        Screen screen = TerminalFacade.createScreen();
//        screen.startScreen();
//        screen.putString(4, 4, "Witam studenta ponownie!", Terminal.Color.BLUE, Terminal.Color.BLACK, ScreenCharacterStyle.Bold);
////false, false, false);
//        screen.refresh();
//        Thread.currentThread();
//        while(screen.readInput()==null){
//            try {
//                Thread.sleep(100);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//        screen.stopScreen();
//    }
//
//    public static void test3() {
//        final GUIScreen guiScreen = TerminalFacade.createGUIScreen();
//        final Window window = new Window("TicTacToe");
//        window.setWindowSizeOverride(new TerminalSize(100,50));
//        window.setSoloWindow(true);
//
//        Panel panel = new Panel("Main Menu");
//        panel.setAlignment(Alignment.FILL);
//
////        panel.setLayoutManager(new VerticalLayout());
//        Button button = new Button("Button");
//        button.setAlignment(Component.Alignment.RIGHT_CENTER);
//        panel.addComponent(button, LinearLayout.GROWS_HORIZONTALLY);
//
//        Table table = new Table(1);
//        table.setColumnPaddingSize(5);
//
//        Component[] row1 = new Component[4];
//        row1[0] = new Button("Start Game");
//        row1[1] = new Button("Score");
//        row1[2] = new Button("Settings");
//
//        ActionListBox temp  = new ActionListBox();
//
//        temp.addAction("Easy",null);
//        temp.addAction("Medium",null);
//        temp.addAction("Hard",null);
//
//        row1[3]=temp;
//        for(int i=0; i<row1.length;i++){
//            row1[i].setAlignment(Alignment.FILL);
//            table.addRow(row1[i]);
//        }
//
//        panel.addComponent(table);
//
////        panelHolder.addComponent(panel);
//
//        window.addComponent(panel);
////        window.addComponent(new EmptySpace());
//
//        /*
//        final Window newWindow = new Window("This window should be of the same size as the previous one");
//
//        Button quitButton = new Button("Show next window", new Action() {
//
//            public void doAction() {
//
//                newWindow.setWindowSizeOverride(new TerminalSize(100,50));
//                newWindow.setSoloWindow(true);
//
//                Button exitBtn = new Button("Exit", new Action() {
//
//                    public void doAction() {
//                        newWindow.close();
//                        window.close();
//                    }
//                });
//
//                exitBtn.setAlignment(Alignment.RIGHT_CENTER);
//
//                newWindow.addComponent(exitBtn, LinearLayout.GROWS_HORIZONTALLY);
//
//                guiScreen.showWindow(newWindow);
//            }
//        });
//        quitButton.setAlignment(Component.Alignment.RIGHT_CENTER);
//        window.addComponent(quitButton, LinearLayout.GROWS_HORIZONTALLY);
//
//         */
//        guiScreen.getScreen().startScreen();
//        guiScreen.showWindow(window);
//        guiScreen.getScreen().stopScreen();
//
//    }
//    /**
//     * @param args
//     */
//    public static void main(String[] args) {
////        test1();
//// test2();
// test3();
//    }
//
//}
