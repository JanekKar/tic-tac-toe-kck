package app.ticTacToe;

import java.io.*;
import java.util.*;

public class PlayerScores {

    private static PlayerScores instance = null;
    File pScores, config;
    List<Player> playersList;
    int totalPlayedGames = 0;



    private PlayerScores(){
        playersList = new ArrayList<>();
        try {
            pScores = new File(".players.jk");
            if(!pScores.createNewFile()){
                Scanner scanner = new Scanner(pScores);

                if (scanner.hasNextLine()){
                    totalPlayedGames = Integer.parseInt(scanner.nextLine());

                }

                while(scanner.hasNextLine()){
                    String data = scanner.nextLine();
                    String[] playerInfo = data.split(";");
                    playersList.add(
                            new Player(playerInfo[0],
                                    Integer.parseInt(playerInfo[1]),
                                    Integer.parseInt(playerInfo[2]),
                                    Integer.parseInt(playerInfo[3]),
                                    Integer.parseInt(playerInfo[4])));
                }

                scanner.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(playersList);
    }


    public void addNeBestScore(Player player){
        if(playersList.size() == 5){
            playersList.remove(4);
        }
        playersList.add(player);
        Collections.sort(playersList);
     }

    public static PlayerScores getInstance() {
        if (instance == null)
            instance = new PlayerScores();
        return instance;
    }

    public void save() {
        try {
            pScores = new File(".players.jk");
            if(!pScores.createNewFile()){
                PrintWriter fileWriter = new PrintWriter(pScores);
                fileWriter.println(totalPlayedGames);
                for(Player player : playersList){
                    fileWriter.println(player.toString());
                }
                fileWriter.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
