/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import com.jme3.app.SimpleApplication;
import com.jme3.font.BitmapText;
import com.jme3.math.ColorRGBA;
import com.jme3.system.AppSettings;
import controller.GameStateController;
import controller.StateController;
import view.OverlayGUI;

/**
 *
 * @author i16036
 */
public class Tester extends SimpleApplication {

    public static final int SCREEN_WIDTH = 1024, SCREEN_HEIGHT = 768;
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
        OverlayGUI.getInstance().init(guiFont, guiNode);
    }
}
