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
    private CreateNewGamePanel createNewGamePanel;
    private GameChooserPanel gameChooserPanel;
    private DeleteGamePanel deleteGamePanel;
    private GameOverviewerPanel gameOverviewerPanelPanel;


    public MainMenuPanel(){
        setLayout(null);

        welcomeLabel = new JLabel();
        btnPlay = new JButton(Config.getBtnPlayText());
        btnWatchReplay = new JButton(Config.getBtnWatchReplayText());
        btnShowHighScore = new JButton(Config.getBtnShowHighScoreText());
        btnDeleteGame = new JButton(Config.getBtnDeleteGameText());
        btnLogOut = new JButton(Config.getBtnLogoutText());
        sidePanel = new JPanel();
        centerPanel = new JPanel();
        createNewGamePanel = new CreateNewGamePanel();
        deleteGamePanel = new DeleteGamePanel();
        gameChooserPanel = new GameChooserPanel();
        gameOverviewerPanelPanel = new GameOverviewerPanel();
        cl = new CardLayout();

        sidePanel.setBounds(Config.getZeroXY(), Config.getZeroXY(), Config.getWidth3JComponent(), Config.getAppHeight());
        centerPanel.setBounds(Config.getWidth3JComponent(), Config.getZeroXY(), Config.getReplayWidth()*2, Config.getAppHeight());
        sidePanel.setLayout(null);
        centerPanel.setLayout(cl);

        welcomeLabel.setBounds(Config.getDefaultXPosJComponent(), Config.getY1PosJComponent(),
                Config.getLblWidth(), Config.getDefaultHeightJComponent());

        btnPlay.setBounds(Config.getDefaultXPosJComponent(), Config.getY3PosJComponent(),
                Config.getDefaultWidthJComponent(), Config.getDefaultHeightJComponent());
        btnWatchReplay.setBounds(Config.getDefaultXPosJComponent(), Config.getY4PosJComponent(),
                Config.getDefaultWidthJComponent(), Config.getDefaultHeightJComponent());
        btnShowHighScore.setBounds(Config.getDefaultXPosJComponent(), Config.getY5PosJComponent(),
                Config.getDefaultWidthJComponent(), Config.getDefaultHeightJComponent());
        btnDeleteGame.setBounds(Config.getDefaultXPosJComponent(), Config.getY6PosJComponent(),
                Config.getDefaultWidthJComponent(), Config.getDefaultHeightJComponent());
        btnLogOut.setBounds(Config.getDefaultXPosJComponent(), Config.getY7PosJComponent(),
                Config.getDefaultWidthJComponent(), Config.getDefaultHeightJComponent());

        sidePanel.add(welcomeLabel);
        sidePanel.add(btnPlay);
        sidePanel.add(btnWatchReplay);
        sidePanel.add(btnShowHighScore);
        sidePanel.add(btnDeleteGame);
        sidePanel.add(btnLogOut);

        centerPanel.add(createNewGamePanel, Config.getCreateNewGameScreen());
        centerPanel.add(gameChooserPanel, Config.getGameChooserScreen());
        centerPanel.add(deleteGamePanel, Config.getDeleteGameScreen());
        centerPanel.add(gameOverviewerPanelPanel, Config.getGameOverviewerScreen());


        add(sidePanel);
        add(centerPanel);


    }

    public void setFonts(Font f){

        welcomeLabel.setFont(f);
        deleteGamePanel.setFonts(f);
        createNewGamePanel.setFonts(f);
        gameChooserPanel.setFonts(f);
        gameOverviewerPanelPanel.setFonts(f);
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
        centerPanel.add(replaySnake, Config.getReplaySnakeScreen());
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
        centerPanel.add(snakeGameEngine, Config.getPlaySnakeScreen());
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

    public CreateNewGamePanel getCreateNewGamePanel() {
        return createNewGamePanel;
    }

    public GameChooserPanel getGameChooserPanel(){

        return gameChooserPanel;
    }

    public DeleteGamePanel getDeleteGamePanel() {
        return deleteGamePanel;
    }

    public GameOverviewerPanel getGameOverviewerPanelPanel(){

        return gameOverviewerPanelPanel;
    }
}
