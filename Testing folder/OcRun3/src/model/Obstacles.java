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
import java.util.ArrayList;
import java.util.Random;
import static model.Model.manager;
import static model.RunTerrain.LONG_ROUTE;

/**
 *
 * @author Hartanto
 */
public class Obstacles {

    private ArrayList<Obstacle> listObstacles;
    private static Obstacles instance;

    private Obstacles() {
        listObstacles = new ArrayList<Obstacle>();
        generate();
    }

    public static Obstacles getInstance() {
        if (instance == null) {
            instance = new Obstacles();
        }
        return instance;
    }

    public ArrayList<Obstacle> getListObstacles() {
        return listObstacles;
    }

    public void generate() {
        Material mat = manager.loadMaterial("Textures/Terrain/BrickWall/BrickWall.j3m");
        for (int i = 0; i < LONG_ROUTE; i++) {
            if (i % 5 == 0) {
                listObstacles.add(new BlockObstacle());
            } else if (i % 5 == 0) {
                listObstacles.add(new FixedObstacle(i));
            }
        }
    }

    public void setListObstacles(ArrayList<Obstacle> listObstacles) {
        this.listObstacles = listObstacles;
    }

}
