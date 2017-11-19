/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.Animation;
import com.jme3.animation.SpatialTrack;
import com.jme3.scene.Node;
import java.util.HashMap;
import model.World;

/**
 *
 * @author Ketua : Kevin R
 */
public class WorldController {

    private Animation spatialAnimation;
    private AnimControl control;
    private static SpatialTrack spatialTrack;
    public final Node world;
    private AnimChannel channel;
    private static WorldController instance;
    private HashMap<String, Animation> animations;

    /**
     * constructor
     */
    private WorldController() {
        world = World.getInstance();
        generateWorld();
        createAnimation();
    }

    /**
     * get instance
     * @return instance
     */
    public static WorldController getInstance() {
        if (instance == null) {
            instance = new WorldController();
        }
        return instance;
    }

    /**
     * init
     * @param track 
     */
    public static void init(SpatialTrack track) {
        spatialTrack = track;
    }

    /**
     * generate world
     */
    private void generateWorld() {
        TerrainController.getInstance().attachTo(world);
//        ObstacleController.getInstance().attachTo(world);
    }
    
    /**
     * update
     * @param tpf 
     */
    public void update(float tpf) {
        ObstacleController.getInstance().update(tpf);
    }

    /**
     * get world
     * @return 
     */
    public Node getWorld() {
        return world;
    }

    /**
     * createAnimation
     */
    public void createAnimation() {
        spatialAnimation = new Animation("anim", GameStateController.ANIM_TIME);
        spatialAnimation.setTracks(new SpatialTrack[]{spatialTrack});

        control = new AnimControl();
        animations = new HashMap<String, Animation>();
        animations.put("anim", spatialAnimation);
        control.setAnimations(animations);
        world.addControl(control);
        channel = control.createChannel();
        channel.setAnim("anim");
        spatialAnimation.addTrack(spatialTrack);
    }

    /**
     * remove track
     */
    public void removeTrack() {
        channel.setSpeed(0);
        spatialAnimation.removeTrack(spatialTrack);
    }
    
    /**
     * reset track
     */
    public void resetTrack() {
        createAnimation();
    }
    /**
     * resume track
     */
    public void resumeTrack() {
        spatialAnimation.addTrack(spatialTrack);
        channel.setSpeed(1);
    }

}
