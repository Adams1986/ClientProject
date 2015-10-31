package gui;

import sdk.Config;
import sdk.Game;
import sdk.User;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ADI on 31-10-2015.
 */
public class GameTableModel extends AbstractTableModel {

    private List<Game> gameList;
    private int numberOfRows;
    private String [] columns;

    public GameTableModel (ArrayList<Game> games){

        gameList = games;
        //columns = new String []{"Host", "Opponent", "Game name", "Game status", "Created", "Winner", "Map size"};
        columns = Config.getColumnNamesGameTable();
        fireTableStructureChanged();
    }



    @Override
    public String getColumnName(int column) {
        return columns[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return super.getColumnClass(columnIndex);
    }

    @Override
    public int getRowCount() {

        numberOfRows = gameList.size();

        return numberOfRows;
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public Object getValueAt(int row, int column) {

        Game game = gameList.get(row);

        switch (column){

            case 0:
                return game.getHost().getUsername();

            case 1:
                return game.getOpponent().getUsername();

            case 2:
                return game.getName();

            case 3:
                return game.getStatus();

            case 4:
                return game.getCreated();

            case 5:
                return game.getWinner().getUsername();

            case 6:
                return game.getMapSize();

        }

        return null;
    }

    public Game getGameFromTable(int row){

        return gameList.get(row);
    }
}
