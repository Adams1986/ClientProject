package run;

import gui.ReplaySnake;
import logic.Controller;
import sdk.Config;
import sdk.Snake;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

/**
 * Created by ADI on 24-10-2015.
 */
public class App {


    public static void main(String[] args) {

        Config.init();

        Snake snake1 = new Snake(Color.BLUE, new LinkedList<Point>(), new Point(0,0), "dddddddddddssaaaaaaaasssssssssdwwwwwdd");
        Snake snake2 = new Snake(Color.RED, new LinkedList<Point>(), new Point(14,14), "wwwwwwwwwwaassssssssaaaaaaaawwddddddw");

        ReplaySnake replaySnake = new ReplaySnake(snake1, snake2);
//
        JFrame jFrame = new JFrame("snake");
        jFrame.add(replaySnake);
        jFrame.setLocationRelativeTo(null);

        jFrame.setSize(Config.getReplayWidth(), Config.getReplayHeight());
        System.out.println(jFrame.getSize());
        jFrame.setVisible(true);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Controller controller = new Controller();
        controller.run();
    }
}
