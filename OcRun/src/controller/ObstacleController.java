/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.Iterator;
import model.Obstacle;
import model.Obstacles;

/**
 *
 * @author Hartanto
 */
public class ObstacleController {

    private Obstacles obstacles;
    private static ObstacleController instance;

    private ObstacleController() {
        obstacles = Obstacles.getInstance();
    }

    public ObstacleController getInstance() {
        if (instance == null) {
            instance = new ObstacleController();
        }
        return instance;
    }

    public void insertToWorld() {
        Iterator<Obstacle> it = obstacles.getListObstacles().iterator();
        int i = 3;
        while (it.hasNext()) {
            it.next().getObstacle().setLocalTranslation(2 * i, 1.15f, 0);
            i += 5;
        }
    }
}
