package example;

import javax.swing.*;
import java.awt.*;

/**
 * Created by ADI on 21-10-2015.
 */
public class SnakeJPanel extends JPanel {

    private final int WIDTH = 20;
    private final int LENGTH = 20;
    private int x, y;
    private String move;


    public SnakeJPanel(int x, int y, String move){

        this.x = x;
        this.y = y;
        this.move = move;
    }

    public void paint(Graphics g) {
        super.paint(g);

        this.setBackground(Color.white);

        for (int i = 0; i < move.length(); i++) {

            g.setColor(Color.black);
            g.fillRect(x, y, WIDTH, LENGTH);
//        g.drawRect(x, y, WIDTH, LENGTH);
        }


    }

    public void move(){

        char[] moves = move.toCharArray();

        for (int i = 0; i < moves.length; i++) {

            if (moves[i] == 'w') {
                y = y - 20;
            } else if (moves[i] == 's') {
                y = y + 20;
            } else if (moves[i] == 'd') {
                x = x + 20;
            } else if (moves[i] == 'a') {
                x = x - 20;
            }
        }
    }
}
