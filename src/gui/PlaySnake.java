package gui;

import sdk.Config;
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
public class PlaySnake extends JPanel implements ActionListener{

    private LinkedList<Point> snake;
    //TODO: flytte move over i controller
    private char direction;
    private StringBuilder sb;
    private String moves;
    private Timer tm;
    private boolean gameEnded;

    private JButton btnSend;
    private JTable opponentTable;
    private JTextField gameNameField;
    private JScrollPane scrollPane;
    private UserTableModel tableModel;

    public PlaySnake(){

        setLayout(null);
        //addKeyListener(this);
        //addActionListener(l);

        gameNameField = new JTextField();
        btnSend = new JButton("Send challenge");
        //btnSend.addActionListener(l);

        opponentTable = new JTable();
        scrollPane = new JScrollPane(opponentTable);

        gameNameField.setBounds(40, 840, 150, 30);
        btnSend.setBounds(40, 900, 150, 30);
        //opponentTable.setBounds(40, 720, 220, 30);
        scrollPane.setBounds(0, 540, 500, 260);

        gameNameField.setToolTipText("Give your game a name");
        gameNameField.setEnabled(false);
        btnSend.setEnabled(false);

        add(gameNameField);
        add(btnSend);
        add(scrollPane);
        //add(opponentList);

        //TODO: move to paintComponent if trouble restarting game?
        snake = new LinkedList<>();
        sb = new StringBuilder();
        tm = new Timer(Config.getDelay(), this);
        tm.start();

        generateDefaultSnake();
        setFocusable(true);
        gameEnded = false;

        keyBindings();
        //getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), Config.getUP());

        focusThis();
    }

    /**
     * Gives focus to PlaySnake panel. Important for the key listener!
     *
     */
    public void focusThis(){

        setFocusable(true);
        requestFocus();
        requestFocusInWindow();
    }

    public String getGameNameText(){

        return gameNameField.getText();
    }

    public void setOpponentTableModel(ArrayList<User> users){

        tableModel = new UserTableModel(users);
        opponentTable.setModel(tableModel);
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

    public User getOpponent(){

        return tableModel.getUserFromTable(opponentTable.getSelectedRow());
    }

    public void addActionListener(ActionListener l){

        btnSend.addActionListener(l);
    }

    private void generateDefaultSnake() {

        //TODO: When playing a game from challenge, change to +2 instead
        snake.clear();
        snake.add(new Point((Config.getBoardWidth()-2)/2,(Config.getBoardHeight()-2)/2));
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
            btnSend.setEnabled(false);
            gameNameField.setEnabled(false);
            gameNameField.setText("Type game name here..");
        }
        else{
            moves = sb.toString();
            btnSend.setEnabled(true);
            gameNameField.setEnabled(true);
            gameNameField.setText("");
        }
        repaint();
    }



    private void drawSnake(Graphics g){

        g.setColor(Color.BLUE);

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

    public boolean isGameEnded() {
        return gameEnded;
    }

    public char getDirection() {
        return direction;
    }

    public void setDirection(char direction) {
        this.direction = direction;
    }

    public void setGameNameField(String text) {
        gameNameField.setText(text);
    }

    public void componentsSetEnabled(boolean b) {

        btnSend.setEnabled(b);
        gameNameField.setEnabled(b);
    }

    public String getSbToString() {
        return sb.toString();
    }

    public void setMoves(String moves) {
        this.moves = moves;
    }
}
