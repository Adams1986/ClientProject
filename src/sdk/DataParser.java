package sdk;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
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

    public static String parseMessage(String dataToBeParsed){

        HashMap<String, String> mapWithData = new Gson().fromJson(dataToBeParsed, HashMap.class);

        //getting string with key value message and returning it
        return mapWithData.get("message");
    }

    //Method overload to create a method that sets the id for a user. Used for the login API.
    public static String parseMessage(String dataToBeParsed, User user){

        JSONParser jsonParser = new JSONParser();
        String message = "";

        try {

            Object obj = jsonParser.parse(dataToBeParsed);
            JSONObject jsonObject = (JSONObject) obj;

            message = ((String) jsonObject.get("message"));

            if (jsonObject.get("userid") != null)
                user.setId((int)(long) jsonObject.get("userid"));

        } catch (ParseException p){

        }

        return message;
    }

    public static void setDecryptedUser(String dataToBeParsed, User user){

        //Issue with json, as it reads/converts ints to doubles, so little workaround to get the userid with gson lib
        //HashMap<String, Double> mapWithUserId = gson.fromJson(dataToBeParsed, HashMap.class);
        HashMap<String, String> mapWithData = new Gson().fromJson(dataToBeParsed, HashMap.class);
        String encryptedData = mapWithData.get("data");

        if (encryptedData != null) {

            User temp = getDecryptedUser(encryptedData);
            user.setId(temp.getId());
            user.setFirstName(temp.getFirstName());
            user.setLastName(temp.getLastName());
            user.setEmail(temp.getEmail());
            user.setTotalScore(temp.getTotalScore());
        }
    }

    public static String getEncryptedUser(User user){

        HashMap<String, String> encryptedDto = new HashMap<>();
        encryptedDto.put("data", Security.encrypt(new Gson().toJson(user), Config.getEncryptionkey()));

        return new Gson().toJson(encryptedDto);
    }

    public static User getDecryptedUser(String jsonData){

        Gson gson = new Gson();
        HashMap<String, String> jsonHashMap = gson.fromJson(jsonData, HashMap.class);
        String encryptedUser = jsonHashMap.get("data");
        String jsonUser = Security.decrypt(encryptedUser, Config.getEncryptionkey());
        System.out.println(jsonUser);

        return gson.fromJson(jsonUser, User.class);

    }

    public static Game getDecryptedGame(String jsonData){

        Gson gson = new Gson();
        HashMap<String, String> jsonHashMap = gson.fromJson(jsonData, HashMap.class);
        String encryptedUser = jsonHashMap.get("data");
        String jsonUser = Security.decrypt(encryptedUser, Config.getEncryptionkey());

        return gson.fromJson(jsonUser, Game.class);

    }

    public static Score getDecryptedScore(String jsonData){

        Gson gson = new Gson();
        HashMap<String, String> jsonHashMap = gson.fromJson(jsonData, HashMap.class);
        String encryptedUser = jsonHashMap.get("data");
        String jsonUser = Security.decrypt(encryptedUser, Config.getEncryptionkey());

        return gson.fromJson(jsonUser, Score.class);

    }

    public static ArrayList<User> getDecryptedUserList(String jsonData){

        Gson gson = new Gson();
        HashMap<String, String> jsonHashMap = gson.fromJson(jsonData, HashMap.class);
        String encryptedUsers = jsonHashMap.get("data");
        String jsonUsers = Security.decrypt(encryptedUsers, Config.getEncryptionkey());

        return gson.fromJson(jsonUsers, new TypeToken<ArrayList<User>>(){}.getType());

    }

    public static ArrayList<Game> getDecryptedGamesList(String jsonData) {

        //TODO: encrypt on server
        Gson gson = new Gson();
        HashMap<String, String> jsonHashMap = gson.fromJson(jsonData, HashMap.class);
        String encryptedGames = jsonHashMap.get("data");
        String jsonGames = Security.decrypt(encryptedGames, Config.getEncryptionkey());

        return gson.fromJson(jsonGames, new TypeToken<ArrayList<Game>>(){}.getType());
        //return gson.fromJson(jsonData, new TypeToken<ArrayList<Game>>(){}.getType());
    }

    public static String getEncryptedGame(Game game) {

        HashMap<String, String> encryptedDto = new HashMap<>();
        String encryptedGame = Security.encrypt(new Gson().toJson(game), Config.getEncryptionkey());
        encryptedDto.put("data", encryptedGame);

        return new Gson().toJson(encryptedDto);
        //return new Gson().toJson(game);
    }

    public static ArrayList<Score> getDecryptedScoresList(String jsonData) {

        Gson gson = new Gson();
        HashMap<String, String> jsonHashMap = gson.fromJson(jsonData, HashMap.class);
        String encryptedScores = jsonHashMap.get("data");
        String jsonScores = Security.decrypt(encryptedScores, Config.getEncryptionkey());

        return gson.fromJson(jsonScores, new TypeToken<ArrayList<Score>>(){}.getType());
        //return gson.fromJson(jsonData, new TypeToken<ArrayList<Score>>(){}.getType());
    }
}
