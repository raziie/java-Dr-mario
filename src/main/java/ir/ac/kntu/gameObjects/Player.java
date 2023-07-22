package ir.ac.kntu.gameObjects;

import java.io.Serializable;

public class Player implements Serializable {

    private String name;

    private int numberOfGames;

    private int highScore;

    public Player(String name) {
        this.name = name;
        numberOfGames = 0;
        highScore = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberOfGames() {
        return numberOfGames;
    }

    public void setNumberOfGames(int numberOfGames) {
        this.numberOfGames = numberOfGames;
    }

    public int getHighScore() {
        return highScore;
    }

    public void updateHighScore(int score) {
        if(score > highScore) {
            this.highScore = score;
        }
    }

    @Override
    public String toString() {
        return "name:" + name + "|games:" + numberOfGames + "|highScore:" + highScore;
    }
}
