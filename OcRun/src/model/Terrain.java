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
public abstract class Terrain extends Model {

    protected abstract void generate();
    public abstract Node[][] getPath();
    public abstract void setPath(Node[][] path);
    public abstract int getLongRoute();
}
