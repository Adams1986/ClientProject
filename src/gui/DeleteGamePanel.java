package gui;

import sdk.Config;
import sdk.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by simonadams on 14/11/15.
 */
public class DeleteGamePanel extends JPanel{

    private JTable deleteGameTable;
    private JScrollPane scrollPane;
    private JButton btnDeleteGame;
    private JLabel deleteGameHeader;
    private GameTableModel model;

    public DeleteGamePanel(){

        setLayout(null);

        deleteGameTable = new JTable();
        scrollPane = new JScrollPane(deleteGameTable);
        btnDeleteGame = new JButton(Config.getBtnDeleteText());
        deleteGameHeader = new JLabel(Config.getBtnDeleteGameText());

        scrollPane.setBounds(Config.getDefaultXPosJComponent(), Config.getY2PosJComponent(),
                Config.getWidth2JComponent(), Config.getY5PosJComponent());

        btnDeleteGame.setBounds(Config.getDefaultXPosJComponent(), Config.getY7PosJComponent(),
                Config.getDefaultWidthJComponent(), Config.getDefaultHeightJComponent());

        deleteGameHeader.setBounds(Config.getDefaultXPosJComponent(), Config.getY1PosJComponent(),
                Config.getLblWidth(), Config.getDefaultHeightJComponent());

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

    public void setDeleteGameTableModel(ArrayList<Game> games){

        model = new GameTableModel(games);
        deleteGameTable.setModel(model);
    }

    public Game getGameToDelete(){

        return model.getGameFromTable(deleteGameTable.getSelectedRow());
    }
}
