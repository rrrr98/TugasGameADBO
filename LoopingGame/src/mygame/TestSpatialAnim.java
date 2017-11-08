package mygame;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.AnimEventListener;
import com.jme3.animation.Animation;
import com.jme3.animation.LoopMode;
import com.jme3.animation.SkeletonControl;
import com.jme3.animation.SpatialTrack;
import com.jme3.app.SimpleApplication;
import com.jme3.asset.TextureKey;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.font.BitmapText;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
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

public class TestSpatialAnim extends SimpleApplication implements AnimEventListener{

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
    private boolean left, right;

    //test gravity var
    private int verticalSpeed = 0;
    private int verticalPosition;
    private int gravity = 10;
    private int terminalVelocity = 300;

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
        Node model = new Node("model");
        Material m = assetManager.loadMaterial("Textures/Terrain/BrickWall/BrickWall.j3m");
        Box box = new Box(1f, 1f, 1f);
        for (int i = 0; i <= 10; i++) {
            Geometry geom = new Geometry("box", box);
            Geometry leftG = new Geometry("box", box);
            Geometry rightG = new Geometry("box", box);
            
            geom.setMaterial(m);
            leftG.setMaterial(m);
            rightG.setMaterial(m);
            Node childModel = new Node("childmodel");
            Node left = new Node("left");
            Node right = new Node("right");
            childModel.setLocalTranslation(2 * i, 0, 0);
            left.setLocalTranslation(2 * i, 0, -2);
            right.setLocalTranslation(2 * i, 0, 2);
            childModel.attachChild(geom);
            left.attachChild(leftG);
            right.attachChild(rightG);
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
        Animation spatialAnimation = new Animation("anim", animTime);
        spatialAnimation.setTracks(new SpatialTrack[]{spatialTrack});

        //create spatial animation control
        AnimControl control = new AnimControl();
        HashMap<String, Animation> animations = new HashMap<String, Animation>();
        animations.put("anim", spatialAnimation);
        control.setAnimations(animations);
        model.addControl(control);

        rootNode.attachChild(model);

        //run animation
        control.createChannel().setAnim("anim");


        loadModel();
        createInput();
        //initKeys();
    }

    public void simpleUpdate(float tpf) {
        //System.out.println(modelCharacter.getLocalTranslation().getY());

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

    private AnalogListener analogListener = new AnalogListener() {

        public void onAnalog(String name, float value, float tpf) {
            if (name.equals("MoveRight")) {
                right = true;
                modelCharacter.move(0, 0, -2 * tpf);
            }

            if (name.equals("MoveLeft")) {
                left = true;
                modelCharacter.move(0, 0, 2 * tpf);
            }
            if (name.equals("JumpStart")) {
                jump(tpf);
                //System.out.println("masuk");
            }
            right = false;
            left = false;
        }
    };


    // test jump
    public void jump(float tpf){
        //cara wajar
        /*Vector3f aim=modelCharacter.getLocalTranslation().add(new Vector3f(0f,3f*tpf,0f));
        modelCharacter.move(aim);
        while(modelCharacter.getLocalTranslation().y!=0){
            channel.setAnim("JumpLoop");
            aim.subtract(new Vector3f(0f,0.5f,0f));
            modelCharacter.move(aim.multLocal(speed + tpf));
        }*/
        //cara naif
        modelCharacter.move(0,3f*tpf,0);
        channel.setAnim("JumpLoop");
        //modelCharacter.move(0,-20f*tpf,0);   
        channel.setLoopMode(LoopMode.DontLoop);
        channel.setAnim("RunBase");
    }

    private void createInput() {
        inputManager.addMapping("MoveRight", new KeyTrigger(KeyInput.KEY_D));
        inputManager.addMapping("MoveLeft", new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping("JumpStart", new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addListener(actionListener, "JumpStart");

        inputManager.addListener(analogListener, new String[]{
            "MoveRight", "MoveLeft", "JumpStart"
        });
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
            if (name.equals("JumpStart") && !keyPressed) {
                if (!channel.getAnimationName().equals("JumpStart")) {
                    channel.setAnim("JumpStart", 0.50f);
                    channel.setLoopMode(LoopMode.Loop);
                }
            }
    
    }
    };
}
