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

    public boolean draw(Game newGame) {

        String message;

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

                DialogMessage.showMessage(screen, message);
            }
            else {

                newGame.getOpponent().setControls(screen.getMainMenuPanel().getSnakeGameEngine().getSbToString());

                message = Api.joinGame(newGame);
                Api.startGame(newGame);
                DialogMessage.showMessage(screen, message);
            }

            //stops animation
            screen.getMainMenuPanel().getSnakeGameEngine().stopTimer();
            screen.getMainMenuPanel().getCreateNewGamePanel().resetFields();
            screen.getMainMenuPanel().setSidePanelState(true);
            screen.getMainMenuPanel().show(Config.getGameChooserScreen());
        }
        return screen.getMainMenuPanel().getSnakeGameEngine().isGameEnded();
    }
}
