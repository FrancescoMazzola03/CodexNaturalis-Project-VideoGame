package it.polimi.ingsw.View.Controller;

import it.polimi.ingsw.EventHandling.EventType;
import it.polimi.ingsw.Model.Card;
import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Model.Resource;
import it.polimi.ingsw.ModelView.GameView;
import it.polimi.ingsw.View.GoldenEdges;
import it.polimi.ingsw.View.SceneEnum;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import javafx.scene.control.ScrollPane;
import java.util.Objects;

/**
 * ControllerOtherStation class, it's mansion is to manage the scene otherStation (otherStation.fxml).
 * It allows the player to see the station of the other players.
 * It shows the resources and the cards of the other players.
 */
public class controllerOtherStation extends ControllerGUI{
    /**
     * Attributes of the class
     * game is the gameView of the game
     * CARD_WIDTH is the width of the card
     * CARD_HEIGHT is the height of the card
     * START_X is the x coordinate of the starting point of the cards
     * START_Y is the y coordinate of the starting point of the cards
     * OFFSET_X is the offset between the cards on the x axis
     * OFFSET_Y is the offset between the cards on the y axis
     *
     */
    GameView game;
    final double CARD_WIDTH = 94;
    final double CARD_HEIGHT = 59;
    final double START_X = 1470;
    final double START_Y = 1500;
    final double OFFSET_X = 80; // Adding a bit of space between the cards
    final double OFFSET_Y = 40; // Adding a bit of space between the cards
    /**
     * FXML attributes to help managing the scene
     */
    @FXML
    private AnchorPane paneStation;
    @FXML
    private Text centerText;


    /**
     * Method to load the scene
     * @param nickname is the nickname of the player that the station will be shown.
     */
    @Override
    public void load(String nickname){
        game = gui.getGameView();
        centerText.setText("F :" +game.getPlayerByUsername(nickname).getStation().getResNum(Resource.FUNGI)+ "; A : " +game.getPlayerByUsername(nickname).getStation().getResNum(Resource.ANIMAL)
                +"; P : "+game.getPlayerByUsername(nickname).getStation().getResNum(Resource.PLANT) +"; I : "+game.getPlayerByUsername(nickname).getStation().getResNum(Resource.INSECT));
        paneStation.getChildren().clear();
        for(int i=0; i< game.getPlayerByUsername(nickname).getStation().getCards().size(); i++){
            String numCard = game.getPlayerByUsername(nickname).getStation().getCards().get(i).getNumCard();
            int x = game.getPlayerByUsername(nickname).getStation().getCards().get(i).getPos()[0];
            int y = game.getPlayerByUsername(nickname).getStation().getCards().get(i).getPos()[1];
            ImageView cardImage = new ImageView();
            if(i == 0){
                if(game.getPlayerByUsername(nickname).getStation().getCards().get(0).isFront()){  // it's done on purpose to reverse back with front because the starter works the opposite
                    cardImage.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Cards/back/" + numCard + ".png"))));}
                else {
                    cardImage.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Cards/front/" + numCard + ".png"))));
                }

            }else{
                if(game.getPlayerByUsername(nickname).getStation().getCards().get(i).isFront())
                    cardImage.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Cards/front/" + numCard + ".png"))));
                else
                    cardImage.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Cards/back/" + numCard + ".png"))));
            }

            // the x's and y's are reversed for the matrix discourse
            placeCardInGraphic(y-40, x-40, cardImage.getImage());

        }

    }
    /**
     * Method to convert the position of the card in the array to the position in the graphic
     * @param matrixX is the x coordinate of the card
     * @param matrixY is the y coordinate of the card
     * @return the position of the card in the graphic
     */
    public Point2D matrixToGraphic(int matrixX, int matrixY) {
        double graphicX = START_X + (matrixX * OFFSET_X);
        double graphicY = START_Y + (matrixY * OFFSET_Y);
        return new Point2D(graphicX, graphicY);
    }

    /**
     * Method to create and place a card in the graphic
     * @param matrixX is the x coordinate of the card
     * @param matrixY is the y coordinate of the card
     * @param cardImage is the image of the card
     */
    public void placeCardInGraphic(int matrixX, int matrixY, Image cardImage) {
        Point2D position = matrixToGraphic(matrixX, matrixY);
        ImageView cardView = new ImageView();
        cardView.setImage(cardImage);
        cardView.setFitWidth(CARD_WIDTH);
        cardView.setFitHeight(CARD_HEIGHT);
        cardView.setLayoutX(position.getX());
        cardView.setLayoutY(position.getY());
        paneStation.getChildren().add(cardView);
    }

}
