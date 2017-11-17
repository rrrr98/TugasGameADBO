/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.font.BitmapText;
import com.jme3.scene.Node;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

public class MyStartScreen implements ScreenController {
    Nifty nifty;
    ScoreBoard nscore;

    MyStartScreen(Nifty niftys,ScoreBoard nScore) {
        
        nifty=niftys;
        nscore =nScore;
        
    }

    public void startGame() {
       nifty.gotoScreen("hud");
       nscore.start();
     }

    public void quitGame() {
       System.exit(0);
    }
    
    public void toControl() {
       nifty.gotoScreen("control");
    }
    
    public void toAbout() {
       nifty.gotoScreen("about");
    }
    
    public void toStart() {
       nifty.gotoScreen("start");
    }

    @Override
    public void bind(Nifty nifty, Screen screen) {

    }

    @Override
    public void onStartScreen() {
    }

    @Override
    public void onEndScreen() {
    }
}