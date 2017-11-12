package engine;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.AnimEventListener;
import com.jme3.animation.Animation;
import com.jme3.animation.LoopMode;
import com.jme3.animation.SpatialTrack;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.bounding.BoundingVolume;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.collision.CollisionResults;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.system.AppSettings;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Main extends SimpleApplication implements AnimEventListener {

    //test variable
    private AnimChannel channel;
    private AnimChannel channel2;
    private AnimControl control;
    private Spatial modelCharacter;
    private CharacterControl player;
    private BulletAppState bulletAppState;
    private boolean left, right, jump;
    private final float maxMove = 0.9f;
    private float now = 0;
    private final float leftMax = -0.7f;
    private final float rightMax = 0.7f;
    private float gridPlacement = 0f;
    private boolean jumpTrigger = false;
    //test gravity var
    private final float verticalMax = 0.7f;
    private float verticalPosition = 0;
    private boolean jumpStatus = false;
    private Node model;
    private Animation spatialAnimation;
    private final float[] arrayRand = {-1.8f, 0f, 1.8f};
    private float factor = 0f;
    private boolean mark = true;
    //test var
    private boolean jumpButtonStatus = false;
    private SpatialTrack spatialTrack;
    private float randomNumber = 0;
    //end test
    private boolean IS_ALIVE = true;
    private HashMap<String, Animation> animations;

    //test collision
    private final ArrayList<Geometry> listOfObstacle = new ArrayList<>();
    private AbstractAppState abstractAppState;
    private  float airMomentum = 0.07f;
    private float nowAirMomentum = 0;
    private final float gravity = 0.025f;
    private float nowGravity = 0.05f;
    private float nowMove = 0;
    //end test variable
    public static void main(String[] args) {
        Main app = new Main();
        AppSettings settings = new AppSettings(true);
        settings.setResolution(1024, 768);
//        settings.setFullscreen(true);
        app.setSettings(settings);
        app.setShowSettings(false);

        app.start();
    }

    @Override
    public void simpleInitApp() {
        abstractAppState = new AbstractAppState();
        abstractAppState.setEnabled(true);
        stateManager.attach(abstractAppState);
        //bullet
        bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState);
        cam.setLocation(new Vector3f(20f, 2.45f, 0f));
        cam.setRotation(new Quaternion(-0.054774776f, 0.94064945f, -0.27974048f, -0.18418397f));
        cam.lookAtDirection(new Vector3f(-2f, -0.75f, 0), new Vector3f(0, 1, 0));
        AmbientLight al = new AmbientLight();
        rootNode.addLight(al);
        viewPort.setBackgroundColor(new ColorRGBA(0.7f, 0.8f, 1f, 1f));
        DirectionalLight dl = new DirectionalLight();
        dl.setDirection(Vector3f.UNIT_XYZ.negate());
        rootNode.addLight(dl);
        //disable movement mouse
        flyCam.setEnabled(false);
        // Create model
        model = new Node("model");
        Material m = assetManager.loadMaterial("Textures/Terrain/BrickWall/BrickWall.j3m");
        Box box = new Box(1f, 1f, 1f);
        Box obstacle = new Box(0.2f, 0.25f, 1.4f);

        for (int i = 0; i <= 10; i++) {
            Geometry geom = new Geometry("box", box);
            Geometry leftG = new Geometry("box", box);
            Geometry rightG = new Geometry("box", box);
            Geometry obsG = new Geometry("obstacle", obstacle);
            geom.setMaterial(m);
            leftG.setMaterial(m);
            rightG.setMaterial(m);
            obsG.setMaterial(m);

            Node childModel = new Node("childmodel");
            Node left = new Node("left");
            Node right = new Node("right");
            Node obs = new Node("obs");

            childModel.setLocalTranslation(2 * i, 0, 0);
            left.setLocalTranslation(2 * i, 0, -2);
            right.setLocalTranslation(2 * i, 0, 2);
            obs.setLocalTranslation(2 * i, 1.15f, 0);

            childModel.attachChild(geom);
            left.attachChild(leftG);
            right.attachChild(rightG);
            if (i % 5 == 2) {
                obs.attachChild(obsG);
                model.attachChild(obs);
                listOfObstacle.add(obsG);
            }

            model.attachChild(childModel);
            model.attachChild(left);
            model.attachChild(right);

        }

        //animation parameters
        float animTime = 2;
        int fps = 100;
        float totalXLength = 10;

        //calculating frames
        int totalFrames = (int) (fps * animTime);
        float dT = animTime / totalFrames, t = 0;
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
        spatialTrack = new SpatialTrack(times, translations, rotations, scales);

        //creating the animation
        spatialAnimation = new Animation("anim", animTime);
        spatialAnimation.setTracks(new SpatialTrack[]{spatialTrack});

        //create spatial animation control
        control = new AnimControl();
        animations = new HashMap<String, Animation>();
        animations.put("anim", spatialAnimation);
        control.setAnimations(animations);

        resetState();
        rootNode.attachChild(model);

        loadModel();
        createInput();
    }

    public void simpleUpdate(float tpf) {
        Random r = new Random();
        int x = r.nextInt(3);
        factor = 1.8f;
        if (abstractAppState.isEnabled()) {
            if (mark) {
                randomNumber += tpf;
            } else {
                randomNumber -= tpf;
            }
            for (int i = 0; i < listOfObstacle.size(); i++) {
                listOfObstacle.get(i).setLocalTranslation(0f, 0f, randomNumber);
                System.out.println(randomNumber);
            }
            if (randomNumber > 1.8) {
                mark = false;
            }
            if (randomNumber < -1.8) {
                mark = true;
            }
            if (left) {
                float move = 2 * tpf;
                if (now + move <= maxMove) {
                    modelCharacter.move(0, 0, move);
                    now += move;
                    gridPlacement -= move;
                    System.out.println(gridPlacement);
                } else {
                    left = false;
                    now = 0;
                }
            } else if (right) {

                float move = 2 * tpf;
                if (now + move <= maxMove) {
                    modelCharacter.move(0, 0, -move);
                    now += move;
                    gridPlacement += move;
                    System.out.println(gridPlacement);
                } else {
                    right = false;
                    now = 0;
                }
            } else if (jump) {
                jumpStatus = true;
                jumpTrigger = true;
                jump = false;
                jumpButtonStatus = true;
                channel.setAnim("JumpStart");
                channel.setLoopMode(LoopMode.DontLoop);
            }
            if (jumpTrigger && jumpStatus) {
                float move = (3.05f - nowGravity) * tpf;
                if (move > 0) {
                    jump(move);
                    nowGravity += gravity;
                    verticalPosition += move;
                } else {
                    jumpStatus = false;
                    nowGravity = gravity;
                }
                System.out.println("masuk jump");
            } else if (jumpTrigger && !jumpStatus) {
                float move = (nowGravity) * tpf;
                if (verticalPosition - move >= 0) {
                    jump(-move);
                    nowGravity += gravity;
                    verticalPosition -= move;
                } else {
                    channel.setAnim("RunBase");
                    channel2.setAnim("RunTop");
                    channel.setLoopMode(LoopMode.Loop);
                    jumpTrigger = false;
                    jumpButtonStatus = false;
                    nowGravity = gravity;
                    verticalPosition = 0;
                }
            }

            // collision
            CollisionResults results = new CollisionResults();
            for (int i = 0; i < listOfObstacle.size(); i++) {
                BoundingVolume bv = listOfObstacle.get(i).getWorldBound();
                modelCharacter.collideWith(bv, results);

                if (results.size() > 0) {
                    //System.out.println("COLLIDE!!!!");
                    //collision
                    abstractAppState.setEnabled(false);
                    System.out.println(abstractAppState.isEnabled());
                    //end of collision
                } else {
                    //System.out.println("NOT COLLIDING");
                }
            }
        } else {
            model.removeControl(control);
            control.clearChannels();
            System.out.println("die");
            model.removeControl(control);
            spatialAnimation.removeTrack(spatialTrack);
        }
    }

    private void resetState() {
        IS_ALIVE = true;
        model.addControl(control);
        channel = control.createChannel();
        channel.setAnim("anim");
    }

    private void loadModel() {
        modelCharacter = (Node) assetManager.loadModel("Models/Sinbad/Sinbad.mesh.xml");
        modelCharacter.setLocalScale(0.1f);
        modelCharacter.setLocalTranslation(cam.getLocation().add(-2f, -1f, 0));
        modelCharacter.getLocalRotation().fromAngleAxis(-1.5708f, Vector3f.UNIT_Y);
        //tambah control
        control = modelCharacter.getControl(AnimControl.class);
        control.addListener(this);
        channel = control.createChannel();
        channel2 = control.createChannel();
        channel.setAnim("RunBase");
        channel2.setAnim("RunTop");
        modelCharacter.addControl(new RigidBodyControl(0f));
        modelCharacter.getControl(RigidBodyControl.class).getCollisionShape().setScale(new Vector3f(2, 2, 2));
        bulletAppState.getPhysicsSpace().add(modelCharacter);
        rootNode.attachChild(modelCharacter);
    }

    // test jump
    public void jump(float tpf) {
        //cara naif
        modelCharacter.move(0, tpf, 0);
        channel.setAnim("JumpLoop");   
        channel.setLoopMode(LoopMode.DontLoop);

    }

    private void createInput() {
        inputManager.addMapping("MoveRight", new KeyTrigger(KeyInput.KEY_D));
        inputManager.addMapping("MoveLeft", new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping("JumpStart", new KeyTrigger(KeyInput.KEY_SPACE));

        inputManager.addListener(actionListener, "JumpStart");
        inputManager.addListener(actionListener, "MoveLeft");
        inputManager.addListener(actionListener, "MoveRight");
    }

    public void onAnimCycleDone(AnimControl control, AnimChannel channel, String animName) {
        if (animName.equals("JumpStart")) {
            channel.setAnim("JumpEnd");
            channel.setSpeed(1f);
            channel.setAnim("RunBase");
        }

    }

    public void onAnimChange(AnimControl control, AnimChannel channel, String animName) {
        // unused
    }

    private ActionListener actionListener = new ActionListener() {

        public void onAction(String name, boolean keyPressed, float tpf) {
            if (!jumpButtonStatus && name.equals("JumpStart") && !keyPressed) {
                System.out.println("masuk jump");
                jump = true;
            }
            if (name.equals("MoveRight") && !keyPressed) {
                System.out.println("masuk right");
                if (gridPlacement < 0.65f) {
                    right = true;
                }
            }

            if (name.equals("MoveLeft") && !keyPressed) {
                System.out.println("masuk left");
                if (gridPlacement > -0.65f) {
                    left = true;
                }
            }

        }
    };
}
