package sdk;

import sdk.dto.*;
import java.util.ArrayList;

/**
 * Api class. Combines DataParser class and ServerConnection class to parse -> send -> parse -> return. Controller will
 * use methods and combine with screen methods to piece it all together.
 */
public class Api {

    /**
     * Authenticates a user passed in the parameter to see if user exists with username and password
     * @param currentUser
     * @return String jsonData with info regarding success of login attempt
     */
    public static String authenticateLogin(User currentUser) {

        String sendingToServer = DataParser.getEncryptedUser(currentUser);

        String dataReceived = ServerConnection.post(Config.getServerPathLogin(), sendingToServer);

        return DataParser.parseLoginData(dataReceived, currentUser);
    }

    /**
     * Retrieves all users from the server and saves them in an arraylist
     * @return ArrayList of all users/gamers
     */
    public static ArrayList<Gamer> getUsers(int userId) {

        String dataReceived = ServerConnection.get(Config.getServerPathUsers() + userId);

        return DataParser.getDecryptedUserList(dataReceived);
    }

    /**
     * returns a single user from the user id, in a json string format
     * @param user
     * @return
     */
    public static String getUser(User user){

        String dataReceived = ServerConnection.get(Config.getServerPathUser() + user.getId());
        User temp = DataParser.getDecryptedUser(dataReceived);

        if (temp != null) {
            user.setId(temp.getId());
            user.setUsername(temp.getUsername());
        }

        return DataParser.parseMessage(dataReceived);
    }

    public static String createUser(User createNewUser){

        String sendingToServer = DataParser.getEncryptedUser(createNewUser);

        String dataReceived = ServerConnection.post(Config.getServerPathUsers(), sendingToServer);

        return DataParser.parseMessage(dataReceived);
    }

    public static String createGame(Game newGame){

        String sendingToServer = DataParser.getEncryptedGame(newGame);

        String dataReceived = ServerConnection.post(Config.getServerPathGames(), sendingToServer);

        return DataParser.parseMessage(dataReceived);

    }

    public static String joinGame(Game game){

        String sendingToServer = DataParser.getEncryptedGame(game);

        String dataReceived = ServerConnection.put(Config.getServerPathJoinGames(), sendingToServer);

        return DataParser.parseMessage(dataReceived);
    }


    public static String startGame(Game game) {

        String sendingToServer = DataParser.getEncryptedGame(game);

        String dataReceived = ServerConnection.put(Config.getServerPathStartGames(), sendingToServer);

        return DataParser.parseMessage(dataReceived);
    }


    public static String deleteGame(int gameId) {

        String receivedData = ServerConnection.delete(Config.getServerPathGames() + gameId);

        return DataParser.parseMessage(receivedData);
    }

    public static String getGame(int gameId){

        return ServerConnection.get(Config.getServerPathGame() + gameId);
    }

    public static ArrayList<Score> getHighScores(){

        String dataReceived = ServerConnection.get(Config.getServerPathHighScores());

        return DataParser.getDecryptedScoresList(dataReceived);
    }


    public static String getGamesByUserID(int userId){

        return ServerConnection.get(Config.getServerPathGames());

        //return new Gson().fromJson(jsonData, new TypeToken<ArrayList<Game>>(){}.getType());
    }


    public static ArrayList<Game> getGamesByStatusAndUserId(String status, int userId){

        String dataReceived = ServerConnection.get(Config.getServerPathGames() + status + userId);

        //returning the games list
        return DataParser.getDecryptedGamesList(dataReceived);
    }

    public static String getScoresByUserId(int userId){

        return ServerConnection.get(Config.getServerPathScores() + userId);
    }
}
