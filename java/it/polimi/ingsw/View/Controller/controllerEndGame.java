package it.polimi.ingsw.View.Controller;

import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.ModelView.GameView;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * ControllerEndGame class, it's mansion is to manage the scene endGame (endGame.fxml).
 */
public class controllerEndGame extends ControllerGUI {
    /**
     * FXML attributes to help managing the scene
     */
    @FXML
    private Text winnersText;
    @FXML
    private VBox winnersList;
    @FXML
    private Text congratsText;
    @FXML
    private AnchorPane paneEndGame;


    /**
     * Method to load the scene
     */
    @Override
    public void load(){
        Stage stage = (Stage) paneEndGame.getScene().getWindow();
        stage.centerOnScreen();
        GameView game = gui.getGameView();
        //trophyImage.setImage(new Image("/path/to/trophy.png"));

        if (game.findWinners().size() == 1) {
            winnersText.setText("And the winner is...");
            winnersText.setStyle("-fx-font-size: " + 30 + "px;");
            Text winnerName = new Text(game.findWinners().get(0).getName());
            winnerName.setStyle("-fx-font-size: " + 30 + "px;");

            winnerName.getStyleClass().add("medieval-text");
            winnersText.getStyleClass().add("medieval-text");
            winnersList.getChildren().add(winnerName);
        } else {
            winnersText.setText("And the winners are...");
            winnersText.setStyle("-fx-font-size: " + 30 + "px;");
            for (Player winner : game.findWinners()) {
                Text winnerName = new Text(winner.getName());
                winnerName.getStyleClass().add("medieval-text");
                winnersText.getStyleClass().add("medieval-text");
                winnerName.setStyle("-fx-font-size: " + 30 + "px;");
                winnersList.getChildren().add(winnerName);
            }
        }
        congratsText.setText("Congrats to y'all for playing this fabulous game!");
    }

    /**
     * Method to start exit the game
     * @param event when the player clicks on the button
     */
 public void finish(MouseEvent event){
        System.exit(0);
 }

}
