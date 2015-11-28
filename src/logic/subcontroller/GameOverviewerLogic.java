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

        if (replayGame.getHost().getId() == userId || (replayGame.getOpponent().getId() == userId && replayGame.getOpponent().getControls() != null)) {
            screen.getMainMenuPanel().addReplaySnakeToPanel(replayGame, replaySnakeHandler);
            screen.getMainMenuPanel().setSidePanelState(false);
        }
        
        else
            DialogMessage.showMessage(screen, "As if! You have to play the game before you can watch a replay");


        return replayGame;
    }

    //TODO: nasty piece of code - little better
    public ArrayList<Game> refreshTable(User currentUser){

        ArrayList<Game> games = null;

//        //TODO use value inside combobox as param in getGamesByStatusAndUserId
//        if (screen.getMainMenuPanel().getGameOverviewerPanel().getTypeOfGameChoice().equals(Config.getTypesOfGamesToReplay()[Config.getIndexOne()])) {
//
//            games = DataParser.getDecryptedGamesList(Api.getGamesInvitedByID(currentUser.getId()));
//        }
//        else if (screen.getMainMenuPanel().getGameOverviewerPanel().getTypeOfGameChoice().equals(Config.getTypesOfGamesToReplay()[Config.getIndexTwo()])){
//
//            games = DataParser.getDecryptedGamesList(Api.getGamesByStatusAndUserId(Config.getServerPathOpenGamesById(), currentUser.getId()));
//        }
//        else if (screen.getMainMenuPanel().getGameOverviewerPanel().getTypeOfGameChoice().equals(Config.getTypesOfGamesToReplay()[Config.getIndexThree()])){
//
//            games = DataParser.getDecryptedGamesList(Api.getGamesByStatusAndUserId(Config.getServerPathPendingGamesById(), currentUser.getId()));
//        }
//        else if (screen.getMainMenuPanel().getGameOverviewerPanel().getTypeOfGameChoice().equals(Config.getTypesOfGamesToReplay()[Config.getIndexFour()])){
//
//            //games = DataParser.getDecryptedGamesList(Api.getGamesHostedById(currentUser.getId()));
//        }
//        else if (screen.getMainMenuPanel().getGameOverviewerPanel().getTypeOfGameChoice().equals(Config.getTypesOfGamesToReplay()[Config.getIndexFive()])){
//
//            games = DataParser.getDecryptedGamesList(Api.getOpenGames(currentUser.getId()));
//        }
//        else if (screen.getMainMenuPanel().getGameOverviewerPanel().getTypeOfGameChoice().equals(Config.getTypesOfGamesToReplay()[Config.getIndexSix()])){
//
//            games = DataParser.getDecryptedGamesList(Api.getGamesByStatusAndUserId(Config.getServerPathFinishedGamesById(), currentUser.getId()));
//        }
        games = DataParser.getDecryptedGamesList(Api.getGamesByStatusAndUserId(screen.getMainMenuPanel().getGameOverviewerPanel().getTypeOfGameChoice()+ "/", currentUser.getId()));

        screen.getMainMenuPanel().getGameOverviewerPanel().setGameTableModel(games);

        return games;
    }
}
