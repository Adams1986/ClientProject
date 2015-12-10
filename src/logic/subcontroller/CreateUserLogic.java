package logic.subcontroller;

import gui.DialogMessage;
import gui.Screen;
import sdk.Api;
import sdk.Config;
import sdk.dto.User;

/**
 * Contains the create method which creates a new User object from UI input and tries to create the user with the Api
 * method createUser
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

            //setting object variables from UI
            createNewUser.setFirstName(screen.getCreateUserPanel().getFirstNameField());
            createNewUser.setLastName(screen.getCreateUserPanel().getLastNameField());
            createNewUser.setEmail(screen.getCreateUserPanel().getEmailField());
            createNewUser.setUsername(screen.getCreateUserPanel().getUsernameField());
            createNewUser.setPassword(screen.getCreateUserPanel().getPasswordField());

            //Sending new user object to server and saving the server message
            message = Api.createUser(createNewUser);
        }
        else
            message = Config.getCreateUserEmptyFieldsText();

        //displaying message from server
        DialogMessage.showMessage(screen, message);

        return message;
    }
}
