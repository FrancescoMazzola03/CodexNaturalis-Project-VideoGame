package it.polimi.ingsw.View.Controller;

import it.polimi.ingsw.Model.Player;
import it.polimi.ingsw.Model.Resource;
import it.polimi.ingsw.Model.State;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import it.polimi.ingsw.ModelView.GameView;
 import javafx.scene.control.ScrollPane;
import it.polimi.ingsw.View.GoldenEdges;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Point2D;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.*;
import javafx.application.Platform;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.*;
import java.util.Objects;

/**
 * ControllerBoard class, it's mansion is to manage the scene board (board.fxml)
 */
public class controllerBoard extends ControllerGUI {
    /**
     * Attributes of the class
     * game is the match
     * boardPoints represent the position of the points in the plateau
     * player is the player that is playing
     * pawns is a map that contains for each player a pawn
     * playersName is a list of the players' names
     * colors is a list of colors
     * popupshown is a counter that counts the number of popups shown
     * counter is a counter for goldenEdges
     * lastHandCardPlaced is the last card placed
     * goldenEdgesList is a list of golden edges
     * placedCards is a list of placed cards
     * mouseX is the x coordinate of the mouse
     * mouseY is the y coordinate of the mouse
     * nickname is the nickname of the player
     */
    GameView game;
    private ArrayList<Point2D> boardPoints;
    Player player;
    Map<String, ImageView> pawns = new HashMap<>();
    ArrayList<String> playersName = new ArrayList<>();
    String[] colors = new String[]{"red", "blue", "green", "yellow", "black"};
    public int popupshown = 0;
    int counter = 0;
    ImageView lastHandCardPlaced;
    ArrayList<GoldenEdges> goldenEdgesList = new ArrayList<>();
    ArrayList<ImageView> placedCards = new ArrayList<>();
    private double mouseX;
    private double mouseY;
    private String nickname;

    /**
     * FXML attributes to help managing the scene
     */
    @FXML
    private Pane paneStation;
    @FXML
    private ImageView card0;
    @FXML
    private ImageView starterCard;
    @FXML
    private Button flipButton;
    @FXML
    private Text myres;
    @FXML
    private GridPane mainGridPane;
    @FXML
    private ImageView card1;
    @FXML
    private ImageView checkobj0;
    @FXML
    private ImageView checkobj1;
    @FXML
    private ImageView checkobjpers;
    @FXML
    private ImageView card2;
    @FXML
    private TextFlow chatView;
    @FXML
    private Text textTurn;
    @FXML
    private ImageView commonObj0;
    @FXML
    private ImageView commonObj1;
    @FXML
    private ScrollPane scroll;
    @FXML
    private Label firstPlayer;
    @FXML
    private Label fourthPlayer;
    @FXML
    private ImageView marketGoldenCard0;
    @FXML
    private ImageView marketGoldenCard1;
    @FXML
    private ImageView marketResourceCard0;
    @FXML
    private ImageView marketResourceCard1;
    @FXML
    private StackPane paneBoard;
    @FXML
    private ImageView personalObjective;
    @FXML
    private ImageView resdeck;
    @FXML
    private ImageView goldeck;
    @FXML
    private Label player1Resources;
    @FXML
    private Label player2Resources;
    @FXML
    private Label player3Resources;
    @FXML
    private Label secondPlayer;
    @FXML
    private Button stationPlayer1;
    @FXML
    private Button stationPlayer2;
    @FXML
    private Button stationPlayer3;
    @FXML
    private Pane pawnPane;
    @FXML
    private Label thirdPlayer;
    @FXML
    private ChoiceBox <String> choiceBox;
    @FXML
    private TextField messageField;
    @FXML
    private Label timesOb1;
    @FXML
    private Label timesOb2;
    @FXML
    private Label timesPersObj;

    /**
     * Method to see the station of the first player
     * @param event when the player click on the button of the first player
     */
    @FXML
    void buttonStationPlayer1(MouseEvent event) {
        gui.showStationPlayer(firstPlayer.getText());

    }
    /**
     * Method to quit the game
     * @param event when the player click on the button "quit" to quit the game
     */
    @FXML
    void quitGame(MouseEvent event){
            gui.quitGame();
        }

    /**
     * Method to flip the cards
     * @param event when the player click on the button "flip" to flip its cards
     */
    @FXML
    void flipButton(MouseEvent event){
        gui.flipCards();
    }
/**
     * Method to see the station of the second player
     * @param event when the player click on the button of the second player
     */
    @FXML
    void buttonStationPlayer2(MouseEvent event) {
        gui.showStationPlayer(secondPlayer.getText());

    }
/**
     * Method to see the station of the third player
     * @param event when the player click on the button of the third player
     */
    @FXML
    void buttonStationPlayer3(MouseEvent event) {
        gui.showStationPlayer(thirdPlayer.getText());

    }
/**
     * Method to manage when a player draws a card
     * @param event when the player click on a card that wants to draw
     */
    @FXML
    void pressDrawableCard(MouseEvent event) {
        if (game.getCurrentPlayer().getName().equals(nickname) && game.getPlayerByUsername(nickname).getHand().size() ==2){
            ImageView clickedCard = (ImageView) event.getSource();

            lastHandCardPlaced.setImage(clickedCard.getImage());
            lastHandCardPlaced.setVisible(true);
            commonObj0.setOpacity(1);
            commonObj1.setOpacity(1);
            switch(clickedCard.getId()){
                case "market_resource_card0":
                    gui.showDrawCard(0);
                    break;
                case "marketResourceCard1":
                    gui.showDrawCard(1);
                    break;
                case "market_golden_card0":
                    gui.showDrawCard(2);
                    break;
                case "market_golden_card1":
                    gui.showDrawCard(3);
                    break;
                case "resdeck":
                    gui.showDrawCard(4);
                    break;
                case "golddeck":
                    gui.showDrawCard(5);
                    break;

            }

        }

    }
    /**
     * Call the pressCard method with the first card in the hand as a parameter
     * @param event when mouse button is clicked
     */
    @FXML
    void pressCard0(MouseEvent event) {
        if (game.getCurrentPlayer().getName().equals(nickname) && game.getPlayerByUsername(nickname).getHand().size() == 3)
            pressCard(event,card0);
    }
    /**
     * Call the pressCard method with the second card in the hand as a parameter
     * @param event when mouse button is clicked
     */
    @FXML
    void pressCard1(MouseEvent event) {
        if (game.getCurrentPlayer().getName().equals(nickname) && game.getPlayerByUsername(nickname).getHand().size() == 3)
            pressCard(event,card1);
    }

    /**
     * Call the pressCard method with the third card in the hand as a parameter
     * @param event when mouse button is clicked
     */
    @FXML
    void pressCard2(MouseEvent event) {
        if (game.getCurrentPlayer().getName().equals(nickname) || game.getPlayerByUsername(nickname).getHand().size() ==2)
            pressCard(event,card2);
    }

    /**
     * Method to manage when player clicked on one of its cards
     * @param event when player clicked on one of its cards
     * @param card card selected
     */
    void pressCard(MouseEvent event, ImageView card) {
        int offsetX,offsetY;
        mouseX = event.getSceneX() - card.getLayoutX();
        mouseY = event.getSceneY() - card.getLayoutY();

        for(ImageView temp: goldenEdgesList)
            paneStation.getChildren().remove(temp);
        goldenEdgesList.clear();
        for (int i = 1; i < 79; i++)
            for (int j = 1; j < 79; j++) {
                if (game.getPlayerByUsername(gui.getNickname()).getStation().fastIsPlaceable(j,i, 0,player)){
                    GoldenEdges imageView = new GoldenEdges("/goldenEdge.png",j,i);
                    imageView.setFitWidth(94);
                    imageView.setFitHeight(59);
                    offsetX = -20;
                    offsetY = -25;
                    imageView.setLayoutX((i - 40) * (94 + offsetX) + 2000);   //20 larghezza corner 25 altezza corner
                    imageView.setLayoutY((j - 40) * (59 + offsetY) + 2000);
                    imageView.setId("goldenEdges" + counter);
                    counter++;
                    paneStation.getChildren().add(imageView);
                    goldenEdgesList.add(imageView);
                }
            }
    }

    /**
     * Call the dragCard method with the first card in the hand as a parameter
     * @param event when mouse is moving
     */
    @FXML
    void dragCard0(MouseEvent event) {
        if (game.getCurrentPlayer().getName().equals(nickname) && game.getPlayerByUsername(nickname).getHand().size() ==3)
            dragCard(event,card0);
    }
    /**
     * Call the dragCard method with the second card in the hand as a parameter
     * @param event when mouse is moving
     */
    @FXML
    void dragCard1(MouseEvent event) {
        if (game.getCurrentPlayer().getName().equals(nickname) && game.getPlayerByUsername(nickname).getHand().size() == 3)
            dragCard(event,card1);
    }
    /**
     * Call the dragCard method with the third card in the hand as a parameter
     * @param event when mouse is moving
     */
    @FXML
    void dragCard2(MouseEvent event) {
        if (game.getCurrentPlayer().getName().equals(nickname) && game.getPlayerByUsername(nickname).getHand().size() ==3)
            dragCard(event,card2);
    }

    /**
     * Change the position of the card in real time following the mouse movement
     * @param event the method need this to know the position of the mouse in real time
     * @param card card selected
     */
    void dragCard(MouseEvent event, ImageView card) {
        card.setLayoutX(event.getSceneX() - mouseX);
        card.setLayoutY(event.getSceneY() - mouseY);
    }
    /**
     * Call the releasedCard method with the first card in the hand as a parameter
     * @param event when mouse button is released
     */
    @FXML
    void releasedCard0(MouseEvent event) {
        if (game.getCurrentPlayer().getName().equals(nickname) && game.getPlayerByUsername(nickname).getHand().size() == 3)
            releasedCard(card0);
    }
    /**
     * Call the releasedCard method with the second card in the hand as a parameter
     * @param event when mouse button is released
     */
    @FXML
    void releasedCard1(MouseEvent event) {
        if (game.getCurrentPlayer().getName().equals(nickname) && game.getPlayerByUsername(nickname).getHand().size() == 3)
            releasedCard(card1);
    }

    /**
     * Call the releasedCard method with the third card in the hand as a parameter
     * @param event when mouse button is released
     */
    @FXML
    void releasedCard2(MouseEvent event) {
        if (game.getCurrentPlayer().getName().equals(nickname) && game.getPlayerByUsername(nickname).getHand().size() == 3)
            releasedCard(card2);
    }

    /**
     * Place the card selected instead of the nearest goldenedge if found, or else
     * replace the card selected in the original position
     * @param card card selected
     */
    void releasedCard(ImageView card) {


        // Se l'immagine è rilasciata nell'area di rilascio, posizionala lì
        GoldenEdges temp = nearestGoldenEdges(card);
        int choice = 0;
        if(temp != null) {
            ImageView cardToPlace = new ImageView(card.getImage());
            cardToPlace.setFitWidth(94);
            cardToPlace.setFitHeight(59);
            cardToPlace.setLayoutX(temp.getLayoutX());
            cardToPlace.setLayoutY(temp.getLayoutY());
            paneStation.getChildren().remove(temp);
            paneStation.getChildren().add(cardToPlace);
            placedCards.add(cardToPlace);
            switch(card.getId()) {
                case "card0":
                    choice = 1;
                    lastHandCardPlaced = card0;
                    card.setLayoutX(43);
                    break;
                case "card1":
                    choice = 2;
                    lastHandCardPlaced = card1;
                    card.setLayoutX(288);
                    break;
                case "card2":
                    choice = 3;
                    lastHandCardPlaced = card2;
                    card.setLayoutX(510);
                    break;
            }
            gui.placeCard(temp.getCoordinateY(), temp.getCoordinateX(), choice);   //le carte della mano nella tui le abbiamo messe da 1 a 3
            card.setLayoutY(45);
            card.setVisible(false);
        }
        else{
            switch(card.getId()){
                case "card0":
                    card.setLayoutX(43);
                    break;
                case "card1":
                    card.setLayoutX(288);
                    break;
                case "card2":
                    card.setLayoutX(510);
                    break;

            }
            card.setLayoutY(45);
        }
    }


    /**
     * Method to add a listener for the event of pressing the Enter key in the TextField
     */
    @FXML
    void initialize() {
        messageField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                sendChat();
            }
        });
    }

    /**
     * Method to send a message in the chat
     */
    @FXML
    void sendChat() {
        int index = 0;

        String playerSelected = choiceBox.getValue();
        String message = messageField.getText();
        for (int i = 0; i<5; i++){
            if(nickname.equals(game.getPawnOwners()[i])){
                index=i;
                break;}}
        Text text = new Text(nickname + ": " + message +"\n");
        text.getStyleClass().add("medieval-text");
        text.setFill(Color.web(colors[index]));
        chatView.getChildren().add(text);
        messageField.clear();
        gui.sendMessage(playerSelected, message);

    }



    /**
     *Find the nearest goldenedge, no more than x pixels away (x decided in the padding),
     * if there is none, returns null
     * @param imageView the card that the method use the position for finding the nearest goldenedge
     * @return the nearest goldenedge
     */
    public GoldenEdges nearestGoldenEdges(ImageView imageView){
        int padding = 20;
        Point2D sceneCoords = imageView.localToScene(0, 0);

        // 2. Convert the coordinates relative to the new Destination pane
        Point2D localCoords = paneStation.sceneToLocal(sceneCoords);


        // 3. Set the new node coordinates within the Destination pane
        imageView.setLayoutX(localCoords.getX());
        imageView.setLayoutY(localCoords.getY());

        for(GoldenEdges golden : goldenEdgesList){
            if(imageView.getLayoutX() < golden.getLayoutX() + padding && imageView.getLayoutX() > golden.getLayoutX() - padding &&
                    imageView.getLayoutY() > golden.getLayoutY() - padding && imageView.getLayoutY() < golden.getLayoutY() + padding) {
                return golden;
            }
        }
        return null;
    }


    /**
     * Method to load the scene of the board
     */
    @Override
    @FXML
    public void load() {
        Stage stage = (Stage) paneBoard.getScene().getWindow();
        stage.centerOnScreen();
        pawnPane = new Pane();
        mainGridPane.add(pawnPane,2,1);
        scroll.setHvalue(0.5);
        scroll.setVvalue(0.5);
        boardPoints = new ArrayList<>();
        boardPoints.add(new Point2D(-10, 470));
        boardPoints.add(new Point2D(60, 460));
        boardPoints.add(new Point2D(120, 460));
        boardPoints.add(new Point2D(150, 400));
        boardPoints.add(new Point2D(90, 400));
        boardPoints.add(new Point2D(30, 400));
        boardPoints.add(new Point2D(-30, 400));
        boardPoints.add(new Point2D(-30, 340));
        boardPoints.add(new Point2D(30, 340));
        boardPoints.add(new Point2D(90, 340));
        boardPoints.add(new Point2D(150, 340));
        boardPoints.add(new Point2D(150, 300));
        boardPoints.add(new Point2D(90, 300));
        boardPoints.add(new Point2D(30, 300));
        boardPoints.add(new Point2D(-30, 300));
        boardPoints.add(new Point2D(-30, 240));
        boardPoints.add(new Point2D(30, 240));
        boardPoints.add(new Point2D(90, 240));
        boardPoints.add(new Point2D(150, 240));
        boardPoints.add(new Point2D(150, 180));
        boardPoints.add(new Point2D(60, 150));
        boardPoints.add(new Point2D(-30, 170));
        boardPoints.add(new Point2D(-30, 120));
        boardPoints.add(new Point2D(-30, 60));
        boardPoints.add(new Point2D(-15, 30));
        boardPoints.add(new Point2D(60, 20));
        boardPoints.add(new Point2D(75, 30));
        boardPoints.add(new Point2D(150, 60));
        boardPoints.add(new Point2D(150, 120));
        boardPoints.add(new Point2D(60, 90));

         game = gui.getGameView();
         nickname = gui.getNickname();
         player = game.getPlayerByUsername(nickname);
        choiceBox.setValue("all");
        choiceBox.getItems().add("all");
        if(game.getCurrentPlayer().getName().equals(nickname)){
            textTurn.setText("It's your turn, place a card!");
            flipButton.setVisible(true);}
        else{
            textTurn.setText("It's " + game.getCurrentPlayer().getName() + "'s turn");
            flipButton.setVisible(false);}
        for (Player player: gui.getGameView().getPlayers())
            if (!player.getName().equals(nickname))
                choiceBox.getItems().add(player.getName());
        if (game.getPlayerByUsername(nickname).getPlayerStarterCard().isFront())
            starterCard.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Cards/back/" + game.getPlayerByUsername(nickname).getPlayerStarterCard().getNumCard() + ".png"))));
       else
           starterCard.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Cards/front/" + game.getPlayerByUsername(nickname).getPlayerStarterCard().getNumCard() + ".png"))));
        placedCards.add(starterCard);
        card0.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Cards/front/" + game.getPlayerByUsername(nickname).getHand().get(0).getNumCard() + ".png"))));
        card1.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Cards/front/" + game.getPlayerByUsername(nickname).getHand().get(1).getNumCard() + ".png"))));
        card2.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Cards/front/" + game.getPlayerByUsername(nickname).getHand().get(2).getNumCard() + ".png"))));
        personalObjective.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Cards/front/" + game.getPlayerByUsername(nickname).getObjectiveCard().getNumCard() + ".png"))));
        commonObj0.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Cards/front/" + game.getCommonObjs().get(0).getNumCard() + ".png"))));
        commonObj1.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Cards/front/" + game.getCommonObjs().get(1).getNumCard() + ".png"))));
        marketResourceCard0.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Cards/front/" + game.getMarket().getResourceMarket()[0].getNumCard() + ".png"))));
        marketResourceCard1.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Cards/front/" + game.getMarket().getResourceMarket()[1].getNumCard() + ".png"))));
        marketGoldenCard0.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Cards/front/" + game.getMarket().getGoldenMarket()[0].getNumCard() + ".png"))));
        marketGoldenCard1.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Cards/front/" + game.getMarket().getGoldenMarket()[1].getNumCard() + ".png"))));
        resdeck.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Cards/back/" + game.getResDeck().getCards().get(game.getResDeck().getCards().size()-1).getNumCard() + ".png"))));
        goldeck.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Cards/back/" + game.getGoldDeck().getCards().get(game.getGoldDeck().getCards().size()-1).getNumCard() + ".png"))));
        checkobj0.setVisible(false);
        checkobj1.setVisible(false);
        checkobjpers.setVisible(false);

        Button[] stations = new Button[]{stationPlayer1, stationPlayer2, stationPlayer3};
        int j = 0;
        for (int i = 0; i < game.getMaxNumPlayers(); i++) {
            if (!game.getPlayers().get(i).getName().equals(nickname)) {
                stations[j].setText(game.getPlayers().get(i).getName());
                stations[j].setVisible(true);
                j++;
            }
        }
        for (int i = j; i < stations.length; i++) {
            stations[i].setVisible(false);
        }
        playersName.add(stationPlayer1.getText());
        if(stationPlayer2.isVisible()){
            playersName.add(stationPlayer2.getText());
        }
        if(stationPlayer3.isVisible()){
            playersName.add(stationPlayer3.getText());
        }
        j = 0;
        Label[] scoreboard = new Label[]{firstPlayer, secondPlayer, thirdPlayer, fourthPlayer};
        for (int i = 0; i<scoreboard.length; i++)
            scoreboard[i].setVisible(false);
        for (int i = 0; i < game.getMaxNumPlayers(); i++) {
            if (!game.getPlayers().get(i).getName().equals(nickname)) {
                scoreboard[j].setText(game.getPlayers().get(i).getName());
                j++;
            }
        }

    j=0;
    Label[] PlayerResources = new Label[]{player1Resources, player2Resources, player3Resources};
    for (int i = 0; i < game.getMaxNumPlayers(); i++) {
        if (!game.getPlayers().get(i).getName().equals(nickname)) {
            PlayerResources[j].setVisible(false);
            PlayerResources[j].setText("F :" +game.getPlayers().get(i).getStation().getResNum(Resource.FUNGI)+ "; A : " +game.getPlayers().get(i).getStation().getResNum(Resource.ANIMAL)
                    +"; P : "+game.getPlayers().get(i).getStation().getResNum(Resource.PLANT) +"; I : "+game.getPlayers().get(i).getStation().getResNum(Resource.INSECT));
            j++;
        }
    }
        for (int i = j; i < PlayerResources.length; i++) {
        PlayerResources[i].setVisible(false);
    }
        myres.setText("F :" +game.getPlayerByUsername(nickname).getStation().getResNum(Resource.FUNGI)+ "; A : " +game.getPlayerByUsername(nickname).getStation().getResNum(Resource.ANIMAL)
                +"; P : "+game.getPlayerByUsername(nickname).getStation().getResNum(Resource.PLANT) +"; I : "+game.getPlayerByUsername(nickname).getStation().getResNum(Resource.INSECT));

        for (int i = 0; i<game.getPlayers().size(); i++){
            ImageView pawn = new ImageView();
            pawn.setFitWidth(20);
            pawn.setFitHeight(20);
            String user = game.getPlayers().get(i).getName();

            pawn.setLayoutX(boardPoints.get(0).getX());
            pawn.setLayoutY(boardPoints.get(0).getY()-15*i);
            pawnPane.getChildren().add(pawn);
            int index4Color = -1;
            for (int x = 0; x < 5; x++){
                if (game.getPawnOwners()[x].equals(user)){
                    index4Color = x;
                    break;}
            }
            if (index4Color == 0)
                pawn.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Pawns/CODEX_pion_rouge.png"))));
            else if (index4Color == 1)
                pawn.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Pawns/CODEX_pion_bleu.png"))));
            else if (index4Color == 2)
                pawn.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Pawns/CODEX_pion_vert.png"))));
            else if (index4Color == 3)
                pawn.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Pawns/CODEX_pion_jaune.png"))));
            else if (index4Color == 4)
                pawn.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Pawns/CODEX_pion_noir.png"))));
            pawns.put(game.getPlayers().get(i).getName(), pawn);

        }


    }

    /**
     * Method to show a message that a player sent in the chat
     * @param message the message to show
     * @param nickname the nickname of the player that sent the message
     */
    public void showMessage(String message, String nickname) {
        Platform.runLater(() -> {
            if(!nickname.equals(gui.getNickname())){
                int index = 0;
                for (int i = 0; i<5; i++)
                    if(nickname.equals(game.getPawnOwners()[i])){
                        index=i;
                        break;}
                Text text = new Text(nickname + ": " + message +"\n");
                text.getStyleClass().add("medieval-text");
                text.setFill(Color.web(colors[index]));

                chatView.getChildren().add(text);}
        });
    }

    /**
     * Method to refresh the board
     */
    @Override
    public void refresh(){
        Platform.runLater(() -> {
            game = gui.getGameView();
            if (game.getCurrentPlayer().getName().equals(nickname) && game.getPlayerByUsername(nickname).getHand().size() == 3) {
                textTurn.setText("It's your turn : place a card");
                flipButton.setVisible(true);
                reloadBoard();
            }
            else if (game.getCurrentPlayer().getName().equals(nickname) && game.getPlayerByUsername(nickname).getHand().size() == 2){
                textTurn.setText("It's your turn : draw a card");
                flipButton.setVisible(false);
                showDrawable();
            }
            else{
                textTurn.setText("It's " + game.getCurrentPlayer().getName() + "'s turn");
                flipButton.setVisible(false);
                reloadBoard();
            }
            gui.showOtherStations(playersName);
        });

    }

    /**
     * Method to manage when the plauer wants to place a card in an invalid position
     * @param message message that says that the position that player chose is invalid
     */
    public void handleInvalidPosition(String message){
    Platform.runLater(() -> {
        Label errorLabel = new Label();
        paneStation.getChildren().add(errorLabel);
        paneStation.getChildren().remove(placedCards.get(placedCards.size()-1));
        placedCards.remove(placedCards.size()-1);
        errorLabel.setText(message.toUpperCase()); // Capitalize the message
        errorLabel.setTextFill(Color.RED); // Set the text color to red
        errorLabel.setFont(Font.font(20)); // Set the font family, weight, style, and size
        errorLabel.getStyleClass().add("medieval-text");
        errorLabel.setOpacity(1.0);
        errorLabel.setVisible(true);
        errorLabel.setLayoutX(paneStation.getWidth() / 2); // Center the label horizontally
        errorLabel.setLayoutY(paneStation.getHeight() / 2 - 50); // Position the label higher
        // Create a Timeline to fade the message after 1 second
        Timeline fadeTimeline = new Timeline(new KeyFrame(Duration.seconds(1), new KeyValue(errorLabel.opacityProperty(), 0)));
        // After the message has faded, hide it
        fadeTimeline.setOnFinished(event -> errorLabel.setVisible(false));
        // Start the Timeline
        fadeTimeline.play();

        reloadBoard();
    });
}

    /**
     * Method to reload cards, resources and all the stuffs that appear on the board
     */
    public void reloadBoard() {
        if (game.getState().equals(State.ENDED))
            gui.showEndGame();
        else {
            if (lastHandCardPlaced != null)
                lastHandCardPlaced.setVisible(true);
            if (game.getPlayerByUsername(gui.getNickname()).getHand().get(0).isFront()) {
            card0.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Cards/front/" + game.getPlayerByUsername(nickname).getHand().get(0).getNumCard() + ".png"))));
            card1.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Cards/front/" + game.getPlayerByUsername(nickname).getHand().get(1).getNumCard() + ".png"))));
            card2.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Cards/front/" + game.getPlayerByUsername(nickname).getHand().get(2).getNumCard() + ".png"))));}

            else {
                card0.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Cards/back/" + game.getPlayerByUsername(nickname).getHand().get(0).getNumCard() + ".png"))));
                card1.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Cards/back/" + game.getPlayerByUsername(nickname).getHand().get(1).getNumCard() + ".png"))));
                card2.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Cards/back/" + game.getPlayerByUsername(nickname).getHand().get(2).getNumCard() + ".png"))));
            }

            personalObjective.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Cards/front/" + game.getPlayerByUsername(nickname).getObjectiveCard().getNumCard() + ".png"))));
            commonObj0.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Cards/front/" + game.getCommonObjs().get(0).getNumCard() + ".png"))));
            commonObj1.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Cards/front/" + game.getCommonObjs().get(1).getNumCard() + ".png"))));
            marketResourceCard0.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Cards/front/" + game.getMarket().getResourceMarket()[0].getNumCard() + ".png"))));
            marketResourceCard1.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Cards/front/" + game.getMarket().getResourceMarket()[1].getNumCard() + ".png"))));
            marketGoldenCard0.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Cards/front/" + game.getMarket().getGoldenMarket()[0].getNumCard() + ".png"))));
            marketGoldenCard1.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Cards/front/" + game.getMarket().getGoldenMarket()[1].getNumCard() + ".png"))));
            resdeck.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Cards/back/" + game.getResDeck().getCards().get(game.getResDeck().getCards().size() - 1).getNumCard() + ".png"))));
            goldeck.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Cards/back/" + game.getGoldDeck().getCards().get(game.getGoldDeck().getCards().size() - 1).getNumCard() + ".png"))));
            myres.setText("F :" + game.getPlayerByUsername(nickname).getStation().getResNum(Resource.FUNGI) + "; A : " + game.getPlayerByUsername(nickname).getStation().getResNum(Resource.ANIMAL)
                    + "; P : " + game.getPlayerByUsername(nickname).getStation().getResNum(Resource.PLANT) + "; I : " + game.getPlayerByUsername(nickname).getStation().getResNum(Resource.INSECT));
            if (game.getCommonObjs().get(0).timesCompleted(game.getPlayerByUsername(gui.getNickname()).getStation()) >0) {
                checkobj0.setVisible(true);
                timesOb1.setText(":" + game.getCommonObjs().get(0).timesCompleted(game.getPlayerByUsername(gui.getNickname()).getStation()));
                timesOb1.setVisible(true);
            }
            else{
                checkobj0.setVisible(false);
                timesOb1.setVisible(false);
            }

            if (game.getCommonObjs().get(1).timesCompleted(game.getPlayerByUsername(gui.getNickname()).getStation()) >0){
                checkobj1.setVisible(true);
                timesOb2.setText(":" + game.getCommonObjs().get(1).timesCompleted(game.getPlayerByUsername(gui.getNickname()).getStation()));
                timesOb2.setVisible(true);}
            else{
                checkobj1.setVisible(false);
                timesOb2.setVisible(false);
            }

            if (game.getPlayerByUsername(gui.getNickname()).getObjectiveCard().timesCompleted(game.getPlayerByUsername(gui.getNickname()).getStation()) >0) {
                checkobjpers.setVisible(true);
                timesPersObj.setText(":" + game.getPlayerByUsername(gui.getNickname()).getObjectiveCard().timesCompleted(game.getPlayerByUsername(gui.getNickname()).getStation()));
                timesPersObj.setVisible(true);
            }
            else{
                checkobjpers.setVisible(false);
                timesPersObj.setVisible(false);
            }
            int j = 0;
            Label[] PlayerResources = new Label[]{player1Resources, player2Resources, player3Resources};
            for (int i = 0; i < game.getMaxNumPlayers(); i++) {
                if (!game.getPlayers().get(i).getName().equals(nickname)) {
                    PlayerResources[j].setText("F :" + game.getPlayers().get(i).getStation().getResNum(Resource.FUNGI) + "; A : " + game.getPlayers().get(i).getStation().getResNum(Resource.ANIMAL)
                            + "; P : " + game.getPlayers().get(i).getStation().getResNum(Resource.PLANT) + "; I : " + game.getPlayers().get(i).getStation().getResNum(Resource.INSECT));
                    PlayerResources[j].setVisible(false);
                    j++;
                }
            }
            for (int i = j; i < PlayerResources.length; i++) {
                PlayerResources[i].setVisible(false);
            }
            int x = 0;
            for (String key : pawns.keySet()) {

                pawns.get(key).setLayoutX(boardPoints.get(game.getPlayerByUsername(key).getPoints()).getX());
                pawns.get(key).setLayoutY(boardPoints.get(game.getPlayerByUsername(key).getPoints()).getY() - 15 * x);
                x++;
            }
            for (int i = 0; i < game.getPlayers().size(); i++) {

                game.getPlayers().get(i).getPoints();

            }

        if (game.getState().equals(State.LAST_TURN) && popupshown == 0) {
            popupshown = 1;
            gui.showlastturn();
        }
    }

    }
;


    /**
     * Method to change the opacity of common objectives when the player has to draw a card
     */
    public void showDrawable(){
        commonObj0.setOpacity(0.5);
        commonObj1.setOpacity(0.5);

    }


}

