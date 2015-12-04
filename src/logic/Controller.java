package logic;

import gui.DialogMessage;
import gui.Screen;
import logic.subcontroller.*;
import sdk.*;
import sdk.dto.Game;
import sdk.dto.Gamer;
import sdk.dto.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * Class contains the implementation of the action- and item listeners to handle button events etc. Furthermore it combines
 * the action- and item events with the subcontrollers, thereby combining the UI and the SDK.
 */
public class Controller {

    //GUI objects
    private Screen screen;

    //DTO's
    private User currentUser;
    private Game replayGame;
    private Game newGame;

    //Logic objects
    private LoginLogic loginLogic;
    private TableLogic tableLogic;
    private DeleteGameLogic deleteGameLogic;
    private GameOverviewerLogic gameOverviewerLogic;
    private GameChooserLogic gameChooserLogic;
    private CreateGameLogic createGameLogic;
    private CreateUserLogic createUserLogic;
    private boolean isAuthenticated;


    //creating instances of gui (screen), sub controllers and the current user
    public Controller(){

        screen = new Screen();
        loginLogic = new LoginLogic(screen);
        tableLogic = new TableLogic(screen);
        deleteGameLogic = new DeleteGameLogic(screen);
        gameOverviewerLogic = new GameOverviewerLogic(screen);
        gameChooserLogic = new GameChooserLogic(screen);
        createGameLogic = new CreateGameLogic(screen);
        createUserLogic = new CreateUserLogic(screen);

        //instantiating the currentUser object, which will be used to the logged on state in the client
        currentUser = new User();
    }

    /**
     * Injection of actionlisteners via inner classes
     */
    public void run(){

        //injection of the 'first' three panels.
        screen.getLoginPanel().addActionListeners(new LoginHandlerClass());
        screen.getMainMenuPanel().addActionListeners(new MainMenuHandlerClass());
        screen.getCreateUserPanel().addActionListeners(new CreateUserHandlerClass());

        //injection of the action listeners of the 'inner' panels to handle actions done within the main menu
        screen.getMainMenuPanel().getCreateNewGamePanel().addActionListeners(new CreateNewGameHandlerClass());
        screen.getMainMenuPanel().getGameChooserPanel().addActionListeners(new GameChooserHandlerClass());
        screen.getMainMenuPanel().getDeleteGamePanel().addActionListeners(new DeleteGameHandlerClass());
        screen.getMainMenuPanel().getGameOverviewerPanel().addActionListeners(new GameOverviewerHandlerClass());
        screen.getMainMenuPanel().getHighScoresPanel().addActionListeners(new HighScoresHandlerClass());

        //injection of item listeners for the combo boxes
        screen.getMainMenuPanel().getGameChooserPanel().addItemListeners(new GameChooserHandlerClass());
        screen.getMainMenuPanel().getGameOverviewerPanel().addItemListeners(new GameOverviewerHandlerClass());

    }

    /**
     * Inner class action listener for login panel
     */
    private class LoginHandlerClass implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            System.out.println(currentUser.getUsername());


            if (e.getActionCommand().equals(Config.getBtnLoginText())) {

                String message = loginLogic.authenticated(currentUser);

                isAuthenticated = message.equals(Config.getLoginAuthentication());

                if (isAuthenticated) {

                    //showing start screen - the game chooser panel
                    screen.show(Config.getMainMenuScreen());
                    screen.getMainMenuPanel().show(Config.getGameChooserScreen());

                    //setting user info.
                    loginLogic.setUserInfo(currentUser, message);

                    //setting tables for welcome screen
                    tableLogic.setUserTableModel(currentUser);
                    tableLogic.setGameChooserTableModel(currentUser);
                    tableLogic.setHighScoresMovingPanel(new MovingHighScoresHandlerClass());
                }
                else {
                    screen.getLoginPanel().setFailedLoginAttempt(message);
                }
                screen.getLoginPanel().clearFields();
            }
            else if (e.getActionCommand().equals(Config.getBtnCreateNewUserText())) {

                screen.show(Config.getCreateUserScreen());
            }
        }
    }

    /**
     * Inner class action listener for main menu panel
     */
    private class MainMenuHandlerClass implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            if (e.getActionCommand().equals(Config.getBtnPlayText())) {

                screen.getMainMenuPanel().show(Config.getGameChooserScreen());
            }
            else if (e.getActionCommand().equals(Config.getBtnWatchReplayText())) {

                screen.getMainMenuPanel().show(Config.getGameOverviewerScreen());
                tableLogic.setGameOverviewerTableModel(currentUser);
            }
            else if (e.getActionCommand().equals(Config.getBtnShowHighScoreText())) {

                screen.getMainMenuPanel().show(Config.getHighScoresScreen());
                tableLogic.setHighScoreTableModel();
            }
            else if (e.getActionCommand().equals(Config.getBtnDeleteGameText())) {

                screen.getMainMenuPanel().show(Config.getDeleteGameScreen());
                tableLogic.setGamesToDeleteTableModel(currentUser);
            }
            else if (e.getActionCommand().equals(Config.getBtnLogoutText())) {

                if (DialogMessage.showConfirmMessage(screen, Config.getLogoutMessage(), Config.getLogoutTitle())) {

                    isAuthenticated = loginLogic.logOut();

                    //'resetting' current user object when logging out
                    currentUser = new User();

                    System.out.println(currentUser.getUsername());
                    System.out.println(isAuthenticated);
                }
            }
        }
    }

    /**
     * Inner class that handles what to do when client attempts to create a new user
     */
    private class CreateUserHandlerClass implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            if (e.getActionCommand().equals(Config.getBtnCreateUserText())){

                String message = createUserLogic.create();

                if (!message.equals(Config.getConfirmedUserCreationText())) {

                }
                else
                    screen.getCreateUserPanel().clearFields();
            }
            else if (e.getActionCommand().equals(Config.getBtnBackToLoginText())) {
                screen.show(Config.getLoginScreen());
            }
        }
    }

    /**
     * Implementation of key binding instead of keylistener. Inner class registers keyboard input and sets direction
     * of the snake accordingly
     */
    private class SnakeEngineKeyBindingHandlerClass extends AbstractAction {

        public SnakeEngineKeyBindingHandlerClass(String text){
            super(text);
            putValue(ACTION_COMMAND_KEY, text);
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            if (e.getActionCommand().equals(Character.toString(Config.getUp()))){
                if(screen.getMainMenuPanel().getSnakeGameEngine().getDirection()!= Config.getDown())
                    screen.getMainMenuPanel().getSnakeGameEngine().setDirection(Config.getUp());
            }
            else if (e.getActionCommand().equals(Character.toString(Config.getDown()))){
                if(screen.getMainMenuPanel().getSnakeGameEngine().getDirection()!= Config.getUp())
                    screen.getMainMenuPanel().getSnakeGameEngine().setDirection(Config.getDown());
            }
            else if (e.getActionCommand().equals(Character.toString(Config.getLeft()))){
                if(screen.getMainMenuPanel().getSnakeGameEngine().getDirection()!= Config.getRight())
                    screen.getMainMenuPanel().getSnakeGameEngine().setDirection(Config.getLeft());
            }
            else if (e.getActionCommand().equals(Character.toString(Config.getRight()))){
                if(screen.getMainMenuPanel().getSnakeGameEngine().getDirection()!= Config.getLeft())
                    screen.getMainMenuPanel().getSnakeGameEngine().setDirection(Config.getRight());
            }
        }
    }

    /**
     * Inner class that handles whether to draw snake or stop drawing and create the game.
     */
    private class SnakeEngineDrawClass implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            String message = createGameLogic.draw(newGame);

            if (message != null) {

                DialogMessage.showMessage(screen, message);
                screen.getMainMenuPanel().show(Config.getGameChooserScreen());


                //starting new thread where client tries to create game ten times if server connection has failed
                if (message.equals("Connection to server failed")){

                    createGameLogic.saveGameInCache(newGame);
                }
            }

        }
    }

    /**
     * Inner class, uses CreateNewGamePanel to setup a new game
     */
    private class CreateNewGameHandlerClass implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            //enable table whenever the open game check box is not selected
            screen.getMainMenuPanel().getCreateNewGamePanel().setOpponentTableState(
                    !screen.getMainMenuPanel().getCreateNewGamePanel().getOpenGameChoice());

            if (e.getActionCommand().equals(Config.getBtnRefreshText())){

                tableLogic.setUserTableModel(currentUser);
            }
            else if (e.getActionCommand().equals(Config.getBtnSendText())) {

                try {
                    newGame = new Game();

                    if (!screen.getMainMenuPanel().getCreateNewGamePanel().getGameNameText().equals(Config.getClearField())) {
                        newGame.setName(screen.getMainMenuPanel().getCreateNewGamePanel().getGameNameText());

                        Gamer host = new Gamer();
                        Gamer opponent = new Gamer();

                        host.setId(currentUser.getId());

                        //only sets opponent if open game is not checked. Api makes sure to create game accordingly
                        if (!screen.getMainMenuPanel().getCreateNewGamePanel().getOpenGameChoice()) {
                            opponent.setId(screen.getMainMenuPanel().getCreateNewGamePanel().getOpponent().getId());
                            newGame.setOpponent(opponent);
                        }
                        newGame.setHost(host);
                        newGame.setMapSize(screen.getMainMenuPanel().getCreateNewGamePanel().getMapSize());

                        //takes an actionlistener as parameter for a dynamic injection of listener
                        screen.getMainMenuPanel().addPlaySnake(new SnakeEngineDrawClass(), newGame);

                        //Injection of binding class for keylistening
                        screen.getMainMenuPanel().getSnakeGameEngine().getActionMap().put(Config.getUp(),
                                new SnakeEngineKeyBindingHandlerClass(Character.toString(Config.getUp())));
                        screen.getMainMenuPanel().getSnakeGameEngine().getActionMap().put(Config.getDown(),
                                new SnakeEngineKeyBindingHandlerClass(Character.toString(Config.getDown())));
                        screen.getMainMenuPanel().getSnakeGameEngine().getActionMap().put(Config.getLeft(),
                                new SnakeEngineKeyBindingHandlerClass(Character.toString(Config.getLeft())));
                        screen.getMainMenuPanel().getSnakeGameEngine().getActionMap().put(Config.getRight(),
                                new SnakeEngineKeyBindingHandlerClass(Character.toString(Config.getRight())));

                        screen.getMainMenuPanel().setSidePanelState(false);
                    }
                    else {
                        JOptionPane.showMessageDialog(screen, Config.getMissingGameNameText());
                        screen.getMainMenuPanel().getCreateNewGamePanel().requestFocusGameNameField();
                    }

                } catch (ArrayIndexOutOfBoundsException e2) {
                    e2.printStackTrace();
                    JOptionPane.showMessageDialog(screen, Config.getMissingOpponentText());
                }
            }
        }
    }

    /**
     * Class handles how to show replays in client.
     */
    private class ReplaySnakeHandlerClass implements ActionListener {

        int counter = Config.getCount();

        @Override
        public void actionPerformed(ActionEvent e) {

            //populates snake by taking the string controls and checking whether up, down, left or right (w,s,a,d).
            if (!screen.getMainMenuPanel().getReplaySnake().hasGameEnded()) {

                if (counter < replayGame.getHost().getControls().length()) {

                    screen.getMainMenuPanel().getReplaySnake().move(
                            replayGame.getHost(), replayGame.getHost().getControls().charAt(counter));
                }
                //checking theres an opponent and populates the snake with points
                if (replayGame.getOpponent().getControls() != null) {

                    if (counter < replayGame.getOpponent().getControls().length()) {

                        screen.getMainMenuPanel().getReplaySnake().move(
                                replayGame.getOpponent(), replayGame.getOpponent().getControls().charAt(counter));
                    }
                }
            }
            boolean isAnimationDone = counter > replayGame.getHost().getControls().length() &&
                    ((replayGame.getOpponent().getControls() != null && counter > replayGame.getOpponent().getControls().length()) ||
                            replayGame.getOpponent().getControls() == null);

            //repaints as long as there are usercontrols and opponentcontrols
            if(isAnimationDone){

                screen.getMainMenuPanel().getReplaySnake().setGameHasEnded(true);
                //screen.getMainMenuPanel().getReplaySnake().stopTimer();
                screen.getMainMenuPanel().setSidePanelState(true);
                counter = Config.getCount();
            }
            else {
                counter++;
                screen.getMainMenuPanel().getReplaySnake().repaint();
            }
        }
    }

    /**
     * Handles events from the GameChooserPanel to start the snake game engine so user can play a game on the client app
     */
    private class GameChooserHandlerClass implements ActionListener, ItemListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            if (e.getActionCommand().equals(Config.getBtnJoinSelectedGameText())){

                newGame = gameChooserLogic.joinGame(currentUser);

                if (newGame != null) {
                    screen.getMainMenuPanel().addPlaySnake(new SnakeEngineDrawClass(), newGame);

                    //Adding keybinding to register user input for snake movement
                    screen.getMainMenuPanel().getSnakeGameEngine().getActionMap().put(Config.getUp(),
                            new SnakeEngineKeyBindingHandlerClass(Character.toString(Config.getUp())));
                    screen.getMainMenuPanel().getSnakeGameEngine().getActionMap().put(Config.getDown(),
                            new SnakeEngineKeyBindingHandlerClass(Character.toString(Config.getDown())));
                    screen.getMainMenuPanel().getSnakeGameEngine().getActionMap().put(Config.getLeft(),
                            new SnakeEngineKeyBindingHandlerClass(Character.toString(Config.getLeft())));
                    screen.getMainMenuPanel().getSnakeGameEngine().getActionMap().put(Config.getRight(),
                            new SnakeEngineKeyBindingHandlerClass(Character.toString(Config.getRight())));
                }
            }
            else if (e.getActionCommand().equals(Config.getBtnCreateNewGameText())){

                screen.getMainMenuPanel().show(Config.getCreateNewGameScreen());
            }
            else if (e.getActionCommand().equals(Config.getBtnRefreshText())){

                tableLogic.setGameChooserTableModel(currentUser);
            }
        }

        /**
         * Item listener fro the combo box. method uses table logic sub controller to set what to show in the tables
         * @param e
         */
        @Override
        public void itemStateChanged(ItemEvent e) {
            
            if (e.getStateChange() == ItemEvent.SELECTED && isAuthenticated)

                tableLogic.setGameChooserTableModel(currentUser);
        }
    }

    /**
     * Takes care of the logic of how to delete a game.
     */
    private class DeleteGameHandlerClass implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            if (DialogMessage.showConfirmMessage(screen, Config.getBtnDeleteText(), Config.getBtnDeleteGameText())) {

                try {
                    DialogMessage.showMessage(screen, deleteGameLogic.deleteGame());

                    tableLogic.setGamesToDeleteTableModel(currentUser);
                }
                //Catch the missing selection and prompt the user
                catch (IndexOutOfBoundsException e2) {

                    DialogMessage.showMessage(screen, Config.getMissingGameSelectionText());
                }
            }
        }
    }

    /**
     * Class instantiates the replayer. The gameOverviewerLogic method - show replay returns the selected game which
     * will be used in the replay handler class
     */
    private class GameOverviewerHandlerClass implements ActionListener, ItemListener {


        @Override
        public void actionPerformed(ActionEvent e) {


            if (e.getActionCommand().equals(Config.getBtnReplayText())){

                try {
                    replayGame = screen.getMainMenuPanel().getGameOverviewerPanel().getGame();
                    replayGame = gameOverviewerLogic.showReplay(new ReplaySnakeHandlerClass(), replayGame, currentUser.getId(), false);
                }
                catch (IndexOutOfBoundsException e2){

                    DialogMessage.showMessage(screen, Config.getMissingGameSelectionText());
                    screen.getMainMenuPanel().setSidePanelState(true);
                }

            }
            else if (e.getActionCommand().equals(Config.getBtnRefreshText())){

                tableLogic.setGameOverviewerTableModel(currentUser);
            }
        }

        /**
         * Item listener for the combo box
         * @param e
         */
        @Override
        public void itemStateChanged(ItemEvent e) {

            //using boolean isAuthenticated to prevent server call when logging out of client and combo box is reset
            if (e.getStateChange() == ItemEvent.SELECTED && isAuthenticated)
                tableLogic.setGameOverviewerTableModel(currentUser);
        }
    }

    /**
     * Inner class for the high scores top 3 panel. Updates the y-coordinates of the high scores and repaints to give
     * a moving motion
     */
    private class MovingHighScoresHandlerClass implements ActionListener {


        @Override
        public void actionPerformed(ActionEvent e) {

            //As long as the high scores are visible the y-coordinates will be incremented by 1
            if (screen.getMainMenuPanel().getHighScoresMovingPanel().getYCoord() < Config.getY4PosJComponent()) {

                screen.getMainMenuPanel().getHighScoresMovingPanel().setYCoord(
                        screen.getMainMenuPanel().getHighScoresMovingPanel().getYCoord() + 1);
            }
            //when the high scores have disappeared from the panel, reset the starting position
            else {
                screen.getMainMenuPanel().getHighScoresMovingPanel().setYCoord(Config.getStartYMovingPanel());
            }

            screen.getMainMenuPanel().getHighScoresMovingPanel().repaint();
        }
    }

    private class HighScoresHandlerClass implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            try {
                replayGame = screen.getMainMenuPanel().getHighScoresPanel().getGameFromHighScore();

                replayGame = gameOverviewerLogic.showReplay(
                        new ReplaySnakeHandlerClass(), replayGame, currentUser.getId(), true );

            }
            catch (IndexOutOfBoundsException e2){

                DialogMessage.showMessage(screen, Config.getMissingGameSelectionText());
                screen.getMainMenuPanel().setSidePanelState(true);
            }
        }
    }
}
