/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jme3.scene.Node;
import model.RunTerrain;
import model.Terrain;

/**
 *
 * @author Jimz
 */
public class TerrainController {
    private static TerrainController instance;
    private Terrain terrain;
    private TerrainController(){
        terrain = RunTerrain.getIntance();
        generate();
    }
    public static TerrainController getInstance(){
        if(instance == null)
            instance = new TerrainController();
        return instance;
    }
    private void generate(){
        Node[][]terrain = this.terrain.getPath();
        for (int i = 0; i < terrain.length; i++) {
            terrain[i][1].setLocalTranslation(2 * i, 0, 0);
            terrain[i][0].setLocalTranslation(2 * i, 0, -2);
            terrain[i][2].setLocalTranslation(2 * i, 0, 2);
        }
    }
    public void attachTo(Node node){
        Node[][] terrain = this.terrain.getPath();
        for (Node[] node1 : terrain){
            for(Node node2 : node1){
                node.attachChild(node2);
            }
        }
    }
    public int getRouteLong(){
        return terrain.getLongRoute();
    }
}
