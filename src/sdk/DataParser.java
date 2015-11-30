package sdk;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import sdk.dto.Game;
import sdk.dto.Gamer;
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

        JSONParser jsonParser = new JSONParser();
        String message = "";

        HashMap<String, String> mapWithData = new Gson().fromJson(dataToBeParsed, HashMap.class);

        try {

            Object obj = jsonParser.parse(dataToBeParsed);
            JSONObject jsonObject = (JSONObject) obj;

            message = ((String) jsonObject.get("message"));

        } catch (ParseException p){
            p.printStackTrace();
        }

        return mapWithData.get("message");
        //return message;
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

    public static String parseLoginData(String dataToBeParsed, User user){

        Gson gson = new Gson();

        //Issue with json, as it reads/converts ints to doubles, so little workaround to get the userid with gson lib
        //HashMap<String, Double> mapWithUserId = gson.fromJson(dataToBeParsed, HashMap.class);
        HashMap<String, String> mapWithData = gson.fromJson(dataToBeParsed, HashMap.class);

        if (mapWithData.get("data") != null) {
            User temp = getDecryptedUser(mapWithData.get("data"));
            user.setId(temp.getId());
            user.setFirstName(temp.getFirstName());
            user.setLastName(temp.getLastName());
            user.setEmail(temp.getEmail());
            user.setTotalScore(temp.getTotalScore());
        }

        return mapWithData.get("message");


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

    public static Object getDecryptedDTO(String jsonData){

        Gson gson = new Gson();
        HashMap<String, String> jsonHashMap = gson.fromJson(jsonData, HashMap.class);
        String encryptedUser = jsonHashMap.get("data");
        String jsonUser = Security.decrypt(encryptedUser, Config.getEncryptionkey());

        return gson.fromJson(jsonUser, Object.class);

    }

    public static ArrayList<Gamer> getDecryptedUserList(String jsonData){

        Gson gson = new Gson();
        HashMap<String, String> jsonHashMap = gson.fromJson(jsonData, HashMap.class);
        String encryptedUsers = jsonHashMap.get("data");
        String jsonUsers = Security.decrypt(encryptedUsers, Config.getEncryptionkey());

        return gson.fromJson(jsonUsers, new TypeToken<ArrayList<Gamer>>(){}.getType());

    }

    public static ArrayList<Game> getDecryptedGamesList(String jsonData) {

        //TODO: encrypt on server
        Gson gson = new Gson();
//        HashMap<String, String> jsonHashMap = gson.fromJson(jsonData, HashMap.class);
//        String encryptedGames = jsonHashMap.get("data");
//        String jsonGames = Security.decrypt(encryptedGames, Config.getEncryptionkey());
//
//        return gson.fromJson(jsonGames, new TypeToken<ArrayList<Game>>(){}.getType());
        return gson.fromJson(jsonData, new TypeToken<ArrayList<Game>>(){}.getType());
    }

    public static String getEncryptedGame(Game game) {

//        HashMap<String, String> encryptedDto = new HashMap<>();
//        String encryptedGame = Security.encrypt(new Gson().toJson(game), Config.getEncryptionkey());
//        encryptedDto.put("data", encryptedGame);
//
//        return new Gson().toJson(encryptedDto);
        return new Gson().toJson(game);
    }

    public static ArrayList<Score> getDecryptedScoresList(String jsonData) {

        Gson gson = new Gson();
//        HashMap<String, String> jsonHashMap = gson.fromJson(jsonData, HashMap.class);
//        String encryptedScores = jsonHashMap.get("data");
//        String jsonScores = Security.decrypt(encryptedScores, Config.getEncryptionkey());
//
//        return gson.fromJson(jsonScores, new TypeToken<ArrayList<Game>>(){}.getType());
        return gson.fromJson(jsonData, new TypeToken<ArrayList<Score>>(){}.getType());
    }
}
