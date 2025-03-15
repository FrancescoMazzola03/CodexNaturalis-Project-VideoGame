package it.polimi.ingsw.View.Controller;


import it.polimi.ingsw.ModelView.GameView;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

/**
 * Controller class for the error popup.
 * An error popup appears every time there is an error.
 */
public class controllerError extends ControllerGUI {
    /**
     * FXML attributes to help managing the scene
     */
    @FXML
    private Text textError;

    /**
     * When player clicks on the button, the error popup will be close.
     */
    public void errorAcknowledged(){
        gui.closeError();

    }

    /**
     * Method to set the text of the error popup.
     * @param caseError is the type of error that will be shown in the popup.
     */
    public void setTextError(int caseError){
        switch (caseError){
            case 0:
                textError.setText("Nickname already taken");
                textError.setLayoutX(150);
                break;
            case 1:
                textError.setText("Nickname is empty");
                textError.setLayoutX(190);

                break;
            case 2:
                textError.setText("Error in the connection");
                break;
            case 3:
                textError.setText("Select a game to join");
                break;
            case 4:
                textError.setText("There aren't players in the game,create one!");
                textError.setLayoutX(60);
                break;

        }
    }

}
