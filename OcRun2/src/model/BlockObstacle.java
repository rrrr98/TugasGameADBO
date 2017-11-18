/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import com.jme3.material.Material;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import static model.Model.manager;

/**
 *
 * @author Hartanto
 */
public class BlockObstacle extends Obstacle {

    public BlockObstacle() {
        Material mat = manager.loadMaterial("Textures/Terrain/BrickWall/BrickWall.j3m");
        Box box = new Box(0.2f, 0.25f, 1.4f);
        Geometry obsG = new Geometry("obstacle", box);
        obsG.setMaterial(mat);
        obstacle = new Node("obstacle");
        obstacle.attachChild(obsG);
    }

    @Override
    public boolean isMoveAble() {
       return true;
    }
}
