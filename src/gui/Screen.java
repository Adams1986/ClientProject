package gui;

import sdk.Config;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Screen class. Is of type JFrame and is the window for the app.
 * Contains instances of custom JPanels from the gui package
 */
public class Screen extends JFrame {

    private JPanel contentPane;
    private JPanel clPanel;
    private CardLayout cl;
    private LoginPanel loginPanel;
    private MainMenuPanel mainMenuPanel;
    private CreateUserPanel createUserPanel;

    public Screen(){

        super(Config.getAppName());

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(Config.getAppBorderSize(), Config.getAppBorderSize(),
                Config.getAppBorderSize(), Config.getAppBorderSize()));

        contentPane.setLayout(new BorderLayout(Config.getZeroXY(), Config.getZeroXY()));
        setContentPane(contentPane);

        loginPanel = new LoginPanel();
        mainMenuPanel = new MainMenuPanel();
        createUserPanel = new CreateUserPanel();

        clPanel = new JPanel();
        cl = new CardLayout();

        clPanel.setLayout(cl);
        clPanel.add(loginPanel, Config.getLoginScreen());
        clPanel.add(mainMenuPanel, Config.getMainMenuScreen());
        clPanel.add(createUserPanel, Config.getCreateUserScreen());
        contentPane.add(clPanel, BorderLayout.CENTER);

        cl.show(clPanel, Config.getLoginScreen());

        setFonts(new Font(Config.getHeaderFont(), Font.BOLD, Config.getHeaderTextSize()));

        setSize(Config.getAppWidth(), Config.getAppHeight());
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void setFonts(Font f){

        mainMenuPanel.setFonts(f);
        createUserPanel.setFonts(f);
        loginPanel.setFonts(f);
    }

    /**
     * get method to call on login panels methods from logic
     * @return
     */
    public LoginPanel getLoginPanel(){

        return loginPanel;
    }

    /**
     * used to navigate betweenn login and menu screens
     * @param card
     */
    public void show(String card){
        cl.show(clPanel, card);
    }

    public MainMenuPanel getMainMenuPanel() {
        return mainMenuPanel;
    }

    public CreateUserPanel getCreateUserPanel(){
        return createUserPanel;
    }
}
