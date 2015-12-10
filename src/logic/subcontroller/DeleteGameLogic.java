package logic.subcontroller;

import gui.Screen;
import sdk.Api;

/**
 * Contains a method that returns a game that can be deleted if user chooses to
 */
public class DeleteGameLogic {

    private Screen screen;

    public DeleteGameLogic(Screen screen){

        this.screen = screen;
    }

    /**
     * Method returns a game if one is selected from the delete game panel's JTable
     * @return
     */
    public String deleteGame() {

        //checking to see if a game was selected before starting server communication
        if (screen.getMainMenuPanel().getDeleteGamePanel().getGameToDelete() != null) {

            //getting game id from the selected game and using it for the Api.deleteGame method
            int gameId = screen.getMainMenuPanel().getDeleteGamePanel().getGameToDelete().getGameId();

            return Api.deleteGame(gameId);
        }
        //if no game was selected send an empty string
        return "";
    }
}
