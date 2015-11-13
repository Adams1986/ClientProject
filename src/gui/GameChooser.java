package gui;

import sdk.Config;
import sdk.Game;
import sdk.User;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by ADI on 06-11-2015.
 */
public class GameChooser extends JPanel {

    private JTable gamesToJoinTable;
    private JScrollPane scrollPane;
    private JButton btnJoinSelectedGame;
    private JButton btnCreateNewGame;
    private GameTableModel tableModel;

    public GameChooser (){

        setLayout(null);

        gamesToJoinTable = new JTable();
        btnJoinSelectedGame = new JButton(Config.getBtnJoinSelectedGameText());
        btnCreateNewGame = new JButton(Config.getBtnCreateNewGameText());

        scrollPane = new JScrollPane(gamesToJoinTable);

        btnCreateNewGame.setBounds(Config.getDefaultXPosJComponent(), Config.getY1PosJComponent(),
                Config.getDefaultWidthJComponent(), Config.getDefaultHeightJComponent());

        scrollPane.setBounds(Config.getDefaultXPosJComponent(), Config.getY2PosJComponent(),
                Config.getWidth2JComponent(), Config.getY5PosJComponent());

        btnJoinSelectedGame.setBounds(Config.getDefaultXPosJComponent(), Config.getY7PosJComponent(),
                Config.getDefaultWidthJComponent(), Config.getDefaultHeightJComponent());

        add(btnCreateNewGame);
        add(scrollPane);
        add(btnJoinSelectedGame);


    }

    public void setGameTableModel(ArrayList<Game> games, String[] columns){

        tableModel = new GameTableModel(games, columns);
        gamesToJoinTable.setModel(tableModel);
    }

    public Game getGame(){

        return tableModel.getGameFromTable(gamesToJoinTable.getSelectedRow());
    }

    public void addActionListeners(ActionListener l){

        btnCreateNewGame.addActionListener(l);
        btnJoinSelectedGame.addActionListener(l);
    }
}
