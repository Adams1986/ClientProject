package logic.subcontroller;

import gui.DialogMessage;
import gui.Screen;
import sdk.*;
import sdk.dto.Game;
import sdk.dto.User;

import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by ADI on 15-11-2015.
 */
public class GameOverviewerLogic {

    private Screen screen;

    public GameOverviewerLogic(Screen screen) {

        this.screen = screen;
    }

    public Game showReplay(ActionListener replaySnakeHandler, int userId){

        Game replayGame = screen.getMainMenuPanel().getGameOverviewerPanel().getGame();

        //TODO: // FIXME: 23/11/15
        //if (replayGame.getHost().getId() == userId || replayGame.getOpponent().getId() == userId)
            screen.getMainMenuPanel().addReplaySnakeToPanel(replayGame, replaySnakeHandler);
        
        //else
            System.out.println("Can't replay this game. You haven't played it");


        return replayGame;
    }

    //TODO: nasty piece of code - little better
    public ArrayList<Game> refreshTable(User currentUser){

        //TODO this is encrypted. Change maybe? Using -1 to get all the users. Important right now to show host and opponent in table
        ArrayList<User> users = DataParser.getDecryptedUserList(Api.getUsers(-1));
        ArrayList<Game> games = null;

        if (screen.getMainMenuPanel().getGameOverviewerPanel().getTypeOfGameChoice().equals(Config.getTypesOfGamesToReplay()[Config.getIndexOne()])) {

            games = DataParser.getDecryptedGamesList(Api.getGamesInvitedByID(currentUser.getId()));
        }
        else if (screen.getMainMenuPanel().getGameOverviewerPanel().getTypeOfGameChoice().equals(Config.getTypesOfGamesToReplay()[Config.getIndexTwo()])){

            games = DataParser.getDecryptedGamesList(Api.getOpenGames(currentUser.getId()));
        }
        else if (screen.getMainMenuPanel().getGameOverviewerPanel().getTypeOfGameChoice().equals(Config.getTypesOfGamesToReplay()[Config.getIndexThree()])){

            games = DataParser.getDecryptedGamesList(Api.getGamesByStatusAndUserId(Config.getServerPathPendingGamesById(), currentUser.getId()));
        }
        else if (screen.getMainMenuPanel().getGameOverviewerPanel().getTypeOfGameChoice().equals(Config.getTypesOfGamesToReplay()[Config.getIndexFour()])){

            games = DataParser.getDecryptedGamesList(Api.getGamesHostedById(currentUser.getId()));
        }
        else if (screen.getMainMenuPanel().getGameOverviewerPanel().getTypeOfGameChoice().equals(Config.getTypesOfGamesToReplay()[Config.getIndexFive()])){

            games = DataParser.getDecryptedGamesList(Api.getGamesByStatusAndUserId(Config.getServerPathOpenGamesById(), currentUser.getId()));
        }
        else if (screen.getMainMenuPanel().getGameOverviewerPanel().getTypeOfGameChoice().equals(Config.getTypesOfGamesToReplay()[Config.getIndexSix()])){

            games = DataParser.getDecryptedGamesList(Api.getGamesByStatusAndUserId(Config.getServerPathFinishedGamesById(), currentUser.getId()));
        }

        //TODO fix this in SQLDriver on the server
        if (games != null) {
            for (int i = Config.getCount(); i < users.size(); i++) {

                for (int j = Config.getCount(); j < games.size(); j++) {

                    if (users.get(i).getId() == games.get(j).getHost().getId()) {

                        games.get(j).getHost().setUsername(users.get(i).getUsername());
                    }
                    else if (users.get(i).getId() == games.get(j).getOpponent().getId()) {

                        games.get(j).getOpponent().setUsername(users.get(i).getUsername());
                    }

                    if (users.get(i).getId() == games.get(j).getWinner().getId()) {

                        games.get(j).getWinner().setUsername(users.get(i).getUsername());
                    }
                }
            }
        }
        screen.getMainMenuPanel().getGameOverviewerPanel().setGameTableModel(games);

        return games;
    }
}
