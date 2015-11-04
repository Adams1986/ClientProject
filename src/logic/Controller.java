package logic;

import gui.DialogMessage;
import gui.PlaySnake;
import gui.Screen;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import sdk.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by ADI on 24-10-2015.
 */
public class Controller {

    private Screen screen;

    private Gamer currentUser;
    private boolean isAuthenticated;

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

        //screen.getMainMenuPanel().getPlaySnake().keyBindings(new PlaySnakeKeyBindingHandlerClass());
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
                    currentUser = new Gamer();
                    currentUser.setUsername(screen.getLoginPanel().getUsernameInput());
                    currentUser.setPassword(screen.getLoginPanel().getPasswordInput());

                    String message = Api.authenticateLogin(currentUser);
                    isAuthenticated = message.equals(Config.getLoginAuthentication());

                    if (isAuthenticated) {

                        screen.show(Config.getMainMenuScreen());
//                        screen.getMainMenuPanel().replayGame(currentUser);
                        screen.getMainMenuPanel().addPlaySnake(new MainMenuHandlerClass());
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
                    //takes an actionlistener as parameter for a dynamic injection of listener
                    screen.getMainMenuPanel().addPlaySnake(new PlaySnakeHandlerClass());
                    screen.getMainMenuPanel().getPlaySnake().setOpponentTableModel(Api.getUsers());

                    //TODO: put this somewhere else and call method here
                    screen.getMainMenuPanel().getPlaySnake().getActionMap().put(Config.getUP(),
                            new PlaySnakeKeyBindingHandlerClass(Character.toString(Config.getUP())));
                    screen.getMainMenuPanel().getPlaySnake().getActionMap().put(Config.getDOWN(),
                            new PlaySnakeKeyBindingHandlerClass(Character.toString(Config.getDOWN())));
                    screen.getMainMenuPanel().getPlaySnake().getActionMap().put(Config.getLEFT(),
                            new PlaySnakeKeyBindingHandlerClass(Character.toString(Config.getLEFT())));
                    screen.getMainMenuPanel().getPlaySnake().getActionMap().put(Config.getRIGHT(),
                            new PlaySnakeKeyBindingHandlerClass(Character.toString(Config.getRIGHT())));
                    break;

                //f�r et map retur fra API, kan m�ske bruges til at tegne spillet?
                case "Watch a replay":
                    try {
                        //TODO: successful test, change to work obv
                        Object obj = new JSONParser().parse(Api.startGame(26));
                        JSONObject jsonObject = (JSONObject) obj;
                        currentUser.setControls((String) jsonObject.get("hostControls"));
                        System.out.println(currentUser.getControls());
                    } catch (ParseException e1) {
                        e1.printStackTrace();
                    }

                    screen.getMainMenuPanel().replayGame(currentUser);
                    break;

                case "High scores":

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
                    screen.getMainMenuPanel().getPlaySnake().repaint();
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

    private class PlaySnakeKeyBindingHandlerClass extends AbstractAction {

        public PlaySnakeKeyBindingHandlerClass(String text){
            super(text);
            putValue(ACTION_COMMAND_KEY, text);
        }

        @Override
        public void actionPerformed(ActionEvent e) {

            String cmd = e.getActionCommand();

            if(cmd.equals(Character.toString(Config.getUP()))){
                if(screen.getMainMenuPanel().getPlaySnake().getDirection()!= Config.getDOWN())
                    screen.getMainMenuPanel().getPlaySnake().setDirection(Config.getUP());
            }
            else if (cmd.equals(Character.toString(Config.getDOWN()))){
                if(screen.getMainMenuPanel().getPlaySnake().getDirection()!= Config.getUP())
                    screen.getMainMenuPanel().getPlaySnake().setDirection(Config.getDOWN());
            }
            else if (cmd.equals(Character.toString(Config.getLEFT()))){
                if(screen.getMainMenuPanel().getPlaySnake().getDirection()!= Config.getRIGHT())
                    screen.getMainMenuPanel().getPlaySnake().setDirection(Config.getLEFT());
            }
            else if (cmd.equals(Character.toString(Config.getRIGHT()))){
                if(screen.getMainMenuPanel().getPlaySnake().getDirection()!= Config.getLEFT())
                    screen.getMainMenuPanel().getPlaySnake().setDirection(Config.getRIGHT());
            }
        }
    }

    private class PlaySnakeHandlerClass implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            //TODO: redundant right now
            /*if(!screen.getMainMenuPanel().getPlaySnake().isGameEnded()) {
                screen.getMainMenuPanel().getPlaySnake().move(screen.getMainMenuPanel().getPlaySnake().getDirection());
                screen.getMainMenuPanel().getPlaySnake().componentsSetEnabled(false);
                screen.getMainMenuPanel().getPlaySnake().setGameNameField("Type game name here..");
            }
            else{

                screen.getMainMenuPanel().getPlaySnake().setMoves(screen.getMainMenuPanel().getPlaySnake().getSbToString());
                screen.getMainMenuPanel().getPlaySnake().componentsSetEnabled(true);
                screen.getMainMenuPanel().getPlaySnake().setGameNameField("");
            }
            screen.getMainMenuPanel().getPlaySnake().repaint();*/

            switch (e.getActionCommand()) {
                case "Send challenge":
                    if (screen.getMainMenuPanel().getPlaySnake().isGameEnded()) {

                        Game game = new Game();
                        game.setName(screen.getMainMenuPanel().getPlaySnake().getGameNameText());

                        Gamer host = new Gamer();
                        host.setControls(screen.getMainMenuPanel().getPlaySnake().getMoves());
                        Gamer opponent = new Gamer();

                        host.setId(currentUser.getId());
                        opponent.setId(screen.getMainMenuPanel().getPlaySnake().getOpponent().getId());
                        game.setHost(host);
                        game.setOpponent(opponent);
                        game.setMapSize(Config.getBoardHeight());
                        DialogMessage.showMessage(screen, Api.createGame(game));

                    } else {
                        DialogMessage.showMessage(screen, "Play a game first");

                        screen.getMainMenuPanel().getPlaySnake().focusThis();
                        //screen.getMainMenuPanel().focusPlaySnake(new PlaySnake());
                    }
                    break;
            }
        }
    }
}
