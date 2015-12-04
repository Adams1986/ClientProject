package logic.subcontroller;

import gui.Screen;
import sdk.Api;
import sdk.Config;
import sdk.dto.User;

/**
 * Class contains methods that handle the login
 */
public class LoginLogic {

    private Screen screen;
    private boolean isRunning;

    public LoginLogic (Screen screen){

        this.screen = screen;
    }

    /**
     * sets the current users information and sends information to server via authenticate login
     * @param currentUser
     * @returns a message from server
     */
    public String authenticated(User currentUser) {

        currentUser.setUsername(screen.getLoginPanel().getUsernameInput());
        currentUser.setPassword(screen.getLoginPanel().getPasswordInput());

        //returning message from server, telling if login was successful or not.
        return Api.authenticateLogin(currentUser);
    }


    /**
     * Method resets state of client application -> logs out
     */
    public boolean logOut() {

        boolean isAuthenticated = false;

        screen.getMainMenuPanel().show(Config.getGameChooserScreen());
        screen.show(Config.getLoginScreen());

        setIsRunning(false);
        screen.getMainMenuPanel().resetFields();

        //
        Api.resetToken();

        return isAuthenticated;
    }


    /**
     * Starts a new thread that changes the top left hand corner information every 1250 millis
     * @param currentUser
     * @param messageFromServer
     */
    public void setUserInfo(final User currentUser, final String messageFromServer) {

        Thread welcomeThread = new Thread(new Runnable() {

            @Override
            public void run() {

                //integer determining which set of information to show
                int showInfo = 0;
                //setting boolean to true to start running the while loop
                setIsRunning(true);
                screen.getMainMenuPanel().setWelcomeMessage(messageFromServer);

                try {
                    while (isRunning()) {

                        Thread.sleep(1250);

                        switch (showInfo) {
                            //showing message from server
                            case 0:
                                screen.getMainMenuPanel().setWelcomeMessage(messageFromServer);
                                screen.getMainMenuPanel().setInfoMessage(Config.getClearField());
                                showInfo++;
                                break;
                            //showing a welcome message along with users first name
                            case 1:
                                screen.getMainMenuPanel().setWelcomeMessage(Config.getWelcomeText());
                                screen.getMainMenuPanel().setInfoMessage(currentUser.getFirstName());
                                showInfo++;
                                break;
                            // - with users last name
                            case 2:
                                screen.getMainMenuPanel().setWelcomeMessage(Config.getWelcomeText());
                                screen.getMainMenuPanel().setInfoMessage(currentUser.getLastName());
                                showInfo++;
                                break;
                            // - with username
                            case 3:
                                screen.getMainMenuPanel().setInfoMessage(currentUser.getUsername());
                                showInfo++;
                                break;
                            //showing the total score the user has.
                            case 4:
                                screen.getMainMenuPanel().setWelcomeMessage(Config.getTotalScoreText());
                                screen.getMainMenuPanel().setInfoMessage(Integer.toString(currentUser.getTotalScore()));
                                showInfo = 0;
                                break;
                        }
                    }
                    screen.getMainMenuPanel().setWelcomeMessage(Config.getClearField());
                    screen.getMainMenuPanel().setInfoMessage(Config.getClearField());
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        //start running thread
        welcomeThread.start();
    }

    public boolean isRunning() {
        return isRunning;
    }

    //Getter used to 'kill' thread when logging out
    public void setIsRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }
}
