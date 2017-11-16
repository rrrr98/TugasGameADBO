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
 * @author Hartanto
 */
public class WorldController {

    private Animation spatialAnimation;
    private AnimControl control;
    private static SpatialTrack spatialTrack;
    public final Node world;
    private AnimChannel channel;
    private static WorldController instance;
    private WorldController() {
        world = World.getInstance();
        generateWorld();
        createAnimation();
    }

    public static WorldController getInstance() {
        if (instance == null) {
            instance = new WorldController();
        }
        return instance;
    }
    
    public static void init(SpatialTrack track){
        spatialTrack = track;
    }
    private void generateWorld() {
        TerrainController.getInstance().attachTo(world);
        ObstacleController.getInstance().attachTo(world);
    }
    public void update(float tpf){
        ObstacleController.getInstance().update(tpf);
    }
    public Node getWorld() {
        return world;
    }
    public void createAnimation(){
        spatialAnimation = new Animation("anim", GameStateController.ANIM_TIME);
        spatialAnimation.setTracks(new SpatialTrack[]{spatialTrack});
        
        control = new AnimControl();
        HashMap <String, Animation> animations = new HashMap<String, Animation>();
        animations.put("anim", spatialAnimation);
        control.setAnimations(animations);
        world.addControl(control);
        channel = control.createChannel();
        channel.setAnim("anim");
        spatialAnimation.addTrack(spatialTrack);
    }
    public void removeTrack(){
        channel.reset(true);
    }
    
}
