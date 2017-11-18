/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import com.jme3.scene.Node;

/**
 *
 * @author Hartanto
 */
public abstract class Obstacle extends Model {

    protected Node obstacle;

    public Node getObstacle() {
        return obstacle;
    }

    public void setObstacle(Node obstacle) {
        this.obstacle = obstacle;
    }
    public abstract boolean isMoveAble();
    

}
