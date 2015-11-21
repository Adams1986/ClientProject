package logic.subcontroller;

import com.google.gson.Gson;
import gui.DialogMessage;
import gui.Screen;
import sdk.Api;
import sdk.DataParser;
import sdk.dto.User;

/**
 * Created by simonadams on 20/11/15.
 */
public class CreateUserLogic {

    private Screen screen;

    public CreateUserLogic(Screen screen) {

        this.screen = screen;
    }


    public String create(DialogMessage dialogMessage) {

        User createNewUser = new User();

        createNewUser.setFirstName(screen.getCreateUserPanel().getFirstNameField());
        createNewUser.setLastName(screen.getCreateUserPanel().getLastNameField());
        createNewUser.setEmail(screen.getCreateUserPanel().getEmailField());
        createNewUser.setUsername(screen.getCreateUserPanel().getUsernameField());
        createNewUser.setPassword(screen.getCreateUserPanel().getPasswordField());

        String jsonData = Api.createUser(new Gson().toJson(createNewUser));
        String message = DataParser.parseMessage(jsonData);

        dialogMessage.showMessage(message);

        return message;
    }
}
