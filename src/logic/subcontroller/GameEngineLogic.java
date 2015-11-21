package logic.subcontroller;

import com.google.gson.Gson;
import gui.DialogMessage;
import gui.Screen;
import sdk.Api;
import sdk.Config;
import sdk.dto.Game;
import sdk.DataParser;

/**
 * Created by ADI on 15-11-2015.
 */
public class GameEngineLogic {

    private Screen screen;

    public GameEngineLogic(Screen screen){

        this.screen = screen;
    }

    public void draw(Game newGame, DialogMessage dialogMessage) {

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
                message = DataParser.parseMessage(Api.createGame(new Gson().toJson(newGame)));
                dialogMessage.showMessage(message);
            }
            else {

                newGame.getOpponent().setControls(screen.getMainMenuPanel().getSnakeGameEngine().getSbToString());

                String gameJson = new Gson().toJson(newGame);
                message = Api.joinGame(gameJson);
                Api.startGame(gameJson);
                dialogMessage.showMessage(DataParser.parseMessage(message));
            }

            //stops animation
            screen.getMainMenuPanel().getSnakeGameEngine().stopTimer();
            screen.getMainMenuPanel().getCreateNewGamePanel().resetFields();
            screen.getMainMenuPanel().setSidePanelState(true);
            screen.getMainMenuPanel().show(Config.getGameChooserScreen());
        }
    }
}
