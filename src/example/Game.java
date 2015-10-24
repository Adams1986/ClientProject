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


        for (int i = 0; i < moves.length(); i++) {



        }

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void start() {


    }

    public static void main(String[] args) {

        Game game = new Game();

    }
}
