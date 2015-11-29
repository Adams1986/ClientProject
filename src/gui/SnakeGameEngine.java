package gui;

import sdk.Config;
import sdk.dto.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.LinkedList;

/**
 * Play a game of snake. Draws the movements of a user to a JPanel
 */
public class SnakeGameEngine extends JPanel {

    private LinkedList<Point> snake;
    private char direction;
    private StringBuilder sb;
    private Timer tm;
    private boolean gameEnded;

    private Game game;

    public SnakeGameEngine(ActionListener l, Game game){

        setBackground(Color.BLACK);

        this.game = game;
        snake = new LinkedList<>();
        sb = new StringBuilder();
        tm = new Timer(Config.getDelay(), l);
        tm.start();

        generateDefaultSnake();

        gameEnded = false;

        keyBindings();
        focusThis();
    }


    /**
     * Gives focus to SnakeGameEngine panel. Important for the key listener!
     *
     */
    public void focusThis(){

        setFocusable(true);
        requestFocus();
        requestFocusInWindow();
    }

    public void keyBindings() {

        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, Config.getKeyStrokeModifier()),
                Config.getUp());
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, Config.getKeyStrokeModifier()),
                Config.getDown());
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, Config.getKeyStrokeModifier()),
                Config.getLeft());
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, Config.getKeyStrokeModifier()),
                Config.getRight());

    }


    private void generateDefaultSnake() {

        snake.clear();
        if(game.getHost().getControls() == null)
            snake.add(new Point((game.getMapSize()-Config.getNumberForSnakePlacement())/Config.getNumberForSnakePlacement(),
                    (game.getMapSize()-Config.getNumberForSnakePlacement())/Config.getNumberForSnakePlacement()));
        else
            snake.add(new Point((game.getMapSize()+Config.getNumberForSnakePlacement())/Config.getNumberForSnakePlacement(),
                    (game.getMapSize()+Config.getNumberForSnakePlacement())/Config.getNumberForSnakePlacement()));

        direction = Config.getAwaiting();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        drawBoard(g);
        drawSnake(g);

        if (gameEnded) {

            //drawMessage(g);
        }

    }

//    private void drawMessage(Graphics g) {
//
//        g.setColor(Color.CYAN);
//        g.setFont(new Font("Sans Serif", Font.BOLD, 20));
//        g.drawString("Hello there", 40, 500);
//    }



    private void drawSnake(Graphics g){

        if (game.getHost().getControls() != null)
            g.setColor(Color.RED);
        else
            g.setColor(Color.BLUE);

        //draw points in snake to canvas
        for (Point p : snake) {
            g.fillRect(p.x * Config.getFieldWidth(), p.y * Config.getFieldHeight(), Config.getFieldWidth(), Config.getFieldHeight());
        }
    }

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


    public void move(char ch) {

        if (direction == Config.getAwaiting()) return;

        Point head = snake.peekFirst();
        Point newPoint = head;
        Point addPoint = (Point) newPoint.clone();

        //hardcoded chars and Points
        if (ch == Config.getUp()) {

            newPoint = new Point(head.x, head.y - Config.getMoveOne());
            sb.append(ch);
        }
        else if (ch == Config.getDown()) {

            newPoint = new Point(head.x, head.y + Config.getMoveOne());
            sb.append(ch);
        }
        else if (ch == Config.getLeft()) {

            newPoint = new Point(head.x - Config.getMoveOne(), head.y);
            sb.append(ch);
        }
        else if (ch == Config.getRight()) {
            newPoint = new Point(head.x + Config.getMoveOne(), head.y);
            sb.append(ch);
        }
        //add head to front
        snake.push(addPoint);

        if (newPoint.x < Config.getZeroXY() || newPoint.x > game.getMapSize() - Config.getMoveOne()){

            //out of board/bounds horizontally - end
            gameEnded = true;
            return;
        }
        else if (newPoint.y < Config.getZeroXY() || newPoint.y > game.getMapSize() - Config.getMoveOne()){

            //out of bounds vertically
            gameEnded = true;
            return;
        }
        else if (snake.contains(newPoint)){

            //eat yourself - end
            gameEnded = true;
            return;
        }
        else if (snake.size() == game.getMapSize() * game.getMapSize()){

            //filled the whole board -end
            gameEnded = true;
            return;
        }
        //add head again
        snake.push(newPoint);
    }

    public boolean isGameEnded() {
        return gameEnded;
    }

    public char getDirection() {
        return direction;
    }

    public void setDirection(char direction) {
        this.direction = direction;
    }

    public String getSbToString() {
        return sb.toString();
    }

    public void stopTimer() {
        tm.stop();
    }
}
