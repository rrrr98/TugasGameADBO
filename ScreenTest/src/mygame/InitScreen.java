/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.niftygui.NiftyJmeDisplay;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.builder.ImageBuilder;
import de.lessvoid.nifty.builder.LayerBuilder;
import de.lessvoid.nifty.builder.PanelBuilder;
import de.lessvoid.nifty.builder.ScreenBuilder;
import de.lessvoid.nifty.controls.button.builder.ButtonBuilder;
import de.lessvoid.nifty.controls.label.builder.LabelBuilder;
import de.lessvoid.nifty.screen.DefaultScreenController;

/**
 *
 * @author Piowman
 */
public class InitScreen {
    Nifty nifty;
    InitScreen(Nifty niftys){
        this.nifty= niftys;
    }
    public void initScreen() {

        nifty.loadStyleFile("nifty-default-styles.xml");
        nifty.loadControlFile("nifty-default-controls.xml");


        nifty.addScreen("start", new ScreenBuilder("start") {{
            controller(new mygame.MyStartScreen(nifty));
            layer(new LayerBuilder("background") {{
                childLayoutCenter();
            image(new ImageBuilder() {{
                filename("Interface/00001.png");
            }});
        }});

        layer(new LayerBuilder("foreground") {{
                childLayoutVertical();

            panel(new PanelBuilder("panel_top") {{
                childLayoutCenter();
                alignCenter();
                backgroundColor("#88f8");
                height("25%");
                width("75%");
                image(new ImageBuilder() {{
                        filename("Interface/269.jpg");
                        valignCenter();
                        alignCenter();
                        height("100%");
                        width("100%");
                    }});
            }});

            panel(new PanelBuilder("panel_mid") {{
                childLayoutCenter();
                backgroundColor("#44f8");
                alignCenter();
                height("50%");
                width("75%");
                image(new ImageBuilder() {{
                        filename("Interface/Nep Logo.jpg");
                        valignCenter();
                        alignCenter();
                        height("100%");
                        width("100%");
                    }});
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
                    control(new ButtonBuilder("StartButton", "Start") {{
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
        }});

    }}.build(nifty));

    nifty.addScreen("hud", new ScreenBuilder("hud") {{
        controller(new DefaultScreenController());

        layer(new LayerBuilder("background") {{
            childLayoutCenter();
            backgroundColor("#000f");
            // <!-- ... -->
        }});

        layer(new LayerBuilder("foreground") {{
            childLayoutHorizontal();
            backgroundColor("#0000");

            panel(new PanelBuilder("panel_left") {{
                childLayoutVertical();
                backgroundColor("#0f08");
                height("100%");
                width("80%");
                // <!-- spacer -->
            }});

            panel(new PanelBuilder("panel_right") {{
                childLayoutVertical();
                backgroundColor("#00f8");
                height("100%");
                width("20%");

                panel(new PanelBuilder("panel_top_right1") {{
                    childLayoutCenter();
                    backgroundColor("#00f8");
                    height("15%");
                    width("100%");
                    control(new LabelBuilder(){{
                        color("#000");
                        text("123");
                        width("100%");
                        height("100%");
                    }});
                }});

                panel(new PanelBuilder("panel_top_right2") {{
                    childLayoutCenter();
                    backgroundColor("#44f8");
                    height("15%");
                    width("100%");
                }});

                panel(new PanelBuilder("panel_bot_right") {{
                    childLayoutCenter();
                    valignCenter();
                    backgroundColor("#88f8");
                    height("70%");
                    width("100%");
                }});
            }});
        }});
    }}.build(nifty));
    nifty.addScreen("exit", new ScreenBuilder("exit") {{
            controller(new mygame.MyStartScreen(nifty));
            layer(new LayerBuilder("background") {{
                childLayoutCenter();
            image(new ImageBuilder() {{
                filename("Interface/owo.png");
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
                childLayoutCenter();
                backgroundColor("#44f8");
                alignCenter();
                height("50%");
                width("75%");
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
        }});

    }}.build(nifty));
    }
    
}
