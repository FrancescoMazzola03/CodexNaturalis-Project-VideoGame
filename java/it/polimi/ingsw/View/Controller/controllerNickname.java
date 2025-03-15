package it.polimi.ingsw.View.Controller;

import it.polimi.ingsw.ModelView.GameView;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * ControllerNickname class, it's mansion is to manage the scene nickname (nickname.fxml).
 * It allows the user to insert his nickname and create a new player.
 */
public class controllerNickname extends ControllerGUI{
    /** GameView attribute to know the information about the game */
        GameView game;
    /** FXML attributes to help managing the scene */
    @FXML
    private ImageView imageview;
    @FXML
    private StackPane stackPane;
    @FXML
    private TextField labelNickname;

    /**
     * Method to create a new player, it checks if the nickname is not empty and if it is not already used by another player
     * @param event when the player clicks on the button to create a new player
     */
    @FXML
    void createNewPlayer(MouseEvent event)  {

        if (labelNickname.getText().trim().isEmpty()) {
            gui.showError(1);
        } else if (gui.getCreators() == null ||  !gui.getCreators().contains(labelNickname.getText().trim())) {
            gui.closeError();
            gui.showWaitingForPlayers(labelNickname.getText());
        } else {
            gui.showError(0);
        }
    }

    /**
     * Method to load the scene
     */
    @Override
    public void load(){
        Stage stage = (Stage) stackPane.getScene().getWindow();
        stage.centerOnScreen();
        game = gui.getGameView();
        imageview.fitHeightProperty().bind(stackPane.heightProperty());
        imageview.fitWidthProperty().bind(stackPane.widthProperty());
    }
}
