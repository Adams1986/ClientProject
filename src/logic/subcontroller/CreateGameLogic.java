package logic.subcontroller;

import gui.DialogMessage;
import gui.Screen;
import sdk.Api;
import sdk.Config;
import sdk.dto.Game;

/**
 * Subcontroller is responsible for drawing the users moves onto the screen and calling the API's methods for playing a
 * game.
 */
public class CreateGameLogic {

    private Screen screen;

    public CreateGameLogic(Screen screen){

        this.screen = screen;
    }

    /**
     * Takes a game as parameter and returns a message, which will tell the user whether or not the game was created,
     * joined or run.
     * @param newGame
     * @return
     */
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

                if (newGame.getStatus().equals("open")) {
                    Api.joinGame(newGame);
                }
                message = Api.startGame(newGame);

            }

            //stops animation
            screen.getMainMenuPanel().getSnakeGameEngine().stopTimer();
            screen.getMainMenuPanel().getCreateNewGamePanel().resetFields();
            screen.getMainMenuPanel().setSidePanelState(true);
        }
        return message;
    }

    /**
     * This method takes a Game object as parameter and tries with some delay to either create, join or start a game with
     * some delay. Only happens if server connection fails when you have or are just about to start a game. Runs in a new
     * thread so as not to cause the application to freeze.
     * TODO: remove hardcoding
     * @param newGame
     */
    public void saveGameInCache(final Game newGame) {


        Thread attemptToCreateGame = new Thread(new Runnable() {

            private String messageFromServer;
            private String failMessage;

            @Override
            public void run() {

                int tryToCreateGame = 0;

                try {

                    while (tryToCreateGame < 10){

                        tryToCreateGame++;
                        Thread.sleep(3500);

                        if (newGame.getOpponent() == null){

                            messageFromServer = Api.createGame(newGame);

                        }
                        else {

                            if (newGame.getStatus().equals("open")) {
                                Api.joinGame(newGame);
                            }
                            messageFromServer = Api.startGame(newGame);

                        }
                        if (!messageFromServer.equals("Connection to server failed")){

                            tryToCreateGame = 11;
                        }
                        else {

                            failMessage = messageFromServer + ". Resending game, attempt: " + tryToCreateGame + "/10";
                        }
                        if (tryToCreateGame < 2) {
                            DialogMessage.showMessage(screen, failMessage);
                        }
                        else {
                            failMessage = "Resending game, attempt: " + tryToCreateGame + "/10";

                            if (tryToCreateGame < 11)
                            screen.getMainMenuPanel().getGameChooserPanel().setGameChooserHeader(failMessage);
                        }
                    }
                    Thread.sleep(3500);
                    if (tryToCreateGame != 11) {
                        DialogMessage.showMessage(screen, "Failed to send your game");
                    }
                    else {
                        screen.getMainMenuPanel().getGameChooserPanel().setGameChooserHeader(messageFromServer);
                    }
                    Thread.sleep(5000);
                    screen.getMainMenuPanel().getGameChooserPanel().setGameChooserHeader(Config.getGameChooserHeaderText());


                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        attemptToCreateGame.start();
    }
}
