package run;

import logic.Controller;
import sdk.Config;


/**
 * Main method. Starts the client by calling the run method from the controller.
 */
public class App {


    public static void main(String[] args) {

        //initialises the values from the Config file, containing variable values.
        Config.init();

        Controller controller = new Controller();
        controller.run();
    }
}
