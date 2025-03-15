package it.polimi.ingsw.View.Controller;

import it.polimi.ingsw.ModelView.GameView;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

import static java.lang.Object.*;

/**
 * ControllerMenu class, it's mansion is to manage the scene menu (menu.fxml).
 * It allows the player to create a new game or join an existing one.
 */
public class controllerMenu extends ControllerGUI{
    /** GameView attribute to know the information about the game */
    GameView game;
    /**
     * FXML attributes to help managing the scene
     */
    @FXML
    private ImageView imageview;
    @FXML
    private StackPane stackPane;

    /**
     * Method to create a new game
     * @param event when the player clicks on the create game button
     */
    @FXML
    void createGame(MouseEvent event)  {
        gui.showChoiceNumberPlayer();
    }

    /**
     * Method to join an existing game
     * @param event when the player clicks on the join game button
     */
    @FXML
    void joinGame(MouseEvent event)  {
            gui.showAvailableGame();
    }

    /**
     * Method to load the scene
     */
    public void load(){
        Stage stage = (Stage) stackPane.getScene().getWindow();
        stage.centerOnScreen();
        game = gui.getGameView();
        imageview.fitHeightProperty().bind(stackPane.heightProperty());
        imageview.fitWidthProperty().bind(stackPane.widthProperty());
    }

}
