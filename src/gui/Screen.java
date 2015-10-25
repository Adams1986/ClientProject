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

    public Screen(){

        super("Snake");

        //TODO:remove later
        Config.init();

        contentPane = new JPanel();
        //TODO: are these numbers OK to have?
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0,0));
        setContentPane(contentPane);

        loginPanel = new LoginPanel();
        mainMenuPanel = new MainMenuPanel();
        clPanel = new JPanel();
        cl = new CardLayout();

        clPanel.setLayout(cl);
        clPanel.add(loginPanel);
        clPanel.add(mainMenuPanel);
        contentPane.add(clPanel, BorderLayout.CENTER);

        cl.addLayoutComponent(loginPanel,Config.getLoginScreen());
        cl.addLayoutComponent(mainMenuPanel, Config.getMainMenuScreen());
        cl.show(clPanel, Config.getLoginScreen());
        //TODO: remove again, but use to see layout
        cl.show(clPanel, Config.getMainMenuScreen());

//        setResizable(false);

        //TODO: change,
        setSize(Config.getReplayWidth() * 2, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    //TODO: remove later
    public static void main(String[] args) {

        Screen screen = new Screen();
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
}
