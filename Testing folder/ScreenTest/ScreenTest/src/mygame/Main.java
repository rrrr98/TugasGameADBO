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
 * 
 * @author pilowman
 */
public class Main extends SimpleApplication {
    BitmapText scoreText ,hiscoreText;
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
        nifty.gotoScreen("exit");
        
        scoreText = new BitmapText(guiFont, false);
        guiNode.attachChild(scoreText);
        hiscoreText = new BitmapText(guiFont, false);
        guiNode.attachChild(hiscoreText);
        
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
            scoreText.setLocalTranslation(0,scoreText.getLineWidth()*6.5f, 0);
            hiscoreText.setText("HighScore: " + nScore.getHighscore());
            hiscoreText.setLocalTranslation(0,scoreText.getLineWidth()*7f, 0);
        }
        else{
            scoreText.setText("");
        }
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
