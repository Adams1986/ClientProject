package logic.subcontroller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import gui.Screen;
import sdk.*;
import sdk.dto.Game;
import sdk.dto.Score;
import sdk.dto.User;

import java.util.ArrayList;


public class TableLogic {

    private Screen screen;

    public TableLogic(Screen screen){

        this.screen = screen;
    }

    public void setGamesTableModel(User currentUser){

        ArrayList<User> users = DataParser.getDecryptedUserList(Api.getUsers(currentUser.getId()));
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

        ArrayList<User> users = DataParser.getDecryptedUserList(Api.getUsers(currentUser.getId()));
        ArrayList<Game> games = DataParser
                .getDecryptedGamesList(Api.getGamesByStatusAndUserId(Config.getServerPathPendingGamesById(), currentUser.getId()));

        //TODO: fix on server side
        for (int i = Config.getCount(); i < users.size(); i++) {

            for (int j = Config.getCount(); j < games.size(); j++) {
                if (users.get(i).getId() == games.get(j).getHost().getId()) {

                    games.get(j).getHost().setUsername(users.get(i).getUsername());
                }
            }

        }

        screen.getMainMenuPanel().getDeleteGamePanel().setDeleteGameTableModel(games);
    }

    public void setHighScoreTableModel() {

        screen.getMainMenuPanel().show(Config.getHighScoresScreen());
        ArrayList<Score> highScores = new Gson().fromJson(Api.getHighScores(), new TypeToken<ArrayList<Score>>(){}.getType());
        screen.getMainMenuPanel().getHighScoresPanel().setHighScoreTableModel(highScores);
    }
}
