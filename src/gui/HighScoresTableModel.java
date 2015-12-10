package gui;

import sdk.Config;
import sdk.dto.Score;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * A model for showing scores in a table. To be used by the high scores panel. The getValueAt method sets the column
 * values in the table.
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

        if (highScores != null) {
            numberOfRows = highScores.size();

            return numberOfRows;
        }

        return 0;
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public Object getValueAt(int row, int column) {

        if (highScores != null) {
            Score score = highScores.get(row);

            switch (column) {

                case Config.USERNAME:
                    return score.getGame().getWinner().getUsername();

                case Config.SCORE:
                    return score.getScore();

                case Config.GAME_NAME:
                    return score.getGame().getName();

                case Config.GAME_ID:
                    return score.getGame().getGameId();
            }
        }

        return null;
    }

    /**
     * Returns the score object for the specified row. Can be used to get a user-selected score.
     * @param row
     * @return
     */
    public Score getHighscoreFromTable(int row){

        return highScores.get(row);
    }
}
