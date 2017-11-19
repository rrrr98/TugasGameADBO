/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import com.jme3.app.SimpleApplication;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.system.AppSettings;
import controller.GameStateController;
import controller.StateController;
import de.lessvoid.nifty.Nifty;
import view.InitScreen;
import view.ScoreBoard;

/**
 *
 * @author Ketua : Kevin R
 */
public class Tester extends SimpleApplication {

    public static final int SCREEN_WIDTH = 1024, SCREEN_HEIGHT = 768;
    private Nifty nifty;

    public static void main(String[] args) {
        Tester app = new Tester();
        AppSettings settings = new AppSettings(true);
        settings.setResolution(SCREEN_WIDTH, SCREEN_HEIGHT);
        settings.setFrameRate(60);
//        settings.setFullscreen(true);
        app.setSettings(settings);
        app.setShowSettings(false);
        app.setDisplayStatView(false);
        app.start();
    }

    @Override
    public void simpleInitApp() {
        flyCam.setEnabled(false);
        GameStateController.getInstance().initialize(stateManager, this);
        StateController.getInstance();
        InitScreen initS;
        ScoreBoard.getInstance().init(guiNode, guiFont);
        NiftyJmeDisplay niftyDisplay = NiftyJmeDisplay.newNiftyJmeDisplay(
                assetManager, inputManager, audioRenderer, guiViewPort);
        nifty = niftyDisplay.getNifty();

        guiViewPort.addProcessor(niftyDisplay);
        flyCam.setDragToRotate(true);
        initS = new InitScreen(nifty);
        initS.initScreen();
        nifty.gotoScreen("start");
        ScoreBoard.getInstance().loadScoreBoard();
    }
}
