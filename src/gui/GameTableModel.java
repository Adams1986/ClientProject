package gui;

import sdk.Config;
import sdk.dto.Game;

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
        columns = Config.getColumnNamesGameTable();
        //Winner and opponent redundant in the case of join game
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

        if (gameList != null) {

            numberOfRows = gameList.size();

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

        if (gameList != null) {
            Game game = gameList.get(row);

            switch (column) {

                case Config.HOST:
                    return game.getHost().getUsername();

                case Config.OPPONENT:
                    return game.getOpponent().getUsername();

                case Config.GAME_NAME:
                    return game.getName();

                case Config.GAME_STATUS:
                    return game.getStatus();

                case Config.GAME_CREATED:
                    return game.getCreated();

                case Config.WINNER_USERNAME:
                    return game.getWinner().getUsername();

                case Config.MAP_SIZE:
                    return game.getMapSize();

            }
        }

        return null;
    }

    public Game getGameFromTable(int row){

        return gameList.get(row);
    }

    public void removeGame(int row) {

        gameList.remove(row);
    }
}
