package logic.subcontroller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import gui.Screen;
import sdk.*;

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

    public Game showReplay(User currentUser, ActionListener replaySnakeHandler){

        Game replayGame = screen.getMainMenuPanel().getGameOverviewerPanelPanel().getGame();
//        //TODO: maybe move to replaysnake class instead! modeling host and opponent to create graphics
//        replayGame.setWinner(
//                new Gson().fromJson(Api.getUser(replayGame.getWinner().getId()), Gamer.class));

        //adding controls to gamer objects instead of game object.
//                    replayGame.getHost().setControls(replayGame.getHostControls());
//                    replayGame.getOpponent().setControls(replayGame.getOpponentControls());

        screen.getMainMenuPanel().addReplaySnakeToPanel(replayGame, replaySnakeHandler);

        return replayGame;
    }

    public ArrayList<Game> refreshTable(User currentUser){

        ArrayList<User> users = new Gson().fromJson(Api.getUsers(), new TypeToken<ArrayList<User>>(){}.getType());
        ArrayList<Game> games = null;

        if (screen.getMainMenuPanel().getGameOverviewerPanelPanel().getTypeOfGameChoice().equals(Config.getTypesOfGamesToReplay()[Config.getIndexOne()])) {

            games = new Gson().fromJson(Api.getGamesInvitedByID(currentUser.getId()), new TypeToken<ArrayList<Game>>(){}.getType());
        }
        else if (screen.getMainMenuPanel().getGameOverviewerPanelPanel().getTypeOfGameChoice().equals(Config.getTypesOfGamesToReplay()[Config.getIndexTwo()])){

            games = new Gson().fromJson(Api.getOpenGames(currentUser.getId()), new TypeToken<ArrayList<Game>>(){}.getType());
        }
        else if (screen.getMainMenuPanel().getGameOverviewerPanelPanel().getTypeOfGameChoice().equals(Config.getTypesOfGamesToReplay()[Config.getIndexThree()])){

            games = new Gson().fromJson(Api.getGamesByStatusAndUserId("pending", currentUser.getId()), new TypeToken<ArrayList<Game>>(){}.getType());
        }
        else if (screen.getMainMenuPanel().getGameOverviewerPanelPanel().getTypeOfGameChoice().equals(Config.getTypesOfGamesToReplay()[Config.getIndexFour()])){

            games = new Gson().fromJson(Api.getGamesHostedById(currentUser.getId()), new TypeToken<ArrayList<Game>>(){}.getType());
        }
        else if (screen.getMainMenuPanel().getGameOverviewerPanelPanel().getTypeOfGameChoice().equals(Config.getTypesOfGamesToReplay()[Config.getIndexFive()])){

            games = new Gson().fromJson(Api.getGamesByStatusAndUserId("openbyid", currentUser.getId()), new TypeToken<ArrayList<Game>>(){}.getType());
        }
        else if (screen.getMainMenuPanel().getGameOverviewerPanelPanel().getTypeOfGameChoice().equals(Config.getTypesOfGamesToReplay()[Config.getIndexSix()])){

            games = new Gson().fromJson(Api.getGamesByStatusAndUserId("finished", currentUser.getId()), new TypeToken<ArrayList<Game>>(){}.getType());
        }

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
        screen.getMainMenuPanel().getGameOverviewerPanelPanel().setGameTableModel(games);

        return games;
    }
}
