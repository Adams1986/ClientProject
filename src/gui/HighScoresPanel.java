package gui;

import sdk.Config;
import sdk.dto.Game;
import sdk.dto.Score;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by simonadams on 20/11/15.
 */
public class HighScoresPanel extends JPanel {

    private JTable highScoresTable;
    private JLabel highScoresHeader;
    private JScrollPane scrollPane;
    private HighScoresTableModel tableModel;
    private JButton btnReplay;

    public HighScoresPanel(){

        setLayout(null);

        highScoresTable = new JTable();
        highScoresHeader = new JLabel(Config.getHighScoresHeaderText());
        scrollPane = new JScrollPane(highScoresTable);
        btnReplay = new JButton(Config.getBtnReplayText());

        highScoresHeader.setBounds(Config.getDefaultXPosJComponent(), Config.getY1PosJComponent(),
                Config.getWidth2JComponent(), Config.getDefaultHeightJComponent());

        scrollPane.setBounds(Config.getDefaultXPosJComponent(), Config.getY3PosJComponent(),
                Config.getWidth2JComponent(), Config.getY5PosJComponent());

        btnReplay.setBounds(Config.getDefaultXPosJComponent(), Config.getY8PosJComponent(),
                Config.getDefaultWidthJComponent(), Config.getDefaultHeightJComponent());

        add(highScoresHeader);
        add(scrollPane);
        add(btnReplay);
    }

    public void setFonts(Font f){

        highScoresHeader.setFont(f);
    }

    public void setTextColor(Color c){

        highScoresHeader.setForeground(c);
    }

    public void addActionListeners(ActionListener l){

        btnReplay.addActionListener(l);
    }

    public void setHighScoreTableModel(ArrayList<Score> scores){

        tableModel = new HighScoresTableModel(scores);
        highScoresTable.setModel(tableModel);
    }

    public Score getHighScore() {

        return tableModel.getHighscoreFromTable(highScoresTable.getSelectedRow());
    }

    public Game getGameFromHighScore(){

        return tableModel.getHighscoreFromTable(highScoresTable.getSelectedRow()).getGame();
    }
}

