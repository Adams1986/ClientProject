package gui;

import sdk.Config;
import sdk.dto.Score;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ADI on 31-10-2015.
 */
public class HighScoresTableModel extends AbstractTableModel {

    private List<Score> highScores;
    private int numberOfRows;
    private String [] columns;

    public HighScoresTableModel (ArrayList<Score> highScores){

        this.highScores = highScores;
        columns = Config.getColumnNameHighScoresTable();
        //Winner and opponent redundant in the case of join game
        fireTableStructureChanged();
    }



    @Override
    public String getColumnName(int column) {
        return columns[column];
    }

    @Override
    public Class<?> getColumnClass(int column) {
        return super.getColumnClass(column);
    }

    @Override
    public int getRowCount() {

        numberOfRows
                = highScores.size();

        return numberOfRows;
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public Object getValueAt(int row, int column) {

        Score score = highScores.get(row);

        //TODO: change for better scaling?
        switch (column){

            case 0:
                return score.getGame().getWinner().getUsername();

            case 1:
                return score.getScore();

            case 2:
                return score.getGame().getGameId();

            case 3:
                return score.getGame().getName();

        }

        return null;
    }

    public Score getHighscoreFromTable(int row){

        return highScores.get(row);
    }
}
