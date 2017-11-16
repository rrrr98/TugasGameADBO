/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package engine;

import com.jme3.app.SimpleApplication;
import com.jme3.system.AppSettings;
import controller.GameStateController;

/**
 *
 * @author i16036
 */
public class Tester extends SimpleApplication{
    public static void main(String[] args) {
        Tester app = new Tester();
        AppSettings settings = new AppSettings(true);
        settings.setResolution(1024, 768);
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
    }
}
