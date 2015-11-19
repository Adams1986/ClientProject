package sdk;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;

import java.util.ArrayList;

/**
 * Api class. Constains generic methods that send and receive data in a String/json format. All the parsing of the json
 * data is handled in the Logic sub-controller classes.
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

    /**
     * returns a single user from the user id, in a json string format
     * @param userId
     * @return
     */
    public static String getUser(int userId){

        return ServerConnection.get(Config.getServerPathUsers() + userId);
    }

    //TODO: handle toJson in controller instead, so only sends and receives Strings
    public static String createUser(User user){

        return ServerConnection.post(Config.getServerPathUsers(), new Gson().toJson(user));
    }

    public static String createGame(Game game){

        return ServerConnection.post(Config.getServerPathGames(), new Gson().toJson(game));
    }

    public static String joinGame(String gameJson){

        return ServerConnection.put(Config.getServerPathJoinGames(), gameJson);
    }


    //TODO: skal vi overhovedet bruge dette game? hvordan skal error jsonDatas håndteres? Måske bare returnere string og så håndtere hvorvidt det skal parses eller laves om til json et andet sted
    public static String startGame(String gameJson) {

        return ServerConnection.put(Config.getServerPathStartGames(), gameJson);
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

    public static String getOpenGames(int userId){

        return ServerConnection.get(Config.getServerPathOpenGames());

        //return new Gson().fromJson(jsonData, new TypeToken<ArrayList<Game>>(){}.getType());
    }

    public static String getScoresByUserId(int userId){

        return ServerConnection.get(Config.getServerPathScores() + userId);
    }
}
