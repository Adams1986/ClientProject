package logic.subcontroller;

import gui.DialogMessage;
import gui.Screen;
import sdk.Api;
import sdk.Config;
import sdk.dto.Game;

/**
 *
 */
public class GameEngineLogic {

    private Screen screen;

    public GameEngineLogic(Screen screen){

        this.screen = screen;
    }

    public String draw(Game newGame) {

        String message = null;

        //if game has not ended yet, move snake according to the direction and repaint
        if (!screen.getMainMenuPanel().getSnakeGameEngine().isGameEnded()) {
            screen.getMainMenuPanel().getSnakeGameEngine().move(screen.getMainMenuPanel().getSnakeGameEngine().getDirection());
            screen.getMainMenuPanel().getSnakeGameEngine().repaint();

        }
        else {

            if(newGame.getHost().getControls() == null) {
                newGame.getHost().setControls(screen.getMainMenuPanel().getSnakeGameEngine().getSbToString());
                //Attempt to create the game and show response from server
                message = Api.createGame(newGame);

            }
            else {

                newGame.getOpponent().setControls(screen.getMainMenuPanel().getSnakeGameEngine().getSbToString());

                message = Api.joinGame(newGame);
                Api.startGame(newGame);
                //TODO: add replay functionality after startGame
            }

            //stops animation
            screen.getMainMenuPanel().getSnakeGameEngine().stopTimer();
            screen.getMainMenuPanel().getCreateNewGamePanel().resetFields();
            screen.getMainMenuPanel().setSidePanelState(true);
        }
        return message;
    }
}
