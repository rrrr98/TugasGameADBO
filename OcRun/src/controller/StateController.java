/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.asset.AssetManager;
import com.jme3.scene.Node;
import com.jme3.ui.Picture;
import engine.Tester;
import model.Character;
import view.MyStartScreen;

enum State {
    GAME_STATE,
    PAUSE_STATE,
    DIE_STATE;
}

/**
 *
 * @author Zero
 */
public class StateController {

    private State state;
    private State before;
    private static StateController instance;
    private Character character;
    private AnimControl control;
    private AnimChannel channel1, channel2;
    private AssetManager assetManager;
    private Picture pic;
    private Node rootNode;

    private StateController() {
        this.character = CharacterController.getInstance().getCharacter();
        this.control = CharacterController.getInstance().getControl();
        this.state = State.PAUSE_STATE;
        this.before = State.GAME_STATE;
        this.assetManager = GameStateController.getInstance().getApp().getAssetManager();
        this.rootNode = GameStateController.getInstance().getApp().getRootNode();
    }

    public static StateController getInstance() {
        if (instance == null) {
            instance = new StateController();
        }
        return instance;
    }

    public void setState(State state) {
        this.state = state;
        updateState();
    }

    public State getState() {
        return state;
    }

    private void removeMovement() {
        character.getModel().removeControl(control);
        WorldController.getInstance().removeTrack();
    }
    public void restart() {
        removeMovement();
        WorldController.getInstance().resetTrack();
        addMovement();
        setState(State.GAME_STATE);
        this.before = State.GAME_STATE;
    }
    
    private void addMovement() {
        character.getModel().addControl(control);
        WorldController.getInstance().resumeTrack();
    }

    private void viewPauseGUI(Node node) {
        pic = new Picture("HUD Picture");
        pic.setImage(assetManager, "Textures/States/PauseState.png", true);
        pic.setWidth(Tester.SCREEN_WIDTH);
        pic.setHeight(Tester.SCREEN_HEIGHT);
        pic.setPosition(0, 0);
        node.attachChild(pic);
    }

    public void updateState() {
        switch (state) {
            case GAME_STATE:
                if (before == State.PAUSE_STATE) {
                    addMovement();
                    rootNode.detachChild(pic);
                    before = State.GAME_STATE;
                }
                break;
            case PAUSE_STATE:
                if (before == State.GAME_STATE) {
                    removeMovement();
                    viewPauseGUI(rootNode);
                    before = State.PAUSE_STATE;
                }
                break;
            case DIE_STATE:
                removeMovement();
                before = State.DIE_STATE;
                System.out.println("DIE DIE DIE");
                // view GUI
                MyStartScreen.getInstance().toExit();
                break;
        }
    }
}
