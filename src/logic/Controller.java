package logic;

import gui.Screen;
import logic.subcontroller.LoginLogic;
import sdk.Api;
import sdk.Config;
import sdk.User;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by ADI on 24-10-2015.
 */
public class Controller {

    private Screen screen;

    private User currentUser;
    private boolean isAuthenticated;

    public Controller(){

        isAuthenticated = false;
        screen = new Screen();
//        screen.show(Config.getLoginScreen());
    }

    public void run(){

        screen.getLoginPanel().addActionListeners(new LoginHandlerClass());
        screen.getMainMenuPanel().addActionListeners(new MainMenuHandlerClass());
    }

    private class LoginHandlerClass implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            currentUser = new User();
            currentUser.setUserName(screen.getLoginPanel().getUsernameInput());
            currentUser.setPassword(screen.getLoginPanel().getPasswordInput());

            String message = Api.authenticateLogin(currentUser);
            isAuthenticated = message.equals(Config.getLoginAuthentication());

            if(isAuthenticated){

                //screen.show(Screen.MAIN_MENU);
                screen.show(Config.getMainMenuScreen());
                screen.getMainMenuPanel().setWelcomeMessage(message);
            }
            else{
                screen.getLoginPanel().clearFields();
                screen.getLoginPanel().setFailedLoginAttempt(message);
            }
        }
    }

    private class MainMenuHandlerClass implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            switch (e.getActionCommand()){

                case "Watch a replay":
                    screen.getMainMenuPanel().show(Config.getReplaySnakeScreen());
                    break;
            }
        }
    }
}
