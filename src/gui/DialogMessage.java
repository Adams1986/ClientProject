package gui;

import javax.swing.*;

/**
 * Class to be used by logic for showing dialog boxes to the user
 */
public class DialogMessage {

    /**
     * Normal JOptionPane dialog box which shows a message to the user
     * @param screen
     * @param message
     */
    public static void showMessage(Screen screen, String message){

        JOptionPane.showConfirmDialog(screen, message);
    }

    /**
     * JOptionPane dialog box that interacts with the user,
     * so user can confirm for example a log out
     * @param screen
     * @param message
     * @param title
     * @return
     */
    public static boolean showConfirmMessage(Screen screen, String message, String title){

        int option = JOptionPane.showConfirmDialog(screen, message, title, JOptionPane.YES_NO_OPTION);

        if (option == JOptionPane.YES_OPTION)
            return true;

        return false;
    }

    /**
     * If error has occurred this method makes it possible to really show user
     * with the JOptionPane.ERROR_MESSAGE int which shows a big fat error logo
     * @param screen
     * @param message
     * @param title
     */
    public static void showErrorMessage(Screen screen, String message, String title){

        JOptionPane.showMessageDialog(screen, message, title, JOptionPane.ERROR_MESSAGE);
    }
}
