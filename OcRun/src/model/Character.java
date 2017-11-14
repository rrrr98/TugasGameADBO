/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import com.jme3.asset.AssetManager;
import com.jme3.renderer.Camera;
import com.jme3.scene.Spatial;

/**
 *
 * @author Hartanto
 */
public abstract class Character extends Model {

    protected Spatial character;
    protected static Camera cam;

    public static final void init(AssetManager manager, Camera cam) {
        setManager(manager);
        setCam(cam);
    }

    public static final void setCam(Camera cam) {
        Character.cam = cam;
    }

    public static final Camera getCam() {
        return cam;
    }
}
