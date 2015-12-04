package logic.subcontroller;

import gui.Screen;
import sdk.*;
import sdk.dto.Game;
import sdk.dto.Score;
import sdk.dto.User;

import java.awt.event.ActionListener;
import java.util.ArrayList;


/**
 * Controller class that handles setting all the tables in the application. Combines array lists from the API calls
 * with the tables/table models from the GUI.
 * Only the
 */
public class TableLogic {

    private Screen screen;

    public TableLogic(Screen screen){

        this.screen = screen;
    }

    public void setGameOverviewerTableModel(User currentUser){

        ArrayList<Game> games = Api.getGamesByStatusAndUserId(
                screen.getMainMenuPanel().getGameOverviewerPanel().getTypeOfGameChoice(), currentUser.getId() );


        screen.getMainMenuPanel().getGameOverviewerPanel().setGameTableModel(games);
    }

    /**
     * Users are an instansvariable as
     * @param currentUser
     */
    public void setUserTableModel(User currentUser){

        ArrayList<User> users = Api.getUsers(currentUser.getId());

        screen.getMainMenuPanel().getCreateNewGamePanel().setOpponentTableModel(users);
    }

    public void setGamesToDeleteTableModel(User currentUser){

        ArrayList<Game> games = Api.getGamesByStatusAndUserId(Config.getServerPathOpenAndPendingGamesById(), currentUser.getId());

        screen.getMainMenuPanel().getDeleteGamePanel().setDeleteGameTableModel(games);
    }


    public void setHighScoreTableModel() {

        ArrayList<Score> highScores = Api.getHighScores();

        screen.getMainMenuPanel().getHighScoresPanel().setHighScoreTableModel(highScores);
        screen.getMainMenuPanel().getHighScoresMovingPanel().setHighScores(highScores);
    }

    public void setHighScoresMovingPanel(ActionListener l){

        ArrayList<Score> highScores = Api.getHighScores();

        screen.getMainMenuPanel().getNewInstanceOfHighScoresMovingPanel(highScores, l);
    }

    public void setGameChooserTableModel(User currentUser) {

        ArrayList<Game> games = Api.getGamesByStatusAndUserId(
                screen.getMainMenuPanel().getGameChooserPanel().getTypeOfGameChoice(), currentUser.getId() );

        screen.getMainMenuPanel().getGameChooserPanel().setGameTableModel(games);
    }

}
