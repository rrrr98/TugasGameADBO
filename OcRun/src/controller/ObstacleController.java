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
import model.RunTerrain;

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
        Obstacle obs;
        for (int i = 0; i < obstacles.getListObstacles().size(); i++) {
            System.out.println(i);
            obs = obstacles.getListObstacles().get(i);
            if (obs.isMoveAble()) {
            Vector3f v = obs.getObstacle().getLocalTranslation().clone();
                if (obs.isMark()) {
                    v.z+=tpf;
                } else {
                    v.z-=tpf;
                }
                obs.setZ(v.z);
                //System.out.println(v.getX());
                obs.getObstacle().setLocalTranslation(v);
                if (v.z > 1.8f) {
                    obs.setMark(false);
                } else if (v.z < -1.8f) {
                    obs.setMark(true);
                }
                obs.getObstacle().setLocalTranslation(v);
                System.out.println(randomNumber);
//                RunTerrain.getIntance().getPath()[i][1].setLocalTranslation(0, 1.15f, randomNumber);
            }
//                System.out.println(randomNumber);

        }
    }
}
