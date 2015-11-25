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

        g.setColor(Color.GREEN);
        g.setFont(new Font(Config.getHeaderFont(), Font.BOLD, Config.getHSMovingTextSize()));
        g.drawString(Config.getHighScoresMovingText(), x, y);
        g.setColor(Color.YELLOW);
        g.drawString("1. "+highScores.get(Config.getIndexOne()).getGame().getWinner().getUsername()+": "
                + highScores.get(Config.getIndexOne()).getScore(), x, y+40);
        g.setColor(Color.LIGHT_GRAY);
        g.drawString("2. "+highScores.get(Config.getIndexTwo()).getGame().getWinner().getUsername()+": "
                + highScores.get(Config.getIndexTwo()).getScore(), x, y+70);
        g.setColor(Color.ORANGE);
        g.drawString("3. "+highScores.get(Config.getIndexThree()).getGame().getWinner().getUsername()+": "
                + highScores.get(Config.getIndexThree()).getScore(), x, y+100);


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
