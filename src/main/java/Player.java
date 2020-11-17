import java.util.UUID;

public class Player {
    private String name;
    private int score;
    private int numberOfWonGames, numberOfLostGames;
    private UUID global_id;

    public Player(String name){
        this.name = name;
        this.global_id = UUID.randomUUID();
        this.score = 0;
        this.numberOfWonGames = 0;
        this.numberOfLostGames = 0;
    }

    public void increaseNumberOfWonGames(){
        this.numberOfWonGames++;
    }

    public void increaseNumberOfLostGames(){
        this.numberOfLostGames++;
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

}
