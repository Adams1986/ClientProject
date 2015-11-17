package run;

import com.google.gson.Gson;
import com.google.gson.internal.Streams;
import logic.Controller;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import sdk.Config;
import sdk.Security;
import sdk.User;

import javax.swing.*;
import java.awt.*;
import java.io.FileReader;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by ADI on 24-10-2015.
 */
public class App {


    public static void main(String[] args) {

        Config.init();

        //TODO: remove this crap
        User user = new User();
        user.setId(1);
        user.setUsername("SimonWeee");

        String encrypted = getEncryptedUser(user);
        User test = getDecryptedUser(encrypted);

        System.out.println(test.getId()+test.getUsername());


        Controller controller = new Controller();
        controller.run();
    }

    //TODO: find suitable place
    public static String getEncryptedUser(User user){

        HashMap<String, String> encryptedUser = new HashMap<>();
        encryptedUser.put("message", Security.encrypt(new Gson().toJson(user), Config.getEncryptionkey()));

        return new Gson().toJson(encryptedUser);
    }

    public static User getDecryptedUser(String s){

        HashMap<String, String> decrpyptedUser = new Gson().fromJson(s, HashMap.class);
        String d = decrpyptedUser.get("message");

        return new Gson().fromJson(Security.decrypt(d, Config.getEncryptionkey()), User.class);

    }
}
