package sdk;

import sdk.dto.*;

import javax.ws.rs.core.MultivaluedMap;
import java.util.ArrayList;

/**
 * Api class. Combines DataParser class and ServerConnection class to parse -> send -> parse -> return. Controller will
 * use methods and combine with screen methods to piece it all together.
 */
public class Api {

    /**
     * Authenticates a user passed in the parameter to see if user exists with username and password
     * @param currentUser
     * @return String parsed message with info regarding success of login attempt
     */
    public static String authenticateLogin(User currentUser) {

        String sendingToServer = DataParser.getEncryptedUser(currentUser);

        String dataReceived = ServerConnection.post(Config.getServerPathLogin(), sendingToServer);

        //setting the current users information
        DataParser.setDecryptedUser(dataReceived, currentUser);

        //returning message from server
        return DataParser.parseMessage(dataReceived);
    }

    /**
     * Retrieves all users from the server and saves them in an arraylist
     * @returns ArrayList of all users/gamers
     */
    public static ArrayList<User> getUsers(int userId) {

        String dataReceived = ServerConnection.get(Config.getServerPathUsers());
//        String dataReceived = ServerConnection.get(Config.getServerPathUsers() + userId);

        return DataParser.getDecryptedUserList(dataReceived);
    }

    /**
     * Parses server response -> sets user -> return the message from server
     * @param user
     * @return
     */
    public static String getUser(User user){

        String dataReceived = ServerConnection.get(Config.getServerPathUser() + user.getId());

        DataParser.setDecryptedUser(dataReceived, user);

        return DataParser.parseMessage(dataReceived);
    }

    /**
     * Connects to servers API for creating a user. Uses the server connection class' post method to send data
     * @param createNewUser
     * @return
     */
    public static String createUser(User createNewUser){

        String sendingToServer = DataParser.getEncryptedUser(createNewUser);

        String dataReceived = ServerConnection.post(Config.getServerPathUsers(), sendingToServer);

        return DataParser.parseMessage(dataReceived);
    }

    /**
     * Method is used to create a new game. Can be either an open or pending (another user was invited) game.
     * @param newGame
     * @return
     */
    public static String createGame(Game newGame){

        String sendingToServer = DataParser.getEncryptedGame(newGame);

        String dataReceived = ServerConnection.post(Config.getServerPathGames(), sendingToServer);

        return DataParser.parseMessage(dataReceived);

    }

    /**
     * Method is used to join an existing game which is open
     * @param game
     * @return
     */
    public static String joinGame(Game game){

        String sendingToServer = DataParser.getEncryptedGame(game);

        String dataReceived = ServerConnection.put(Config.getServerPathJoinGames(), sendingToServer);

        return DataParser.parseMessage(dataReceived);
    }

    /**
     * Method is used to play a game. Either after joining an open game or if player was invited by another player
     * @param game
     * @return
     */
    public static String startGame(Game game) {

        String sendingToServer = DataParser.getEncryptedGame(game);

        String dataReceived = ServerConnection.put(Config.getServerPathStartGames(), sendingToServer);

        return DataParser.parseMessage(dataReceived);
    }

    /**
     * Will be used to delete a game, if the user wishes to do so. Method will be combined with an array list of games
     * that are either open or pending, not finished!
     * @param gameId
     * @return
     */
    public static String deleteGame(int gameId) {

        String receivedData = ServerConnection.delete(Config.getServerPathGames() + gameId);

        return DataParser.parseMessage(receivedData);
    }

    /**
     * TODO: change or delete
     * Method can be used to get a single game from the server
     * @param gameId
     * @return
     */
    public static Game getGame(int gameId){

        String receivedData =  ServerConnection.get(Config.getServerPathGame() + gameId);

        return DataParser.getDecryptedGame(receivedData);
    }

    /**
     * Method is used to get the top 15 high scores from the server.
     * @return
     */
    public static ArrayList<Score> getHighScores(){

        String dataReceived = ServerConnection.get(Config.getServerPathHighScores());

        return DataParser.getDecryptedScoresList(dataReceived);
    }

    /**
     * //TODO change
     * Can be used for returning a list of games from a user
     * @param userId
     * @return
     */
    public static ArrayList<Game> getGamesByUserID(int userId){

        String dataReceived = ServerConnection.get(Config.getServerPathGames() + userId);

        return DataParser.getDecryptedGamesList(dataReceived);

    }

    /**
     * This is a very flexible method that can return a whole range of different lists of games. Will be used in a number
     * of different ways, e.g. for the Game overviewer to see all users different types of games and statuses
     * @param status
     * @param userId
     * @return
     */
    public static ArrayList<Game> getGamesByStatusAndUserId(String status, int userId){

        String dataReceived = ServerConnection.get(Config.getServerPathGames() + status + userId);

        //returning the games list
        return DataParser.getDecryptedGamesList(dataReceived);
    }

    /**
     * //TODO: change or delete
     * Gets scores for a user
     * @param userId
     * @return
     */
    public static ArrayList<Score> getScoresByUserId(int userId){

        String dataReceived = ServerConnection.get(Config.getServerPathScores() + userId);

        return DataParser.getDecryptedScoresList(dataReceived);
    }

    /**
     * Resets the JWT token. To be used when logging out so the token isn't stored in the cache before a new user attempts
     * to log on.
     */
    public static void resetToken() {

        ServerConnection.resetToken();
    }
}
