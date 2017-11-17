/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


/**
 *
 * @author user
 */
public class ScoreBoard{
    private int score;
    private String highscore;
    private boolean status;
    private long start,stop;
    private BufferedWriter writer;
    private BufferedReader reader;

    public ScoreBoard() {
        this.highscore="0";
    }

    public boolean isStatus() {
        return status;
    }

    public double getScore() {
        return score;
    }
    
    public void start(){
        start=System.currentTimeMillis();
        this.status=true;
        this.score=0;
        try {
            reader = new BufferedReader(new FileReader("highScore.txt"));
            if(reader.readLine()!=null){
                this.highscore = reader.readLine();
            }
        } 
        catch (FileNotFoundException ex) {
            ex.getMessage();
        } 
        catch (IOException ex) {
            ex.getMessage();
        }
    }
    public void update(){
        stop=System.currentTimeMillis();
        if((stop-start)%100==0){
            this.score += 1;
        }
//        if(Integer.parseInt(highscore)< score){
//            highscore = score+"";
//            try {
//            writer = new BufferedWriter(new FileWriter("highScore.txt"));
//            writer.write(highscore);
//            writer.flush();
//            writer.close();
//            } 
//            catch (IOException ex) {
//                ex.getMessage();
//            }
//        }
            
    }  
}
