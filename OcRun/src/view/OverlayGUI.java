/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Node;
import controller.GameStateController;
import static engine.Tester.SCREEN_HEIGHT;

/**
 *
 * @author Zero
 */
public class OverlayGUI {
    private BitmapFont guiFont;
    private Node guiNode;
    private BitmapText label, hudText;
    private static OverlayGUI instance;
    private OverlayGUI() {
        
    }
    public static OverlayGUI getInstance() {
        if (instance == null) {
            instance = new OverlayGUI();
        }
        return instance;
    }
    public void init(BitmapFont guiFont, Node guiNode) {
        this.guiFont = guiFont;
        this.guiNode = guiNode;
        loadOverlayGUI();
    }
    private void loadOverlayGUI() {
        label = new BitmapText(guiFont, false);
        hudText = new BitmapText(guiFont, false);
        label.setSize(guiFont.getCharSet().getRenderedSize());      // font size
        label.setColor(ColorRGBA.White);                             // font color
        label.setText("Score : ");             // the text
        label.setLocalTranslation(0, SCREEN_HEIGHT - hudText.getLineHeight(), 10); // position
        hudText.setSize(guiFont.getCharSet().getRenderedSize());      // font size
        hudText.setColor(ColorRGBA.White);                             // font color
        hudText.setText("0");             // the text
        hudText.setLocalTranslation(70 ,SCREEN_HEIGHT - hudText.getLineHeight(), 10); // position
        
        
        guiNode.attachChild(hudText);
        guiNode.attachChild(label);
    }

    public void setHudText(String text) {
        this.hudText.setText(text);
    }
    
    
}
