package gui;

import sdk.Config;
import sdk.Snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Class contains the logic for drawing one or two snakes to the canvas from a finished game
 */
public class ReplaySnake extends JPanel implements ActionListener{

    private Snake userSnake;
    private Snake opponentSnake;

    //Timer instead of Thread.sleep. Important in a JPanel
    private Timer tm;
    private int counter;
    ;

    /**
     * Constructor for completed game.
     * Two snake objects, for a completed game with two moving snakes.
     * @param userSnake
     * @param opponentSnake
     */
    public ReplaySnake(Snake userSnake, Snake opponentSnake){

        this.userSnake = userSnake;
        this.opponentSnake = opponentSnake;
        tm = new Timer(Config.getDelay(), this);
        counter = Config.getCount();

        setBounds(0, 80, 320, 500);

    }

    /**
     * Constructor for a single users game, so he can watch his own movement.
     * @param userSnake
     */
    public ReplaySnake(Snake userSnake){

        this.userSnake = userSnake;
    }

    /**
     *The paint method. Contains the method-calls that do the painting to the canvas
     * @param g
     */
    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        drawBoard(g);


        drawSnake(g, userSnake);

        if (opponentSnake != null) {
            drawSnake(g, opponentSnake);
        }
        tm.start();
    }

    /**
     * Draws the snake objects linked list to the canvas. Uses the move method to populate the list.
     * @param g takes a graphics object as parameter
     * @param snake takes a snake object as a parameter (see snake class for more info)
     */
    private void drawSnake(Graphics g, Snake snake) {


        g.setColor(snake.getColor());

        g.fillRect(snake.getMoves().peekFirst().x * Config.getFieldWidth(),
                snake.getMoves().peekFirst().y * Config.getFieldHeight(), Config.getFieldWidth(), Config.getFieldHeight());


        //draw points in snake to canvas
        for (Point p : snake.getMoves()) {
            g.fillRect(p.x * Config.getFieldWidth(), p.y * Config.getFieldHeight(), Config.getFieldWidth(), Config.getFieldHeight());
        }

        //change back to black if we want to draw more.
//        g.setColor(Color.BLACK);
    }

    /**
     * The move method. Uses a snakes controls to asses the direction of the snake
     * @param snake
     * @param ch
     */
    private void move(Snake snake, char ch){

        Point head = snake.getMoves().peekFirst();
        Point newPoint = head;

        //hardcoded chars and Points
        switch (ch) {

            case 'w':
                //
                newPoint = new Point(head.x, head.y - 1);
                break;

            case 's':
                newPoint = new Point(head.x, head.y + 1);
                break;

            case 'a':
                newPoint = new Point(head.x - 1, head.y);
                break;

            case 'd':
                newPoint = new Point(head.x + 1, head.y);
                break;
        }
        snake.getMoves().push(newPoint);

    }


    /**
     * uses movemethod to make the 'incremental' changes of the snake to make animation materialize
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        //populates snake by taking the string controls and checking whether up, down, left or right (w,s,a,d).
        if (counter < userSnake.getControls().length()) {
            move(userSnake, userSnake.getControls().charAt(counter));
        }
        if (opponentSnake != null) {
            if (counter < opponentSnake.getControls().length()){
                move(opponentSnake, opponentSnake.getControls().charAt(counter));
            }
        }
        if(counter > userSnake.getControls().length() &&
                (opponentSnake!= null && counter > opponentSnake.getControls().length())){

            tm.stop();
        }
        else {
            counter++;
            repaint();
        }
    }


    /**
     * Fills canvas with the board.
     * Firstly with the complete board as a rectangle. Next, the lines horizontally and vertically
     * @param g takes a graphics object
     */
    private void drawBoard(Graphics g){

        g.drawRect(Config.getBoardStartXY(), Config.getBoardStartXY(), Config.getFieldWidth() * Config.getBoardWidth(), Config.getFieldHeight() * Config.getBoardHeight());


        //TODO: horizontal lines or is it
        for (int x = Config.getFieldWidth(); x < Config.getFieldWidth() * Config.getBoardWidth() ; x+= Config.getFieldWidth()) {

            g.drawLine(x, Config.getBoardStartXY(), x, Config.getFieldHeight() * Config.getBoardHeight());
        }

        //TODO: vertical lines
        for (int y = Config.getFieldHeight(); y < Config.getFieldHeight() * Config.getBoardHeight() ; y+= Config.getFieldHeight()) {

            g.drawLine(Config.getBoardStartXY(), y, Config.getFieldWidth() * Config.getBoardWidth(), y);
        }
        for (int y = Config.getFieldHeight(); y < Config.getFieldHeight() * Config.getBoardHeight() ; y+= Config.getFieldHeight()) {

            g.drawLine(Config.getBoardStartXY(), y, Config.getFieldWidth() * Config.getBoardWidth(), y);
        }
    }
}
