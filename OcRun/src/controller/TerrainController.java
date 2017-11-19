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
 * @author Ketua : Kevin R
 */
public class TerrainController {
    private static TerrainController instance;
    private Terrain terrain;
    /**
     * constructor
     */
    private TerrainController(){
        terrain = RunTerrain.getIntance();
        generate();
    }
    /**
     * getter instance
     * @return instance
     */
    public static TerrainController getInstance(){
        if(instance == null)
            instance = new TerrainController();
        return instance;
    }
    /**
     * generate terrain
     */
    private void generate(){
        Node[][]terrain = this.terrain.getPath();
        for (int i = 0; i < terrain.length; i++) {
            terrain[i][1].setLocalTranslation(2 * i, 0, 0);
            terrain[i][0].setLocalTranslation(2 * i, 0, -2);
            terrain[i][2].setLocalTranslation(2 * i, 0, 2);
        }
        
    }
    /**
     * attaach to node
     * @param node 
     */
    public void attachTo(Node node){
        Node[][] terrain = this.terrain.getPath();
        for (Node[] node1 : terrain){
            for(Node node2 : node1){
                node.attachChild(node2);
            }
        }
    }
    /**
     * mendapatkan panjang route
     * @return terrain length
     */
    public int getRouteLong(){
        return terrain.getLongRoute();
    }
}
