package gui;

import sdk.Config;

import javax.swing.*;
import java.awt.*;

/**
 * Created by ADI on 24-10-2015.
 */
public class Screen extends JFrame{

    private JPanel contentPane;

    public Screen(){

        super("Snake");
        Config.init();



        contentPane = new JPanel();
        setContentPane(contentPane);

        LoginPanel loginPanel = new LoginPanel();
        contentPane.add(BorderLayout.CENTER, loginPanel);

        setSize(Config.getReplayWidth()*2, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {

        Screen screen = new Screen();
    }
}
