package gui;

import sdk.Config;
import sdk.dto.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Customized JPanel which contains a table and button to use for deleting a game that has been created but not yet
 * played.
 */
public class DeleteGamePanel extends JPanel{

    //declaring instance variables
    private JTable deleteGameTable;
    private JScrollPane scrollPane;
    private JButton btnDeleteGame;
    private JLabel deleteGameHeader;
    private GameTableModel model;

    public DeleteGamePanel(){

        setLayout(null);

        //initialising instance variables
        deleteGameTable = new JTable();
        scrollPane = new JScrollPane(deleteGameTable);
        btnDeleteGame = new JButton(Config.getBtnDeleteText());
        deleteGameHeader = new JLabel(Config.getBtnDeleteGameText());

        //setting bounds via the config file
        scrollPane.setBounds(Config.getDefaultXPosJComponent(), Config.getY3PosJComponent(),
                Config.getWidth2JComponent(), Config.getY5PosJComponent());

        btnDeleteGame.setBounds(Config.getDefaultXPosJComponent(), Config.getY8PosJComponent(),
                Config.getDefaultWidthJComponent(), Config.getDefaultHeightJComponent());

        deleteGameHeader.setBounds(Config.getDefaultXPosJComponent(), Config.getY1PosJComponent(),
                Config.getLblWidth(), Config.getDefaultHeightJComponent());

        //adding stuff to panel
        add(scrollPane);
        add(btnDeleteGame);
        add(deleteGameHeader);

    }

    public void addActionListeners(ActionListener l){

        btnDeleteGame.addActionListener(l);
    }

    public void setFonts(Font f){

        deleteGameHeader.setFont(f);
    }

    public void setTextColor(Color c){

        deleteGameHeader.setForeground(c);
    }

    public void setBackgroundColor(Color c){

        setBackground(c);
    }

    /**
     * Takes an list of games and uses it to populate the table, via the GameTableModel class
     * @param games
     */
    public void setDeleteGameTableModel(ArrayList<Game> games){

        model = new GameTableModel(games);
        deleteGameTable.setModel(model);
    }

    /**
     * returns the selected game from the table
     * @return
     */
    public Game getGameToDelete(){

        return model.getGameFromTable(deleteGameTable.getSelectedRow());
    }
}
