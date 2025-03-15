package it.polimi.ingsw.View.Controller;

import it.polimi.ingsw.Model.State;
import it.polimi.ingsw.ModelView.GameView;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * ControllerWaitObj class, it's mansion is to manage the scene waitObj (waitObj.fxml).
 * The Scene is shown when the player already chose the objective and starter card but is waiting for the other players to choose these one
 */
public class controllerWaitObj extends ControllerGUI{
    /**
     * FXML attributes to help managing the scene
     */
    @FXML
     private ImageView imageview;
    @FXML
    private StackPane stackPane;

    /**
     * Method to load the scene
     */
    @Override
    public void load(){
        Stage stage = (Stage) stackPane.getScene().getWindow();
        stage.centerOnScreen();
        imageview.fitHeightProperty().bind(stackPane.heightProperty());
        imageview.fitWidthProperty().bind(stackPane.widthProperty());
    }

    /**
     * Method to refresh the scene
     */
    @Override
    public void refresh(){
         Platform.runLater(() -> {
            GameView game = gui.getGameView();
            if (game.getState().equals(State.RUNNING)) {
                gui.showBoard();
            }
        });
    }
}