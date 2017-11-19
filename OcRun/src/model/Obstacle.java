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
    protected float x,y,z;
    protected boolean mark;
    protected Node obstacle;
    public Node getObstacle() {
        return obstacle;
    }

    public void setObstacle(Node obstacle) {
        this.obstacle = obstacle;
    }
    
    public abstract boolean isMoveAble();

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public boolean isMark() {
        return mark;
    }

    public void setMark(boolean mark) {
        this.mark = mark;
    }
    
}
