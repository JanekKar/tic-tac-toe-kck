package app.ticTacToe;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class BestScoreManager {

    private static BestScoreManager instance = null;
    File bestScoreFile;
    List<Player> bestScoreList;
    int totalPlayedGames = 0;


    private BestScoreManager() {
        bestScoreList = new ArrayList<>();
        try {
            bestScoreFile = new File(".score.jk");
            if (!bestScoreFile.createNewFile()) {
                Scanner scanner = new Scanner(bestScoreFile);

                if (scanner.hasNextLine()) {
                    totalPlayedGames = Integer.parseInt(scanner.nextLine());

                }

                while (scanner.hasNextLine()) {
                    String data = scanner.nextLine();
                    String[] playerInfo = data.split(";");
                    bestScoreList.add(
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
    }

    public static BestScoreManager getInstance() {
        if (instance == null)
            instance = new BestScoreManager();
        return instance;
    }

    public void addNewBestScore(Player player) {
        if (bestScoreList.size() == 5) {
            bestScoreList.remove(4);
        }
        bestScoreList.add(player);
        Collections.sort(bestScoreList);
    }

    public void save() {
        try {
            bestScoreFile = new File(".score.jk");
            if (!bestScoreFile.createNewFile()) {
                PrintWriter fileWriter = new PrintWriter(bestScoreFile);
                fileWriter.println(totalPlayedGames);
                for (Player player : bestScoreList) {
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

    public List<Player> getBestScoreList() {
        return bestScoreList;
    }

    public boolean addPlayer(Player player) {
        if (player.getScore() > this.getLowestScore()) {
            addNewBestScore(player);
            return true;
        }
        return false;
    }

    private int getLowestScore() {
        if (bestScoreList.size() != 0) {
            if (bestScoreList.size() < 5)
                return 0;
            Collections.sort(bestScoreList);
            return bestScoreList.get(bestScoreList.size() - 1).getScore();
        }
        return 0;
    }

    public void clearBestScoreList() {
        this.bestScoreList.clear();
    }
}
