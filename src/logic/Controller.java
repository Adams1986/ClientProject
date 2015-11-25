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

/**
 * Created by ADI on 24-10-2015.
 */
public class Controller {

    private Screen screen;

    private User currentUser;
    private Game replayGame;
    private Game newGame;

    private LoginLogic loginLogic;
    private TableLogic tableLogic;
    private DeleteGameLogic deleteGameLogic;
    private GameOverviewerLogic gameOverviewerLogic;
    private GameChooserLogic gameChooserLogic;
    private GameEngineLogic gameEngineLogic;
    private DialogMessage dialogMessage;

    private boolean isAuthenticated;
    private CreateUserLogic createUserLogic;

    public Controller(){

        isAuthenticated = false;
        screen = new Screen();
        loginLogic = new LoginLogic(screen);
        tableLogic = new TableLogic(screen);
        deleteGameLogic = new DeleteGameLogic(screen);
        gameOverviewerLogic = new GameOverviewerLogic(screen);
        gameChooserLogic = new GameChooserLogic(screen);
        gameEngineLogic = new GameEngineLogic(screen);
        dialogMessage = new DialogMessage(screen);
        createUserLogic = new CreateUserLogic(screen);
        currentUser = new User();
    }

    /**
     * Injection of actionlisteners via inner classes
     */
    public void run(){

        screen.getLoginPanel().addActionListeners(new LoginHandlerClass());
        screen.getMainMenuPanel().addActionListeners(new MainMenuHandlerClass());
        screen.getCreateUserPanel().addActionListeners(new CreateUserHandlerClass());

        screen.getMainMenuPanel().getCreateNewGamePanel().addActionListeners(new CreateNewGameHandlerClass());
        screen.getMainMenuPanel().getGameChooserPanel().addActionListeners(new GameChooserHandlerClass());
        screen.getMainMenuPanel().getDeleteGamePanel().addActionListeners(new DeleteGameHandlerClass());
        screen.getMainMenuPanel().getGameOverviewerPanel().addActionListeners(new GameOverviewerHandlerClass());

    }

    /**
     * Inner class action listener for login panel
     */
    private class LoginHandlerClass implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            if (e.getActionCommand().equals(Config.getBtnLoginText())) {

                String message = loginLogic.authenticated(currentUser);
                isAuthenticated = message.equals(Config.getLoginAuthentication());

                if (isAuthenticated) {

                    screen.show(Config.getMainMenuScreen());
                    screen.getMainMenuPanel().show(Config.getGameChooserScreen());
                    screen.getMainMenuPanel().setWelcomeMessage(message);
                    screen.getMainMenuPanel().setInfoMessage(Config.getWelcomeText() + currentUser.getUsername());
                    tableLogic.setGamesTableModel(currentUser);
                    tableLogic.setUserTableModel(currentUser);

                    //TODO highscores when logging on because of moving panel
                    tableLogic.setHighScoresMovingPanel(new MovingHighScoresHandlerClass());

                    gameOverviewerLogic.refreshTable(currentUser);

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
     * Inner class actionlistener for main menu panel
     */
    private class MainMenuHandlerClass implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            if (e.getActionCommand().equals(Config.getBtnPlayText())) {

                screen.getMainMenuPanel().show(Config.getGameChooserScreen());
            }
            else if (e.getActionCommand().equals(Config.getBtnWatchReplayText())) {

                screen.getMainMenuPanel().show(Config.getGameOverviewerScreen());
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

                if (dialogMessage.showConfirmMessage(Config.getLogoutMessage(), Config.getLogoutTitle())) {

                    screen.getMainMenuPanel().show(Config.getGameChooserScreen());
                    screen.show(Config.getLoginScreen());
                    screen.getMainMenuPanel().getCreateNewGamePanel().resetFields();
                    screen.getMainMenuPanel().getGameOverviewerPanel().resetFields();
                    screen.getMainMenuPanel().getGameChooserPanel().resetFields();
                    screen.getMainMenuPanel().getHighScoresMovingPanel().stopTimer();
                    currentUser = new User();
                }
            }
        }
    }

    private class CreateUserHandlerClass implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            if (e.getActionCommand().equals(Config.getBtnCreateUserText())){

                String message = createUserLogic.create(dialogMessage);

                if (!message.equals(Config.getConfirmedUserCreationText())) {

                } else
                    screen.getCreateUserPanel().clearFields();
            }
            else if (e.getActionCommand().equals(Config.getBtnBackToLoginText())) {
                screen.show(Config.getLoginScreen());
            }
        }
    }

    private class SnakeEngineKeyBindingHandlerClass extends AbstractAction {

        public SnakeEngineKeyBindingHandlerClass(String text){
            super(text);
            putValue(ACTION_COMMAND_KEY, text);
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            if(e.getActionCommand().equals(Character.toString(Config.getUp()))){
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
     * Inner class that handles whether to draw snake or stop drawing and create a new game.
     */
    private class SnakeEngineDrawClass implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            gameEngineLogic.draw(newGame, dialogMessage);

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

            if (e.getActionCommand().equals(Config.getBtnSendText())) {

                //TODO: move shit to subcontroller
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

    private class GameChooserHandlerClass implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            if (e.getActionCommand().equals(Config.getBtnJoinSelectedGameText())){

                newGame = gameChooserLogic.joinGame(currentUser, dialogMessage);

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
            else if (e.getActionCommand().equals(Config.getBtnCreateNewGameText())){

                screen.getMainMenuPanel().show(Config.getCreateNewGameScreen());
            }
            else if (e.getActionCommand().equals(Config.getBtnRefreshText())){

                //gameChooserLogic.refreshTable(currentUser);
                tableLogic.setGameChooserTableModel(currentUser);
            }
        }
    }

    private class DeleteGameHandlerClass implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            if (dialogMessage.showConfirmMessage(Config.getBtnDeleteText(), Config.getBtnDeleteGameText())) {

                try {
                    dialogMessage.showMessage(deleteGameLogic.deleteGame());

                    tableLogic.setGamesToDeleteTableModel(currentUser);
                } catch (IndexOutOfBoundsException e2) {
                    dialogMessage.showMessage(Config.getMissingGameSelectionText());
                }
            }
        }
    }

    private class GameOverviewerHandlerClass implements ActionListener {

        //private ArrayList<Game> replayGames;
        @Override
        public void actionPerformed(ActionEvent e) {

            if (e.getActionCommand().equals(Config.getBtnReplayText())){

                screen.getMainMenuPanel().setSidePanelState(false);

                try {

                    replayGame = gameOverviewerLogic.showReplay(new ReplaySnakeHandlerClass(), currentUser.getId());
                }
                catch (IndexOutOfBoundsException e2){

                    dialogMessage.showMessage(Config.getMissingGameSelectionText());
                }

            }
            else if (e.getActionCommand().equals(Config.getBtnRefreshText())){

                gameOverviewerLogic.refreshTable(currentUser);

            }
        }
    }

    private class MovingHighScoresHandlerClass implements ActionListener {


        @Override
        public void actionPerformed(ActionEvent e) {

            if (screen.getMainMenuPanel().getHighScoresMovingPanel().getYCoord() < Config.getY4PosJComponent())
                screen.getMainMenuPanel().getHighScoresMovingPanel().setYCoord(
                        screen.getMainMenuPanel().getHighScoresMovingPanel().getYCoord()+1);
            else
                screen.getMainMenuPanel().getHighScoresMovingPanel().setYCoord(Config.getStartYMovingPanel());

            screen.getMainMenuPanel().getHighScoresMovingPanel().repaint();
        }
    }
}
