package logic.subcontroller;

import gui.Screen;
import sdk.Api;

/**
 *
 */
public class DeleteGameLogic {

    private Screen screen;

    public DeleteGameLogic(Screen screen){

        this.screen = screen;
    }

    public String deleteGame() {

        if (screen.getMainMenuPanel().getDeleteGamePanel().getGameToDelete() != null) {
            int gameId = screen.getMainMenuPanel().getDeleteGamePanel().getGameToDelete().getGameId();

            return Api.deleteGame(gameId);
        }
        return "";
    }
}
