package gui;

import javax.swing.*;

/**
 * Class to be used by logic for showing dialog boxes to the user
 */
public class DialogMessage {

    private Screen screen;

    public DialogMessage(Screen screen){

        this.screen = screen;
    }

    /**
     * Normal JOptionPane dialog box which shows a message to the user
     * @param message
     */
    public void showMessage(String message){

        JOptionPane.showMessageDialog(screen, message);
    }

    /**
     * JOptionPane dialog box that interacts with the user,
     * so user can confirm for example a log out
     * @param message
     * @param title
     * @return
     */
    public boolean showConfirmMessage(String message, String title){

        int option = JOptionPane.showConfirmDialog(screen, message, title, JOptionPane.YES_NO_OPTION);

        if (option == JOptionPane.YES_OPTION)
            return true;

        return false;
    }

    /**
     * If error has occurred this method makes it possible to really show user
     * with the JOptionPane.ERROR_MESSAGE int which shows a big fat error logo
     * @param message
     * @param title
     */
    public void showErrorMessage(String message, String title){

        JOptionPane.showMessageDialog(screen, message, title, JOptionPane.ERROR_MESSAGE);
    }
}
