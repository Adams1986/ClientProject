package gui;

import sdk.Config;
import sdk.Game;
import sdk.Gamer;
import sdk.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Play a game of snake. Draws the movements of a user to a JPanel
 */
//TODO: remove actionlistener from here and instead add them in controller somehow
public class SnakeGameEngine extends JPanel {

    private LinkedList<Point> snake;
    //TODO: flytte move over i controller
    private char direction;
    private StringBuilder sb;
    private Timer tm;
    private boolean gameEnded;

    private Game game;


    //TODO: change to int mapSize at some point?!
    public SnakeGameEngine(ActionListener l, Game game){


        //TODO: move to paintComponent if trouble restarting game?
        this.game = game;
        snake = new LinkedList<>();
        sb = new StringBuilder();
        tm = new Timer(Config.getDelay(), l);
        tm.start();

        generateDefaultSnake();

        gameEnded = false;

        keyBindings();
        //getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), Config.getUP());

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

        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), Config.getUP());
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), Config.getDOWN());
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), Config.getLEFT());
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), Config.getRIGHT());

        //TODO: Done in controller
//        getActionMap().put(Config.getUP(), new AbstractAction() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//
//                if(direction != Config.getDOWN())
//                    direction = Config.getUP();
//            }
//        });
    }


    private void generateDefaultSnake() {

        //TODO: When playing a game from challenge, change to +2 instead
        snake.clear();
        if(game.getHostControls() == null)
            snake.add(new Point((game.getMapSize()-2)/2,(game.getMapSize()-2)/2));
        else
            snake.add(new Point((game.getMapSize()+2)/2,(game.getMapSize()+2)/2));

        direction = Config.getAwaiting();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        drawBoard(g);
        drawSnake(g);

        if (gameEnded) {

            drawMessage(g);
        }

    }

    private void drawMessage(Graphics g) {

        g.setColor(Color.CYAN);
        g.setFont(new Font("Sans Serif", Font.BOLD, 20));
        g.drawString("Hello there", 40, 500);
    }

//    @Override
//    public void actionPerformed(ActionEvent e) {
//
//        if(!gameEnded) {
//            move(direction);
//            btnSend.setEnabled(false);
//            gameNameField.setEnabled(false);
//            gameNameField.setText("Type game name here..");
//        }
//        else{
//            moves = sb.toString();
//            btnSend.setEnabled(true);
//            gameNameField.setEnabled(true);
//            gameNameField.setText("");
//        }
//        repaint();
//    }



    private void drawSnake(Graphics g){

        g.setColor(Color.BLUE);

        //draw points in snake to canvas
        for (Point p : snake) {
            g.fillRect(p.x * Config.getFieldWidth(), p.y * Config.getFieldHeight(), Config.getFieldWidth(), Config.getFieldHeight());
        }
    }

    private void drawBoard(Graphics g){

        //draw outer frame
        g.drawRect(Config.getBoardStartXY(), Config.getBoardStartXY(), Config.getFieldWidth() * game.getMapSize(),
                Config.getFieldHeight() * game.getMapSize());


        //vertical lines
        for (int x = Config.getFieldWidth(); x < Config.getFieldWidth() * game.getMapSize() ; x+= Config.getFieldWidth()) {

            g.drawLine(x, Config.getBoardStartXY(), x, Config.getFieldHeight() * game.getMapSize());
        }
        //horizontal lines
        for (int y = Config.getFieldHeight(); y < Config.getFieldHeight() * game.getMapSize() ; y+= Config.getFieldHeight()) {

            g.drawLine(Config.getBoardStartXY(), y, Config.getFieldWidth() * game.getMapSize(), y);
        }
    }


    public void move(char ch){

        if(direction == Config.getAwaiting()) return;

        Point head = snake.peekFirst();
        Point newPoint = head;
        Point addPoint = (Point) newPoint.clone();

        //hardcoded chars and Points
        switch (ch) {

            case 'w':
                newPoint = new Point(head.x, head.y - 1);
                sb.append(ch);
                break;

            case 's':
                newPoint = new Point(head.x, head.y + 1);
                sb.append(ch);
                break;

            case 'a':
                newPoint = new Point(head.x - 1, head.y);
                sb.append(ch);
                break;

            case 'd':
                newPoint = new Point(head.x + 1, head.y);
                sb.append(ch);
                break;
        }
        //add head to front
        snake.push(addPoint);

        if (newPoint.x < 0 || newPoint.x > game.getMapSize() - 1){

            //out of board/bounds horizontally - end
            gameEnded = true;
            return;
        }
        else if (newPoint.y < 0 || newPoint.y > game.getMapSize() - 1){

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
