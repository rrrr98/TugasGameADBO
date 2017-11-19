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
        Random rd = new Random();
        for (int i = 10; i < LONG_ROUTE - 5; i++) {
            if (i == LONG_ROUTE - 4 || i == 10) {
                FixedObstacle fo = new FixedObstacle();
                listObstacles.add(fo);
                RunTerrain.getIntance().getPath()[i][1].attachChild(fo.getObstacle());
            }else if (i % 10 == 0) {
                FixedObstacle fo = new FixedObstacle();
                listObstacles.add(fo);
                int rng = rd.nextInt(3);
                RunTerrain.getIntance().getPath()[i][rng].attachChild(fo.getObstacle());
//                fo.getObstacle().setLocalTranslation(RunTerrain.getIntance().getPath()[i][rng].getLocalTranslation());
            }
            else if (i % 9 == 0) {
                BlockObstacle bo = new BlockObstacle();
                listObstacles.add(bo);
                RunTerrain.getIntance().getPath()[i][1].attachChild(bo.getObstacle());
            } 

        }
    }

    public void setListObstacles(ArrayList<Obstacle> listObstacles) {
        this.listObstacles = listObstacles;
    }

}
