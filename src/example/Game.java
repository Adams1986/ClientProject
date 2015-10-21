package example;

import javax.swing.*;
import java.awt.*;

/**
 * Created by ADI on 21-10-2015.
 */
public class Game extends JFrame {

    public Game (){

        setSize(500, 500);
        setVisible(true);

        String moves = "wwwwwwddddddddd";

//        SnakeJPanel snake = new SnakeJPanel(getHeight()/2,getWidth()/2, moves);

        for (int i = 0; i < moves.length(); i++) {

            SnakeJPanel snake2 = new SnakeJPanel(getHeight()/2,getWidth()/2, moves);
            add(snake2);

            snake2.move();
        }

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void start() {


    }

    public static void main(String[] args) {

        Game game = new Game();

    }
}
