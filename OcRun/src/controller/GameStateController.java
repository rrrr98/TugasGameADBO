/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jme3.animation.SpatialTrack;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.bullet.BulletAppState;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import view.OverlayGUI;

/**
 *
 * @author Jimz
 */
public class GameStateController extends AbstractAppState {

    public static final int FPS = 60;
    public static final float ANIM_TIME = 2;

    private SimpleApplication app;
    private Node rootNode;
    private AssetManager assetManager;
    private AppStateManager stateManager;
    private InputManager inputManager;
    private ViewPort viewPort;
    private BulletAppState physics;
    private CharacterController characterCtrl;
    private WorldController worldCtrl;
    private static GameStateController instance;
    private Camera cam;
    
    private float score;

    private GameStateController() {
        this.score = 0;
    }

    public static GameStateController getInstance() {
        if (instance == null) {
            instance = new GameStateController();
        }
        return instance;
    }

    @Override
    public void initialize(AppStateManager manager, Application app) {
        this.app = (SimpleApplication) app;
        this.rootNode = this.app.getRootNode();
        this.assetManager = this.app.getAssetManager();
        this.stateManager = this.app.getStateManager();
        this.inputManager = this.app.getInputManager();
        this.cam = this.app.getCamera();
        this.viewPort = this.app.getViewPort();
        model.Character.init(assetManager, this.app.getCamera());
        characterCtrl = CharacterController.getInstance();
        System.out.println(model.Model.getManager().toString());
        this.physics = this.stateManager.getState(BulletAppState.class);
        System.out.println(rootNode.toString());
        characterCtrl.attatchTo(rootNode);

//        DirectionalLight dl = new DirectionalLight(new Vector3f(10f, 2f, 10f));
//        rootNode.addLight(dl);
        rootNode.addLight(new AmbientLight());
        this.setEnabled(true);
        stateManager.attach(new BulletAppState());
        manager.attach(this);
        cam.setLocation(new Vector3f(20f, 2.45f, 0f));
        cam.setRotation(new Quaternion(-0.054774776f, 0.94064945f, -0.27974048f, -0.18418397f));
        cam.lookAtDirection(new Vector3f(-2f, -0.75f, 0), new Vector3f(0, 1, 0));
        //animation parameters
        float totalXLength = TerrainController.getInstance().getRouteLong();

        //calculating frames
        int totalFrames = (int) (FPS * ANIM_TIME);
        float dT = ANIM_TIME / totalFrames, t = 0;
        float dX = totalXLength / totalFrames, x = 0;
        float[] times = new float[totalFrames];
        Vector3f[] translations = new Vector3f[totalFrames];
        Quaternion[] rotations = new Quaternion[totalFrames];
        Vector3f[] scales = new Vector3f[totalFrames];
        for (int i = 0; i < totalFrames; ++i) {
            times[i] = t;
            t += dT;
            translations[i] = new Vector3f(x, 0, 0);
            x += dX;
            rotations[i] = Quaternion.IDENTITY;
            scales[i] = Vector3f.UNIT_XYZ;
        }
        SpatialTrack spatialTrack = new SpatialTrack(times, translations, rotations, scales);
        WorldController.init(spatialTrack);
        worldCtrl = WorldController.getInstance();
        characterCtrl = CharacterController.getInstance();
        this.rootNode.attachChild(worldCtrl.getWorld());
        createInput();
    }

    public SimpleApplication getApp() {
        return app;
    }

    @Override
    public void update(float tpf) {
        if (StateController.getInstance().getState() == State.GAME_STATE) {
            super.update(tpf);
            characterCtrl.update(tpf);
            worldCtrl.update(tpf);
            score += tpf * 2;
            OverlayGUI.getInstance().setHudText(String.format("%.0f", score));
        }
    }

    private void createInput() {
        ActionListener actionListener = characterCtrl.getActionListener();
        inputManager.addMapping("MoveRight", new KeyTrigger(KeyInput.KEY_D));
        inputManager.addMapping("MoveLeft", new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping("JumpStart", new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addMapping("Resume", new KeyTrigger(KeyInput.KEY_TAB));
        inputManager.addListener(actionListener, "JumpStart");
        inputManager.addListener(actionListener, "MoveLeft");
        inputManager.addListener(actionListener, "MoveRight");
        inputManager.addListener(actionListener, "Resume");
    }
}
