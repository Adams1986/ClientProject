package sdk;

import java.awt.*;
import java.util.LinkedList;

public class Gamer extends User {

    private int score;
    private int totalScore;
    private int kills;
    private String controls;
    private boolean winner;
    private LinkedList<Point> snake;
    private Color snakeColor;

    public boolean isWinner() {
        return winner;
    }

    public void setWinner(boolean winner) {
        this.winner = winner;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public void setControls(String controls) {
        this.controls = controls;
    }

    public int getKills() {
        return kills;
    }

    public int getScore() {
        return score;
    }

    public String getControls() {
        return controls;
    }

    public LinkedList<Point> getSnake() {
        return snake;
    }

    public void setSnake(LinkedList<Point> snake) {
        this.snake = snake;
    }

    public Color getSnakeColor() {
        return snakeColor;
    }

    public void setSnakeColor(Color snakeColor) {
        this.snakeColor = snakeColor;
    }
}
