/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.AnimEventListener;
import com.jme3.animation.Animation;
import com.jme3.animation.LoopMode;
import com.jme3.animation.SpatialTrack;
import com.jme3.app.Application;
import com.jme3.app.state.AbstractAppState;
import com.jme3.bounding.BoundingVolume;
import com.jme3.collision.CollisionResults;
import com.jme3.input.controls.ActionListener;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import java.util.ArrayList;
import java.util.HashMap;
import model.Character;
import model.Obstacle;
import model.Obstacles;
import model.Sinbad;

/**
 *
 * @author Hartanto
 */
public class CharacterController implements AnimEventListener {

    private final float MAX_MOVE = 0.9f;
    private final float GRAVITY = 0.025f;

    private ArrayList<Obstacle> listOfObstacle;
    private static CharacterController instance;
    private Character character;
    private AnimChannel channel1, channel2;
    private AnimControl control;
    private boolean mark = true;
    private ActionListener actionListener;
    private boolean left, right, jump, jumpButtonStatus, jumpStatus, jumpTrigger;
    private float gridPlacement, now, verticalPosition, nowSpeed;
    private Application app;
    private AbstractAppState appState;

    private CharacterController() {
        appState = GameStateController.getInstance();
        app = GameStateController.getInstance().getApp();
        character = Sinbad.getInstance();
        control = character.getModel().getControl(AnimControl.class);
        control.addListener(this);
        channel1 = control.createChannel();
        channel2 = control.createChannel();
        channel1.setAnim("RunBase");
        channel2.setAnim("RunTop");
        character.getModel().setLocalScale(0.1f);
        character.getModel().setLocalTranslation(app.getCamera().getLocation().add(18.2f, 1.5f, -10));
        character.getModel().getLocalRotation().fromAngleAxis(-1.5708f, Vector3f.UNIT_Y);
        listOfObstacle = Obstacles.getInstance().getListObstacles();
        setUpListener();
    }

    public static CharacterController getInstance() {
        if (instance == null) {
            instance = new CharacterController();
        }
        return instance;
    }

    private void setUpListener() {
        actionListener = new ActionListener() {
            public void onAction(String name, boolean keyPressed, float tpf) {
                if (!jumpButtonStatus && name.equals("JumpStart") && !keyPressed) {
                    //System.out.println("masuk jump");
                    jump = true;
                }
                if (name.equals("MoveRight") && !keyPressed) {
                    //System.out.println("masuk right");
                    if (gridPlacement < 0.65f) {
                        right = true;
                    }
                }

                if (name.equals("MoveLeft") && !keyPressed) {
                    //System.out.println("masuk left");
                    if (gridPlacement > -0.65f) {
                        left = true;
                    }
                }
                if (name.equals("Restart") && !keyPressed) {
                    if (!mark) {
                        mark = true;
                        character.getModel().addControl(control);
                        WorldController.getInstance().createAnimation();
                        channel1 = control.createChannel();
                        channel2 = control.createChannel();
                        channel1.setAnim("RunBase");
                        channel2.setAnim("RunTop");
                        if (jumpTrigger) {
                            now = 0;
                            jumpTrigger = false;
                            character.getModel().move(0, -verticalPosition, 0);
                            verticalPosition = 0;
                            jumpStatus = false;
                            jump = false;
                            jumpButtonStatus=false;
                        }
                    }
                }

            }
        };
    }

    public void attatchTo(Node node) {
        node.attachChild(this.character.getModel());
    }

    public void update(float tpf) {
        if (mark) {
            if (left) {
                float move = 2 * 0.015f;
                if (now + move < MAX_MOVE) {
                    character.getModel().move(0, 0, move);
                    now += move;
                    gridPlacement -= move;
                    //System.out.println(gridPlacement);
                } else {
                    left = false;
                    now = 0;
                }
            } else if (right) {
                float move = 2 * 0.015f;
                if (now + move <= MAX_MOVE) {
                    character.getModel().move(0, 0, -move);
                    now += move;
                    gridPlacement += move;
                    //System.out.println(gridPlacement);
                } else {
                    right = false;
                    now = 0;
                }
            } else if (jump) {
                jumpStatus = true;
                jumpTrigger = true;
                jump = false;
                jumpButtonStatus = true;
                channel1.setAnim("JumpStart");
                channel1.setLoopMode(LoopMode.DontLoop);
            }
            if (jumpTrigger && jumpStatus && verticalPosition >= 0) {
//                float move = GRAVITY * 1.0048f - nowSpeed * tpf * 2.553f;
                float move = (0.5475f - nowSpeed) * 0.015f * 6f;
//                System.out.println(tpf);
                if (move > 0) {
                    jump(move);
                    nowSpeed += GRAVITY;
                    verticalPosition += move;
                    if (verticalPosition < 0) {
                        verticalPosition = 0;
                    }
                } else {
                    jumpStatus = false;
                    nowSpeed = GRAVITY;
                }
                System.out.println("masuk jump");
            } else if (jumpTrigger && !jumpStatus) {
                float move = (nowSpeed) * 0.015f * 6f;
                System.out.println(tpf);
                if (verticalPosition > 0) {
                    jump(-move);
                    nowSpeed += GRAVITY;
                    if (verticalPosition - move < 0) {
                        verticalPosition = 0;
                    } else {
                        verticalPosition -= move;
                    }
                } else {
                    channel1.setAnim("RunBase");
                    channel2.setAnim("RunTop");
                    channel1.setLoopMode(LoopMode.Loop);
                    jumpTrigger = false;
                    jumpButtonStatus = false;
                    nowSpeed = GRAVITY;
                    verticalPosition = 0;
                }
                left = false;
                right = false;
            }

            // collision
            CollisionResults results = new CollisionResults();
            for (int i = 0; i < listOfObstacle.size(); i++) {
                BoundingVolume bv = listOfObstacle.get(i).getObstacle().getWorldBound();
                character.getModel().collideWith(bv, results);
                if (results.size() > 40) {
                    //collision
                    // mark = false;
                    System.out.println(appState.isEnabled());
                    //end of collision
                }
            }
        } else {
            character.getModel().removeControl(control);
            control.clearChannels();
            //System.out.println("die");
            WorldController.getInstance().removeTrack();
        }
    }

    public boolean isMark() {
        return mark;
    }

    private void jump(float tpf) {
        //cara naif
        character.getModel().move(0, tpf, 0);
        channel1.setAnim("JumpLoop");
        channel1.setLoopMode(LoopMode.DontLoop);
    }

    @Override
    public void onAnimCycleDone(AnimControl control, AnimChannel channel1, String animName) {
        if (animName.equals("JumpStart")) {
            channel1.setAnim("JumpEnd");
            channel1.setSpeed(1f);
            channel1.setAnim("RunBase");
        }
    }

    @Override
    public void onAnimChange(AnimControl control, AnimChannel channel1, String animName) {
        // unused
    }

    public ActionListener getActionListener() {
        return this.actionListener;
    }

    public Character getCharacter() {
        return character;
    }

    public AnimChannel getChannel1() {
        return channel1;
    }

    public AnimControl getControl() {
        return control;
    }

}
