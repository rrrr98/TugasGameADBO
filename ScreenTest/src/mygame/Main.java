package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.niftygui.NiftyJmeDisplay;
import de.lessvoid.nifty.Nifty;

/**
 * This is the Main Class of your Game. You should only do initialization here.
 * Move your Logic into AppStates or Controls
 * @author normenhansen
 */
public class Main extends SimpleApplication {
    InitScreen initS;

    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        NiftyJmeDisplay niftyDisplay = NiftyJmeDisplay.newNiftyJmeDisplay(
            assetManager, inputManager, audioRenderer, guiViewPort);
        final Nifty nifty = niftyDisplay.getNifty();


        guiViewPort.addProcessor(niftyDisplay);
        flyCam.setDragToRotate(true);
        initS= new InitScreen(nifty);
        initS.initScreen();
        nifty.gotoScreen("start");
    }
}
