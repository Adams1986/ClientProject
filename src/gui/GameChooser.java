package gui;

import sdk.Config;
import sdk.Game;

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
    private JButton btnRefreshJTable;
    private GameTableModel tableModel;

    public GameChooser (){

        setLayout(null);

        gamesToJoinTable = new JTable();
        btnJoinSelectedGame = new JButton(Config.getBtnJoinSelectedGameText());
        btnCreateNewGame = new JButton(Config.getBtnCreateNewGameText());
        btnRefreshJTable = new JButton(Config.getBtnRefreshText());

        scrollPane = new JScrollPane(gamesToJoinTable);

        btnCreateNewGame.setBounds(Config.getDefaultXPosJComponent(), Config.getY1PosJComponent(),
                Config.getDefaultWidthJComponent(), Config.getDefaultHeightJComponent());

        scrollPane.setBounds(Config.getDefaultXPosJComponent(), Config.getY2PosJComponent(),
                Config.getWidth2JComponent(), Config.getY5PosJComponent());

        btnJoinSelectedGame.setBounds(Config.getDefaultXPosJComponent(), Config.getY7PosJComponent(),
                Config.getDefaultWidthJComponent(), Config.getDefaultHeightJComponent());

        btnRefreshJTable.setBounds(Config.getX1PosJComponent(), Config.getY7PosJComponent(),
                Config.getDefaultWidthJComponent(), Config.getDefaultHeightJComponent());

        add(btnCreateNewGame);
        add(scrollPane);
        add(btnJoinSelectedGame);
        add(btnRefreshJTable);


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
        btnRefreshJTable.addActionListener(l);
    }
}
