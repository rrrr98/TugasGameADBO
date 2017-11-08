package mygame;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.AnimEventListener;
import com.jme3.animation.Animation;
import com.jme3.animation.LoopMode;
import com.jme3.animation.SkeletonControl;
import com.jme3.animation.SpatialTrack;
import com.jme3.app.SimpleApplication;
import com.jme3.bounding.BoundingVolume;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.collision.CollisionResults;
import com.jme3.font.BitmapText;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TestSpatialAnim extends SimpleApplication implements AnimEventListener {

    //test variable
    private AnimChannel channel;
    private AnimControl control;
    private String[] animNames = {"Dodge", "Walk", "pull", "push"};
    private final static int SIZE = 10;
    private boolean hwSkinningEnable = true;
    private List<SkeletonControl> skControls = new ArrayList<SkeletonControl>();
    private BitmapText hwsText;
    private Spatial modelCharacter;
    private CharacterControl player;
    private BulletAppState bulletAppState;
    private boolean left, right, jump;
    private float maxMove = 0.7f;
    private float now = 0;
    private float leftMax = -0.7f, rightMax = 0.7f;
    private float gridPlacement = 0f;
    private boolean jumpTrigger = false;
    //test gravity var
    private float verticalMax = 0.7f;
    private float verticalPosition = 0;
    private boolean jumpStatus = false;
    private int gravity = 10;
    private int terminalVelocity = 300;
    private RigidBodyControl controlObs;
    private Node model;
    private Animation spatialAnimation;

    private boolean IS_ALIVE = true;
    private HashMap<String, Animation> animations;

    //test collision
    private ArrayList<Geometry> listOfObstacle = new ArrayList<>();

    //end test variable
    public static void main(String[] args) {
        TestSpatialAnim app = new TestSpatialAnim();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        //bullet
        bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState);

        cam.setLocation(new Vector3f(20f, 2f, 0f));
        cam.setRotation(new Quaternion(-0.054774776f, 0.94064945f, -0.27974048f, -0.18418397f));
        cam.lookAtDirection(new Vector3f(-2f, 0, 0), new Vector3f(0, 1, 0));
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
        Box obstacle = new Box(0.3f, 0.2f, 2f);

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
            obs.setLocalTranslation(2 * i, 1.5f, 0);

            childModel.attachChild(geom);
            left.attachChild(leftG);
            right.attachChild(rightG);
            if (i % 5 == 0) {
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
        SpatialTrack spatialTrack = new SpatialTrack(times, translations, rotations, scales);

        //creating the animation
        spatialAnimation = new Animation("anim", animTime);
        spatialAnimation.setTracks(new SpatialTrack[]{spatialTrack});

        //create spatial animation control
        control = new AnimControl();
        animations = new HashMap<String, Animation>();
        animations.put("anim", spatialAnimation);
        control.setAnimations(animations);
        //model.addControl(control);

        resetState();
        rootNode.attachChild(model);

        //run animation
        //control.createChannel().setAnim("anim");
        loadModel();
        createInput();
        //initKeys();
    }

    public void simpleUpdate(float tpf) {
        //System.out.println(modelCharacter.getLocalTranslation().getY());
        if (IS_ALIVE) {
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
            }
            if (jumpTrigger && jumpStatus) {
                //if (!channel.getAnimationName().equals("JumpStart")) {
                float move = 3 * tpf;
                if (verticalPosition + move <= verticalMax) {
                    jump(move);
                    verticalPosition += move;
                } else {
                    jumpStatus = false;
                }
                System.out.println("masuk jump");
                //}
            } else if (jumpTrigger && !jumpStatus) {
                float move = 2 * tpf;
                if (verticalPosition - move >= 0) {
                    fall(move);
                    verticalPosition -= move;
                } else {
                    channel.setAnim("RunBase");
                    channel.setLoopMode(LoopMode.Loop);
                    //System.out.println("masuk sini");
                    jumpTrigger = false;
                }
            }

            // collision
            CollisionResults results = new CollisionResults();
            for (int i = 0; i < listOfObstacle.size(); i++) {
                BoundingVolume bv = listOfObstacle.get(i).getWorldBound();
                modelCharacter.collideWith(bv, results);

                if (results.size() > 0) {
                    System.out.println("COLLIDE!!!!");
                    IS_ALIVE = false;
                } else {
                    //System.out.println("NOT COLLIDING");
                }
            }
        } else {
            model.removeControl(control);
            control.clearChannels();
        }

//        BoundingVolume bv
//        golem.collideWith(bv, results);
//
//        if (results.size() > 0) {
//            geom1.getMaterial().setColor("Color", ColorRGBA.Red);
//        }else{
//            geom1.getMaterial().setColor("Color", ColorRGBA.Blue);
//        }
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
        modelCharacter.setLocalTranslation(cam.getLocation().add(-1.2f, 0, 0));
        modelCharacter.getLocalRotation().fromAngleAxis(-1.5708f, Vector3f.UNIT_Y);
        //tambah control
        control = modelCharacter.getControl(AnimControl.class);
        control.addListener(this);
        channel = control.createChannel();
        channel.setAnim("RunBase");
        modelCharacter.addControl(new RigidBodyControl(0));
        modelCharacter.getControl(RigidBodyControl.class).getCollisionShape().setScale(new Vector3f(2, 2, 2));
        bulletAppState.getPhysicsSpace().add(modelCharacter);
        rootNode.attachChild(modelCharacter);
    }


    /* private void loadModel() {
        Material m = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        TextureKey k = new TextureKey("Models/Oto/Oto.jpg", false);
        m.setTexture("ColorMap", assetManager.loadTexture(k));
        modelCharacter = (Spatial) assetManager.loadModel("Models/Oto/Oto.mesh.xml");
        //setting a different material
        modelCharacter.setMaterial(m.clone());
        //modelCharacter.setLocalScale(0.1f);
        //modelCharacter.setLocalTranslation(-SIZE / 2, 0, -SIZE / 2);
        modelCharacter.setLocalTranslation(cam.getLocation().add(-1.2f, 0, 0));
        //modelCharacter.setLocalTranslation(new Vector3f(50f,0f,0f));
         //modelCharacter.getLocalRotation().fromAngleAxis(1.5708f, Vector3f.UNIT_Y);
        modelCharacter.rotate(0, -1.5f, 0);
        modelCharacter.setLocalScale(0.05f);
        control = modelCharacter.getControl(AnimControl.class);

        channel = control.createChannel();
        channel.setAnim(animNames[1]);
        channel.setLoopMode(LoopMode.Loop);
        SkeletonControl skeletonControl = modelCharacter.getControl(SkeletonControl.class);

        //This is a workaround the issue. this call will make the SkeletonControl gather the targets again.
        //skeletonControl.setSpatial(model);
        skeletonControl.setHardwareSkinningPreferred(hwSkinningEnable);
        skControls.add(skeletonControl);
        rootNode.attachChild(modelCharacter);
    }*/
    // test jump
    public void jump(float tpf) {
        //cara wajar
        /*Vector3f aim=modelCharacter.getLocalTranslation().add(new Vector3f(0f,3f*tpf,0f));
        modelCharacter.move(aim);
        while(modelCharacter.getLocalTranslation().y!=0){
            channel.setAnim("JumpLoop");
            aim.subtract(new Vector3f(0f,0.5f,0f));
            modelCharacter.move(aim.multLocal(speed + tpf));
        }*/
        //cara naif
        modelCharacter.move(0, tpf, 0);
        channel.setAnim("JumpLoop");
        //modelCharacter.move(0,-20f*tpf,0);   
        channel.setLoopMode(LoopMode.DontLoop);

    }

    public void fall(float tpf) {
        modelCharacter.move(0, -tpf, 0);
        channel.setAnim("JumpLoop");
        //modelCharacter.move(0,-20f*tpf,0);   
        channel.setLoopMode(LoopMode.DontLoop);
    }

    private void createInput() {
        inputManager.addMapping("MoveRight", new KeyTrigger(KeyInput.KEY_D));
        inputManager.addMapping("MoveLeft", new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping("JumpStart", new KeyTrigger(KeyInput.KEY_SPACE));
     
        inputManager.addListener(actionListener, "JumpStart");
        inputManager.addListener(actionListener, "MoveLeft");
        inputManager.addListener(actionListener, "MoveRight");
//        inputManager.addListener(analogListener, new String[]{
//            "MoveRight", "MoveLeft", "JumpStart"
//        });
    }

    public void onAnimCycleDone(AnimControl control, AnimChannel channel, String animName) {
        if (animName.equals("JumpStart")) {
            channel.setAnim("JumpEnd");
            channel.setSpeed(1f);
            //System.out.println("masuk sini");
            channel.setAnim("RunBase");
        }

    }

    public void onAnimChange(AnimControl control, AnimChannel channel, String animName) {
        // unused
    }

    private ActionListener actionListener = new ActionListener() {

        public void onAction(String name, boolean keyPressed, float tpf) {
            if (name.equals("JumpStart") && !keyPressed) {
                System.out.println("masuk jump");
                jump = true;
            }
            if (name.equals("MoveRight") && !keyPressed) {
                System.out.println("masuk right");
                if (gridPlacement < 0.69f) {
                    right = true;
                }
            }

            if (name.equals("MoveLeft") && !keyPressed) {
                System.out.println("masuk left");
                if (gridPlacement > -0.69f) {
                    left = true;
                }
            }

        }
    };
}
