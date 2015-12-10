package logic.subcontroller;

import gui.DialogMessage;
import gui.Screen;
import sdk.Api;
import sdk.Config;
import sdk.dto.Game;
import sdk.dto.User;

import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Logic sub controller which handles instantiating replays
 */
public class GameOverviewerLogic {

    private Screen screen;

    public GameOverviewerLogic(Screen screen) {

        this.screen = screen;
    }

    /**
     * Takes care of showing replays of already played games. Takes a lot of parameters...
     * @param l
     * @param replayGame
     * @param userId
     * @param isFromHighScorePanel
     * @return
     */
    public Game showReplay(ActionListener l, Game replayGame, int userId, boolean isFromHighScorePanel){

        //checking to see if the current users id is either the same as host id, or same as opponent id and is not null
        //added small extra check with boolean to allow highscores to show all the highest scoring games
        if ( ( replayGame.getHost().getId() == userId ||
                ( replayGame.getOpponent().getId() == userId && replayGame.getOpponent().getControls() != null ) )
                || isFromHighScorePanel) {

            //uses add replay.. method to create a new instance of the ReplaySnake class and showing it on the screen.
            screen.getMainMenuPanel().addReplaySnakeToPanel(replayGame, l);
            //deactivating menu panel
            screen.getMainMenuPanel().setSidePanelState(false);
        }
        
        else
            DialogMessage.showMessage(screen, Config.getNoPeakingOtherGamesText());

        return replayGame;
    }
}
