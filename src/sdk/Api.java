package sdk;

import sdk.dto.Gamer;
import sdk.dto.User;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Api class. Constains generic methods that send and receive data in a String/json format. All the parsing of the json
 * data is handled in the Logic sub-controller classes.
 */
public class Api {

    /**
     * Authenticates a user passed in the parameter to see if user exists with username and password
     * @param currentUser
     * @return String jsonData with info regarding success of login attempt
     */
    public static String authenticateLogin(User currentUser) {

        String sendingToServer = DataParser.getEncryptedUser(currentUser);

        String receivedData = ServerConnection.post(Config.getServerPathLogin(), sendingToServer);

        return DataParser.parseHashMapMessage(receivedData, currentUser);
    }

    /**
     * Retrieves all users from the server and saves them in an arraylist
     * @return Arraylist of all users/gamers
     */
    public static ArrayList<Gamer> getUsers(int userId) {

        String receivedData = ServerConnection.get(Config.getServerPathUsers() + userId);

        return DataParser.getDecryptedUserList(receivedData);
    }

    /**
     * returns a single user from the user id, in a json string format
     * @param user
     * @return
     */
    public static String getUser(User user){

        String receivedData = ServerConnection.get(Config.getServerPathUser() + user.getId());
        User temp = DataParser.getDecryptedUser(receivedData);

        if (temp != null) {
            user.setId(temp.getId());
            user.setUsername(temp.getUsername());
        }

        return DataParser.parseMessage(receivedData);
    }

    public static String createUser(String userJson){

        return ServerConnection.post(Config.getServerPathUsers(), userJson);
    }

    public static String createGame(String gameJson){

        return ServerConnection.post(Config.getServerPathGames(), gameJson);
    }

    public static String joinGame(String gameJson){

        System.out.println(gameJson);
        return ServerConnection.put(Config.getServerPathJoinGames(), gameJson);
    }


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

        return ServerConnection.get(Config.getServerPathHighScores());
    }


    public static String getGamesByUserID(int userId){

        return ServerConnection.get(Config.getServerPathGames());

        //return new Gson().fromJson(jsonData, new TypeToken<ArrayList<Game>>(){}.getType());
    }


    public static String getGamesByStatusAndUserId(String status, int userId){

        //TODO: forwardslash...
        return ServerConnection.get(Config.getServerPathGames() + status + userId);
    }

    public static String getScoresByUserId(int userId){

        return ServerConnection.get(Config.getServerPathScores() + userId);
    }
}
