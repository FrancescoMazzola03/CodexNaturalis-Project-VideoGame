package it.polimi.ingsw.View;


import javafx.scene.Scene;
import it.polimi.ingsw.View.Controller.ControllerGUI;

/**
 * SceneInfo class, is used to save information about a scene.
 */
public class SceneInfo {
    /**
     * Attributes of the class.
     */
    private Scene scene;
    private SceneEnum sceneEnum;
    private ControllerGUI controller;

    /**
     * Constructor of the class.
     * @param sceneEnum the scene enum
     * @param controller the common controller
     * @param scene the scene
     */
    public SceneInfo(Scene scene, SceneEnum sceneEnum, ControllerGUI controller) {
        this.scene = scene;
        this.sceneEnum = sceneEnum;
        this.controller= controller;
    }

    /**
     * Method to obtain the scene.
     * @return the scene
     */
    public Scene getScene() {
        return scene;
    }


    /**
     * Method to obtain the scene enum.
     * @return the scene enum
     */
    public SceneEnum getSceneEnum() {
        return sceneEnum;
    }



    /**
     * Method to obtain the common controller.
     * @return the common controller
     */
    public ControllerGUI getCommonController(){
        return controller;
    }
}

