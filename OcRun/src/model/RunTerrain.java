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
public class RunTerrain extends Terrain {

    private static RunTerrain instance;
    private Node[][] terrain;

    private RunTerrain() {
        terrain = new Node[11][3];
        generate();
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
        for (int i = 0; i <= 10; i++) {
            //box create
            Geometry geom = new Geometry("box", box);
            Geometry leftG = new Geometry("box", box);
            Geometry rightG = new Geometry("box", box);
            //set box material
            geom.setMaterial(mat);
            leftG.setMaterial(mat);
            rightG.setMaterial(mat);
            //create node to wrap geometry
            Node mid = new Node("mid");
            Node left = new Node("left");
            Node right = new Node("right");
            mid.attachChild(geom);
            left.attachChild(leftG);
            right.attachChild(rightG);
            //insert node to array
            terrain[i][0] = left;
            terrain[i][1] = mid;
            terrain[i][2] = right;
        }
    }

    public Node[][] getTerrain() {
        return terrain;
    }

    public void setTerrain(Node[][] terrain) {
        this.terrain = terrain;
    }

}
