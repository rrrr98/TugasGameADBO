/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.builder.ImageBuilder;
import de.lessvoid.nifty.builder.LayerBuilder;
import de.lessvoid.nifty.builder.PanelBuilder;
import de.lessvoid.nifty.builder.ScreenBuilder;
import de.lessvoid.nifty.controls.button.builder.ButtonBuilder;

/**
 *
 * @author Ketua : Kevin R
 */
public class InitScreen {
    //attribute
    private Nifty nifty;
    /**
     * constructor
     * @param niftys 
     */
    public InitScreen(Nifty niftys){
        this.nifty= niftys;
        this.nifty.setIgnoreKeyboardEvents(true);
        MyStartScreen.getInstance().init(niftys);
    }
    /**
     * init the screen
     */
    public void initScreen() {

    nifty.loadStyleFile("nifty-default-styles.xml");
    nifty.loadControlFile("nifty-default-controls.xml");

////Screen Start
    nifty.addScreen("start", new ScreenBuilder("start") {{
        controller(MyStartScreen.getInstance());
        layer(new LayerBuilder("background") {{
            childLayoutCenter();
            image(new ImageBuilder() {{
                filename("Interface/01.jpg");
            }});
        }});

        layer(new LayerBuilder("foreground") {{
                childLayoutVertical();

            panel(new PanelBuilder("panel_top") {{
                childLayoutCenter();
                alignCenter();
                height("25%");
                width("75%");
                image(new ImageBuilder() {{
                        filename("Interface/OrgeRun.png");
                        valignCenter();
                        alignCenter();
                        height("100%");
                        width("100%");
                    }});
            }});
            panel(new PanelBuilder("panel_mid_top") {{
                childLayoutCenter();
                alignCenter();
                height("15%");
                width("75%");
                control(new ButtonBuilder("StartButton", "Start") {{
                      alignCenter();
                      valignCenter();
                      height("50%");
                      width("50%");
                      visibleToMouse(true);
                      interactOnClick("startGame()");
                }});
            }});

            panel(new PanelBuilder("panel_mid_bot") {{
                childLayoutCenter();
                alignCenter();
                height("15%");
                width("75%");
                control(new ButtonBuilder("ControlButton", "Control") {{
                      alignCenter();
                      valignCenter();
                      height("50%");
                      width("50%");
                      visibleToMouse(true);
                      interactOnClick("toControl()");
                }});
            }});

            panel(new PanelBuilder("panel_bottom") {{
                childLayoutCenter();
                alignCenter();
                height("15%");
                width("75%");
                control(new ButtonBuilder("AboutButton", "About") {{
                      alignCenter();
                      valignCenter();
                      height("50%");
                      width("50%");
                      visibleToMouse(true);
                      interactOnClick("toAbout()");
                }});
            }}); 
            panel(new PanelBuilder("panel_bot_bottom") {{
                childLayoutCenter();
                alignCenter();
                height("15%");
                width("75%");
                control(new ButtonBuilder("QuitButton", "Quit") {{
                      alignCenter();
                      valignCenter();
                      height("50%");
                      width("50%");
                      visibleToMouse(true);
                      interactOnClick("quitGame()");
                }});
            }});
        }});

    }}.build(nifty));
    
////Screen Control
    nifty.addScreen("control", new ScreenBuilder("control") {{
        controller(MyStartScreen.getInstance());
        layer(new LayerBuilder("background") {{
            childLayoutCenter();
            image(new ImageBuilder() {{
                filename("Interface/02.jpg");
            }});
            image(new ImageBuilder() {{
                        filename("Interface/control.png");
                        valignCenter();
                        alignCenter();
                        height("100%");
                        width("100%");
            }});
        }});

        layer(new LayerBuilder("foreground") {{
            childLayoutVertical();
            panel(new PanelBuilder("panel_top") {{
                childLayoutCenter();
                alignCenter();
                height("85%");
                width("100%");
            }});
            panel(new PanelBuilder("panel_bot") {{
                childLayoutCenter();
                alignCenter();
                height("15%");
                width("100%");
                control(new ButtonBuilder("BackButton", "Back") {{
                      alignRight();
                      valignCenter();
                      height("50%");
                      width("20%");
                      visibleToMouse(true);
                      interactOnClick("toStart()");
                }});
            }});
        }});
    }}.build(nifty));
    
////Screen About
    nifty.addScreen("about", new ScreenBuilder("about") {{
        controller(MyStartScreen.getInstance());
        layer(new LayerBuilder("background") {{
            childLayoutCenter();
            image(new ImageBuilder() {{
                filename("Interface/03.jpg");
            }});
            image(new ImageBuilder() {{
                        filename("Interface/AboutUs.png");
                        valignCenter();
                        alignCenter();
                        height("100%");
                        width("100%");
            }});
        }});

        layer(new LayerBuilder("foreground") {{
            childLayoutVertical();
            
            panel(new PanelBuilder("panel_top") {{
                childLayoutCenter();
                alignCenter();
                height("85%");
                width("100%");
            }});
            panel(new PanelBuilder("panel_bot") {{
                childLayoutCenter();
                alignCenter();
                height("15%");
                width("100%");
                control(new ButtonBuilder("BackButton", "Back") {{
                      alignRight();
                      valignCenter();
                      height("50%");
                      width("20%");
                      visibleToMouse(true);
                      interactOnClick("toStart()");
                }});
            }});
        }});
    }}.build(nifty));
        
////Screen HUD
    nifty.addScreen("hud", new ScreenBuilder("hud") {{
        controller(MyStartScreen.getInstance());
        layer(new LayerBuilder("background") {{
                childLayoutVertical();
                image(new ImageBuilder() {{
                        filename("Interface/hud.png");
                        valignCenter();
                        alignCenter();
                        height("100%");
                        width("100%");
            }});
        }});
        

    }}.build(nifty));
    
//// Screen Exit   
    nifty.addScreen("exit", new ScreenBuilder("exit") {{
            controller(MyStartScreen.getInstance());
            layer(new LayerBuilder("background") {{
                childLayoutCenter();
                image(new ImageBuilder() {{
                    filename("Interface/04.jpg");
                    valignCenter();
                        alignCenter();
                        height("100%");
                        width("100%");
            }});
                image(new ImageBuilder() {{
                        filename("Interface/gameover.png");
                        valignCenter();
                        alignCenter();
                        height("100%");
                        width("100%");
            }});
        }});

        layer(new LayerBuilder("foreground") {{
                childLayoutVertical();

            panel(new PanelBuilder("panel_top") {{
                childLayoutCenter();
                alignCenter();
                height("25%");
                width("75%");
            }});

            panel(new PanelBuilder("panel_mid") {{
                childLayoutHorizontal();
                alignCenter();
                height("50%");
                width("75%");  
                //control(new LabelBuilder("keyEventLabelId", score.getHighscore()));
            }});
            
            panel(new PanelBuilder("panel_bottom") {{
                childLayoutHorizontal();
                alignCenter();
                height("25%");
                width("75%");

                panel(new PanelBuilder("panel_bottom_left") {{
                    childLayoutCenter();
                    valignCenter();
                    height("50%");
                    width("50%");
                    control(new ButtonBuilder("RestartButton", "Restart") {{
                      alignCenter();
                      valignCenter();
                      height("50%");
                      width("50%");
                      visibleToMouse(true);
                      interactOnClick("startGame()");
                    }});
                }});

                panel(new PanelBuilder("panel_bottom_right") {{
                    childLayoutCenter();
                    valignCenter();
                    height("50%");
                    width("50%");
                    control(new ButtonBuilder("QuitButton", "Main Menu") {{
                      alignCenter();
                      valignCenter();
                      height("50%");
                      width("50%");
                      visibleToMouse(true);
                      interactOnClick("toStart()");
                    }});
                }});
            }}); 
        }});

    }}.build(nifty));
    }
    
}
