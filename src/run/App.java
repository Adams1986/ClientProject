package run;

import com.google.gson.Gson;
import logic.Controller;
import sdk.Config;
import sdk.Security;
import sdk.dto.User;

import java.util.HashMap;

/**
 * Created by ADI on 24-10-2015.
 */
public class App {


    public static void main(String[] args) {

        Config.init();

        Controller controller = new Controller();
        controller.run();
    }
}
