import com.googlecode.lanterna.*;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.graphics.TextImage;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import org.w3c.dom.Text;

import java.awt.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;





public class Main {
    static int score =100;
    static String playerName = "Janek";

    static int tempRowsSize, tempCollSize, xWidth, xHeight, paddingLeft, paddingTop, fieldOffset;

    public static void drawX(TextGraphics tg, int x, int y){
        tg.drawLine(new TerminalPosition(paddingLeft+fieldOffset+(x*tempCollSize), paddingTop+(tempRowsSize*y)), new TerminalPosition(paddingLeft+xWidth+fieldOffset+ (x*tempCollSize), paddingTop+xHeight+(tempRowsSize*y)), 'O');
        tg.drawLine(new TerminalPosition(paddingLeft+xWidth+fieldOffset+ (x*tempCollSize), paddingTop+(tempRowsSize*y)), new TerminalPosition(paddingLeft+fieldOffset+(x*tempCollSize), paddingTop+xHeight+(tempRowsSize*y)), 'O');
    }

    public static void highlightField(TextGraphics tg, int x, int y){
        tg.setBackgroundColor(TextColor.ANSI.GREEN);
        tg.fillRectangle(new TerminalPosition(paddingLeft+(x*tempCollSize), paddingTop+(y*tempRowsSize)), new TerminalSize(tempCollSize-1, tempRowsSize-1), ' ');
        // TODO if contains O or X Draw it
        drawX(tg, x, y);
        tg.setBackgroundColor(TextColor.ANSI.DEFAULT);
    }

    public static void unHighlightField(TextGraphics tg, int x, int y){
        tg.setBackgroundColor(TextColor.ANSI.DEFAULT);
        tg.fillRectangle(new TerminalPosition(paddingLeft+(x*tempCollSize), paddingTop+(y*tempRowsSize)), new TerminalSize(tempCollSize-1, tempRowsSize-1), ' ');
        // TODO if contains O or X Draw it
        drawX(tg, x, y);
    }

    public static void drawCircle(TextGraphics tg, int x, int y){

    }

    public static void main(String[] args) throws IOException {
        Terminal terminal = new DefaultTerminalFactory().createTerminal();
        terminal.setCursorVisible(true);
        Screen screen = new TerminalScreen(terminal);
        screen.doResizeIfNecessary();

        TextGraphics tg = screen.newTextGraphics();
        screen.startScreen();

        tg.putString(1,1 ,"Score: " + score,SGR.BOLD);
        int sidebar = 12;
        int columns = terminal.getTerminalSize().getColumns();
        int rows = terminal.getTerminalSize().getRows();

        tg.drawLine(sidebar, 0, sidebar, rows, '|');

        tg.drawLine(0, 3, sidebar, 3, '-');

        tg.putString(sidebar, 3, "+");


        tg.putString(1,5 ,"Player: ",SGR.BOLD);
        tg.putString(1,6 ,playerName);


        tg.setForegroundColor(TextColor.ANSI.MAGENTA);
        tg.setBackgroundColor(TextColor.ANSI.CYAN);


        tg.setForegroundColor(TextColor.ANSI.DEFAULT);
        tg.setBackgroundColor(TextColor.ANSI.DEFAULT);

        tempRowsSize = (rows-1)/3;
        tempCollSize = ((columns-sidebar-6)/3)-1;
        xWidth = 11;
        xHeight = 5;
        paddingLeft = sidebar+6;
        paddingTop = 2;
        fieldOffset = 3;

        for(int j=0; j<3; j++){
            for(int i=0; i<3;i++){
                tg.drawRectangle(new TerminalPosition(paddingLeft-1+((tempCollSize)*i), 1+((tempRowsSize)*j)), new TerminalSize(tempCollSize+1,tempRowsSize+1), '#');
            }
        }

        tg.drawRectangle(new TerminalPosition(paddingLeft-1, 1), new TerminalSize(tempCollSize*3+1, tempRowsSize*3+1), ' ');


        for (int j = 0; j < 3; j++) {
            for (int i=0; i<3; i++){
                drawX(tg, i, j);
            }
        }

        int x = 0;
        int y = 0;

        highlightField(tg, x,y);



        terminal.flush();

        screen.refresh();

        screen.startScreen();

        while(true) {
            KeyStroke keyStroke = terminal.pollInput();
            if (keyStroke != null){
                unHighlightField(tg, x, y);

                if (keyStroke.getKeyType() == KeyType.ArrowDown && y<2)
                    y++;
                if (keyStroke.getKeyType() == KeyType.ArrowUp && y>0)
                    y--;
                if (keyStroke.getKeyType() == KeyType.ArrowRight && x<2)
                    x++;
                if (keyStroke.getKeyType() == KeyType.ArrowLeft && x>0)
                    x--;

                if (keyStroke.getKeyType() == KeyType.Escape){
                    break;
                }

                highlightField(tg, x, y);
                terminal.flush();
                screen.refresh();
            }
        }
        terminal.close();




        /*
            putString(..) exists in a couple of different flavors but it generally works by taking a string and
            outputting it somewhere in terminal window. Notice that it doesn't take the current position of the text
            cursor when doing this.
             */
//        tg.putString(2, 1, "Lanterna Tutorial 2 - Press ESC to exit", SGR.BOLD);
//        tg.putString(5, 3, "Terminal Size: ", SGR.BOLD);
//        tg.putString(5 + "Terminal Size: ".length(), 3, terminal.getTerminalSize().toString());


        /*

        Scanner scanner = new Scanner(System.in);

        TicTacToe game = new TicTacToe();
        TicTacToeLogic logic = new TicTacToeLogic(game);

//        game.put(1, new Point(1,2));



        boolean play_game = true;

        while (play_game){

//            int x,y;
//            do {
//                System.out.println("O Moves");
//
//                x = scanner.nextInt();
//                y = scanner.nextInt();
//            }
//            while( !game.checkIfFree( new Point(x, y)) );

//            game.put(2, new Point(x,y));
            System.out.println("O Moves");
            game.put(2, logic.random());
            game.printBoard();
            int i = game.checkForWin();
//            System.out.println(i);
            switch(i){
                case 0:
                    break;
                case -1:
                    play_game = false;
                    break;
                case 1:
                    System.out.println("Player 1 won");
                    play_game = false;
                    break;

                case 2:
                    System.out.println("Player 2 won");
                    play_game = false;
                    break;


            }

            if(!play_game)
                break;

//            game.put(++oTurn, logic1.random());

            System.out.println("X Moves");
            game.put(1, logic.bestMove());
            game.printBoard();


            i = game.checkForWin();
//            System.out.println(i);
            switch(i){
                case 0:
                    break;
                case -1:
                    play_game = false;
                    System.out.println("Tie");
                    break;
                case 1:
                    System.out.println("Player 1 won");
                    play_game = false;
                    break;

                case 2:
                    System.out.println("Player 2 won");
                    play_game = false;
                    break;


            }
        }
        System.out.println(game.getWinnerId());
        */
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
