/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import com.jme3.asset.AssetManager;

/**
 *
 * @author Hartanto
 */
public abstract class Model {

    protected static AssetManager manager;

    public static final void setManager(AssetManager manager) {
        Model.manager = manager;
    }

    public static final AssetManager getManager() {
        return manager;
    }

}
