package logic.subcontroller;

import com.google.gson.Gson;
import com.sun.jersey.api.client.ClientResponse;
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

        String message = Api.authenticateLogin(new Gson().toJson(currentUser));

        return DataParser.parseHashMapMessage(message, currentUser);
        //return DataParser.parseMessage(message, currentUser);
    }
}
