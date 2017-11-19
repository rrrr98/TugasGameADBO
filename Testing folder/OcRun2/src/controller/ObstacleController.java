/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jme3.scene.Node;
import java.util.Iterator;
import java.util.Random;
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
    private float mark2;
    private Random r;
    private float x=3f;
    private ObstacleController() {
        obstacles = Obstacles.getInstance();
        r=new Random();
    }

    public static ObstacleController getInstance() {
        if (instance == null) {
            instance = new ObstacleController();
        }
        return instance;
    }
    public void attachTo(Node node){
        Iterator<Obstacle> it = obstacles.getListObstacles().iterator();
        while (it.hasNext()) {
            node.attachChild(it.next().getObstacle());
        }
    }
    public void update(float tpf){
        if(CharacterController.getInstance().isMark())
        if (mark) {
                randomNumber += tpf;
            } else {
                randomNumber -= tpf;
            }
            for (int i = 0; i < obstacles.getListObstacles().size(); i++) {
                //System.out.println(obstacles.getListObstacles().get(i).getClass().toString());
            if(obstacles.getListObstacles().get(i).isMoveAble())
                    obstacles.getListObstacles().get(i).getObstacle().setLocalTranslation(i*5, 1.15f, randomNumber);
            else {  
                System.out.println(i*5f);
                obstacles.getListObstacles().get(i).getObstacle().setLocalTranslation(i*5f, 1.15f,mark2);
            }
//                System.out.println(randomNumber);
                }
            if (randomNumber > 1.8) {
                mark = false;
            }
            if (randomNumber < -1.8) {
                mark = true;
            }
            if(x<0f){
                mark2=rng();
                x=3f;
            }
            else x-=tpf;
    }
    public float rng(){
        float[] arr={-1f,0f,2f};
        int f=r.nextInt(3);
        return arr[f];
    }
}
