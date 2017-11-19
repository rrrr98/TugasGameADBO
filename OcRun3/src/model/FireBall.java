/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import com.jme3.effect.ParticleEmitter;
import com.jme3.effect.ParticleMesh;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Sphere;
import static model.Model.manager;

/**
 *
 * @author dsa
 */
public class FireBall extends Character{
    
    public static FireBall instance;

    private FireBall() {
        Node fireNode=new Node();
        /*Material mat = new Material(manager,"Common/MatDefs/Misc/Particle.j3md");
        mat.setTexture("Texture", manager.loadTexture("Effects/Explosion/flame.png"));
        Sphere ball = new Sphere(10,10,10);
        Geometry geom = new Geometry("ball", ball);
        geom.setMaterial(mat);
        this.model=geom;
        this.model.setLocalScale(10f);
        model.setLocalTranslation(cam.getLocation().add(-2f,3f, 0));*/
        
    ParticleEmitter fire =
            new ParticleEmitter("Emitter", ParticleMesh.Type.Triangle, 30);
    Material mat_red = new Material(manager,
            "Common/MatDefs/Misc/Particle.j3md");
    mat_red.setTexture("Texture", manager.loadTexture(
            "Effects/Explosion/flame.png"));
    fire.setMaterial(mat_red);
    fire.setImagesX(2);
    fire.setImagesY(2); // 2x2 texture animation
    fire.setEndColor(  new ColorRGBA(.2f,.2f, .2f,.2f));   // red
    fire.setStartColor(new ColorRGBA(.8f, .8f, .8f, .8f)); // yellow
    fire.getParticleInfluencer().setInitialVelocity(new Vector3f(0, 2, 0));
    fire.setStartSize(0.5f);
    fire.setEndSize(0.1f);
    fire.setLowLife(0.1f);
    fire.setHighLife(0.3f);
    fire.getParticleInfluencer().setVelocityVariation(0.3f);
    fire.setLocalTranslation(new Vector3f(16.5f, 2f, 0f));
    fireNode.attachChild(fire);
    model=fireNode;

    }

    public static FireBall getInstance() {
        if (instance == null) {
            instance = new FireBall();
        }
        return instance;
   
}
}
