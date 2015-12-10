package sdk;

import sdk.dto.Game;
import sdk.dto.Gamer;
import sdk.dto.Score;
import sdk.dto.User;

import java.util.ArrayList;

/**
 * Class not used yet. Mentioned in the report for future, better handling of server responses
 */
public class DataHelper {

    private String serverMessage;
    private int serverStatusCode;
    private User user;
    private Gamer gamer;
    private Game game;
    private Score score;
    private ArrayList<User> users;
    private ArrayList<Game>games;
    private ArrayList<Score>scores;

    public DataHelper (){

    }

    public String getServerMessage() {
        return serverMessage;
    }

    public void setServerMessage(String serverMessage) {
        this.serverMessage = serverMessage;
    }

    public int getServerStatusCode() {
        return serverStatusCode;
    }

    public void setServerStatusCode(int serverStatusCode) {
        this.serverStatusCode = serverStatusCode;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Gamer getGamer() {
        return gamer;
    }

    public void setGamer(Gamer gamer) {
        this.gamer = gamer;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Score getScore() {
        return score;
    }

    public void setScore(Score score) {
        this.score = score;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public ArrayList<Game> getGames() {
        return games;
    }

    public void setGames(ArrayList<Game> games) {
        this.games = games;
    }

    public ArrayList<Score> getScores() {
        return scores;
    }

    public void setScores(ArrayList<Score> scores) {
        this.scores = scores;
    }
}

