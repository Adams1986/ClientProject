package gui;

import sdk.Config;
import sdk.dto.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by ADI on 15-11-2015.
 */
public class GameOverviewerPanel extends JPanel {

    private JLabel gameOverviewerHeader;
    private JButton btnReplayGame;
    private JButton btnRefreshJTable;
    private JTable gamesToReplayTable;
    private JScrollPane scrollPane;
    private JComboBox<String> gameType;
    private GameTableModel tableModel;
    private String[] typesOfGames;


    public GameOverviewerPanel(){

        setLayout(null);

        gameOverviewerHeader = new JLabel(Config.getGameOverviewerHeaderText());
        btnReplayGame = new JButton(Config.getBtnReplayText());
        btnRefreshJTable = new JButton(Config.getBtnRefreshText());
        gamesToReplayTable = new JTable();
        gameType = new JComboBox<>();
        typesOfGames = Config.getTypesOfGamesToReplay();
        scrollPane = new JScrollPane(gamesToReplayTable);

        gameOverviewerHeader.setBounds(Config.getDefaultXPosJComponent(), Config.getY1PosJComponent(),
                Config.getWidth2JComponent(), Config.getDefaultHeightJComponent());

        scrollPane.setBounds(Config.getDefaultXPosJComponent(), Config.getY3PosJComponent(),
                Config.getWidth2JComponent(), Config.getY5PosJComponent());

        gameType.setBounds(Config.getDefaultXPosJComponent(), Config.getY8PosJComponent(),
                Config.getDefaultWidthJComponent(), Config.getDefaultHeightJComponent());

        btnReplayGame.setBounds(Config.getDefaultXPosJComponent(), Config.getY9PosJComponent(),
                Config.getDefaultWidthJComponent(), Config.getDefaultHeightJComponent());

        btnRefreshJTable.setBounds(Config.getX1PosJComponent(), Config.getY8PosJComponent(),
                Config.getDefaultWidthJComponent(), Config.getDefaultHeightJComponent());

        for (int i = 0; i < typesOfGames.length; i++) {

            gameType.addItem(typesOfGames[i]);

        }

        add(gameOverviewerHeader);
        add(btnReplayGame);
        add(btnRefreshJTable);
        add(gameType);
        add(scrollPane);
    }

    public String getTypeOfGameChoice (){

        return (String) gameType.getSelectedItem();
    }

    public void setFonts(Font f){

        gameOverviewerHeader.setFont(f);
    }

    public void setTextColor(Color c){

        gameOverviewerHeader.setForeground(c);
    }

    public void setGameTableModel(ArrayList<Game> games){

        tableModel = new GameTableModel(games);
        gamesToReplayTable.setModel(tableModel);
    }

    public Game getGame(){

        return tableModel.getGameFromTable(gamesToReplayTable.getSelectedRow());
    }

    public void addActionListeners(ActionListener l){

        btnReplayGame.addActionListener(l);
        btnRefreshJTable.addActionListener(l);
    }

    public void resetFields() {

        gameType.setSelectedIndex(Config.getIndexOne());
    }
}
