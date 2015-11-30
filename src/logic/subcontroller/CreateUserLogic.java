package logic.subcontroller;

import gui.DialogMessage;
import gui.Screen;
import sdk.Api;
import sdk.Config;
import sdk.dto.User;

/**
 * Created by simonadams on 20/11/15.
 */
public class CreateUserLogic {

    private Screen screen;

    public CreateUserLogic(Screen screen) {

        this.screen = screen;
    }


    public String create() {

        User createNewUser = new User();

        String message;

        if (screen.getCreateUserPanel().checkForEmptyFields()) {

            createNewUser.setFirstName(screen.getCreateUserPanel().getFirstNameField());
            createNewUser.setLastName(screen.getCreateUserPanel().getLastNameField());
            createNewUser.setEmail(screen.getCreateUserPanel().getEmailField());
            createNewUser.setUsername(screen.getCreateUserPanel().getUsernameField());
            createNewUser.setPassword(screen.getCreateUserPanel().getPasswordField());

            message = Api.createUser(createNewUser);
        }
        else
            message = Config.getCreateUserEmptyFieldsText();

        DialogMessage.showMessage(screen, message);

        return message;
    }
}
