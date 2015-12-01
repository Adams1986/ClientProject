package logic.subcontroller;

import gui.DialogMessage;
import gui.Screen;
import sdk.Api;
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

    public Game showReplay(ActionListener l, int userId){

        Game replayGame = screen.getMainMenuPanel().getGameOverviewerPanel().getGame();

        if (replayGame.getHost().getId() == userId ||
                ( replayGame.getOpponent().getId() == userId && replayGame.getOpponent().getControls() != null ) ) {

            screen.getMainMenuPanel().addReplaySnakeToPanel(replayGame, l);
            screen.getMainMenuPanel().setSidePanelState(false);
        }
        
        else
            DialogMessage.showMessage(screen, "As if! You have to play the game before you can watch a replay");

        return replayGame;
    }


    public ArrayList<Game> refreshTable(User currentUser){

        String gameStatus = screen.getMainMenuPanel().getGameOverviewerPanel().getTypeOfGameChoice();
        ArrayList<Game> games = Api.getGamesByStatusAndUserId(gameStatus, currentUser.getId());

        screen.getMainMenuPanel().getGameOverviewerPanel().setGameTableModel(games);

        return games;
    }
}
