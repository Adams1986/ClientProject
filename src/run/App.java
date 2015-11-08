package run;

import com.google.gson.Gson;
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
import java.util.LinkedList;

/**
 * Created by ADI on 24-10-2015.
 */
public class App {


    public static void main(String[] args) {

        Config.init();


//        String test = "{\"" + Security.encrypt("id", Config.getEncryptionkey()) + "\": \"" + Security.encrypt(1, Config.getEncryptionkey()) + "\"}";
//        System.out.println(test);
//
//        JSONParser jsonParser = new JSONParser();
//
//        try {
//
//            //Initialize Object class as json, parsed by jsonParsed.
//            Object obj = jsonParser.parse(test);
//
//            //Instantiate JSONObject class as jsonObject equal to obj object.
//            JSONObject jsonObject = (JSONObject) obj;
//
//            System.out.println(Security.decrypt(
//                    Integer.parseInt( (String) jsonObject.get( Security.encrypt( "id", Config.getEncryptionkey() ) ) ),
//                    Config.getEncryptionkey()));
//
//            User user = new User();
//            user.setId(
//                    Security.decrypt(
//                            Integer.parseInt( (String) jsonObject.get(Security.encrypt("id", Config.getEncryptionkey()))),
//                            Config.getEncryptionkey()));
//
//            System.out.println(user.getId());
//            System.out.println(new Gson().toJson(user, User.class));
//
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }

        Controller controller = new Controller();
        controller.run();
    }
}
