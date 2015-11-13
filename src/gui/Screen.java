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

        //TODO:remove later
        Config.init();

        contentPane = new JPanel();
        //TODO: are these numbers OK to have?
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0,0));
        setContentPane(contentPane);

        loginPanel = new LoginPanel();
        mainMenuPanel = new MainMenuPanel();
        createUserPanel = new CreateUserPanel();

        clPanel = new JPanel();
        cl = new CardLayout();

        clPanel.setLayout(cl);
        clPanel.add(loginPanel);
        clPanel.add(mainMenuPanel);
        clPanel.add(createUserPanel);
        contentPane.add(clPanel, BorderLayout.CENTER);

        cl.addLayoutComponent(loginPanel,Config.getLoginScreen());
        cl.addLayoutComponent(mainMenuPanel, Config.getMainMenuScreen());
        cl.addLayoutComponent(createUserPanel, Config.getCreateUserScreen());
        cl.show(clPanel, Config.getLoginScreen());
        //TODO: remove again, but use to see layout
        //cl.show(clPanel, Config.getMainMenuScreen());

//        setResizable(false);

        //TODO: change,
        setSize(Config.getAppWidth(), Config.getAppHeight());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
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
