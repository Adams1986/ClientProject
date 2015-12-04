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
 * Created by ADI on 15-11-2015.
 */
public class GameOverviewerLogic {

    private Screen screen;

    public GameOverviewerLogic(Screen screen) {

        this.screen = screen;
    }

    public Game showReplay(ActionListener l, Game replayGame, int userId, boolean isFromHighScorePanel){

        if ( ( replayGame.getHost().getId() == userId ||
                ( replayGame.getOpponent().getId() == userId && replayGame.getOpponent().getControls() != null ) )
                || isFromHighScorePanel) {

            screen.getMainMenuPanel().addReplaySnakeToPanel(replayGame, l);
            screen.getMainMenuPanel().setSidePanelState(false);
            System.out.println("in here");
        }
        
        else
            DialogMessage.showMessage(screen, Config.getNoPeakingOtherGamesText());

        return replayGame;
    }
}
