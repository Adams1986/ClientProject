package logic.subcontroller;

import gui.Screen;
import sdk.Api;
import sdk.DataParser;
import sdk.dto.User;


public class LoginLogic {

    Screen screen;

    public LoginLogic (Screen screen){

        this.screen = screen;
    }

    public String authenticated(User currentUser) {

        currentUser.setUsername(screen.getLoginPanel().getUsernameInput());
        currentUser.setPassword(screen.getLoginPanel().getPasswordInput());

        return Api.authenticateLogin(currentUser);
    }
}
