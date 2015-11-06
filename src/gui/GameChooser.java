package gui;

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
        btnJoinSelectedGame = new JButton("Join selected game");
        btnCreateNewGame = new JButton("Create a new game");

        scrollPane = new JScrollPane(gamesToJoinTable);

        btnCreateNewGame.setBounds(40, 40, 150, 30);
        scrollPane.setBounds(40, 100, 550, 260);
        btnJoinSelectedGame.setBounds(40, 400, 150, 30);

        add(btnCreateNewGame);
        add(scrollPane);
        add(btnJoinSelectedGame);


    }

    public void setGameTableModel(ArrayList<Game> games){

        tableModel = new GameTableModel(games);
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
