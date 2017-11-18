/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;

/**
 *
 * @author Hartanto
 */
public class Obstacles {

    private ArrayList<Obstacle> listObstacles;
    private static Obstacles instance;

    private Obstacles() {
        listObstacles = new ArrayList<Obstacle>();
        for (int i = 0; i < 2; i++) {
            listObstacles.add(new BlockObstacle());
            listObstacles.add(new StayingObstacle());
        }
        
        
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

    public void setListObstacles(ArrayList<Obstacle> listObstacles) {
        this.listObstacles = listObstacles;
    }

}
