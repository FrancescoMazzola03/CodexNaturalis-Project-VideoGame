package it.polimi.ingsw.View.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.Objects;

/**
 * ControllerObjectiveCard class, it's mansion is to manage the scene objectiveCard (objectiveCard.fxml).
 * The scene allows the player to choose the objective card and the starter card for the game.
 *
 */
public class controllerObjectiveCard extends ControllerGUI {
    /**
     * FXML attributes to help managing the scene
     */
    @FXML
    private ImageView backStarterImage;
    @FXML
    private RadioButton buttonBackStarterCard;
    @FXML
    private RadioButton buttonFirstObjectiveCard;
    @FXML
    private RadioButton buttonFrontStarterCard;
    @FXML
    private RadioButton buttonSecondObjectiveCard;
    @FXML
    private ImageView firstObjectiveImage;
    @FXML
    private ImageView frontStarterImage;
    @FXML
    private ImageView secondObjectiveImage;
    @FXML
    private StackPane stackPane;

    /**
     * Method to choose the objective card and the starter card for the game
     * @param event when the player clicks on the button to choose the objective card and the starter card
     */
    @FXML
    void playerChoseObjectiveCard(MouseEvent event) {
        int objectiveCard;
        boolean starterCardSide;
        if (buttonFrontStarterCard.isSelected() && buttonFirstObjectiveCard.isSelected()) {

            objectiveCard = 1;
            starterCardSide = true;
        }
        else if (buttonFrontStarterCard.isSelected() && buttonSecondObjectiveCard.isSelected()) {

           objectiveCard = 2;
           starterCardSide = true;

        }
        else if (buttonBackStarterCard.isSelected() && buttonFirstObjectiveCard.isSelected()) {

           objectiveCard = 1;
           starterCardSide = false;
        }
        else if (buttonBackStarterCard.isSelected() && buttonSecondObjectiveCard.isSelected()) {

            objectiveCard = 2;
            starterCardSide = false;
        }
        else {
            return;


        }
        gui.showwaiting4obj(objectiveCard,starterCardSide);
    }

    /**
     * Method to load the scene
     */
    @Override
    public void load() {

        Stage stage = (Stage) stackPane.getScene().getWindow();
        stage.centerOnScreen();

        firstObjectiveImage.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Cards/front/" + gui.getGameView().getPlayerByUsername(gui.getNickname()).possibleObjectives().get(0).getNumCard()+ ".png"))));
        secondObjectiveImage.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Cards/front/" + gui.getGameView().getPlayerByUsername(gui.getNickname()).possibleObjectives().get(1).getNumCard()+ ".png"))));
        // the front and back in the starter cards are reversed because our logic is opposite to that of the game
        backStarterImage.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Cards/front/" + gui.getGameView().getPlayerByUsername(gui.getNickname()).getPlayerStarterCard().getNumCard() + ".png"))));
        frontStarterImage.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Cards/back/" + gui.getGameView().getPlayerByUsername(gui.getNickname()).getPlayerStarterCard().getNumCard() + ".png"))));



    }
}
