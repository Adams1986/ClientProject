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
    public LoginPanel(){

        setLayout(null);

        infoLabel = new JLabel("Enter user details below");
        depardieu = new ImageIcon("src/depardieu.png");
//        System.out.println(this.location);
        iconLabel = new JLabel(depardieu);
        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        btnLogin = new JButton("Login");

        infoLabel.setBounds(50, 100, 220, 20);
        iconLabel.setBounds(300, 100, 320, 240);
        usernameField.setBounds(50, 180, 220 ,20);
        passwordField.setBounds(50, 240, 220, 20);
        btnLogin.setBounds(50, 300, 100, 20);

        add(infoLabel);
        add(usernameField);
        add(passwordField);
        add(btnLogin);
        add(iconLabel);
    }

    public void addActionListeners(ActionListener l){

        usernameField.addActionListener(l);
    }

    public void clearFields(){

        usernameField.setText("");
        passwordField.setText("");
    }

    public String getUsernameInput(){

        return usernameField.getText();
    }

    public String getPasswordInput(){

        return new String(passwordField.getPassword());
    }
}
