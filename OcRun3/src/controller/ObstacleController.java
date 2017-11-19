/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
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
    private float randomNumber;
    private boolean mark;
    private static float x = 0;
    private ObstacleController() {
        obstacles = Obstacles.getInstance();
    }

    public static ObstacleController getInstance() {
        if (instance == null) {
            instance = new ObstacleController();
        }
        return instance;
    }

    public void attachTo(Node node) {
        Iterator<Obstacle> it = obstacles.getListObstacles().iterator();
        while (it.hasNext()) {
            node.attachChild(it.next().getObstacle());
        }
    }

    public void update(float tpf) {
        if (mark) {
            randomNumber += tpf;
        } else {
            randomNumber -= tpf;
        }
        for (int i = 0; i < obstacles.getListObstacles().size(); i++) {
            Obstacle obs = obstacles.getListObstacles().get(i);
            Vector3f v = obs.getObstacle().getLocalTranslation();
            if (obs.isMoveAble()) {
                obs.getObstacle().setLocalTranslation(v.getX(), 1.15f, randomNumber);
            }
            
//                System.out.println(randomNumber);
        }
        if (randomNumber > 1.8) {
            mark = false;
        }
        if (randomNumber < -1.8) {
            mark = true;
        }
    }
}
