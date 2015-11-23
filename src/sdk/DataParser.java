package sdk;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import sdk.dto.Game;
import sdk.dto.User;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by simonadams on 09/11/15.
 *///TODO remove hardcoding from class
public class DataParser {

    public static String parseMessage(String dataToBeParsed){

        JSONParser jsonParser = new JSONParser();
        String message = "";

        try {

            Object obj = jsonParser.parse(dataToBeParsed);
            JSONObject jsonObject = (JSONObject) obj;

            message = ((String) jsonObject.get("message"));

        } catch (ParseException p){
            p.printStackTrace();
        }

        return message;
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

    public static User getDecryptedUser(String jsonData){

        Gson parser = new Gson();
        HashMap<String, String> jsonHashMap = parser.fromJson(jsonData, HashMap.class);
        String encryptedUser = jsonHashMap.get("data");
        String jsonUser = Security.decrypt(encryptedUser, Config.getEncryptionkey());

        return parser.fromJson(jsonUser, User.class);

    }

    public static Object getDecryptedDTO(String jsonData){

        Gson parser = new Gson();
        HashMap<String, String> jsonHashMap = parser.fromJson(jsonData, HashMap.class);
        String encryptedUser = jsonHashMap.get("data");
        String jsonUser = Security.decrypt(encryptedUser, Config.getEncryptionkey());

        return parser.fromJson(jsonUser, Object.class);

    }

    public static ArrayList<User> getDecryptedUserList(String jsonData){

        Gson parser = new Gson();
        HashMap<String, String> jsonHashMap = parser.fromJson(jsonData, HashMap.class);
        String encryptedUsers = jsonHashMap.get("data");
        String jsonUsers = Security.decrypt(encryptedUsers, Config.getEncryptionkey());

        return parser.fromJson(jsonUsers, new TypeToken<ArrayList<User>>(){}.getType());

    }

    public static ArrayList<Game> getDecryptedGamesList(String jsonData) {

        Gson parser = new Gson();
//        HashMap<String, String> jsonHashMap = parser.fromJson(jsonData, HashMap.class);
//        String encryptedGames = jsonHashMap.get("data");
//        String jsonGames = Security.decrypt(encryptedGames, Config.getEncryptionkey());
//
//        return parser.fromJson(jsonGames, new TypeToken<ArrayList<Game>>(){}.getType());
        return parser.fromJson(jsonData, new TypeToken<ArrayList<Game>>(){}.getType());
    }

}
