package logic.subcontroller;

import com.google.gson.Gson;
import gui.Screen;
import sdk.Api;
import sdk.Config;
import sdk.MessageParser;
import sdk.User;


public class LoginLogic {

    Screen screen;

    public LoginLogic (Screen screen){

        this.screen = screen;
    }

    public String authenticated(User currentUser) {

        currentUser.setUsername(screen.getLoginPanel().getUsernameInput());
        currentUser.setPassword(screen.getLoginPanel().getPasswordInput());

        String message = Api.authenticateLogin(new Gson().toJson(currentUser));

        return MessageParser.parseMessage(message, currentUser);
    }
}
