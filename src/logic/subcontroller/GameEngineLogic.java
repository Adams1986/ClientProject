package logic.subcontroller;

import com.google.gson.Gson;
import gui.DialogMessage;
import gui.Screen;
import sdk.Api;
import sdk.Config;
import sdk.Game;
import sdk.MessageParser;

/**
 * Created by ADI on 15-11-2015.
 */
public class GameEngineLogic {

    public static void draw(Screen screen, Game newGame) {

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
                message = MessageParser.parseMessage(Api.createGame(newGame));
                DialogMessage.showMessage(screen, message);
            }
            else {

                newGame.getOpponent().setControls(screen.getMainMenuPanel().getSnakeGameEngine().getSbToString());

                String gameJson = new Gson().toJson(newGame);
                message = Api.joinGame(gameJson);
                Api.startGame(gameJson);
                DialogMessage.showMessage(screen, MessageParser.parseMessage(message));
            }

            //stops animation
            screen.getMainMenuPanel().getSnakeGameEngine().stopTimer();
            screen.getMainMenuPanel().getCreateNewGamePanel().resetFields();
            screen.getMainMenuPanel().setSidePanelState(true);
            screen.getMainMenuPanel().show(Config.getGameChooserScreen());
        }
    }
}
