package sdk;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import sdk.dto.Game;
import sdk.dto.Score;
import sdk.dto.User;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class parses data by combining Gson and json simple librarys and parses DTO's (Game, Gamer, Score, User) to and
 * from json format
 */
//TODO remove hardcoding from class. Plus major clean up needed!
public class DataParser {

    /**
     * Parses a string into a hash map and returns the value with the key 'message'
     * @param dataToBeParsed
     * @return
     */
    public static String parseMessage(String dataToBeParsed){

        HashMap<String, String> mapWithData = new Gson().fromJson(dataToBeParsed, HashMap.class);

        //getting string with key value message and returning it
        return mapWithData.get(Config.getMessageKey());
    }


    public static String encryptMessage(String dataToBeEncrypted){

        return Security.encrypt(dataToBeEncrypted, Config.getEncryptionkey());
    }

    /**
     * Method takes a user DTO and a string. Gets a user object from the encrypted to from the hash map and sets a
     * number of values in the user object that was passed into the method.
     * @param dataToBeParsed
     * @param user
     */
    public static void setDecryptedUser(String dataToBeParsed, User user){

        //Issue with json, as it reads/converts ints to doubles, so little workaround to get the userid with gson lib
        HashMap<String, String> mapWithData = new Gson().fromJson(dataToBeParsed, HashMap.class);
        String encryptedData = mapWithData.get(Config.getDataKey());

        if (encryptedData != null) {

            //creating temp user from data
            User temp = getDecryptedUser(encryptedData);
            //setting values to the user object that was passed in params
            user.setId(temp.getId());
            user.setFirstName(temp.getFirstName());
            user.setLastName(temp.getLastName());
            user.setEmail(temp.getEmail());
            user.setTotalScore(temp.getTotalScore());
        }
    }

    /**
     * Takes a user object and returns it as an encrypted JSON string
     * @param user
     * @return
     */
    public static String getEncryptedUser(User user){

        HashMap<String, String> encryptedDto = new HashMap<>();
        encryptedDto.put(Config.getDataKey(), Security.encrypt(new Gson().toJson(user), Config.getEncryptionkey()));

        return new Gson().toJson(encryptedDto);
    }

    /**
     * Takes a string of json and decrypts it to a user object
     * @param jsonData
     * @return
     */
    public static User getDecryptedUser(String jsonData){

        Gson gson = new Gson();
        HashMap<String, String> jsonHashMap = gson.fromJson(jsonData, HashMap.class);
        String encryptedUser = jsonHashMap.get(Config.getDataKey());
        String jsonUser = Security.decrypt(encryptedUser, Config.getEncryptionkey());

        return gson.fromJson(jsonUser, User.class);

    }

    /**
     * Decrypts a game object
     * @param jsonData
     * @return
     */
    public static Game getDecryptedGame(String jsonData){

        Gson gson = new Gson();
        HashMap<String, String> jsonHashMap = gson.fromJson(jsonData, HashMap.class);
        String encryptedUser = jsonHashMap.get(Config.getDataKey());
        String jsonUser = Security.decrypt(encryptedUser, Config.getEncryptionkey());

        return gson.fromJson(jsonUser, Game.class);

    }

    /**
     * Decrypts a score object
     * @param jsonData
     * @return
     */
    public static Score getDecryptedScore(String jsonData){

        Gson gson = new Gson();
        HashMap<String, String> jsonHashMap = gson.fromJson(jsonData, HashMap.class);
        String encryptedUser = jsonHashMap.get(Config.getDataKey());
        String jsonUser = Security.decrypt(encryptedUser, Config.getEncryptionkey());

        return gson.fromJson(jsonUser, Score.class);

    }

    /**
     * Creates an array list from the encrypted string that is passed to it
     * @param jsonData
     * @return
     */
    public static ArrayList<User> getDecryptedUserList(String jsonData){

        Gson gson = new Gson();
        HashMap<String, String> jsonHashMap = gson.fromJson(jsonData, HashMap.class);
        String encryptedUsers = "";

        if (jsonHashMap != null){
            encryptedUsers = jsonHashMap.get(Config.getDataKey());
        }
        String jsonUsers = Security.decrypt(encryptedUsers, Config.getEncryptionkey());

        return gson.fromJson(jsonUsers, new TypeToken<ArrayList<User>>(){}.getType());
    }

    /**
     *
     * @param jsonData
     * @returns a list of games objects
     */
    public static ArrayList<Game> getDecryptedGamesList(String jsonData) {

        Gson gson = new Gson();
        HashMap<String, String> jsonHashMap = gson.fromJson(jsonData, HashMap.class);
        String encryptedGames = jsonHashMap.get(Config.getDataKey());
        String jsonGames = Security.decrypt(encryptedGames, Config.getEncryptionkey());

        return gson.fromJson(jsonGames, new TypeToken<ArrayList<Game>>(){}.getType());
    }

    /**
     * Takes a game object and returns it as an encrypted json string
     * @param game
     * @return
     */
    public static String getEncryptedGame(Game game) {

        HashMap<String, String> encryptedDto = new HashMap<>();
        String encryptedGame = Security.encrypt(new Gson().toJson(game), Config.getEncryptionkey());
        encryptedDto.put(Config.getDataKey(), encryptedGame);

        return new Gson().toJson(encryptedDto);
    }

    /**
     * Decrypts a list of score objects from a json string
     * @param jsonData
     * @return
     */
    public static ArrayList<Score> getDecryptedScoresList(String jsonData) {

        Gson gson = new Gson();
        HashMap<String, String> jsonHashMap = gson.fromJson(jsonData, HashMap.class);
        String encryptedScores = jsonHashMap.get(Config.getDataKey());
        String jsonScores = Security.decrypt(encryptedScores, Config.getEncryptionkey());

        return gson.fromJson(jsonScores, new TypeToken<ArrayList<Score>>(){}.getType());
    }

    public static String getFormattedDataToSend(String s, int i){

        //creating a string with a divider so that the string can be easily parsed back to original values (String and
        //int) that are passed to method in parameters
        return s + "---" + i;
    }
}
