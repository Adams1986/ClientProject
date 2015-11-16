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

        numberOfRows = userList.size();

        return numberOfRows;
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public Object getValueAt(int row, int column) {

        User user = userList.get(row);

        switch (column){

            case 0:
                return user.getFirstName();

            case 1:
                return user.getLastName();

            case 2:
                return user.getUsername();
        }

        return null;
    }

    public User getUserFromTable(int row){

        return userList.get(row);
    }
}
