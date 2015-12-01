package gui;

import sdk.Config;
import sdk.dto.Gamer;
import sdk.dto.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by ADI on 05-11-2015.
 * Customized JPanel to help create a new game. Contains a table that will list all the users to invite.
 */
public class CreateNewGamePanel extends JPanel {

    private JLabel headerLabel;
    private JButton btnSend;
    private JTable opponentTable;
    private JTextField gameNameField;
    private JScrollPane scrollPane;
    private UserTableModel tableModel;
    private JComboBox<Integer> mapSizeChooser;
    private JCheckBox openGame;
    private JLabel lblOpenGame;
    private int[] mapSizes;


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

        //placing components in the panel with params (x-pos, y-pos, width, height)
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

        //adding items from String array to the combo box
        for (int i = 0; i < mapSizes.length; i++){
            mapSizeChooser.addItem(mapSizes[i]);
        }

        //adding tool tip text to text field and combo box to inform the user what components are used for
        gameNameField.setToolTipText(Config.getGameNameFieldTtt());
        mapSizeChooser.setToolTipText(Config.getMapSizeChooserTtt());

        //mapSizeChooser.setSize(new Dimension(Config.getDefaultWidthJComponent(), Config.getDefaultHeightJComponent()));
        openGame.setSelected(true);
        setOpponentTableState(false);

        //adding components to the panel
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

    public void setTextColor(Color c){

        headerLabel.setForeground(c);
    }

    public void setTextColorInfoLabels(Color c){

        lblOpenGame.setForeground(c);
    }

    //method done for Windows specifically as otherwise the background of the check box would be white (standard)
    public void setOpenGameBackGround(Color c){

        openGame.setBackground(c);
    }

    /**
     * Handles whether table can be used. If check box is ticked, the table should be disabled as there is no opponent
     * in this kind of a game (open game)
     * @param b
     */
    public void setOpponentTableState(boolean b) {

        opponentTable.setEnabled(b);
    }

    /**
     * Returns true if check box is selected. If it is not selected the state of the table should be enabled
     * @return
     */
    public boolean getOpenGameChoice (){

        return openGame.isSelected();
    }

    /**
     * Returns the selected choice of the map size combo box. Used to set the size of the game board.
     * @return
     */
    public int getMapSize(){

        return (int) mapSizeChooser.getSelectedItem();
    }

    /**
     * Used to create a game with the typed name
     * @return
     */
    public String getGameNameText(){

        return gameNameField.getText();
    }

    public void setOpponentTableModel(ArrayList<User> users){

        tableModel = new UserTableModel(users);
        opponentTable.setModel(tableModel);
    }

    /**
     * Returns the selected opponent's user object
     * @return
     */
    public User getOpponent(){

        return tableModel.getUserFromTable(opponentTable.getSelectedRow());
    }

    /**
     * Adds action listeners to buttons
     * @param l
     */
    public void addActionListeners (ActionListener l){

        btnSend.addActionListener(l);
        openGame.addActionListener(l);
    }

    /**
     * Gets the focus of the game name text field. Used if user forgets to give the game a name
     */
    public void requestFocusGameNameField() {

        gameNameField.requestFocus();
    }

    /**
     * Resets the panel's input fields. Method will be called when the new game starts and when logging on and off
     */
    public void resetFields() {

        gameNameField.setText(Config.getClearField());
        mapSizeChooser.setSelectedIndex(Config.getIndexOne());
        openGame.setSelected(true);
        setOpponentTableState(false);
    }

}
