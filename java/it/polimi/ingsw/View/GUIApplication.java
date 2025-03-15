package it.polimi.ingsw.View;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

import it.polimi.ingsw.View.Controller.ControllerGUI;

/**
 * GUIApplication class, it's mansion is t load and set scenes by calling the controllers of these.
 * It extends Application to use JavaFX.
 */
public class GUIApplication extends Application {
    /**
     * Attributes of the class
     * primaryStage is the main stage of the application
     * popUpStage is the stage of the pop up
     * scenes is the list of all the scenes
     * controller is the controller of the scenes
     * gui is the GUI of the application
     * currentScene is the scene that is currently active
     * instance is the instance of the class, it's static because it's called before created
     */
    private Stage primaryStage, popUpStage;
    private ArrayList<SceneInfo> scenes = new ArrayList<>();
    private static ControllerGUI controller;
    private static GUI gui;
    private SceneEnum currentScene;
    private static GUIApplication instance;

    /**
     * Method to get the instance of the class
     * @return the instance of the class
     */
    public static GUIApplication getInstance() {
        return instance;
    }

    /**
     * Method to initialize the GUIApplication
     */
    @Override
    public void init() {
        instance = this;
    }


    /**
     * Method to set the GUI
     * @param gui the gui to set
     */
        public static void setGui(GUI gui) {
        GUIApplication.gui = gui;

        }

    /**
     * Method to start the application
     * @param primaryStage the main stage of the application
     * @throws Exception if the scene is not found
     */
        public void start(Stage primaryStage) throws Exception {

        loadScenes();
        this.primaryStage = primaryStage;
        setActiveScene(SceneEnum.MENU);

    }

    /**
     * Method to load the scenes
     */
    public void loadScenes(){
        FXMLLoader loader;
        Parent root;

        for (int i = 0; i < SceneEnum.values().length; i++) {
            loader = new FXMLLoader(getClass().getResource(SceneEnum.values()[i].value()));
            try {
                root = loader.load();
                controller = loader.getController();
                controller.setGui(this.gui);
                scenes.add(new SceneInfo(new Scene(root), SceneEnum.values()[i], controller));

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    /**
     * Method to get the index of a scene
     * @param sceneName the scene to get the index of
     * @return the index of the scene
     */
    private int getSceneIndex(SceneEnum sceneName) {
        for (int i = 0; i < scenes.size(); i++) {
            if (scenes.get(i).getSceneEnum().equals(sceneName))
                return i;
        }
        return -1;
    }

    /**
     * Method to close the pop up stage
     */
    public void closePopUpStage() {
        if (popUpStage != null)
            popUpStage.hide();

    }


    /**
     * Method to set the active scene
     * @param scene the scene to set as active
     */
    public void setActiveScene(SceneEnum scene) {

        currentScene = scene;
        this.primaryStage.setTitle("CodexNaturalis");
        int index = getSceneIndex(scene);
        if (index != -1) {
            SceneInfo s = scenes.get(getSceneIndex(scene));
            this.primaryStage.setAlwaysOnTop(true);
            this.primaryStage.centerOnScreen();
            this.primaryStage.setScene(s.getScene());
            this.primaryStage.show();
            s.getCommonController().load();

        }
    }

    /**
     * Method to open a pop up
     * @param scene the scene to open
     */
    public void openPopup(SceneEnum scene) {
        popUpStage = new Stage();
        popUpStage.setTitle("Info");
        popUpStage.setResizable(false);
        popUpStage.setScene(scenes.get(getSceneIndex(scene)).getScene());

        popUpStage.show();

        popUpStage.setX(primaryStage.getX() + (primaryStage.getWidth() - scenes.get(getSceneIndex(scene)).getScene().getWidth()) * 0.5);
        popUpStage.setY(primaryStage.getY() + (primaryStage.getHeight() - scenes.get(getSceneIndex(scene)).getScene().getHeight()) * 0.5);
        popUpStage.setAlwaysOnTop(true);
    }

    /**
     * Method to load the otherStation scene and set the correct nickname
     * @param nickname the nickname of the player to show the station of
     */
    public void setOtherStation(String nickname){
        popUpStage.setTitle("Player " + nickname+"'s station");

        scenes.get(getSceneIndex(SceneEnum.OTHERSTATION)).getCommonController().load(nickname);
    }

    /**
     * Method to get the pop up stage
     * @return the pop up stage
     */
    public Stage getPopup(){
        return this.popUpStage;
    }

    /**
     * Method to set the error
     * @param caseError the type of error to set
     */
    public void setError(int caseError) {
        scenes.get(getSceneIndex(SceneEnum.ERROR)).getCommonController().setTextError(caseError);
    }

    /**
     * Method to run the application
     */
    public static void run() {
        launch();
    }

    /**
     * Method to load the popup for scene lastTurn
     */
    public void showLastTurn(){
        scenes.get(getSceneIndex(SceneEnum.LASTTURN)).getCommonController().load();
    }

    /**
     * Method to load the popup for scene exit
     */
    public void caseExit(){
        scenes.get(getSceneIndex(SceneEnum.EXIT)).getCommonController().load();
    }


    /**
     * Method to refresh the scene
     */
    public void refresh(){
        scenes.get(getSceneIndex(currentScene)).getCommonController().refresh();
    }

    /**
     * Method to handle when player place a card in an invalid position
     * @param message the error message to show
     */
    public void handleInvalidUser(String message){
        scenes.get(getSceneIndex(currentScene)).getCommonController().handleInvalidPosition(message);
    }

    /**
     * Method to show a message that a player sent in the chat
     * @param message message to show
     * @param username username of the player that sent the message
     */
    public void showMessage (String message, String username){
        scenes.get(getSceneIndex(currentScene)).getCommonController().showMessage(message, username);
    }
}
