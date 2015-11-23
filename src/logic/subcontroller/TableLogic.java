package logic.subcontroller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import gui.Screen;
import sdk.*;
import sdk.dto.Game;
import sdk.dto.Score;
import sdk.dto.User;

import java.awt.event.ActionListener;
import java.util.ArrayList;


public class TableLogic {

    private Screen screen;

    public TableLogic(Screen screen){

        this.screen = screen;
    }

    public void setGamesTableModel(User currentUser){

        ArrayList<User> users = DataParser.getDecryptedUserList(Api.getUsers(currentUser.getId()));
        ArrayList<Game> games = new Gson().fromJson(Api.getGamesInvitedByID(currentUser.getId()), new TypeToken<ArrayList<Game>>(){}.getType());

        addUsernameToList(users, games);

        screen.getMainMenuPanel().getGameChooserPanel().setGameTableModel(games);
        screen.getMainMenuPanel().getCreateNewGamePanel().setOpponentTableModel(users);
    }

    public void setGamesToDeleteTableModel(User currentUser){

        //TODO // FIXME: 22/11/15
        ArrayList<User> users = DataParser.getDecryptedUserList(Api.getUsers(-1));
        ArrayList<Game> games = DataParser
                .getDecryptedGamesList(Api.getGamesByStatusAndUserId(Config.getServerPathOpenAndPendingGamesById(), currentUser.getId()));

        addUsernameToList(users, games);

        screen.getMainMenuPanel().getDeleteGamePanel().setDeleteGameTableModel(games);
    }

    //TODO: make instansvariabel for highScores, so they use the same data and moving panel is updated same time as
    public void setHighScoreTableModel() {

        ArrayList<Score> highScores = new Gson().fromJson(Api.getHighScores(), new TypeToken<ArrayList<Score>>(){}.getType());
        screen.getMainMenuPanel().getHighScoresPanel().setHighScoreTableModel(highScores);
        screen.getMainMenuPanel().getHighScoresMovingPanel().setHighScores(highScores);
    }

    public void setHighScoresMovingPanel(ActionListener l){

        ArrayList<Score> highScores = new Gson().fromJson(Api.getHighScores(), new TypeToken<ArrayList<Score>>(){}.getType());
        screen.getMainMenuPanel().getNewInstanceOfHighScoresMovingPanel(highScores, l);
    }

    public void setGameChooserTableModel(User currentUser) {

        //TODO //FIX ME please
        ArrayList<User> users = DataParser.getDecryptedUserList(Api.getUsers(-1));
        ArrayList<Game> games = null;

        if (screen.getMainMenuPanel().getGameChooserPanel().getTypeOfGameChoice().equals(Config.getTypesOfGames()[Config.getIndexOne()]))
            games = DataParser.getDecryptedGamesList(Api.getGamesInvitedByID(currentUser.getId()));
        else
            //TODO: you shouldn't see your own games! Kinda fixed
            games = DataParser.getDecryptedGamesList(Api.getGamesByStatusAndUserId(Config.getServerPathOpenGamesByOtherUsers(), currentUser.getId()));

        addUsernameToList(users, games);

        screen.getMainMenuPanel().getGameChooserPanel().setGameTableModel(games);
    }

    private void addUsernameToList(ArrayList<User>users, ArrayList<Game>games){

        //TODO: fix on server side
        for (int i = Config.getCount(); i < users.size(); i++) {

            for (int j = Config.getCount(); j < games.size(); j++) {
                if (users.get(i).getId() == games.get(j).getHost().getId()) {

                    games.get(j).getHost().setUsername(users.get(i).getUsername());
                }
                else if (users.get(i).getId() == games.get(j).getOpponent().getId()) {

                    games.get(j).getOpponent().setUsername(users.get(i).getUsername());
                }
            }

        }
    }
}
