package it.polimi.ingsw.View.Controller;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Objects;

/**
 * Controller for the pawn selection scene (pawn.fxml)
 * The scene allows the player to choose the color of his pawn
 */
public class PawnSelectionController extends ControllerGUI {
    /**
     * Attributes of the class
     * pawns: array of labels to save who chose the pawn
     * pawnsimage: array of imageviews to show the pawns
     */
    Label[] pawns;
    ImageView[] pawnsimage;
    /**
     * FXML attributes to help managing the scene
     */
    @FXML
    private ImageView pawnRed;
    @FXML
    private ImageView pawnBlue;
    @FXML
    private ImageView pawnGreen;
    @FXML
    private ImageView pawnYellow;
    @FXML
    private ImageView pawnBlack;
    @FXML
    private Label labelRed;
    @FXML
    private Label labelBlue;
    @FXML
    private Label labelGreen;
    @FXML
    private Label labelYellow;
    @FXML
    private Label labelBlack;


    /**
     * Method to load the scene
     */
    @Override
    @FXML
    public void load() {
        // Set the images for the colored circles
        pawnRed.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Pawns/CODEX_pion_rouge.png"))));
        pawnBlue.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Pawns/CODEX_pion_bleu.png"))));
        pawnGreen.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Pawns/CODEX_pion_vert.png"))));
        pawnYellow.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Pawns/CODEX_pion_jaune.png"))));
        pawnBlack.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Pawns/CODEX_pion_noir.png"))));

        //Set the event of selection for each circle
        pawnRed.setOnMouseClicked(event -> gui.showObjectiveCards(1));
        pawnBlue.setOnMouseClicked(event -> gui.showObjectiveCards(2));
        pawnGreen.setOnMouseClicked(event -> gui.showObjectiveCards(3));
        pawnYellow.setOnMouseClicked(event -> gui.showObjectiveCards(4));
        pawnBlack.setOnMouseClicked(event -> gui.showObjectiveCards(5));
         pawns = new Label[]{labelRed, labelBlue, labelGreen, labelYellow, labelBlack};
        pawnsimage = new ImageView[]{pawnRed, pawnBlue, pawnGreen, pawnYellow, pawnBlack};
    }

    /**
     * Method to refresh the scene
     */
    @Override
    public void refresh() {
        Platform.runLater(() -> {
            for (int i = 0; i<5; i++){
                if (!gui.getGameView().getPawnOwners()[i].equals("none")) {
                    pawns[i].setText("Taken by " + gui.getGameView().getPawnOwners()[i]);
                    pawns[i].setVisible(true);
                    pawnsimage[i].setOnMouseClicked(null);

                }
        }
        });
        }
}


