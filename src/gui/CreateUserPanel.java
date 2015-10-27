package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Created by simonadams on 27/10/15.
 */
public class CreateUserPanel extends JPanel {

    private JLabel infoLabel;
    private JLabel firstNameLabel;
    private JLabel lastNameLabel;
    private JLabel emailLabel;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField emailField;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton btnCreate;
    private JButton btnBack;

    public CreateUserPanel(){
        setLayout(null);
        setBounds(0, 0, 640, 500);

        infoLabel = new JLabel("Create an account");
        firstNameLabel = new JLabel("First name: ");
        lastNameLabel = new JLabel("Last name: ");
        emailLabel = new JLabel("Email: ");
        usernameLabel = new JLabel("Username: ");
        passwordLabel = new JLabel("Password: ");

        firstNameField = new JTextField(20);
        lastNameField = new JTextField(20);
        emailField = new JTextField(20);
        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);

        btnCreate = new JButton("Create user");
        btnBack = new JButton("To Login");

        infoLabel.setBounds(40, 40, 220, 20);
        infoLabel.setFont(new Font("Verdana", Font.BOLD, 16));

        firstNameLabel.setBounds(40, 80, 220, 20);
        lastNameLabel.setBounds(40, 120, 220, 20);
        emailLabel.setBounds(40, 160, 220, 20);
        usernameLabel.setBounds(40, 200, 220, 20);
        passwordLabel.setBounds(40, 240, 220, 20);

        firstNameField.setBounds(200, 80, 220, 20);
        lastNameField.setBounds(200, 120, 220, 20);
        emailField.setBounds(200, 160, 220, 20);
        usernameField.setBounds(200, 200, 220, 20);
        passwordField.setBounds(200, 240, 220, 20);

        btnBack.setBounds(40, 290, 150, 30);
        btnCreate.setBounds(200, 290, 150, 30);

        add(infoLabel);
        add(firstNameLabel);
        add(lastNameLabel);
        add(emailLabel);
        add(usernameLabel);
        add(passwordLabel);

        add(firstNameField);
        add(lastNameField);
        add(emailField);
        add(usernameField);
        add(passwordField);

        add(btnCreate);
        add(btnBack);

    }

    public String getFirstNameField() {
        return firstNameField.getText();
    }

    public String getLastNameField() {
        return lastNameField.getText();
    }

    public String getEmailField() {
        return emailField.getText();
    }

    public String getUsernameField() {
        return usernameField.getText();
    }

    public String getPasswordField() {
        return new String(passwordField.getPassword());
    }



    public void addActionListeners(ActionListener l){

        btnCreate.addActionListener(l);
        btnBack.addActionListener(l);
    }

    public void clearFields() {

        firstNameField.setText("");
        lastNameField.setText("");
        emailField.setText("");
        usernameField.setText("");
        passwordField.setText("");
    }
}
