package it.polimi.ingsw.View.Controller;

import it.polimi.ingsw.Model.Player;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.List;

/**
 * ControllerWaitingPlayers class, it's mansion is to manage the scene waitingPlayers (waitingPlayers.fxml).
 * It allows the player to wait for the other players to join the game.
 */
public class controllerWaitingPlayers extends ControllerGUI {
    /**
     * FXML attributes to help managing the scene
     */
    @FXML
    private HBox playerContainer;
    @FXML
    private StackPane stackPane;
    @FXML
    private ImageView imageView;

    /**
     * Method to load the scene
     */
    @Override
    public void load() {
        imageView.fitHeightProperty().bind(stackPane.heightProperty());
        imageView.fitWidthProperty().bind(stackPane.widthProperty());

        // Get your name
        String myName = gui.getNickname();

        // Get the list of players
        List<Player> players = gui.getGameView().getPlayers();

        createPlayerBox(myName, true);
        // Create a VBox for each player
        for (Player player : players) {
            if (!player.getName().equals(myName))
                createPlayerBox(player.getName(), true);
        }

        // If there are fewer players than the maximum number, create a VBox for each remaining player slot
        for (int i = players.size(); i < gui.getGameView().getMaxNumPlayers(); i++) {
            createPlayerBox("Waiting for player", false);
        }
    }

    /**
     * Method to create a VBox for each player
     * @param playerName The name of the player
     * @param isReady   Whether the player is ready or not
     */
    @FXML
    private void createPlayerBox(String playerName, boolean isReady) {
        VBox playerBox = new VBox(10);  // Vertical container for each player with spacing

        String statusText = isReady ? playerName + " is ready" : "Waiting for player";
        Text status = new Text(statusText);
        status.setFill(javafx.scene.paint.Color.valueOf("#fffbfb"));
        status.getStyleClass().add("medieval-text");
        status.setFont(new Font(33.0));

        Region spacer = new Region();
        spacer.setPrefHeight(50);  // Adjust this value to move the text further under the image

        playerBox.getChildren().addAll(spacer, status);

        // Make the playerBox grow to fill available horizontal space
        HBox.setHgrow(playerBox, Priority.ALWAYS);

        playerContainer.getChildren().add(playerBox);
        if (gui.getGameView().getMaxNumPlayers() == gui.getGameView().getPlayers().size())
            gui.showPawnChoice();
    }

    /**
     * Method to refresh the scene
     */
    @Override
    public void refresh() {
        Platform.runLater(() -> {
            playerContainer.getChildren().clear();
            this.load();
        });
    }
}
