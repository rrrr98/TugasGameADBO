/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Node;
import engine.Tester;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 
 * @author Ketua : Kevin R
 */
public class ScoreBoard {
    //attribute 
    private float score;
    private String highscore;
    private boolean status;
    private long start, stop;
    private BufferedWriter writer;
    private BufferedReader reader;
    private Node guiNode;
    private BitmapFont guiFont;
    private BitmapText scoreText, hiscoreText;
    private static ScoreBoard instance;

    /**
     * constructor singleton
     */
    private ScoreBoard() {
        score = 0;
    }

    /**
     * get instance singleton
     * @return instance
     */
    public static ScoreBoard getInstance() {
        if (instance == null) {
            instance = new ScoreBoard();
        }
        return instance;
    }

    /**
     * inisialisasi class singleton
     * @param guiNodes 
     * @param guiFont 
     */
    public void init(Node guiNodes, BitmapFont guiFont) {
        this.guiNode = guiNodes;
        this.guiFont = guiFont;
        try {
            reader = new BufferedReader(new FileReader("highScore.txt"));
            this.highscore = reader.readLine();
        } catch (FileNotFoundException ex) {
            ex.getMessage();
        } catch (IOException ex) {
            ex.getMessage();
        }
    }

    /**
     * apakah status true / false
     * @return status
     */
    public boolean isStatus() {
        return status;
    }

    /**
     * mendapatkan highscore
     * @return highscore
     */
    public String getHighscore() {
        return highscore;
    }

    /**
     * setter status
     * @param status 
     */
    public void setStatus(boolean status) {
        this.status = status;
    }

    /**
     * getter score
     * @return score
     */
    public float getScore() {
        return score;
    }

    /**
     * awal start scoreboard
     */
    public void start() {
        this.status = true;
        this.score = 0;
    }

    /**
     * update scoreboard ke file
     * @param tpf 
     */
    public void update(float tpf) {
        this.score += 5 * tpf;
        int temp = Integer.parseInt(this.highscore);
        if (temp < (int)this.score) {
            this.highscore = (int)this.score + "";
            try {
                writer = new BufferedWriter(new FileWriter("highScore.txt"));
                writer.write(highscore);
                writer.flush();
                writer.close();
            } catch (IOException ex) {
                ex.getMessage();
            }
        }
        scoreText.setText("Score: " + (int)this.getScore());
        hiscoreText.setText("HighScore: " + this.getHighscore());
        this.hiscoreText.setColor(ColorRGBA.White);
        this.hiscoreText.setSize(guiFont.getCharSet().getRenderedSize());
        hiscoreText.setLocalTranslation(30, Tester.SCREEN_HEIGHT - scoreText.getLineHeight(), 0);

    }

    /**
     * load scoreboard
     */
    public void loadScoreBoard() {
        scoreText = new BitmapText(guiFont, false);
        guiNode.attachChild(scoreText);
        scoreText.setLocalTranslation(30, Tester.SCREEN_HEIGHT - scoreText.getLineHeight() * 2, 0);
        hiscoreText = new BitmapText(guiFont, false);
        guiNode.attachChild(hiscoreText);
        hiscoreText.setLocalTranslation(30, Tester.SCREEN_HEIGHT - scoreText.getLineHeight(), 0);
    }

    /**
     * setter score text
     * @param x 
     */
    public void setScoreText(String x) {
        this.scoreText.setText(x);
    }

    /**
     * setter highscore text
     * @param x 
     */
    public void setHiscoreText(String x) {
        this.hiscoreText.setText(x);
    }

    /**
     * setter highscore location
     */
    public void setHiscoreTextLocation() {
        this.hiscoreText.setLocalTranslation(Tester.SCREEN_WIDTH / 2 - this.hiscoreText.getLineWidth() / 2 - 20, Tester.SCREEN_HEIGHT / 2 - this.hiscoreText.getLineHeight() / 2, 0);
        this.hiscoreText.setColor(ColorRGBA.Red);
        this.hiscoreText.setSize(guiFont.getCharSet().getRenderedSize() + 20);
    }

}
