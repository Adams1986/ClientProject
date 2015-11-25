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

        ArrayList<Game> games = new Gson().fromJson(Api.getGamesInvitedByID(currentUser.getId()), new TypeToken<ArrayList<Game>>(){}.getType());
        screen.getMainMenuPanel().getGameChooserPanel().setGameTableModel(games);
    }

    public void setUserTableModel(User currentUser){

        ArrayList<User> users = DataParser.getDecryptedUserList(Api.getUsers(currentUser.getId()));
        screen.getMainMenuPanel().getCreateNewGamePanel().setOpponentTableModel(users);
    }

    public void setGamesToDeleteTableModel(User currentUser){

        ArrayList<Game> games = DataParser
                .getDecryptedGamesList(Api.getGamesByStatusAndUserId(Config.getServerPathOpenAndPendingGamesById(), currentUser.getId()));

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

        ArrayList<Game> games;

        if (screen.getMainMenuPanel().getGameChooserPanel().getTypeOfGameChoice().equals(Config.getTypesOfGames()[Config.getIndexOne()]))
            games = DataParser.getDecryptedGamesList(Api.getGamesInvitedByID(currentUser.getId()));
        else
            //TODO: you shouldn't see your own games! Kinda fixed
            games = DataParser.getDecryptedGamesList(Api.getGamesByStatusAndUserId(Config.getServerPathOpenGamesByOtherUsers(), currentUser.getId()));

        screen.getMainMenuPanel().getGameChooserPanel().setGameTableModel(games);
    }

}
