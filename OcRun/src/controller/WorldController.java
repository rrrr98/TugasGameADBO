/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jme3.scene.Node;
import model.RunTerrain;

/**
 *
 * @author Hartanto
 */
public class WorldController {

    public final Node world;
    public final RunTerrain terrain;
    private static WorldController instance;

    private WorldController() {
        world = new Node("Terrain");
        terrain = RunTerrain.getIntance();
    }

    public static WorldController getInstance() {
        if (instance == null) {
            instance = new WorldController();
        }
        return instance;
    }

    private void generateWorld() {
        Node[][] terrain = this.terrain.getTerrain();
        for (int i = 0; i < terrain.length; i++) {
            terrain[i][1].setLocalTranslation(2 * i, 0, 0);
            terrain[i][0].setLocalTranslation(2 * i, 0, -2);
            terrain[i][2].setLocalTranslation(2 * i, 0, 2);
        }
    }
}
