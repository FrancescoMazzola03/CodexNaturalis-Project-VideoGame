package it.polimi.ingsw.View.Controller;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Controller class for the exit popup.
 * An exit popup appears every time the player left the game.
 */
public class controllerExit extends ControllerGUI{

    /**
     * FXML attributes to help managing the scene
     */
    @FXML
    private AnchorPane paneStation;

    /**
     * Method to load the scene
     */
    @Override
    public void load(){
        Stage stage = (Stage) paneStation.getScene().getWindow();
        stage.centerOnScreen();
        stage.setOnCloseRequest(e -> System.exit(0));

    }


}
