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
import java.util.Random;
import static model.Model.manager;

/**
 *
 * @author dsa
 */
public class FixedObstacle extends Obstacle {

    private int x, y;
    private Random rd;
    public FixedObstacle(int x) {
        Material mat = manager.loadMaterial("Textures/Terrain/BrickWall/BrickWall.j3m");
        Box box = new Box(0.2f, 1.4f, 0.4f);
        Geometry obsG = new Geometry("obstacle", box);
        obsG.setMaterial(mat);
        obstacle = new Node("obstacle");
        obstacle.attachChild(obsG);
        obstacle.setLocalTranslation(0, 1.15f, 0);
        rd = new Random();
        int random = rd.nextInt(2);
        this.x = x;
        this.y = random;
    }
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
    
    public void setX(int x) {
        this.x = x;
    }
    
    public void setY(int y) {
        this.y = y;
    }
    @Override
    public boolean isMoveAble() {
        return false;
    }
}
