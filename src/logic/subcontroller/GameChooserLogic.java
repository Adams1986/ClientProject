package logic.subcontroller;

import gui.DialogMessage;
import gui.Screen;
import sdk.Config;
import sdk.dto.Game;
import sdk.dto.User;

/**
 * Class contains the join game method, which gets the chosen game from the table before controller starts the game
 * engine
 */
public class GameChooserLogic {

    private Screen screen;

    public GameChooserLogic(Screen screen){

        this.screen = screen;
    }

    /**
     * returns a selected game from the game chooser panel and removes it 'manually' instead of needing to call server API
     * for new list
     * @param currentUser
     * @return
     */
    public Game joinGame(User currentUser) {

        Game newGame = null;

        try {

            newGame = screen.getMainMenuPanel().getGameChooserPanel().getGame();
            newGame.getOpponent().setId(currentUser.getId());


            screen.getMainMenuPanel().getGameChooserPanel().removeGameFromTable();
            //disabling sidepanel to start game activity
            screen.getMainMenuPanel().setSidePanelState(false);

        }
        //catch if no selection was made and prompt message
        catch (IndexOutOfBoundsException e1) {

            DialogMessage.showMessage(screen, Config.getMissingGameSelectionText());
        }

        return newGame;
    }
}
