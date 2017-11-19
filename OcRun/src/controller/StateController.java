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

/**
 * game state
 * @author Kevin R
 */
enum State {
    GAME_STATE,
    PAUSE_STATE,
    DIE_STATE;
}

/**
 *
 * @author Ketua : Kevin R
 */
public class StateController {
    // attribute
    private State state;
    private State before;
    private static StateController instance;
    private Character character;
    private AnimControl control;
    private AnimChannel channel1, channel2;
    private AssetManager assetManager;
    private Picture pic;
    private Node rootNode;

    /**
     * constructor
     */
    private StateController() {
        this.character = CharacterController.getInstance().getCharacter();
        this.control = CharacterController.getInstance().getControl();
        this.state = State.PAUSE_STATE;
        this.before = State.GAME_STATE;
        this.assetManager = GameStateController.getInstance().getApp().getAssetManager();
        this.rootNode = GameStateController.getInstance().getApp().getRootNode();
    }

    /**
     * getter instance
     * @return instance
     */
    public static StateController getInstance() {
        if (instance == null) {
            instance = new StateController();
        }
        return instance;
    }

    /**
     * setter state
     * @param state 
     */
    public void setState(State state) {
        this.state = state;
        updateState();
    }

    /**
     * getter state
     * @return state
     */
    public State getState() {
        return state;
    }

    /**
     * remove movement from track, character
     */
    private void removeMovement() {
        character.getModel().removeControl(control);
        WorldController.getInstance().removeTrack();
    }
    
    /**
     * restart movement from track, character
     */
    public void restart() {
        removeMovement();
        WorldController.getInstance().resetTrack();
        addMovement();
        setState(State.GAME_STATE);
        this.before = State.GAME_STATE;
    }
    
    /**
     * add movement for track, character
     */
    private void addMovement() {
        character.getModel().addControl(control);
        WorldController.getInstance().resumeTrack();
    }

    /**
     * view pause gui
     * @param node 
     */
    private void viewPauseGUI(Node node) {
        pic = new Picture("HUD Picture");
        pic.setImage(assetManager, "Textures/States/PauseState.png", true);
        pic.setWidth(Tester.SCREEN_WIDTH);
        pic.setHeight(Tester.SCREEN_HEIGHT);
        pic.setPosition(0, 0);
        node.attachChild(pic);
    }

    /**
     * update state SWITCH state
     */
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
