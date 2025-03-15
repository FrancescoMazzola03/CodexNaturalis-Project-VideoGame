package it.polimi.ingsw.View.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * ControllerJoiningGame class, it's mansion is to manage the scene joiningGame (joiningGame.fxml).
 * This scene is used to let the player choose the game he wants to join.
 */
public class controllerJoiningGame extends ControllerGUI {
    /**
     * List of games that the player can join.
     */
    ArrayList<String> games = new ArrayList<>();
    /**
     * FXML attributes to help managing the scene
     */
    @FXML
    private StackPane stackPane;
    @FXML
    private ImageView imageView;
    @FXML
    private ToggleGroup joinGame = new ToggleGroup();
    @FXML
    private VBox radioButtonContainer;

    /**
     * Method to load the scene
     */
    public void load()  {
        Stage stage = (Stage) stackPane.getScene().getWindow();
        stage.centerOnScreen();
        imageView.fitHeightProperty().bind(stackPane.heightProperty());
        imageView.fitWidthProperty().bind(stackPane.widthProperty());

        for (String creator : gui.getCreators()) {
            games.add(creator);
        }

        for (String game : games) {
            RadioButton radioButton = new RadioButton(game);
            radioButton.getStyleClass().add("generic-radio-button");
            radioButton.setTextFill(javafx.scene.paint.Color.WHITE);
            radioButton.setToggleGroup(joinGame);

            radioButtonContainer.getChildren().add(radioButton);
        }
    }

    /**
     * Method to proceeds after the player clicks on the button to join a game.
     * @param event when the player clicks on the button to join a game
     */
    @FXML
    void playerWantToJoin(MouseEvent event)  {
        RadioButton selectedRadioButton = (RadioButton) joinGame.getSelectedToggle();
        if (selectedRadioButton == null) {
            gui.showError(3);
        } else {
            String selectedGame = selectedRadioButton.getText();
            gui.showSetNicknameAndJoin(0, selectedGame);
        }
    }
}
