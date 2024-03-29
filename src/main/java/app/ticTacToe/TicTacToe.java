package app.ticTacToe;

import java.awt.*;

public class TicTacToe {

    private static TicTacToe instance;
    private final String[] players = {"X", "O"};
    protected String blank = "";
    protected String[][] board = new String[3][3];
    private int currentPlayer;
    private int available;
    private Player player;
    private Point[] winningPositions;
    private String winner;
    private int bonus;
    private int gameNumber = 0;

    private TicTacToe() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = blank;
                available++;
            }
        }
        currentPlayer = 0;
    }

    public static TicTacToe getInstance() {
        if (instance == null)
            instance = new TicTacToe();
        return instance;
    }

    public boolean equal3(String a, String b, String c) {
        return a.equals(b) && b.equals(c) && !a.equals(blank);
    }

    // null - game on, "TIE" - tie, "X" - x won, "O" - o won
    public String checkWinner() {
        String winner = null;

        // horizontal
        for (int i = 0; i < 3; i++) {
            if (equal3(board[i][0], board[i][1], board[i][2])) {
                winner = board[i][0];
                winningPositions = new Point[]{new Point(i, 0), new Point(i, 1), new Point(i, 2)};
            }
        }

        // vertical
        for (int i = 0; i < 3; i++) {
            if (equal3(board[0][i], board[1][i], board[2][i])) {
                winner = board[0][i];
                winningPositions = new Point[]{new Point(0, i), new Point(1, i), new Point(2, i)};
            }
        }

        // diagonal
        if (equal3(board[0][0], board[1][1], board[2][2])) {
            winner = board[0][0];
            winningPositions = new Point[]{new Point(0, 0), new Point(1, 1), new Point(2, 2)};

        }
        if (equal3(board[2][0], board[1][1], board[0][2])) {
            winner = board[2][0];
            winningPositions = new Point[]{new Point(2, 0), new Point(1, 1), new Point(0, 2)};

        }

        if (winner == null && available == 0) {
            winningPositions = null;
            return "TIE";
        } else {
            this.winner = winner;
            return winner;
        }
    }

    public boolean checkIfFree(Point point) {
        return board[point.x][point.y].equals(blank);
    }

    public void makeMove(Point point) {
        if (board[point.x][point.y].equals(blank)) {
            available--;
            board[point.x][point.y] = players[currentPlayer];
            currentPlayer++;
            currentPlayer %= 2;
        }
    }

    public void makeMove(int x, int y, String player) {
        board[x][y] = player;
        available--;
    }

    public void undoMove(int x, int y) {
        board[x][y] = blank;
        available++;
    }

    public void nextGame() {
        player.setScore(calcScore());

        if (winner != null)
            if (winner.equals("X"))
                player.increaseNumberOfWonGames();
            else
                player.increaseNumberOfLostGames();
        else
            player.increaseNumberOfTies();

        available = 0;


        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = blank;
                available++;
            }
        }

        gameNumber++;
        currentPlayer = 0;
    }

    public boolean isEndOfSession() {
        return gameNumber == 10;
    }

    private int calcScore() {
        int score = player.getScore();
        if (winner != null) {
            if (winner.equals("X")) {
                score += bonus * 2;
                score += available * 5;
            } else
                score -= available * 5;
        } else
            score += bonus;

        if (score < 0)
            return 0;
        return score;
    }

    public Point[] getWinningPositions() {
        return winningPositions;
    }

    public String getFieldContent(Point point) {
        return board[point.x][point.y];
    }

    public String getCurrentPlayer() {
        return players[currentPlayer];
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void debugPrintBoard() {
        for (String[] row : this.board) {
            for (String cell : row) {
                if (cell.equals("")) {
                    System.out.print("_");
                } else {
                    System.out.print(cell);
                }
            }
            System.out.println();
        }
    }

    public String getBlank() {
        return this.blank;
    }

    public String[][] getBoard() {
        return this.board;
    }

    public boolean isNewBest() {
        return BestScoreManager.getInstance().addPlayer(player);
    }

    public void setBonus(int i) {
        this.bonus = i;
    }

    public int getGameNo() {
        return gameNumber;
    }

    public void endSession() {
        gameNumber = 0;
    }
}
