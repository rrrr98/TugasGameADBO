package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.font.BitmapText;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import de.lessvoid.nifty.Nifty;

/**
 * This is the Main Class of your Game. You should only do initialization here.
 * Move your Logic into AppStates or Controls
 * @author normenhansen
 */
public class Main extends SimpleApplication {
    BitmapText scoreText;
    ScoreBoard nScore;
    Nifty nifty;

    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        InitScreen initS;
        nScore = new ScoreBoard();
        NiftyJmeDisplay niftyDisplay = NiftyJmeDisplay.newNiftyJmeDisplay(
            assetManager, inputManager, audioRenderer, guiViewPort);
        nifty = niftyDisplay.getNifty();


        guiViewPort.addProcessor(niftyDisplay);
        flyCam.setDragToRotate(true);
        initS= new InitScreen(nifty, nScore);
        initS.initScreen();
        nifty.gotoScreen("start");
        
        scoreText = new BitmapText(guiFont, false);
        scoreText.setText("");
        guiNode.attachChild(scoreText);
        scoreText.setLocalTranslation((cam.getWidth() - scoreText.getLineWidth()) / 2.0f,
        scoreText.getLineHeight(), 0.0f);
        
        Box b = new Box(1, 1, 1);
        Geometry geom = new Geometry("Box", b);

        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.Blue);
        geom.setMaterial(mat);

        rootNode.attachChild(geom);
    }

    @Override
    public void simpleUpdate(float tpf) {
        // Update and position the score
        if(nScore.isStatus()==true){
            nScore.update();
            scoreText.setText("Score: " + nScore.getScore());
            scoreText.setLocalTranslation((cam.getWidth() - scoreText.getLineWidth()) / 2.0f,
                scoreText.getLineHeight(), 0.0f);
        }
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
