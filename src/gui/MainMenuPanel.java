package gui;

import sdk.Config;
import sdk.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

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
    private CardLayout cl;
    private SnakeGameEngine snakeGameEngine;
    private ReplaySnake replaySnake;
    private CreateNewGame createNewGame;
    private GameChooser gameChooser;


    public MainMenuPanel(){
        setLayout(null);

        welcomeLabel = new JLabel("welcomelabel...");
        btnPlay = new JButton(Config.getBtnPlayText());
        btnWatchReplay = new JButton("Watch a replay");
        btnShowHighScore = new JButton("High scores");
        btnDeleteGame = new JButton("Delete a game");
        btnLogOut = new JButton("Log out");
        sidePanel = new JPanel();
        centerPanel = new JPanel();
        createNewGame = new CreateNewGame();
        gameChooser = new GameChooser();
        cl = new CardLayout();

        sidePanel.setBounds(0, 0, 320, 500);
        centerPanel.setBounds(300, 0, Config.getReplayWidth()*2, Config.getAppHeight());
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

        centerPanel.add(createNewGame, Config.getCreateNewGameScreen());
        centerPanel.add(gameChooser, Config.getGameChooserScreen());


        add(sidePanel);
        add(centerPanel);


    }

    /**
     * For controller to use to enable/disable sidepanel with menu buttons to help user not to get distracted and focus
     * on the game and more importantly to avoid the unreliable nature of pressing menu button while game is currently
     * being played.
     * Also avoids cheating if not doing so well TODO: (can close the client app though)
     * @param b boolean to determine if sidepanels components should be enabled
     */
    public void setSidePanelState(boolean b) {

        Component[] components = sidePanel.getComponents();

        for (Component c : components)
            c.setEnabled(b);
    }

    /**
     * Dynamically shows a replay of a game. To be used in the logic
     */
    public void addReplaySnakeToPanel(Game game, ActionListener l){

        replaySnake = new ReplaySnake(game, l);
        centerPanel.add(replaySnake);
        cl.addLayoutComponent(replaySnake, Config.getReplaySnakeScreen());
        cl.show(centerPanel, Config.getReplaySnakeScreen());

    }

    public ReplaySnake getReplaySnake(){

        //makes sure that addReplaySnakeToPanel is called first
        if (replaySnake != null)
            return replaySnake;

        return null;
    }

    /**
     * Creates a new instance of the SnakeGameEngine JPanel,
     * to be started everytime an event happens (e.g. button-click)
     */
    public void addPlaySnake(ActionListener l, Game newGame){

        snakeGameEngine = new SnakeGameEngine(l, newGame);
        centerPanel.add(snakeGameEngine);
        cl.addLayoutComponent(snakeGameEngine, Config.getPlaySnakeScreen());
        cl.show(centerPanel, Config.getPlaySnakeScreen());
    }

    public SnakeGameEngine getSnakeGameEngine(){

        return snakeGameEngine;
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

    public void setWelcomeMessage(String welcomeMessage) {
        welcomeLabel.setText(welcomeMessage);
    }

    public CreateNewGame getCreateNewGamePanel() {
        return createNewGame;
    }

    public GameChooser getGameChooserPanel(){

        return gameChooser;
    }

}
