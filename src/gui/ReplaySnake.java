package gui;

import sdk.Config;
import sdk.Gamer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Class contains the logic for drawing one or two snakes to the canvas from a finished game
 */
public class ReplaySnake extends JPanel implements ActionListener{

    private Gamer user;
    private Gamer opponent;

    //Timer instead of Thread.sleep. Important in a JPanel
    private Timer tm;
    private int counter;
    ;

    /**
     * Constructor for completed game.
     * Two gamer objects, for a completed game with two moving snakes.
     * @param host
     * @param opponent
     */
    public ReplaySnake(Gamer host, Gamer opponent){

        this.user = host;
        this.opponent = opponent;
        tm = new Timer(Config.getDelay(), this);
        counter = Config.getCount();

        setBounds(0, 80, 320, 500);

    }

    /**
     * Constructor for a single users game, so he can watch his own movement.
     * @param user
     */
    public ReplaySnake(Gamer user){

        this.user = user;
        tm = new Timer(Config.getDelay(), this);
        counter = Config.getCount();

        setBounds(0, 80, 320, 500);
    }

    /**
     *The paint method. Contains the method-calls that do the painting to the canvas
     * @param g
     */
    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        drawBoard(g);
        drawSnake(g, user);

        //makes it possible to replay runs with single user
        if (opponent != null) {
            drawSnake(g, opponent);
        }
        tm.start();
    }

    /**
     * Draws the gamer objects linked list to the canvas. Uses the move method to populate the list.
     * @param g takes a graphics object as parameter
     * @param gamer takes a gamer object as a parameter (see gamer class for more info)
     */
    private void drawSnake(Graphics g, Gamer gamer) {


        g.setColor(gamer.getSnakeColor());

        //draw starting point pf snake
        g.fillRect(gamer.getSnake().peekFirst().x * Config.getFieldWidth(),
                gamer.getSnake().peekFirst().y * Config.getFieldHeight(), Config.getFieldWidth(), Config.getFieldHeight());


        //draw points in snake to jpanel
        for (Point p : gamer.getSnake()) {
            g.fillRect(p.x * Config.getFieldWidth(), p.y * Config.getFieldHeight(), Config.getFieldWidth(), Config.getFieldHeight());
        }

        //change back to black if we want to draw more.
//        g.setColor(Color.BLACK);
    }

    /**
     * The move method. Uses a snakes controls to asses the direction of the snake
     * @param user
     * @param ch
     */
    private void move(Gamer user, char ch){

        Point head = user.getSnake().peekFirst();
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
        user.getSnake().push(newPoint);

    }


    /**
     * uses movemethod to make the 'incremental' changes of the snake to make animation materialize
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        //populates snake by taking the string controls and checking whether up, down, left or right (w,s,a,d).
        if (counter < user.getControls().length()) {
            move(user, user.getControls().charAt(counter));
        }
        //checking theres an opponent and populates the snake with points
        if (opponent != null) {
            if (counter < opponent.getControls().length()){
                move(opponent, opponent.getControls().charAt(counter));
            }
        }
        //repaints as long as there are usercontrols and opponentcontrols
        if(counter > user.getControls().length() &&
                (opponent != null && counter > opponent.getControls().length())){

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

        //draw outer frame
        g.drawRect(Config.getBoardStartXY(), Config.getBoardStartXY(), Config.getFieldWidth() * Config.getBoardWidth(),
                Config.getFieldHeight() * Config.getBoardHeight());


        //draw vertical lines
        for (int x = Config.getFieldWidth(); x < Config.getFieldWidth() * Config.getBoardWidth() ; x+= Config.getFieldWidth()) {

            g.drawLine(x, Config.getBoardStartXY(), x, Config.getFieldHeight() * Config.getBoardHeight());
        }

        //draw horizontal lines
        for (int y = Config.getFieldHeight(); y < Config.getFieldHeight() * Config.getBoardHeight() ; y+= Config.getFieldHeight()) {

            g.drawLine(Config.getBoardStartXY(), y, Config.getFieldWidth() * Config.getBoardWidth(), y);
        }
        for (int y = Config.getFieldHeight(); y < Config.getFieldHeight() * Config.getBoardHeight() ; y+= Config.getFieldHeight()) {

            g.drawLine(Config.getBoardStartXY(), y, Config.getFieldWidth() * Config.getBoardWidth(), y);
        }
    }
}
