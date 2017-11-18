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
public class World extends Node{
    private static World instance;
    private Obstacles obstacles;
    private Terrain terrain;

    private World() {
        terrain = RunTerrain.getIntance();
        obstacles = Obstacles.getInstance();
    }

    public static World getInstance() {
        if (instance == null) {
            instance = new World();
        }
        return instance;
    }

    public Obstacles getObstacles() {
        return obstacles;
    }

    public void setObstacles(Obstacles obstacles) {
        this.obstacles = obstacles;
    }

    public Terrain getTerrain() {
        return terrain;
    }

    public void setTerrain(Terrain terrain) {
        this.terrain = terrain;
    }

}
