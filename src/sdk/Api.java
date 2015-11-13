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
     * @param user
     * @return String jsonData with info regarding success of login attempt
     */
    public static String authenticateLogin(User user) {

        String jsonData = ServerConnection.post(Config.getServerPathLogin(), new Gson().toJson(user));

        return MessageParser.parseMessage(jsonData, user);
    }

    /**
     * Retrieves all users from the server and saves them in an arraylist
     * @return Arraylist of all users/gamers
     */
    public static ArrayList<User> getUsers() {

        ArrayList<User> users;
        String jsonData = ServerConnection.get(Config.getServerPathUsers());

        users = new Gson().fromJson(jsonData, new TypeToken<ArrayList<User>>(){}.getType());

        return users;
    }

    public static User getUser(int userId){

        String jsonData = ServerConnection.get(Config.getServerPathGames() + userId);

        return new Gson().fromJson(jsonData, User.class);
    }

    public static String createUser(User user){

        String jsonData = ServerConnection.post(Config.getServerPathUsers(), new Gson().toJson(user));

        return MessageParser.parseMessage(jsonData);
    }

    public static Game createGame(Game game){

        String jsonData = ServerConnection.post(Config.getServerPathGames(), new Gson().toJson(game));

        return new Gson().fromJson(jsonData, Game.class);
        //return MessageParser.parseMessage(jsonData);
    }

    public static String joinGame(Game game){

        String jsonData = ServerConnection.put("games/join/", new Gson().toJson(game));

        return MessageParser.parseMessage(jsonData);
    }


    //TODO: skal vi overhovedet bruge dette game? hvordan skal error jsonDatas håndteres? Måske bare returnere string og så håndtere hvorvidt det skal parses eller laves om til json et andet sted
    public static Game startGame(int gameId) {

        String jsonData = ServerConnection.get(Config.getServerPathGames()+ "/start/" + gameId);

        return new Gson().fromJson(jsonData, Game.class);

    }

    public static String deleteGame(int gameId) {

        return ServerConnection.delete(Config.getServerPathGames() + gameId);

    }

    public static Game getGame(int gameId){

        String jsonData = ServerConnection.get(Config.getServerPathGame() + gameId);

        return new Gson().fromJson(jsonData, Game.class);
    }

    public static ArrayList<Score> getHighScores(){

        String jsonData = ServerConnection.get(Config.getServerPathScores());

        return new Gson().fromJson(jsonData, new TypeToken<ArrayList<Score>>(){}.getType());
    }


    public static ArrayList<Game> getGamesByUserID(int userId){

        String response = Client
                .create()
                .resource("http://" + Config.getIpAddress() + ":" + Config.getServerPort() + "/api/games/" + userId)
                .accept("application/json")
                .get(ClientResponse.class)
                .getEntity(String.class);

        return new Gson().fromJson(response, new TypeToken<ArrayList<Game>>(){}.getType());
    }


    public static ArrayList<Game> getGamesByStatusAndUserId(String status, int userId){

        String jsonData = ServerConnection.get(Config.getServerPathGames() + status + "/" + userId);

        return new Gson().fromJson(jsonData, new TypeToken<ArrayList<Game>>(){}.getType());
    }

    public static ArrayList<Game> getGamesInvitedByID(int userId){

        String response = Client
                .create()
                .resource("http://" + Config.getIpAddress() + ":" + Config.getServerPort() + "/api/games/opponent/" + userId)
                .accept("application/json")
                .get(ClientResponse.class)
                .getEntity(String.class);

        return new Gson().fromJson(response, new TypeToken<ArrayList<Game>>(){}.getType());
    }

    public static ArrayList<Game> getGamesHostedById(int userId){

        String jsonData = ServerConnection.get(Config.getServerPathGames() + "host/" + userId);

        return new Gson().fromJson(jsonData, new TypeToken<ArrayList<Game>>(){}.getType());
    }

    public static ArrayList<Game> getOpenGames(){

        String jsonData = ServerConnection.get(Config.getServerPathGames() + "open/");

        return new Gson().fromJson(jsonData, new TypeToken<ArrayList<Game>>(){}.getType());
    }

    public static ArrayList<Score> getScoresByUserId(int userId){

        String jsonData = ServerConnection.get(Config.getServerPathScores() + userId);

        return new Gson().fromJson(jsonData, new TypeToken<ArrayList<Score>>(){}.getType());
    }
}
