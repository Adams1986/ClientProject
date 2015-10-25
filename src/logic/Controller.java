package logic;

import gui.DialogMessage;
import gui.PlaySnake;
import gui.Screen;
import logic.subcontroller.LoginLogic;
import sdk.Api;
import sdk.Config;
import sdk.Gamer;
import sdk.User;

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
    }

    /**
     * Inner class action listener for login panel
     */
    private class LoginHandlerClass implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            currentUser = new Gamer();
            currentUser.setUserName(screen.getLoginPanel().getUsernameInput());
            currentUser.setPassword(screen.getLoginPanel().getPasswordInput());

            String message = Api.authenticateLogin(currentUser);
            isAuthenticated = message.equals(Config.getLoginAuthentication());

            if(isAuthenticated){

                screen.show(Config.getMainMenuScreen());
                screen.getMainMenuPanel().setWelcomeMessage(message);
            }
            else{
                screen.getLoginPanel().clearFields();
                screen.getLoginPanel().setFailedLoginAttempt(message);
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
                    screen.getMainMenuPanel().playSnake();

                    if (screen.getMainMenuPanel().getMoves() != null){
                        currentUser.setControls(screen.getMainMenuPanel().getMoves());
                        screen.getMainMenuPanel().setWelcomeMessage(screen.getMainMenuPanel().getMoves());
                    }
                    break;

                case "Watch a replay":
                    screen.getMainMenuPanel().replayGame();
                    break;

                case "High scores":
                    break;

                case "Delete a game":
                    Api.deleteUser(7);
                    break;

                //TODO: for some reason when pressing log out after a replay is done running, part of board disappears
                case "Log out":
                    if(DialogMessage.showConfirmMessage(screen, Config.getLogoutMessage(), Config.getLogoutTitle()))
                        screen.show(Config.getLoginScreen());
                    break;

                case "Send challenge":
                    System.out.println("in here");
                    break;


            }
        }
    }
}
