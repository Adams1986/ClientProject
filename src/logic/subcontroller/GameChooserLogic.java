package logic.subcontroller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import gui.DialogMessage;
import gui.Screen;
import sdk.Api;
import sdk.Config;
import sdk.DataParser;
import sdk.dto.Game;
import sdk.dto.User;

import java.util.ArrayList;

/**
 * Created by ADI on 15-11-2015.
 */
public class GameChooserLogic {

    private Screen screen;

    public GameChooserLogic(Screen screen){

        this.screen = screen;
    }

    public Game joinGame(User currentUser) {

        Game newGame = null;

        try {

            newGame = screen.getMainMenuPanel().getGameChooserPanel().getGame();
            newGame.getOpponent().setId(currentUser.getId());
            screen.getMainMenuPanel().setSidePanelState(false);

        } catch (IndexOutOfBoundsException e1) {

            DialogMessage.showMessage(screen, Config.getMissingGameSelectionText());
        }

        return newGame;
    }
}
