package gui;

import sdk.Config;
import sdk.dto.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.util.ArrayList;

/**
 * Created by ADI on 06-11-2015.
 * Contains JComponents for setting up a game, including a JComboBox to join either an open or invited game.
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

        gameType.setActionCommand(Config.getComboBoxActionCommand());

        add(btnCreateNewGame);
        add(scrollPane);
        add(btnJoinSelectedGame);

        //TODO: redundant right now. Could be useful if caching instead of direct server calls
        add(btnRefreshJTable);
        add(gameChooserHeader);
        add(gameType);


    }

    /**
     * Method to return the String from the selected item in the combo box. Used to determine which games list to get
     * from the server.
     * @return
     */
    public String getTypeOfGameChoice (){

        String selectedItem = (String) gameType.getSelectedItem();
        return selectedItem.replace(" ", "_");
    }

    public void setFonts(Font f){

        gameChooserHeader.setFont(f);
    }

    public void setTextColor(Color c){

        gameChooserHeader.setForeground(c);
    }

    public void setGameTableModel(ArrayList<Game> games){

        tableModel = new GameTableModel(games);
        gamesToJoinTable.setModel(tableModel);
    }

    /**
     * Gets the game which the user selected to join and play.
     * @return
     */
    public Game getGame(){

        return tableModel.getGameFromTable(gamesToJoinTable.getSelectedRow());
    }

    /**
     * Method for adding action listeners to the panel's buttons
     * @param l
     */
    public void addActionListeners(ActionListener l){

        btnCreateNewGame.addActionListener(l);
        btnJoinSelectedGame.addActionListener(l);
        btnRefreshJTable.addActionListener(l);
    }

    public void addItemListeners(ItemListener l){

        gameType.addItemListener(l);
    }

    /**
     * Method to reset state when user logs off and on
     */
    public void resetFields() {

        gameType.setSelectedIndex(Config.getIndexOne());

    }
}
