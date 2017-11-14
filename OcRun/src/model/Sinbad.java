/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

/**
 *
 * @author Hartanto
 */
public class Sinbad extends Character {

    public static Sinbad instance;

    private Sinbad() {
        this.character = (Node) manager.loadModel("Models/Sinbad/Sinbad.mesh.xml");
        this.character.setLocalScale(0.1f);
        character.setLocalTranslation(cam.getLocation().add(-2f, -1f, 0));
        character.getLocalRotation().fromAngleAxis(-1.5708f, Vector3f.UNIT_Y);
    }

    public static Sinbad getInstance() {
        if (instance == null) {
            instance = new Sinbad();
        }
        return instance;
    }
}
