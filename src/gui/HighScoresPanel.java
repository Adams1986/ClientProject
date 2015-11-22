package gui;

import sdk.Config;
import sdk.dto.Score;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by simonadams on 20/11/15.
 */
public class HighScoresPanel extends JPanel {

    private JTable highScoresTable;
    private JLabel highScoresHeader;
    private JScrollPane scrollPane;
    private HighScoresTableModel tableModel;

    public HighScoresPanel(){

        setLayout(null);

        highScoresTable = new JTable();
        highScoresHeader = new JLabel(Config.getHighScoresHeaderText());
        scrollPane = new JScrollPane(highScoresTable);

        highScoresHeader.setBounds(Config.getDefaultXPosJComponent(), Config.getY1PosJComponent(),
                Config.getWidth2JComponent(), Config.getDefaultHeightJComponent());

        scrollPane.setBounds(Config.getDefaultXPosJComponent(), Config.getY3PosJComponent(),
                Config.getWidth2JComponent(), Config.getY5PosJComponent());

        add(highScoresHeader);
        add(scrollPane);
    }

    public void setFonts(Font f){

        highScoresHeader.setFont(f);
    }

    public void setTextColor(Color c){

        highScoresHeader.setForeground(c);
    }

    public void setHighScoreTableModel(ArrayList<Score> scores){

        tableModel = new HighScoresTableModel(scores);
        highScoresTable.setModel(tableModel);
    }

    public Score getHighScore(){

        return tableModel.getHighscoreFromTable(highScoresTable.getSelectedRow());
    }

}

