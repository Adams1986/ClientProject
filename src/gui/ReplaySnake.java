package gui;

import sdk.Config;
import sdk.dto.Game;
import sdk.dto.Gamer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.LinkedList;

/**
 * Class contains the logic for drawing one or two snakes to the canvas from a finished game
 */
public class ReplaySnake extends JPanel {

    private Gamer host;
    private Gamer opponent;

    //Timer instead of Thread.sleep. Important in a JPanel
    private Timer tm;
    private Game game;
    private boolean gameHasEnded;

    /**
     * Constructor. Takes a game object and an action listener. The action listener will be used to draw the board and
     * snakes to the screen dynamically. The game object is used to set the drawing details, such as start point of
     * snakes, game name etc.
     * @param game
     * @param l
     */
    //TODO: Maybe use a Score object instead and make som graphics for winner/loser points etc.
    public ReplaySnake(Game game, ActionListener l){

        setBackground(Color.BLACK);

        game.getHost().setSnakeColor(Color.BLUE);
        game.getHost().setSnake(new LinkedList<Point>());
        game.getHost().getSnake().add(new Point((game.getMapSize() - 2) / 2, (game.getMapSize() - 2) / 2));

        this.game = game;
        host = game.getHost();
        if (game.getOpponent() != null) {

            game.getOpponent().setSnakeColor(Color.RED);
            game.getOpponent().setSnake(new LinkedList<Point>());
            game.getOpponent().getSnake().add(new Point((game.getMapSize() + 2) / 2, (game.getMapSize() + 2) / 2));

            opponent = game.getOpponent();
        }

        tm = new Timer(Config.getDelay(), l);
        System.out.println(Config.getDelay());
        gameHasEnded = false;

    }

    /**
     * Method used to signal to the object, that the game has ended. Used to draw winner info and to stop the paintComponent
     * from repainting to the panel (done with the timer's stop method).
     * @param gameHasEnded
     */
    public void setGameHasEnded(boolean gameHasEnded) {
        this.gameHasEnded = gameHasEnded;
    }

    /**
     *The paint method. Contains the method-calls that do the painting to the canvas
     * @param g
     */
    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        //if this is set in controller, maybe panel can be added straight away to screen?
        tm.start();
        drawBoard(g);
        drawGameInfo(g, game);

        //if statement that makes sure to draw the winner last. So he is the one 'eating' the other one on screen
        if (game.getWinner() != null && game.getWinner().getId() == host.getId()){
            //makes it possible to replay runs with only host
            if (opponent.getControls() != null) {

                drawSnake(g, opponent);
            }
            drawSnake(g, host);
        }
        else {

            drawSnake(g, host);
            //makes it possible to replay runs with only host
            if (opponent.getControls() != null) {

                drawSnake(g, opponent);
            }
        }

        if (gameHasEnded) {

            drawWinnerInfo(g, game);
            tm.stop();
        }
    }

    /**
     * Draws the winner's name to the screen after game has ended.
     * @param g
     * @param game
     */
    private void drawWinnerInfo(Graphics g, Game game) {


        g.setFont(new Font(Config.getHeaderFont(), Font.BOLD, Config.getHeaderTextSize()));
        g.setColor(Color.WHITE);

        if (game.getWinner().getUsername() != null) {

            if (game.getWinner().getId() == game.getHost().getId()) {

                g.setColor(game.getHost().getSnakeColor());
            }
            else {

                g.setColor(game.getOpponent().getSnakeColor());
            }

            g.drawString(game.getWinner().getUsername() + Config.getWinnerText(), Config.getDefaultXPosJComponent(), Config.getY10PosJComponent());
        }
        else {

            if (game.getHost().getControls() != null && game.getOpponent().getControls() != null)
                g.drawString(Config.getGameTiedText(), Config.getDefaultXPosJComponent(), Config.getY10PosJComponent());
            else
                g.drawString(Config.getAwaitingOpponentText(), Config.getDefaultXPosJComponent(), Config.getY10PosJComponent());
        }
    }

    /**
     * Draw the game info to the screen.
     * @param g
     * @param game
     */
    private void drawGameInfo(Graphics g, Game game) {

        g.setColor(Color.WHITE);
        g.drawString("Replaying game: " + game.getName(), Config.getDefaultXPosJComponent(), Config.getY9PosJComponent());
    }

    /**
     * Draws the gamer object's linked list to the canvas. Uses the move method to populate the list.
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
        g.setColor(Color.BLACK);
    }

    /**
     * The move method. Uses a snake's controls to asses the direction of the snake. Also stops the snake from getting
     * drawn outside of the board (see if-else statement).
     * @param user
     * @param ch
     */
    public void move(Gamer user, char ch){

        Point head = user.getSnake().peekFirst();
        Point newPoint = head;
        Point addPoint = (Point) newPoint.clone();

        //hardcoded chars and Points
        switch (ch) {

            //decreases y with one (x,y - 0,0 is left side, top hand corner, so deduction in y moves snake up
            case 'w':
                //
                newPoint = new Point(head.x, head.y - Config.getMoveOne());
                break;

            //increases y so moves snake down when char 's' is fed to the method's parameter
            case 's':
                newPoint = new Point(head.x, head.y + Config.getMoveOne());
                break;

            //decreases x, which moves snake to the left if char is 'a'
            case 'a':
                newPoint = new Point(head.x - Config.getMoveOne(), head.y);
                break;

            //increases x, which moves snake to the right if char is 'd'
            case 'd':
                newPoint = new Point(head.x + Config.getMoveOne(), head.y);
                break;
        }
        user.getSnake().push(addPoint);

        //if-else statement used to handle collisions with 'wall', the body of the snake or opposite of the direction
        if (newPoint.x < Config.getZeroXY() || newPoint.x > game.getMapSize() - Config.getMoveOne()){

            return;
        }
        else if (newPoint.y < Config.getZeroXY() || newPoint.y > game.getMapSize() - Config.getMoveOne()){

            return;
        }
        else if (user.getSnake().contains(newPoint)){

            return;
        }
        else if (user.getSnake().size() == game.getMapSize() * game.getMapSize()){

            return;
        }
        /*
        if method reaches this point (hint, it does not reach one of the if-else conditions, the move is valid and the
        snake gets the point 'newpoint' added to its body (the linked list)
         */
        user.getSnake().push(newPoint);
    }


    /**
     * Fills canvas with the board.
     * Firstly with the complete board as a rectangle. Next, the lines horizontally and vertically
     * @param g takes a graphics object
     */
    private void drawBoard(Graphics g){

        g.setColor(Color.GREEN);
        //draw outer frame
        g.drawRect(Config.getZeroXY(), Config.getZeroXY(), Config.getFieldWidth() * game.getMapSize(),
                Config.getFieldHeight() * game.getMapSize());


        //vertical lines
        for (int x = Config.getFieldWidth(); x < Config.getFieldWidth() * game.getMapSize() ; x+= Config.getFieldWidth()) {

            g.drawLine(x, Config.getZeroXY(), x, Config.getFieldHeight() * game.getMapSize());
        }
        //horizontal lines
        for (int y = Config.getFieldHeight(); y < Config.getFieldHeight() * game.getMapSize() ; y+= Config.getFieldHeight()) {

            g.drawLine(Config.getZeroXY(), y, Config.getFieldWidth() * game.getMapSize(), y);
        }
    }

    public void stopTimer() {
        tm.stop();
    }

    public boolean hasGameEnded() {
        return gameHasEnded;
    }
}
