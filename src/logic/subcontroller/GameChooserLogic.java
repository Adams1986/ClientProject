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

    public void refreshTable(User currentUser) {

        ArrayList<User> users = DataParser.getDecryptedUserList(Api.getUsers(currentUser.getId()));
        ArrayList<Game> games = null;

        if (screen.getMainMenuPanel().getGameChooserPanel().getTypeOfGameChoice().equals(Config.getTypesOfGames()[Config.getIndexOne()]))
            games = DataParser.getDecryptedGamesList(Api.getGamesInvitedByID(currentUser.getId()));
        else
            //TODO: you shouldn't see your own games! Kinda fixed
            games = DataParser.getDecryptedGamesList(Api.getOpenGames(currentUser.getId()));

        //TODO:Fix this from the Server side
        for (int i = Config.getCount(); i < users.size(); i++) {

            for (int j = Config.getCount(); j < games.size(); j++) {
                if (users.get(i).getId() == games.get(j).getHost().getId()) {

                    games.get(j).getHost().setUsername(users.get(i).getUsername());
                }
            }

        }
        screen.getMainMenuPanel().getGameChooserPanel().setGameTableModel(games);
    }

    public Game joinGame(User currentUser, DialogMessage dialogMessage) {

        Game newGame = null;

        try {

            newGame = screen.getMainMenuPanel().getGameChooserPanel().getGame();
            newGame.getOpponent().setId(currentUser.getId());
            screen.getMainMenuPanel().setSidePanelState(false);

        } catch (IndexOutOfBoundsException e1) {

            dialogMessage.showMessage(Config.getMissingGameSelectionText());
        }

        return newGame;
    }
}
