package gui;

import sdk.Config;
import sdk.Gamer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.LinkedList;

/**
 * The Main menu. Is a customised panel which contains the view of the clients main menu
 */
public class MainMenuPanel extends JPanel{

    private JLabel welcomeLabel;
    private JButton btnPlay;
    private JButton btnWatchReplay;
    private JButton btnShowHighScore;
    private JButton btnDeleteGame;
    private JButton btnLogOut;
    private JPanel sidePanel;
    private JPanel centerPanel;
    private JPanel snakePanel;
    private CardLayout cl;
    private PlaySnake playSnake;
    private String moves;


    public MainMenuPanel(){
        setLayout(null);

        welcomeLabel = new JLabel("welcomelabel...");
        btnPlay = new JButton("Play a game");
        btnWatchReplay = new JButton("Watch a replay");
        btnShowHighScore = new JButton("High scores");
        btnDeleteGame = new JButton("Delete a game");
        btnLogOut = new JButton("Log out");
        sidePanel = new JPanel();
        centerPanel = new JPanel();
        snakePanel = new JPanel();
        cl = new CardLayout();

        sidePanel.setBounds(0, 0, 320, 500);
        centerPanel.setBounds(300, 0, 320, 500);
        sidePanel.setLayout(null);
        centerPanel.setLayout(cl);

        welcomeLabel.setBounds(40, 40, 220, 20);

        //TODO: maybe make a method and throw it in the screen class
        welcomeLabel.setFont(new Font("Verdana", Font.BOLD, 16));

        btnPlay.setBounds(40, 180, 150, 30);
        btnWatchReplay.setBounds(40, 230, 150, 30);
        btnShowHighScore.setBounds(40, 280, 150, 30);
        btnDeleteGame.setBounds(40, 330, 150, 30);
        btnLogOut.setBounds(40, 380, 150, 30);

        sidePanel.add(welcomeLabel);
        sidePanel.add(btnPlay);
        sidePanel.add(btnWatchReplay);
        sidePanel.add(btnShowHighScore);
        sidePanel.add(btnDeleteGame);
        sidePanel.add(btnLogOut);


        add(sidePanel);
        add(centerPanel);


    }

    /**
     * Dynamically shows a replay of a game. To be used in the logic
     */
    public void replayGame(){

        //TODO: will probably take to gamer objects as parameter, for dynamic creating
        Gamer user = new Gamer();
        Gamer opponent = new Gamer();

        user.setSnakeColor(Color.BLUE);
        user.setSnake(new LinkedList<Point>());
        user.getSnake().add(new Point(6, 6));
        user.setControls("aaaaaawwwwwwdssssddddwwwddd");

        opponent.setSnakeColor(Color.RED);
        opponent.setSnake(new LinkedList<Point>());
        opponent.getSnake().add(new Point(8, 8));
        opponent.setControls("ddddddssssssaaaaaaaaawwwwddd");

        ReplaySnake replaySnake = new ReplaySnake(user, opponent);
        replaySnake.setBounds(0, 80, 320, 500);

        centerPanel.add(replaySnake);
        cl.addLayoutComponent(replaySnake, Config.getReplaySnakeScreen());
        cl.show(centerPanel, Config.getReplaySnakeScreen());

    }

    /**
     * Creates a new instance of the PlaySnake JPanel,
     * to be started everytime an event happens (e.g. button-click)
     */
    public void playSnake(){

        playSnake = new PlaySnake();
        playSnake.setBounds(0, 80, 320, 500);
        moves = playSnake.getMoves();

        centerPanel.add(playSnake);
        cl.addLayoutComponent(playSnake, Config.getPlaySnakeScreen());
        cl.show(centerPanel, Config.getPlaySnakeScreen());

        focusPlaySnake(playSnake);
    }
    /**
     * Method to be used by logic to inject an actionlistener,
     * so that the button clicks are handled there and not here.
     * @param l
     */
    public void addActionListeners(ActionListener l) {
        btnPlay.addActionListener(l);
        btnWatchReplay.addActionListener(l);
        btnShowHighScore.addActionListener(l);
        btnDeleteGame.addActionListener(l);
        btnLogOut.addActionListener(l);

    }

    public void show(String card){
        cl.show(centerPanel, card);
    }

    /**
     * Gives fokus to PlaySnake panel. Important for the keylistener!
     * Also important to initialize after JFrame has loaded
     * @param playSnake
     */
    public void focusPlaySnake(PlaySnake playSnake){
        playSnake.setFocusable(true);
        playSnake.requestFocus();
        playSnake.requestFocusInWindow();
    }

    public void setWelcomeMessage(String welcomeMessage) {
        welcomeLabel.setText(welcomeMessage);
    }

    public String getMoves() {
        return moves;
    }
}
