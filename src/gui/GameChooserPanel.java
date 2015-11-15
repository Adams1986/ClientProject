package gui;

import sdk.Config;
import sdk.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by ADI on 06-11-2015.
 */
public class GameChooserPanel extends JPanel {

    private JTable gamesToJoinTable;
    private JScrollPane scrollPane;
    private JButton btnJoinSelectedGame;
    private JButton btnCreateNewGame;
    private JButton btnRefreshJTable;
    private JLabel gameChooserHeader;
    private JComboBox <String> gameType;
    private GameTableModel tableModel;
    private String[] typesOfGames;

    public GameChooserPanel(){

        setLayout(null);

        gamesToJoinTable = new JTable();
        btnJoinSelectedGame = new JButton(Config.getBtnJoinSelectedGameText());
        btnCreateNewGame = new JButton(Config.getBtnCreateNewGameText());
        btnRefreshJTable = new JButton(Config.getBtnRefreshText());
        gameChooserHeader = new JLabel(Config.getGameChooserHeaderText());
        gameType = new JComboBox<>();
        typesOfGames = Config.getTypesOfGames();

        scrollPane = new JScrollPane(gamesToJoinTable);

        btnCreateNewGame.setBounds(Config.getDefaultXPosJComponent(), Config.getY2PosJComponent(),
                Config.getDefaultWidthJComponent(), Config.getDefaultHeightJComponent());

        scrollPane.setBounds(Config.getDefaultXPosJComponent(), Config.getY3PosJComponent(),
                Config.getWidth2JComponent(), Config.getY5PosJComponent());

        gameType.setBounds(Config.getDefaultXPosJComponent(), Config.getY8PosJComponent(),
                Config.getDefaultWidthJComponent(), Config.getDefaultHeightJComponent());

        btnJoinSelectedGame.setBounds(Config.getDefaultXPosJComponent(), Config.getY9PosJComponent(),
                Config.getDefaultWidthJComponent(), Config.getDefaultHeightJComponent());

        btnRefreshJTable.setBounds(Config.getX1PosJComponent(), Config.getY8PosJComponent(),
                Config.getDefaultWidthJComponent(), Config.getDefaultHeightJComponent());

        gameChooserHeader.setBounds(Config.getDefaultXPosJComponent(), Config.getY1PosJComponent(),
                Config.getWidth2JComponent(), Config.getDefaultHeightJComponent());

        for (int i = 0; i < typesOfGames.length; i++) {

            gameType.addItem(typesOfGames[i]);
        }

        add(btnCreateNewGame);
        add(scrollPane);
        add(btnJoinSelectedGame);
        add(btnRefreshJTable);
        add(gameChooserHeader);
        add(gameType);


    }

    public String getTypeOfGameChoice (){

        return (String) gameType.getSelectedItem();
    }

    public void setFonts(Font f){

        gameChooserHeader.setFont(f);
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
