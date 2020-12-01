package app.ticTacToe;

import java.util.UUID;

public class Player implements Comparable{
    private final String name;
    private int score;
    private int numberOfWonGames;
    private int numberOfLostGames;

    private int numberOfTies;

    public Player(String name) {
        this.name = name;
        this.score = 0;
        this.numberOfWonGames = 0;
        this.numberOfLostGames = 0;
    }

    public Player(String name, int score, int numberOfWonGames, int numberOfLostGames, int numberOfTies) {
        this.name = name;
        this.score = score;
        this.numberOfWonGames = numberOfWonGames;
        this.numberOfLostGames = numberOfLostGames;
        this.numberOfTies = numberOfTies;
    }

    public void increaseNumberOfWonGames() {
        this.numberOfWonGames++;
    }

    public void increaseNumberOfLostGames() {
        this.numberOfLostGames++;
    }

    public void increaseNumberOfTiess() {
        this.numberOfTies++;
    }


    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getNumberOfWonGames() {
        return numberOfWonGames;
    }

    public int getNumberOfLostGames() {
        return numberOfLostGames;
    }

    public int getNumberOfTies() {
        return numberOfTies;
    }

    public void setNumberOfTies(int numberOfTies) {
        this.numberOfTies = numberOfTies;
    }

    @Override
    public String toString() {
        return  name +
                ';' + score +
                ';' + numberOfWonGames +
                ';' + numberOfLostGames +
                ';' + numberOfTies;
    }

    @Override
    public int compareTo(Object o) {
        return ((Player)o).getScore()-this.getScore();
    }
}
