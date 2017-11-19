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
 * @author Hartanto
 */
public class RunTerrain extends Terrain {

    public static final int LONG_ROUTE = 100;
    private static RunTerrain instance;
    private Node[][] terrain;

    private RunTerrain() {
        terrain = new Node[LONG_ROUTE][3];
        generate();
    }

    @Override
    public int getLongRoute() {
        return LONG_ROUTE;
    }

    public static RunTerrain getIntance() {
        if (instance == null) {
            instance = new RunTerrain();
        }
        return instance;
    }

    protected void generate() {
        Material mat = manager.loadMaterial("Textures/Terrain/BrickWall/BrickWall.j3m");
        Box box = new Box(1f, 1f, 1f);
        
        Box ob = new Box(0.2f, 1.4f, 0.4f);
        Random rd = new Random();
        for (int i = 0; i < LONG_ROUTE; i++) {
            //box create
            Geometry geom = new Geometry("box", box);
            Geometry leftG = new Geometry("box", box);
            Geometry rightG = new Geometry("box", box);
            //test
            Geometry obsG = new Geometry("obs", ob);
            //set box material
            geom.setMaterial(mat);
            leftG.setMaterial(mat);
            rightG.setMaterial(mat);
            obsG.setMaterial(mat);

            //create node to wrap geometry
            Node mid = new Node("mid");
            Node left = new Node("left");
            Node right = new Node("right");
            Node obs = new Node("obs");
            
            mid.attachChild(geom);
            left.attachChild(leftG);
            right.attachChild(rightG);
            obs.attachChild(obsG);
            //insert node to array
            terrain[i][0] = left;
            terrain[i][1] = mid;
//            terrain[i][1].attachChild(obs);
            terrain[i][2] = right;
        }
    }

    @Override
    public Node[][] getPath() {
        return terrain;
    }

    public void setPath(Node[][] terrain) {
        this.terrain = terrain;
    }

}
