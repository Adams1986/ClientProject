package logic;

import gui.DialogMessage;
import gui.Screen;
import sdk.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by ADI on 24-10-2015.
 */
public class Controller {

    private Screen screen;

    private User currentUser;
    private Game replayGame;
    private Game newGame;
    private boolean isAuthenticated;
    private SnakeEngineDrawClass snakeEngineDrawClass = new SnakeEngineDrawClass();
    private ReplaySnakeHandlerClass replaySnakeHandler = new ReplaySnakeHandlerClass();

    public Controller(){

        isAuthenticated = false;
        screen = new Screen();
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

    }

    /**
     * Inner class action listener for login panel
     */
    private class LoginHandlerClass implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            switch (e.getActionCommand()) {

                //TODO: lad v�re med at bruge switch og i stedet bruge if-else for brug af Config filen
                case "Login":
                    currentUser = new User();
                    currentUser.setUsername(screen.getLoginPanel().getUsernameInput());
                    currentUser.setPassword(screen.getLoginPanel().getPasswordInput());

                    String message = Api.authenticateLogin(currentUser);
                    isAuthenticated = message.equals(Config.getLoginAuthentication());

                    if (isAuthenticated) {

                        screen.show(Config.getMainMenuScreen());
                        screen.getMainMenuPanel().show(Config.getGameChooserScreen());

                        ArrayList<User>users = Api.getUsers();
                        ArrayList<Game>games = Api.getGamesInvitedByID(currentUser.getId());

                        for (int i = 0; i < users.size(); i++){

                            for (int j = 0; j < games.size(); j++){
                                if (users.get(i).getId() == games.get(j).getHost().getId()){

                                    games.get(j).getHost().setUsername(users.get(i).getUsername());
                                }
                            }

                        }
                        String[] columns = new String []{"Challenger", "Opponent", "Game name", "Game status", "Created", "Winner", "Map size"};
                        //String[] columns = Config.getColumnNamesGameTable();
                        screen.getMainMenuPanel().getGameChooserPanel().setGameTableModel(games, columns);
                        //TODO: move?
                        screen.getMainMenuPanel().getCreateNewGamePanel().setOpponentTableModel(Api.getUsers());

                        //TODO: change ^this^ to some start menu or something, works like crap
                        screen.getMainMenuPanel().setWelcomeMessage(message);
                    } else {
                        screen.getLoginPanel().setFailedLoginAttempt(message);
                    }
                    screen.getLoginPanel().clearFields();
                    break;

                case "Don't have a user? Create a new one":
                    screen.show(Config.getCreateUserScreen());
                    break;
            }
        }
    }

    /**
     * Inner class actionlistener for main menu panel
     */
    private class MainMenuHandlerClass implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            switch (e.getActionCommand()){

                //TODO: bigish workaround to not hard code these
                case "Play a game":
                    screen.getMainMenuPanel().show(Config.getGameChooserScreen());
                    break;

                //f�r et map retur fra API, kan m�ske bruges til at tegne spillet?
                case "Watch a replay":
                    //TODO: clean up, some of set stuff can be added in gamer class.
                    replayGame = Api.getGame(51);

                    //TODO: maybe move to replaysnake class instead! modeling host and opponent to create graphics
                    replayGame.getHost().setSnakeColor(Color.BLUE);
                    replayGame.getHost().setSnake(new LinkedList<Point>());
                    replayGame.getHost().getSnake().add(new Point((replayGame.getMapSize()-2)/2, (replayGame.getMapSize()-2)/2));
                    replayGame.getOpponent().setSnakeColor(Color.RED);
                    replayGame.getOpponent().setSnake(new LinkedList<Point>());
                    replayGame.getOpponent().getSnake().add(new Point((replayGame.getMapSize()+2)/2, (replayGame.getMapSize()+2)/2));

                    //adding controls to gamer objects instead of game object.
//                    replayGame.getHost().setControls(replayGame.getHostControls());
//                    replayGame.getOpponent().setControls(replayGame.getOpponentControls());

                    screen.getMainMenuPanel().addReplaySnakeToPanel(replayGame, replaySnakeHandler);
                    break;

                case "High scores":
                    System.out.println(Api.getHighScores().get(0).getScore() + " " + Api.getHighScores().get(0).getGame().getWinner());
                    System.out.println(Api.getScoresByUserId(2).size());
                    break;

                case "Delete a game":
                    Api.deleteGame(23);
                    break;

                //TODO: for some reason when pressing log out after a replay is done running, part of board disappears. Also if in play screen, game cannot regain focus
                case "Log out":
                    if(DialogMessage.showConfirmMessage(screen, Config.getLogoutMessage(), Config.getLogoutTitle())) {
                        screen.show(Config.getLoginScreen());
                        currentUser = null;
                    }
                    break;
            }
        }
    }

    private class CreateUserHandlerClass implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            switch (e.getActionCommand()){

                case "Create user":
                    User createNewUser = new User();
                    createNewUser.setFirstName(screen.getCreateUserPanel().getFirstNameField());
                    createNewUser.setLastName(screen.getCreateUserPanel().getLastNameField());
                    createNewUser.setEmail(screen.getCreateUserPanel().getEmailField());
                    createNewUser.setUsername(screen.getCreateUserPanel().getUsernameField());
                    createNewUser.setPassword(screen.getCreateUserPanel().getPasswordField());

                    //TODO: default setting of this in some way
                    createNewUser.setStatus("active");
                    String message = Api.createUser(createNewUser);

                    DialogMessage.showMessage(screen, message);

                    if(!message.equals("User was created")){

                    }
                    else
                        screen.getCreateUserPanel().clearFields();
                    break;

                case "To Login":
                    screen.show(Config.getLoginScreen());
                    break;
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

            String cmd = e.getActionCommand();

            if(cmd.equals(Character.toString(Config.getUp()))){
                if(screen.getMainMenuPanel().getSnakeGameEngine().getDirection()!= Config.getDown())
                    screen.getMainMenuPanel().getSnakeGameEngine().setDirection(Config.getUp());
            }
            else if (cmd.equals(Character.toString(Config.getDown()))){
                if(screen.getMainMenuPanel().getSnakeGameEngine().getDirection()!= Config.getUp())
                    screen.getMainMenuPanel().getSnakeGameEngine().setDirection(Config.getDown());
            }
            else if (cmd.equals(Character.toString(Config.getLeft()))){
                if(screen.getMainMenuPanel().getSnakeGameEngine().getDirection()!= Config.getRight())
                    screen.getMainMenuPanel().getSnakeGameEngine().setDirection(Config.getLeft());
            }
            else if (cmd.equals(Character.toString(Config.getRight()))){
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

            //if game has not ended yet, move snake according to the direction and repaint
            if (!screen.getMainMenuPanel().getSnakeGameEngine().isGameEnded()) {
                screen.getMainMenuPanel().getSnakeGameEngine().move(screen.getMainMenuPanel().getSnakeGameEngine().getDirection());
                screen.getMainMenuPanel().getSnakeGameEngine().repaint();

            } else {

                if(newGame.getHost().getControls() == null) {
                    newGame.getHost().setControls(screen.getMainMenuPanel().getSnakeGameEngine().getSbToString());
                    //Attempt to create the game and show response from server
                    DialogMessage.showMessage(screen, Api.createGame(newGame).getName());//TODO:...
                }
                else {

                    newGame.getOpponent().setControls(screen.getMainMenuPanel().getSnakeGameEngine().getSbToString());

                    newGame.setStatus("finished");

                    DialogMessage.showMessage(screen, Api.joinGame(newGame));
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
     * Inner class, uses CreateNewGame to setup a new game
     */
    private class CreateNewGameHandlerClass implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            //enable table whenever the open game check box is not selected
            screen.getMainMenuPanel().getCreateNewGamePanel().setOpponentTableState(
                    !screen.getMainMenuPanel().getCreateNewGamePanel().getOpenGameChoice());

            switch (e.getActionCommand()) {

                case "Send challenge":

                    //TODO: move shit to subcontroller
                    try {
                        newGame = new Game();

                        if (!screen.getMainMenuPanel().getCreateNewGamePanel().getGameNameText().equals("")) {
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
                        }
                        else {
                            JOptionPane.showMessageDialog(screen, "Please give the game a name!");
                            screen.getMainMenuPanel().getCreateNewGamePanel().requestFocusGameNameField();
                        }

                    } catch (ArrayIndexOutOfBoundsException e2) {
                        e2.printStackTrace();
                        JOptionPane.showMessageDialog(screen, "You must select an opponent!");
                    }

                    break;
            }
        }
    }

    private class ReplaySnakeHandlerClass implements ActionListener {

        int counter = 0;

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

                screen.getMainMenuPanel().getReplaySnake().stopTimer();
                counter = 0;
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

            if (e.getActionCommand().equals("Join selected game")){


                try {
                    newGame = screen.getMainMenuPanel().getGameChooserPanel().getGame();
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

                    DialogMessage.showMessage(screen, "Please select a game to join");
                }
            }
            else if (e.getActionCommand().equals("Create a new game")){

                screen.getMainMenuPanel().show(Config.getCreateNewGameScreen());
            }
        }
    }
}
