/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.StateController;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;
import engine.Tester;
/**
 *
 * @author Ketua : Kevin R
 */
public class MyStartScreen implements ScreenController {
    // attribute
    private Nifty nifty;
    private Tester tester;
    private static MyStartScreen instance;
    /**
     * constructor
     */
    private MyStartScreen() {
    }
    /**
     * getter instance singleton
     * @return instance
     */
    public static MyStartScreen getInstance() {
        if (instance == null) {
            instance = new MyStartScreen();
        }
        return instance;
    }
    /**
     * init singleton
     * @param niftys 
     */
    public void init(Nifty niftys) {
        this.nifty=niftys;
    }
    
    /**
     * to start the game
     */
    public void startGame() {
       nifty.gotoScreen("hud");
       StateController.getInstance().restart();
       ScoreBoard.getInstance().start();
     }

    /**
     * quit the game
     */
    public void quitGame() {
       System.exit(0);
    }
    
    /**
     * to control menu
     */
    public void toControl() {
       nifty.gotoScreen("control");
    }
    /**
     * to exit menu
     */
    public void toExit() {
       nifty.gotoScreen("exit");
       ScoreBoard.getInstance().setScoreText("");
       ScoreBoard.getInstance().setStatus(false);
       ScoreBoard.getInstance().setHiscoreTextLocation();
       
    }
    
    /**
     * to about menu
     */
    public void toAbout() {
       nifty.gotoScreen("about");
    }
    
    /**
     * to start menu
     */
    public void toStart() {
       ScoreBoard.getInstance().setStatus(false);
       ScoreBoard.getInstance().setHiscoreText("");
       ScoreBoard.getInstance().setScoreText("");
       nifty.gotoScreen("start");
    }

    @Override
    public void bind(Nifty nifty, Screen screen) {
        //unused
    }

    @Override
    public void onStartScreen() {
        //unused
    }

    @Override
    public void onEndScreen() {
        //unused
    }
}