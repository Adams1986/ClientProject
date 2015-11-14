package sdk;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;

import java.util.ArrayList;

/**
 * TODO: Maybe class should only pass data String through via ServerConnection and then logic can handle how to parse the data otherwise hard to both have jsonDatas and DAO!
 */
public class Api {

    /**
     * Authenticates a user passed in the parameter to see if user exists with username and password
     * @param userJson
     * @return String jsonData with info regarding success of login attempt
     */
    public static String authenticateLogin(String userJson) {

        return ServerConnection.post(Config.getServerPathLogin(), userJson);
    }

    /**
     * Retrieves all users from the server and saves them in an arraylist
     * @return Arraylist of all users/gamers
     */
    public static String getUsers() {

        return ServerConnection.get(Config.getServerPathUsers());
    }

    public static String getUser(int userId){

        return ServerConnection.get(Config.getServerPathGames() + userId);
    }

    public static String createUser(User user){

        return ServerConnection.post(Config.getServerPathUsers(), new Gson().toJson(user));
    }

    public static String createGame(Game game){

        return ServerConnection.post(Config.getServerPathGames(), new Gson().toJson(game));
    }

    public static String joinGame(Game game){

        return ServerConnection.put(Config.getServerPathJoinGames(), new Gson().toJson(game));
    }


    //TODO: skal vi overhovedet bruge dette game? hvordan skal error jsonDatas håndteres? Måske bare returnere string og så håndtere hvorvidt det skal parses eller laves om til json et andet sted
    public static String startGame(int gameId) {

        return ServerConnection.get(Config.getServerPathStartGames() + gameId);
    }

    public static String deleteGame(int gameId) {

        return ServerConnection.delete(Config.getServerPathGames() + gameId);
    }

    public static String getGame(int gameId){

        return ServerConnection.get(Config.getServerPathGame() + gameId);
    }

    public static String getHighScores(){

        return ServerConnection.get(Config.getServerPathScores());
    }


    public static String getGamesByUserID(int userId){

        return ServerConnection.get(Config.getServerPathGames());

        //return new Gson().fromJson(jsonData, new TypeToken<ArrayList<Game>>(){}.getType());
    }


    public static String getGamesByStatusAndUserId(String status, int userId){

        //TODO: forwardslash...
        return ServerConnection.get(Config.getServerPathGames() + status + "/" + userId);
    }

    public static String getGamesInvitedByID(int userId){

        return ServerConnection.get(Config.getServerPathGamesInvitedById() + userId);
    }

    public static String getGamesHostedById(int userId){

        return ServerConnection.get(Config.getServerPathGamesHostedById() + userId);

        //return new Gson().fromJson(jsonData, new TypeToken<ArrayList<Game>>(){}.getType());
    }

    public static String getOpenGames(){

        return ServerConnection.get(Config.getServerPathOpenGames());

        //return new Gson().fromJson(jsonData, new TypeToken<ArrayList<Game>>(){}.getType());
    }

    public static String getScoresByUserId(int userId){

        return ServerConnection.get(Config.getServerPathScores() + userId);
    }
}
