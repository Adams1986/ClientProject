package run;

import logic.Controller;
import sdk.Config;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

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
