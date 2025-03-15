package it.polimi.ingsw.View.Controller;

import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.ModelView.GameView;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Controller class for the last turn popup.
 * A last turn popup appears when it's the last turn of the game.
 */
public class controllerLastTurn extends ControllerGUI {
    /**
     * GameView attribute to get the game status
     */
    GameView game;
    /**
     * FXML attributes to help managing the scene
     */
    @FXML
    private AnchorPane paneStation;
    @FXML
     private Text textLastTurn;

    /**
     * Method to load the scene
     */
    @Override
    public void load(){
        Stage stage = (Stage) paneStation.getScene().getWindow();
        stage.centerOnScreen();
        game = gui.getGameView();
        int myposition = game.getPlayers().indexOf(game.getPlayerByUsername(gui.getNickname()));
        int currentpos = game.getPlayers().indexOf(game.getCurrentPlayer());
        Player leader = game.getPlayers().get(0);
        for (Player p : game.getPlayers()){
            if(p.getPoints() > leader.getPoints()){
                leader = p;
            }
        }
        if(myposition < currentpos){
            if(leader.getName().equals(gui.getNickname()))
                textLastTurn.setText("It's the last turn of the game! You are the current leader and you will no longer play");
            else
                textLastTurn.setText("It's the last turn of the game! You will no longer play as " +leader.getName() + " has reached"+ leader.getPoints() +"points!");
        }
        else{
            textLastTurn.setText("It's the last turn of the game!. "+ leader.getName()+" has reached "+ leader.getPoints() +" points and you will play one more turn and then the game will end!");
        }

    }
}
