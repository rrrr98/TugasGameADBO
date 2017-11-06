package mygame;

import com.jme3.animation.AnimControl;
import com.jme3.animation.Animation;
import com.jme3.animation.SpatialTrack;
import com.jme3.app.SimpleApplication;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import java.util.HashMap;

public class TestSpatialAnim extends SimpleApplication {

    public static void main(String[] args) {
        TestSpatialAnim app = new TestSpatialAnim();
        app.start();
    }

    @Override
    public void simpleInitApp() {
        cam.setLocation(new Vector3f(20f, 2f, 0f));
        cam.setRotation(new Quaternion(-0.054774776f, 0.94064945f, -0.27974048f, -0.18418397f));
        cam.lookAtDirection(new Vector3f(-2f,0,0), new Vector3f(0, 1, 0));
        AmbientLight al = new AmbientLight();
        rootNode.addLight(al);
        
        DirectionalLight dl = new DirectionalLight();
        dl.setDirection(Vector3f.UNIT_XYZ.negate());
        rootNode.addLight(dl);
        flyCam.setEnabled(false);
        // Create model
        Node model = new Node("model");

        for (int i = 0; i <= 10; i++) {
            Box box = new Box(1f, 1f, 1f);
            Geometry geom = new Geometry("box", box);
            geom.setMaterial(assetManager.loadMaterial("Textures/Terrain/BrickWall/BrickWall.j3m"));
            Node childModel = new Node("childmodel");
            childModel.setLocalTranslation(2 * i, 0, 0);
            childModel.attachChild(geom);
            model.attachChild(childModel);
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
    }

    public void simpleUpdate(float tpf) {
        System.out.println(cam.getDirection().getZ());
    }
}
