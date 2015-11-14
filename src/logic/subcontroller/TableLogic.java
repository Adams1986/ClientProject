package logic.subcontroller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import gui.Screen;
import sdk.Api;
import sdk.Config;
import sdk.Game;
import sdk.User;

import java.util.ArrayList;


public class TableLogic {

    private Screen screen;

    public TableLogic(Screen screen){

        this.screen = screen;
    }

    public void setGamesTableModel(User currentUser){

        ArrayList<User> users = new Gson().fromJson(Api.getUsers(), new TypeToken<ArrayList<User>>(){}.getType());
        ArrayList<Game> games = new Gson().fromJson(Api.getGamesInvitedByID(currentUser.getId()), new TypeToken<ArrayList<Game>>(){}.getType());

        for (int i = Config.getCount(); i < users.size(); i++) {

            for (int j = Config.getCount(); j < games.size(); j++) {
                if (users.get(i).getId() == games.get(j).getHost().getId()) {

                    games.get(j).getHost().setUsername(users.get(i).getUsername());
                }
            }

        }

        screen.getMainMenuPanel().getGameChooserPanel().setGameTableModel(games);
        screen.getMainMenuPanel().getCreateNewGamePanel().setOpponentTableModel(users);
    }

    public void setGamesToDeleteTableModel(User currentUser){

        ArrayList<User> users = new Gson().fromJson(Api.getUsers(), new TypeToken<ArrayList<User>>(){}.getType());
        ArrayList<Game> games = new Gson().fromJson(Api.getGamesByStatusAndUserId("pending", currentUser.getId()), new TypeToken<ArrayList<Game>>(){}.getType());

        for (int i = Config.getCount(); i < users.size(); i++) {

            for (int j = Config.getCount(); j < games.size(); j++) {
                if (users.get(i).getId() == games.get(j).getHost().getId()) {

                    games.get(j).getHost().setUsername(users.get(i).getUsername());
                }
            }

        }

        screen.getMainMenuPanel().getDeleteGamePanel().setDeleteGameTableModel(games);
    }
}
