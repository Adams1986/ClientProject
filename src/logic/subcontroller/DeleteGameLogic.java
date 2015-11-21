package logic.subcontroller;

import gui.Screen;
import sdk.Api;
import sdk.DataParser;

/**
 * Created by simonadams on 14/11/15.
 */
public class DeleteGameLogic {

    private Screen screen;

    public DeleteGameLogic(Screen screen){

        this.screen = screen;
    }

    public String deleteGame() {

        String message = Api.deleteGame(screen.getMainMenuPanel().getDeleteGamePanel().getGameToDelete().getGameId());
        return DataParser.parseMessage(message);
    }
}
