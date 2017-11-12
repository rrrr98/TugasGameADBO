/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

public class MyStartScreen implements ScreenController {
    Main app;
    Nifty nifty;

    MyStartScreen(Nifty niftys) {
        nifty=niftys;
    }

    public void startGame() {
       nifty.gotoScreen("hud");
     }

    public void quitGame() {
       System.exit(0);
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