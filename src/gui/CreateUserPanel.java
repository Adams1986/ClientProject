package gui;

import sdk.Config;

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

        infoLabel = new JLabel(Config.getInfoLabelText());
        firstNameLabel = new JLabel(Config.getFirstNameLabelText());
        lastNameLabel = new JLabel(Config.getLastNameLabelText());
        emailLabel = new JLabel(Config.getEmailLabelText());
        usernameLabel = new JLabel(Config.getUsernameLabelText());
        passwordLabel = new JLabel(Config.getPasswordLabelText());

        firstNameField = new JTextField();
        lastNameField = new JTextField();
        emailField = new JTextField();
        usernameField = new JTextField();
        passwordField = new JPasswordField();

        btnCreate = new JButton(Config.getBtnCreateUserText());
        btnBack = new JButton(Config.getBtnBackToLoginText());

        infoLabel.setBounds(Config.getDefaultXPosJComponent(), Config.getY1PosJComponent(),
                Config.getWidth1JComponent(), Config.getDefaultHeightJComponent());

        firstNameLabel.setBounds(Config.getDefaultXPosJComponent(), Config.getY3PosJComponent(),
                Config.getLblWidth(), Config.getDefaultHeightJComponent());

        lastNameLabel.setBounds(Config.getDefaultXPosJComponent(), Config.getY4PosJComponent(),
                Config.getLblWidth(), Config.getDefaultHeightJComponent());

        emailLabel.setBounds(Config.getDefaultXPosJComponent(), Config.getY5PosJComponent(),
                Config.getLblWidth(), Config.getDefaultHeightJComponent());

        usernameLabel.setBounds(Config.getDefaultXPosJComponent(), Config.getY6PosJComponent(),
                Config.getLblWidth(), Config.getDefaultHeightJComponent());

        passwordLabel.setBounds(Config.getDefaultXPosJComponent(), Config.getY7PosJComponent(),
                Config.getLblWidth(), Config.getDefaultHeightJComponent());


        firstNameField.setBounds(Config.getX1PosJComponent(), Config.getY3PosJComponent(),
                Config.getWidth1JComponent(), Config.getDefaultHeightJComponent());

        lastNameField.setBounds(Config.getX1PosJComponent(), Config.getY4PosJComponent(),
                Config.getWidth1JComponent(), Config.getDefaultHeightJComponent());

        emailField.setBounds(Config.getX1PosJComponent(), Config.getY5PosJComponent(),
                Config.getWidth1JComponent(), Config.getDefaultHeightJComponent());

        usernameField.setBounds(Config.getX1PosJComponent(), Config.getY6PosJComponent(),
                Config.getWidth1JComponent(), Config.getDefaultHeightJComponent());

        passwordField.setBounds(Config.getX1PosJComponent(), Config.getY7PosJComponent(),
                Config.getWidth1JComponent(), Config.getDefaultHeightJComponent());


        btnBack.setBounds(Config.getDefaultXPosJComponent(), Config.getY8PosJComponent(),
                Config.getDefaultWidthJComponent(), Config.getDefaultHeightJComponent());

        btnCreate.setBounds(Config.getX1PosJComponent(), Config.getY8PosJComponent(),
                Config.getDefaultWidthJComponent(), Config.getDefaultHeightJComponent());


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

    public void setFonts(Font f){

        infoLabel.setFont(f);
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

        firstNameField.setText(Config.getClearField());
        lastNameField.setText(Config.getClearField());
        emailField.setText(Config.getClearField());
        usernameField.setText(Config.getClearField());
        passwordField.setText(Config.getClearField());
    }
}
