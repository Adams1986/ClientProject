package gui;

import sdk.Config;
import sdk.dto.Score;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by simonadams on 22/11/15.
 */
public class HighScoresMovingPanel extends JPanel{

    private Timer tm;
    private int x;
    private int y;
    private ArrayList<Score> highScores;

    public HighScoresMovingPanel(ArrayList<Score> highScores, ActionListener l){

        tm = new Timer(Config.getDelayMovingPanel(), l);
        this.highScores = highScores;
        setBackground(Color.BLACK);

        x = 20;
        y = Config.getStartYMovingPanel();

        tm.start();


    }


    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        String champ = "No one";
        int champScore = 0;

        String noTwo = "No one";
        int noTwoScore = 0;

        String noThree = "No one";
        int noThreeScore = 0;

        try {
            champ = highScores.get(Config.getIndexOne()).getGame().getWinner().getUsername();
            champScore = highScores.get(Config.getIndexOne()).getScore();


            noTwo = highScores.get(Config.getIndexTwo()).getGame().getWinner().getUsername();
            noTwoScore = highScores.get(Config.getIndexTwo()).getScore();


            noThree = highScores.get(Config.getIndexThree()).getGame().getWinner().getUsername();
            noThreeScore = highScores.get(Config.getIndexThree()).getScore();

        }
        catch (IndexOutOfBoundsException e) {}

        g.setColor(Color.GREEN);
        g.setFont(new Font(Config.getHeaderFont(), Font.BOLD, Config.getHSMovingTextSize()));
        g.drawString(Config.getHighScoresMovingText(), x, y);
        g.setColor(Color.YELLOW);
        g.drawString("1. " + champ + ": " + champScore, x, y+40);
        g.setColor(Color.LIGHT_GRAY);
        g.drawString("2. " + noTwo + ": " + noTwoScore, x, y+70);
        g.setColor(Color.ORANGE);
        g.drawString("3. " + noThree + ": " + noThreeScore, x, y+100);


    }

    public int getYCoord() {
        return y;
    }

    public void setYCoord(int y) {
        this.y = y;
    }

    public void setHighScores(ArrayList<Score> highScores) {
        this.highScores = highScores;
    }

    public void stopTimer(){

        tm.stop();
    }
}
