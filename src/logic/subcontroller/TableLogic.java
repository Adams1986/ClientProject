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

    /**
     * Uses a user id as parameter to help get an array list of games that show different types of games depending on
     * their status in the GameOverviewerPanel(open, pending, finished, user hosted, user was invited to game etc.).
     * @param userId
     */
    public void setGameOverviewerTableModel(int userId){

        ArrayList<Game> games = Api.getGamesByStatusAndUserId(
                screen.getMainMenuPanel().getGameOverviewerPanel().getTypeOfGameChoice(), userId);


        screen.getMainMenuPanel().getGameOverviewerPanel().setGameTableModel(games);
    }

    /**
     * Sets the table with possible opponents of a new game from a list of users
     */
    public void setUserTableModel(int userId){

        ArrayList<User> users = Api.getUsers(userId);

        screen.getMainMenuPanel().getCreateNewGamePanel().setOpponentTableModel(users);
    }

    /**
     * Sets the delete table with a list of all the user's games which are either open or pending. User cannot delete
     * a finished game or a game that he is not the host of.
     * @param userId
     */
    public void setGamesToDeleteTableModel(int userId){

        ArrayList<Game> games = Api.getGamesByStatusAndUserId(Config.getServerPathOpenAndPendingGamesById(), userId);

        screen.getMainMenuPanel().getDeleteGamePanel().setDeleteGameTableModel(games);
    }

    /**
     * Sets both the table with high scores and the top three best scores in the 'animation panel'. Sets both panels
     * so as they have the same content at all times
     */
    public void setHighScoreTableModel() {

        ArrayList<Score> highScores = Api.getHighScores();

        screen.getMainMenuPanel().getHighScoresPanel().setHighScoreTableModel(highScores);
        screen.getMainMenuPanel().getHighScoresMovingPanel().setHighScores(highScores);
    }

    /**
     * This method is used to set the 'moving panel' with high scores. Unlike the above method, this also creates the
     * instance of the panel while the aforementioned is only used to update the panel.
     * @param l
     */
    public void setHighScoresMovingPanel(ActionListener l){

        ArrayList<Score> highScores = Api.getHighScores();

        screen.getMainMenuPanel().getNewInstanceOfHighScoresMovingPanel(highScores, l);
    }

    /**
     * Sets the panel with the existing games that are playable right now. It will either show open games or pending
     * games which are games other people have invited you to.
     * @param userId
     */
    public void setGameChooserTableModel(int userId) {

        ArrayList<Game> games = Api.getGamesByStatusAndUserId(
                screen.getMainMenuPanel().getGameChooserPanel().getTypeOfGameChoice(), userId);

        screen.getMainMenuPanel().getGameChooserPanel().setGameTableModel(games);
    }

}
