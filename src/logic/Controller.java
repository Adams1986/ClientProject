package logic;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import gui.DialogMessage;
import gui.Screen;
import logic.subcontroller.DeleteGameLogic;
import logic.subcontroller.GameOverviewerLogic;
import logic.subcontroller.LoginLogic;
import logic.subcontroller.TableLogic;
import sdk.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by ADI on 24-10-2015.
 */
public class Controller {

    private Screen screen;

    private User currentUser;
    private Game replayGame;
    private Game newGame;
    private ArrayList<Game> games;
    private boolean isAuthenticated;
    private SnakeEngineDrawClass snakeEngineDrawClass = new SnakeEngineDrawClass();
    private ReplaySnakeHandlerClass replaySnakeHandler = new ReplaySnakeHandlerClass();
    private LoginLogic loginLogic;
    private TableLogic tableLogic;
    private DeleteGameLogic deleteGameLogic;
    private GameOverviewerLogic gameOverviewerLogic;

    public Controller(){

        isAuthenticated = false;
        screen = new Screen();
        loginLogic = new LoginLogic(screen);
        tableLogic = new TableLogic(screen);
        deleteGameLogic = new DeleteGameLogic(screen);
        gameOverviewerLogic = new GameOverviewerLogic(screen);
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
        screen.getMainMenuPanel().getGameOverviewerPanelPanel().addActionListeners(new GameOverviewerHandlerClass());

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
                    tableLogic.setGamesTableModel(currentUser);
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
                ArrayList<Score> scores = new Gson().fromJson(Api.getHighScores(), new TypeToken<ArrayList<Score>>(){}.getType());
                System.out.println(scores.get(0).getScore() + " " + scores.get(0).getGame().getWinner());

                ArrayList<Score> scores1 = new Gson().fromJson(Api.getScoresByUserId(2), new TypeToken<ArrayList<Score>>(){}.getType());
                System.out.println(scores1.size());
            }
            else if (e.getActionCommand().equals(Config.getBtnDeleteGameText())) {
                screen.getMainMenuPanel().show(Config.getDeleteGameScreen());
                tableLogic.setGamesToDeleteTableModel(currentUser);

            }
            else if (e.getActionCommand().equals(Config.getBtnLogoutText())) {
                //TODO: for some reason when pressing log out after a replay is done running, part of board disappears. Also if in play screen, game cannot regain focus
                if (DialogMessage.showConfirmMessage(screen, Config.getLogoutMessage(), Config.getLogoutTitle())) {
                    screen.show(Config.getLoginScreen());
                    currentUser = new User();
                }
            }
        }
    }

    private class CreateUserHandlerClass implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            if (e.getActionCommand().equals(Config.getBtnCreateUserText())) {

                User createNewUser = new User();

                createNewUser.setFirstName(screen.getCreateUserPanel().getFirstNameField());
                createNewUser.setLastName(screen.getCreateUserPanel().getLastNameField());
                createNewUser.setEmail(screen.getCreateUserPanel().getEmailField());
                createNewUser.setUsername(screen.getCreateUserPanel().getUsernameField());
                createNewUser.setPassword(screen.getCreateUserPanel().getPasswordField());

                String jsonData = Api.createUser(createNewUser);
                String message = MessageParser.parseMessage(jsonData);

                DialogMessage.showMessage(screen, message);

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

            String message;

            //if game has not ended yet, move snake according to the direction and repaint
            if (!screen.getMainMenuPanel().getSnakeGameEngine().isGameEnded()) {
                screen.getMainMenuPanel().getSnakeGameEngine().move(screen.getMainMenuPanel().getSnakeGameEngine().getDirection());
                screen.getMainMenuPanel().getSnakeGameEngine().repaint();

            }
            else {

                if(newGame.getHost().getControls() == null) {
                    newGame.getHost().setControls(screen.getMainMenuPanel().getSnakeGameEngine().getSbToString());
                    //Attempt to create the game and show response from server
                    message = MessageParser.parseMessage(Api.createGame(newGame));
                    DialogMessage.showMessage(screen, message);
                }
                else {

                    newGame.getOpponent().setControls(screen.getMainMenuPanel().getSnakeGameEngine().getSbToString());

                    String gameJson = new Gson().toJson(newGame);
                    message = Api.joinGame(gameJson);
                    Api.startGame(gameJson);
                    DialogMessage.showMessage(screen, MessageParser.parseMessage(message));
                }

                //stops animation
                screen.getMainMenuPanel().getSnakeGameEngine().stopTimer();
                screen.getMainMenuPanel().getCreateNewGamePanel().resetFields();
                screen.getMainMenuPanel().setSidePanelState(true);
                screen.getMainMenuPanel().show(Config.getGameChooserScreen());
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
                        screen.getMainMenuPanel().addPlaySnake(snakeEngineDrawClass, newGame);

                        //TODO: put this somewhere else and call method here. Used it twice now
                        screen.getMainMenuPanel().getSnakeGameEngine().getActionMap().put(Config.getUp(),
                                new SnakeEngineKeyBindingHandlerClass(Character.toString(Config.getUp())));
                        screen.getMainMenuPanel().getSnakeGameEngine().getActionMap().put(Config.getDown(),
                                new SnakeEngineKeyBindingHandlerClass(Character.toString(Config.getDown())));
                        screen.getMainMenuPanel().getSnakeGameEngine().getActionMap().put(Config.getLeft(),
                                new SnakeEngineKeyBindingHandlerClass(Character.toString(Config.getLeft())));
                        screen.getMainMenuPanel().getSnakeGameEngine().getActionMap().put(Config.getRight(),
                                new SnakeEngineKeyBindingHandlerClass(Character.toString(Config.getRight())));

                        screen.getMainMenuPanel().setSidePanelState(false);
                    } else {
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
            //TODO: what to do? :D Hver gang den kører laver den en ny instans af replaySnake, men der sker ingen nye actions.. NEEDS WORK DONE
            //populates snake by taking the string controls and checking whether up, down, left or right (w,s,a,d).
            if (counter < replayGame.getHost().getControls().length()) {
                screen.getMainMenuPanel().getReplaySnake().move(replayGame.getHost(), replayGame.getHost().getControls().charAt(counter));
            }
            //checking theres an opponent and populates the snake with points
            if (replayGame.getOpponent().getControls() != null) {
                if (counter < replayGame.getOpponent().getControls().length()){
                    screen.getMainMenuPanel().getReplaySnake().move(replayGame.getOpponent(), replayGame.getOpponent().getControls().charAt(counter));
                }
            }
            //repaints as long as there are usercontrols and opponentcontrols
            if(counter > replayGame.getHost().getControls().length() &&
                    ((replayGame.getOpponent().getControls() != null
                            && counter > replayGame.getOpponent().getControls().length()) ||
                            replayGame.getOpponent().getControls() == null)){

                screen.getMainMenuPanel().getReplaySnake().setGameHasEnded(true);
                //screen.getMainMenuPanel().getReplaySnake().stopTimer();
                counter = Config.getCount();
                //DialogMessage.showMessage(screen, replayGame.getWinner().getUsername() +" is the winner. Congratulations");
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

                try {
                    newGame = screen.getMainMenuPanel().getGameChooserPanel().getGame();
                    newGame.getOpponent().setId(currentUser.getId());

                    screen.getMainMenuPanel().addPlaySnake(
                            snakeEngineDrawClass,
                            newGame);

                    //TODO: put this somewhere else and call method here. Used it twice now
                    screen.getMainMenuPanel().getSnakeGameEngine().getActionMap().put(Config.getUp(),
                            new SnakeEngineKeyBindingHandlerClass(Character.toString(Config.getUp())));
                    screen.getMainMenuPanel().getSnakeGameEngine().getActionMap().put(Config.getDown(),
                            new SnakeEngineKeyBindingHandlerClass(Character.toString(Config.getDown())));
                    screen.getMainMenuPanel().getSnakeGameEngine().getActionMap().put(Config.getLeft(),
                            new SnakeEngineKeyBindingHandlerClass(Character.toString(Config.getLeft())));
                    screen.getMainMenuPanel().getSnakeGameEngine().getActionMap().put(Config.getRight(),
                            new SnakeEngineKeyBindingHandlerClass(Character.toString(Config.getRight())));

                } catch (IndexOutOfBoundsException e1) {

                    DialogMessage.showMessage(screen, Config.getMissingGameSelectionText());
                }
            }
            else if (e.getActionCommand().equals(Config.getBtnCreateNewGameText())){

                screen.getMainMenuPanel().show(Config.getCreateNewGameScreen());
            }
            else if (e.getActionCommand().equals(Config.getBtnRefreshText())){


                ArrayList<User> users = new Gson().fromJson(Api.getUsers(), new TypeToken<ArrayList<User>>() {
                }.getType());

                if (screen.getMainMenuPanel().getGameChooserPanel().getTypeOfGameChoice().equals(Config.getTypesOfGames()[Config.getIndexOne()]))
                    games = new Gson().fromJson(Api.getGamesInvitedByID(currentUser.getId()), new TypeToken<ArrayList<Game>>() {
                    }.getType());
                else
                    //TODO: you shouldn't see your own games!
                    games = new Gson().fromJson(Api.getOpenGames(currentUser.getId()), new TypeToken<ArrayList<Game>>() {
                    }.getType());

                for (int i = Config.getCount(); i < users.size(); i++) {

                    for (int j = Config.getCount(); j < games.size(); j++) {
                        if (users.get(i).getId() == games.get(j).getHost().getId()) {

                            games.get(j).getHost().setUsername(users.get(i).getUsername());
                        }
                    }

                }
                screen.getMainMenuPanel().getGameChooserPanel().setGameTableModel(games);
            }
        }
    }

    private class DeleteGameHandlerClass implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            if (DialogMessage.showConfirmMessage(screen, Config.getBtnDeleteText(), Config.getBtnDeleteGameText())) {

                try {
                    DialogMessage.showMessage(screen, deleteGameLogic.deleteGame());

                    tableLogic.setGamesToDeleteTableModel(currentUser);
                } catch (IndexOutOfBoundsException e2) {
                    DialogMessage.showMessage(screen, Config.getMissingGameSelectionText());
                }
            }
        }
    }

    private class GameOverviewerHandlerClass implements ActionListener {

        private ArrayList<Game> replayGames;
        @Override
        public void actionPerformed(ActionEvent e) {

            if (e.getActionCommand().equals(Config.getBtnReplayText())){

                try {

                    replayGame = gameOverviewerLogic.showReplay(currentUser, replaySnakeHandler);
                }
                catch (IndexOutOfBoundsException e2){

                    DialogMessage.showMessage(screen, Config.getMissingGameSelectionText());
                }

            }
            else if (e.getActionCommand().equals(Config.getBtnRefreshText())){

                replayGames = gameOverviewerLogic.refreshTable(currentUser);

            }
        }
    }
}
