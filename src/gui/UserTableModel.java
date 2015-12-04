package gui;

import sdk.Config;
import sdk.dto.Gamer;
import sdk.dto.User;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ADI on 31-10-2015.
 */
public class UserTableModel extends AbstractTableModel {

    private List<User> userList;
    private int numberOfRows;
    private String [] columns;
//    private static final int FIRST_NAME = Config.getIndexOne();
//    private static final int LAST_NAME = Config.getIndexTwo();
//    private static final int USERNAME = Config.getIndexThree();

    public UserTableModel (ArrayList<User> users){

        userList = users;
        columns = Config.getColumnNamesUserTable();

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

        if (userList != null) {
            numberOfRows = userList.size();

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

        if (userList != null) {
            User user = userList.get(row);

            switch (column) {

                case Config.USERNAME:
                    return user.getUsername();

                case Config.FIRST_NAME:
                    return user.getFirstName();

                case Config.LAST_NAME:
                    return user.getLastName();

                case Config.TOTAL_SCORE:
                    return user.getTotalScore();
            }
        }
        return null;
    }

    public User getUserFromTable(int row){

        return userList.get(row);
    }
}
