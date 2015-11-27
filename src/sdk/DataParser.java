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

    public static String parseHashMapMessage(String dataToBeParsed, User user){

        Gson gson = new Gson();

        //Issue with json, as it reads/converts ints to doubles, there needs workaround to get the userid with gson lib
        HashMap<String, Double> mapWithUserId = gson.fromJson(dataToBeParsed, HashMap.class);
        HashMap<String, String> mapWithData = gson.fromJson(dataToBeParsed, HashMap.class);

            if (mapWithUserId.get("userid") != null)
                user.setId(mapWithUserId.get("userid").intValue());

            return mapWithData.get("message");


    }

    public static String getEncryptedUser(){

        return null;
    }

    public static String getEncryptedDto(User user){

        HashMap<String, String> encryptedDto = new HashMap<>();
        encryptedDto.put("data", Security.encrypt(new Gson().toJson(user), Config.getEncryptionkey()));

        return new Gson().toJson(encryptedDto);
    }

    public static User getDecryptedUser(String jsonData){

        Gson gson = new Gson();
        HashMap<String, String> jsonHashMap = gson.fromJson(jsonData, HashMap.class);
        String encryptedUser = jsonHashMap.get("data");
        String jsonUser = Security.decrypt(encryptedUser, Config.getEncryptionkey());

        return gson.fromJson(jsonUser, User.class);

    }

    public static Object getDecryptedDTO(String jsonData){

        Gson gson = new Gson();
        HashMap<String, String> jsonHashMap = gson.fromJson(jsonData, HashMap.class);
        String encryptedUser = jsonHashMap.get("data");
        String jsonUser = Security.decrypt(encryptedUser, Config.getEncryptionkey());

        return gson.fromJson(jsonUser, Object.class);

    }

    public static ArrayList<User> getDecryptedUserList(String jsonData){

        Gson gson = new Gson();
        HashMap<String, String> jsonHashMap = gson.fromJson(jsonData, HashMap.class);
        String encryptedUsers = jsonHashMap.get("data");
        String jsonUsers = Security.decrypt(encryptedUsers, Config.getEncryptionkey());

        return gson.fromJson(jsonUsers, new TypeToken<ArrayList<User>>(){}.getType());

    }

    public static ArrayList<Game> getDecryptedGamesList(String jsonData) {

        Gson gson = new Gson();
//        HashMap<String, String> jsonHashMap = gson.fromJson(jsonData, HashMap.class);
//        String encryptedGames = jsonHashMap.get("data");
//        String jsonGames = Security.decrypt(encryptedGames, Config.getEncryptionkey());
//
//        return gson.fromJson(jsonGames, new TypeToken<ArrayList<Game>>(){}.getType());
        return gson.fromJson(jsonData, new TypeToken<ArrayList<Game>>(){}.getType());
    }

}
