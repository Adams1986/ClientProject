package gui;

import sdk.Config;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Class is a JPanel that shows the login screen
 */
public class LoginPanel extends JPanel{

    private JLabel infoLabel;
    private JLabel iconLabel;
    private ImageIcon depardieu;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton btnLogin;
    private JButton btnCreateNewUser;


    /**
     * empty constructor which inits the JComponents
     */
    public LoginPanel() {

        setLayout(null);

        infoLabel = new JLabel(Config.getLoginInfoLabelText());
        usernameField = new JTextField();
        passwordField = new JPasswordField();
        btnLogin = new JButton(Config.getBtnLoginText());
        btnCreateNewUser = new JButton(Config.getBtnCreateNewUserText());
        depardieu = new ImageIcon(Config.getDepardieuImagePath());
        iconLabel = new JLabel(depardieu);

        infoLabel.setBounds(Config.getDefaultXPosJComponent(), Config.getY1PosJComponent(),
                Config.getLblWidth(), Config.getDefaultHeightJComponent());

        infoLabel.setFont(new Font(Config.getHeaderFont(), Font.BOLD, Config.getHeaderTextSize()));

        usernameField.setBounds(Config.getDefaultXPosJComponent(), Config.getY3PosJComponent(),
                Config.getLblWidth(), Config.getDefaultHeightJComponent());

        passwordField.setBounds(Config.getDefaultXPosJComponent(), Config.getY4PosJComponent(),
                Config.getLblWidth(), Config.getDefaultHeightJComponent());

        btnLogin.setBounds(Config.getDefaultXPosJComponent(), Config.getY5PosJComponent(),
                Config.getDefaultWidthJComponent(), Config.getDefaultHeightJComponent());

        btnCreateNewUser.setBounds(Config.getDefaultXPosJComponent(), Config.getY7PosJComponent(),
                Config.getWidth3JComponent(), Config.getDefaultHeightJComponent());

        iconLabel.setBounds(Config.getX2PosJComponent(), Config.getY3PosJComponent(),
                Config.getDepardieuWidth(), Config.getDepardieuHeight());

        add(infoLabel);
        add(usernameField);
        add(passwordField);
        add(btnLogin);
        add(iconLabel);
        add(btnCreateNewUser);
    }

    public void addActionListeners(ActionListener l) {

        btnLogin.addActionListener(l);
        btnCreateNewUser.addActionListener(l);
    }

    public void clearFields(){

        usernameField.setText(Config.getClearField());
        passwordField.setText(Config.getClearField());
    }

    public String getUsernameInput() {

        return usernameField.getText();
    }

    public String getPasswordInput() {

        return new String(passwordField.getPassword());
    }

    public void setFailedLoginAttempt(String message) {

        infoLabel.setText(message);
    }
}
