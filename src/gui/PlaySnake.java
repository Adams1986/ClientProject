package gui;

import sdk.Config;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;

/**
 * Play a game of snake. Draws the movements of a user to a JPanel
 */
public class PlaySnake extends JPanel implements ActionListener, KeyListener {

    private LinkedList<Point> snake;
    private char direction;
    private StringBuilder sb;
    private String moves;
    private Timer tm;
    private boolean gameEnded;

    private JButton btnSend;
    private JTextField sendField;

    public PlaySnake(){

        setLayout(null);

        addKeyListener(this);

        btnSend = new JButton("Send challenge");
        sendField = new JTextField(20);

        btnSend.setBounds(40, 380, 150, 30);
        sendField.setBounds(40, 340, 220, 30);

        add(btnSend);
        add(sendField);

        //TODO: move to paintComponent if trouble restarting game?
        snake = new LinkedList<>();
        sb = new StringBuilder();
        tm = new Timer(Config.getDelay(), this);
        tm.start();

        generateDefaultSnake();
        setFocusable(true);
        gameEnded = false;
    }

    public String getSendField() {
        return sendField.getText();
    }

    public void addActionlistener(ActionListener l){

        btnSend.addActionListener(l);
    }

    private void generateDefaultSnake() {

        snake.clear();
        snake.add(new Point(6,6));
        direction = Config.getAwaiting();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        drawBoard(g);
        drawSnake(g);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(!gameEnded) {
            move(direction);
        }
        else{
            moves = sb.toString();
        }
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        switch (e.getKeyCode()){

            case KeyEvent.VK_UP:
                if(direction != Config.getDOWN())
                direction = Config.getUP();
                break;

            case KeyEvent.VK_DOWN:
                if(direction != Config.getUP())
                    direction = Config.getDOWN();
                break;

            case KeyEvent.VK_LEFT:
                if(direction != Config.getRIGHT())
                    direction = Config.getLEFT();
                break;

            case KeyEvent.VK_RIGHT:
                if(direction != Config.getLEFT())
                    direction = Config.getRIGHT();
                break;

            case KeyEvent.VK_SPACE:
                if(gameEnded){
                    System.out.println("space");
                    revalidate();
                    removeAll();
                    repaint();
                    break;
                }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    private void drawSnake(Graphics g){

        g.setColor(Color.BLUE);

        //this draws first point, was implemented for replaysnake
//        g.fillRect(snake.peekFirst().x * Config.getFieldWidth(),
//                snake.peekFirst().y * Config.getFieldHeight(), Config.getFieldWidth(), Config.getFieldHeight());

        //draw points in snake to canvas
        for (Point p : snake) {
            g.fillRect(p.x * Config.getFieldWidth(), p.y * Config.getFieldHeight(), Config.getFieldWidth(), Config.getFieldHeight());
        }
    }

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



    private void move(char ch){

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

        if (newPoint.x < 0 || newPoint.x > Config.getBoardWidth() - 1){

            //out of board/bounds horizontally - end
            gameEnded = true;
            return;
        }
        else if (newPoint.y < 0 || newPoint.y > Config.getBoardHeight() - 1){

            //out of bounds vertically
            gameEnded = true;
            return;
        }
        else if (snake.contains(newPoint)){

            //eat yourself - end
            gameEnded = true;
            return;
        }
        else if (snake.size() == Config.getBoardHeight() * Config.getBoardWidth()){

            //filled the whole board -end
            gameEnded = true;
            return;
        }
        //add head again
        snake.push(newPoint);
    }

    public String getMoves() {
        return moves;
    }
}
