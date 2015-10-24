package gui;

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


    /**
     * empty constructor which inits the JComponents
     */
    public LoginPanel() {

        setLayout(null);

        infoLabel = new JLabel("Enter user details below");
        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        btnLogin = new JButton("Login");
        depardieu = new ImageIcon("src/depardieu.png");
        iconLabel = new JLabel(depardieu);

        infoLabel.setBounds(40, 140, 220, 20);
        usernameField.setBounds(40, 180, 220 ,30);
        passwordField.setBounds(40, 230, 220, 30);
        btnLogin.setBounds(40, 280, 150, 30);
        iconLabel.setBounds(280, 100, 320, 240);

        add(infoLabel);
        add(usernameField);
        add(passwordField);
        add(btnLogin);
        add(iconLabel);
    }

    public void addActionListeners(ActionListener l) {

        btnLogin.addActionListener(l);
    }

    public void clearFields(){

        usernameField.setText("");
        passwordField.setText("");
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
