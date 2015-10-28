package logic;

import gui.DialogMessage;
import gui.PlaySnake;
import gui.Screen;
import logic.subcontroller.LoginLogic;
import sdk.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

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

                //TODO: lad være med at bruge switch og i stedet bruge if-else for brug af Config filen
                case "Login":
                    currentUser = new Gamer();
                    currentUser.setUserName(screen.getLoginPanel().getUsernameInput());
                    currentUser.setPassword(screen.getLoginPanel().getPasswordInput());

                    String message = Api.authenticateLogin(currentUser);
                    isAuthenticated = message.equals(Config.getLoginAuthentication());

                    if (isAuthenticated) {

                        screen.show(Config.getMainMenuScreen());
                        screen.getMainMenuPanel().setWelcomeMessage(message);
                    } else {
                        screen.getLoginPanel().clearFields();
                        screen.getLoginPanel().setFailedLoginAttempt(message);
                    }
                    break;

                case "Don't have a user? Create a new one":
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

            switch (e.getActionCommand()){

                //TODO: bigish workaround to not hard code these
                case "Play a game":
                    //takes an actionlistener as parameter for a dynamic injection of listener
                    screen.getMainMenuPanel().playSnake(this, Api.getUsers());

                    //TODO: put this somewhere else and call method here
                    screen.getMainMenuPanel().getPlaySnake().getActionMap().put(Config.getUP(),
                            new PlaySnakeKeyBindingHandlerClass(Character.toString(Config.getUP())));
                    screen.getMainMenuPanel().getPlaySnake().getActionMap().put(Config.getDOWN(),
                            new PlaySnakeKeyBindingHandlerClass(Character.toString(Config.getDOWN())));
                    screen.getMainMenuPanel().getPlaySnake().getActionMap().put(Config.getLEFT(),
                            new PlaySnakeKeyBindingHandlerClass(Character.toString(Config.getLEFT())));
                    screen.getMainMenuPanel().getPlaySnake().getActionMap().put(Config.getRIGHT(),
                            new PlaySnakeKeyBindingHandlerClass(Character.toString(Config.getRIGHT())));


                    if (screen.getMainMenuPanel().getMoves() != null){
                        System.out.println(screen.getMainMenuPanel().getMoves());
                        currentUser.setControls(screen.getMainMenuPanel().getMoves());
                        screen.getMainMenuPanel().setWelcomeMessage(screen.getMainMenuPanel().getMoves());
                    }
                    break;

                case "Watch a replay":
                    screen.getMainMenuPanel().replayGame(currentUser);
                    break;

                case "High scores":

                    break;

                case "Delete a game":
                    Api.deleteUser(3);
                    break;

                //TODO: for some reason when pressing log out after a replay is done running, part of board disappears
                case "Log out":
                    if(DialogMessage.showConfirmMessage(screen, Config.getLogoutMessage(), Config.getLogoutTitle()))
                        screen.show(Config.getLoginScreen());
                    break;

                case "Send challenge":
                    if(screen.getMainMenuPanel().getPlaySnake().isGameEnded()) {
                        System.out.println(screen.getMainMenuPanel().getPlaySnake().getMoves());
                        Game game = new Game();
                        game.setName("New game");

                        Gamer host = new Gamer();
                        host.setId(6);
                        host.setControls(screen.getMainMenuPanel().getPlaySnake().getMoves());
                        Gamer opponent = new Gamer();
                        opponent.setId(3);
                        System.out.println(Api.createGame(game, host, opponent));
                    }
                    else {
                        DialogMessage.showMessage(screen, "Play a game first");

                        screen.getMainMenuPanel().focusPlaySnake(new PlaySnake());
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
                    createNewUser.setUserName(screen.getCreateUserPanel().getUsernameField());
                    createNewUser.setPassword(screen.getCreateUserPanel().getPasswordField());
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
}
