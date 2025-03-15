package it.polimi.ingsw.View.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.io.IOException;

/**
 * ControllerNumbPlayer class, it's mansion is to manage the scene numbPlayer (numbPlayer.fxml).
 * It allows the player to choose the number of players for the game.
 */
public class controllerNumbPlayer extends ControllerGUI {
        /**
         * FXML attributes to help managing the scene
         */
        @FXML
        private ToggleGroup numberPlayer;
        @FXML
        private ImageView imageview;
        @FXML
        private StackPane stackPane;


        /**
         * Method to choose the number of players for the game
         * @param event when the player clicks on the button to choose the number of players
         */
        @FXML
        void choiceNumberPlayer(MouseEvent event)  {
                ToggleButton selectedToggleButton = (ToggleButton) numberPlayer.getSelectedToggle();
                if (selectedToggleButton != null) {
                        int selectedNumber = Integer.parseInt(selectedToggleButton.getText());
                        gui.showSetNicknameAndJoin(selectedNumber,null);
                }
        }


        /**
         * Method to load the scene
         */
        @Override
        public void load(){
                imageview.fitHeightProperty().bind(stackPane.heightProperty());
                imageview.fitWidthProperty().bind(stackPane.widthProperty());
        }
}