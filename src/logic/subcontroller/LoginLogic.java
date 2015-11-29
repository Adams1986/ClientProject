package logic.subcontroller;

import gui.Screen;
import sdk.Api;
import sdk.Config;
import sdk.DataParser;
import sdk.dto.User;


public class LoginLogic {

    private Screen screen;
    private boolean isRunning;

    public LoginLogic (Screen screen){

        this.screen = screen;
    }

    public String authenticated(User currentUser) {

        currentUser.setUsername(screen.getLoginPanel().getUsernameInput());
        currentUser.setPassword(screen.getLoginPanel().getPasswordInput());

        return Api.authenticateLogin(currentUser);
    }

    /**
     * Starts a new thread that runs
     * @param currentUser
     * @param message
     */
    public void setUserInfo(final User currentUser, final String message) {

        Thread welcomeThread = new Thread(new Runnable() {

            @Override
            public void run() {

                int showInfo = 0;
                setIsRunning(true);
                screen.getMainMenuPanel().setWelcomeMessage(message);

                try {
                    while (isRunning()) {
                        Thread.sleep(1250);
                        System.out.println("thread running");

                        switch (showInfo) {
                            case 0:
                                screen.getMainMenuPanel().setWelcomeMessage(message);
                                screen.getMainMenuPanel().setInfoMessage(Config.getClearField());
                                showInfo++;
                                break;

                            case 1:
                                screen.getMainMenuPanel().setWelcomeMessage(Config.getWelcomeText());
                                screen.getMainMenuPanel().setInfoMessage(currentUser.getFirstName());
                                showInfo++;
                                break;

                            case 2:
                                screen.getMainMenuPanel().setWelcomeMessage(Config.getWelcomeText());
                                screen.getMainMenuPanel().setInfoMessage(currentUser.getLastName());
                                showInfo++;
                                break;

                            case 3:
                                screen.getMainMenuPanel().setInfoMessage(currentUser.getUsername());
                                showInfo++;
                                break;

                            case 4:
                                screen.getMainMenuPanel().setWelcomeMessage("Total score: ");
                                screen.getMainMenuPanel().setInfoMessage(currentUser.getTotalScore()+"");
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
        welcomeThread.start();
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setIsRunning(boolean isRunning) {
        this.isRunning = isRunning;
    }
}
