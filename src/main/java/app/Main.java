package app;

import app.cli.Config;
import app.cli.Game;
import app.gui.GUIManager;
import app.ticTacToe.BestScoreManager;
import app.ticTacToe.TicTacToe;
import app.ticTacToe.logic.TicTacToeLogic;

import java.awt.*;
import java.io.IOException;

public class Main {



    public static TicTacToe game;
    public static TicTacToeLogic logic;

    private static boolean guiMode = false;

    public static void main(String[] args){
        if(args.length >0 ){
            for(String arg : args){
                switch (arg){
                    case "--gui":
                        guiMode = true;
                        break;
                }
            }
        }



        BestScoreManager bestScoreManager = BestScoreManager.getInstance();

        game = TicTacToe.getInstance();
        logic = null;

        if(guiMode){
            GUIManager.runWindowMode();
        }else{
            Game.setupGameConfig();
            try {
                Game.mainLoop(Config.tg);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            bestScoreManager.save();

            Config.getInstance().saveConfig();
            try {
                Config.closeGame();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }




    }

}

