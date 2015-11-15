package gui;

import sdk.Config;
import sdk.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by ADI on 05-11-2015.
 */
public class CreateNewGamePanel extends JPanel {

    private JLabel headerLabel;
    private JButton btnSend;
    private JTable opponentTable;
    private JTextField gameNameField;
    private JScrollPane scrollPane;
    private UserTableModel tableModel;
    private JComboBox<Integer>mapSizeChooser;
    private JCheckBox openGame;
    private JLabel lblOpenGame;
    private int[] mapSizes;
    //TODO: label for the table


    public CreateNewGamePanel(){

        setLayout(null);

        //headerLabel = new JLabel(Config.getCreateNewGameHeaderText());
        gameNameField = new JTextField();
        btnSend = new JButton(Config.getBtnSendText());
        mapSizeChooser = new JComboBox<>();
        openGame = new JCheckBox();
        lblOpenGame = new JLabel(Config.getLblOpenGameText());
        headerLabel = new JLabel(Config.getLblCreateNewGameText());

        opponentTable = new JTable();
        scrollPane = new JScrollPane(opponentTable);
        mapSizes = Config.getMapSizes();

        headerLabel.setBounds(Config.getDefaultXPosJComponent(), Config.getY1PosJComponent(),
                Config.getWidth3JComponent(), Config.getDefaultHeightJComponent());

        gameNameField.setBounds(Config.getDefaultXPosJComponent(), Config.getY9PosJComponent(),
                Config.getDefaultWidthJComponent(), Config.getDefaultHeightJComponent());

        btnSend.setBounds(Config.getDefaultXPosJComponent(), Config.getY11PosJComponent(),
                Config.getDefaultWidthJComponent(), Config.getDefaultHeightJComponent());

        scrollPane.setBounds(Config.getDefaultXPosJComponent(), Config.getY3PosJComponent(),
                Config.getWidth2JComponent(), Config.getY5PosJComponent());

        mapSizeChooser.setBounds(Config.getDefaultXPosJComponent(), Config.getY10PosJComponent(),
                Config.getDefaultWidthJComponent(), Config.getDefaultHeightJComponent());

        openGame.setBounds(Config.getX1PosJComponent(), Config.getY8PosJComponent(),
                Config.getDefaultWidthJComponent(), Config.getDefaultHeightJComponent());

        lblOpenGame.setBounds(Config.getDefaultXPosJComponent(), Config.getY8PosJComponent(),
                Config.getDefaultWidthJComponent(), Config.getDefaultHeightJComponent());

        for (int i = 0; i < mapSizes.length; i++){
            mapSizeChooser.addItem(mapSizes[i]);
        }
        gameNameField.setToolTipText(Config.getGameNameFieldTtt());
        mapSizeChooser.setToolTipText(Config.getMapSizeChooserTtt());
        openGame.setSelected(true);
        setOpponentTableState(false);

        add(gameNameField);
        add(btnSend);
        add(scrollPane);
        add(mapSizeChooser);
        add(openGame);
        add(lblOpenGame);
        add(headerLabel);
    }

    public void setFonts(Font f){

        headerLabel.setFont(f);
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

    //TODO: hardcoding?
    public void resetFields() {

        gameNameField.setText(Config.getClearField());
        mapSizeChooser.setSelectedIndex(Config.getIndexOne());
        openGame.setSelected(true);
        setOpponentTableState(false);
    }

}
