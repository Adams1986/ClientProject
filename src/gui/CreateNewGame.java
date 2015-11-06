package gui;

import sdk.Config;
import sdk.User;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by ADI on 05-11-2015.
 */
public class CreateNewGame extends JPanel {

    private JButton btnSend;
    private JTable opponentTable;
    private JTextField gameNameField;
    private JScrollPane scrollPane;
    private UserTableModel tableModel;
    private JComboBox<Integer>mapSizeChooser;
    private JCheckBox openGame;
    private int[] mapSizes = {9, 15, 25};


    public CreateNewGame(){

        setLayout(null);

        gameNameField = new JTextField();
        btnSend = new JButton("Send challenge");
        mapSizeChooser = new JComboBox<>();
        openGame = new JCheckBox("Open game");

        opponentTable = new JTable();
        scrollPane = new JScrollPane(opponentTable);

        gameNameField.setBounds(40, 340, 150, 30);
        btnSend.setBounds(40, 400, 150, 30);
        scrollPane.setBounds(40, 40, 500, 260);
        mapSizeChooser.setBounds(40, 460, 150, 30);
        openGame.setBounds(40, 520, 150, 30);

        for (int i = 0; i < mapSizes.length; i++){
            mapSizeChooser.addItem(mapSizes[i]);
        }
        gameNameField.setToolTipText("Give your game a name");
        openGame.setSelected(true);
        setOpponentTableState(false);

        add(gameNameField);
        add(btnSend);
        add(scrollPane);
        add(mapSizeChooser);
        add(openGame);
    }

    public void setOpponentTableState(boolean b) {

        opponentTable.setEnabled(b);
    }

    public boolean getOpenGameChoice (){

        return openGame.isSelected();
    }

    public int getMapSize(){

        return (int) mapSizeChooser.getSelectedItem();
    }

    public String getGameNameText(){

        return gameNameField.getText();
    }

    public void setOpponentTableModel(ArrayList<User> users){

        tableModel = new UserTableModel(users);
        opponentTable.setModel(tableModel);
    }

    public User getOpponent(){

        return tableModel.getUserFromTable(opponentTable.getSelectedRow());
    }

    public void addActionListeners (ActionListener l){

        btnSend.addActionListener(l);
        openGame.addActionListener(l);
    }

    public void setGameNameField(String text) {
        gameNameField.setText(text);
    }

    public void requestFocusGameNameField() {

        gameNameField.requestFocus();
    }

    public void resetFields() {

        gameNameField.setText("");
        mapSizeChooser.setSelectedIndex(0);
        openGame.setSelected(true);
        setOpponentTableState(false);
    }

}
