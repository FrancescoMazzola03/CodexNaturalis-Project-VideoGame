package it.polimi.ingsw.Controller;

import it.polimi.ingsw.EventHandling.EventType;
import it.polimi.ingsw.EventHandling.NoPawnEvent;
import it.polimi.ingsw.EventHandling.QuitEvent;
import it.polimi.ingsw.EventHandling.TerminateGameEvent;
import it.polimi.ingsw.Listener.GameListener;
import it.polimi.ingsw.Model.Exceptions.InsufficientResourcesException;
import it.polimi.ingsw.Model.Exceptions.InvalidPositionException;
import it.polimi.ingsw.Model.Exceptions.NoCornersException;
import it.polimi.ingsw.Model.*;

import java.net.http.WebSocket;

/**
 * This class is the controller of the game, it is used to manage the game logic and the game flow.
 * It's the controller in the MVC pattern
 */
public class GameController1 {
    /**
     * Attributes of the class.
     * model is the model of the game, it contains the game and the listeners.
     */
    private Model1 model;


    /**
     * Constructor of the class.
     * @param model is the model of the game, it contains the game and the listeners.
     */
    public GameController1(Model1 model) {
        this.model = model;
    }

    /**
     * Method to get the model of the game.
     * @return the model of the game
     */
    public Model1 getModel() {
        return this.model;
    }

    /**
     * Method to notify others players that a player quit the game
     * @param quitter is the player that quits the game
     */
    public void rageQuitGame(String quitter) {
        for (Player player : model.getGame().getPlayers()) {
            if (!player.getName().equals(quitter))
                model.notifyListener(model.getListenerByName(player.getName()), new TerminateGameEvent(EventType.TERMINATEEVENT, null), quitter);
        }
    }

    /**
     * Method to set the pawn of a player
     * @param pawn is the pawn that the player wants to set
     * @param username is the player that wants to set the pawn
     */
    public void setPawn(int pawn, String username){
        if (model.getGame().getPawnOwners()[pawn -1].equals("none")){
            model.getGame().setPawnOwner(pawn, username);
            model.notifyAllListeners(null,username);}
        else{
            GameListener listener = model.getListenerByName(username);
            model.notifyListener(listener,new NoPawnEvent(username, model.getGame().getPawnOwners()[pawn -1]),username);}
    }

    /**
     * Method to create a game and join it by the player that creates the game
     * @param MaxNumPlayers is the maximum number of players that can play the game
     * @param username is the username of the player that created the game
     */
    public void CreateGameAndJoin(int MaxNumPlayers, String username){
        model.setGame(new Game(MaxNumPlayers));
        playerJoin(username);
        model.getGame().setCreator();
    }

    /**
     * Method to join a game
     * @param username is the username of the player that wants to join the game
     */
    public void playerJoin(String username) {      // also the creator of the game will do the join as soon as he created the game

        model.getGame().addPlayer(new Player(username));
        System.out.println("max num :" + model.getGame().getMaxNumPlayers());
        System.out.println("current num : " + model.getGame().getPlayers().size());
        if (model.getGame().getMaxNumPlayers() == model.getGame().getPlayers().size()) {
            model.getGame().setState(State.WAITINGTOPLAY);
            startGame();
        }
        model.notifyAllListeners(null,username);
    }

    /**
     * Method to set the objective card that the player has chosen
     * @param chosen is the index of the objective card that the player has chosen
     * @param username is the username of the player that has chosen the objective card
     */
    public void chosenObjective(int chosen,String username){
        Player player = model.getGame().getPlayerByUsername(username);
        player.setObjectiveCard(chosen);
        model.notifyAllListeners(null,username);
    }

    /**
     * Method to place a card in the station of the player
     * @param row row of the matrix where the card will be placed
     * @param column column of the matrix where the card will be placed
     * @param handCardPos position of the card in the hand of the player
     * @param username username of the player that wants to place the card
     * @throws InvalidPositionException if the position is invalid
     * @throws InsufficientResourcesException if the player has not enough resources to place the card
     * @throws NoCornersException if the player wants to place a card in a place without corners
     */
    public void placeCard(int row, int column, int handCardPos, String username) throws InvalidPositionException, InsufficientResourcesException, NoCornersException {
        Player currentPlayer = model.getGame().getCurrentPlayer();
        try {
            currentPlayer.placeCard(row, column, handCardPos);
            currentPlayer.getStation().getCards().get(currentPlayer.getStation().getCards().size()-1).setPos(row,column);
            if (!currentPlayer.getStation().getCards().get(currentPlayer.getStation().getCards().size()-1).isFront())
                flipCards(username);
            model.notifyAllListeners(null,username);  // call the model's notify directly from the controller
        } catch (InvalidPositionException | InsufficientResourcesException | NoCornersException e) {
            throw e;
        }


    }

    /**
     * Method to draw a card by the player
     * @param choice choice of the player to draw a card from the market or from the decks
     * @param username username of the player that wants to draw a card
     */
    public void drawCard(int choice, String username) {
        Player currentplayer = model.getGame().getCurrentPlayer();
        currentplayer.drawCard(model.getGame().getResDeck(), model.getGame().getGoldDeck(), model.getGame().getMarket(), choice);
        model.getGame().setCurrentPlayer();
        if (currentplayer.getPoints() >= 20 && model.getGame().getState().equals(State.RUNNING)) {
            // when the first player reaches 1 point, change the state of the game to last turn
            model.getGame().setState(State.LAST_TURN);
            if (model.getGame().getPlayers().indexOf(currentplayer) == model.getGame().getMaxNumPlayers() - 1)
                model.getGame().setState(State.ENDED);
        } else if (model.getGame().getState().equals(State.LAST_TURN) && model.getGame().getPlayers().indexOf(currentplayer) == model.getGame().getMaxNumPlayers() - 1) {
            model.getGame().setState(State.ENDED);
        }
        model.notifyAllListeners(null, username);
    }

    /**
     * Method to start a game
     */
    public void startGame() {
        model.getGame().setCreator();
        model.getGame().getResDeck().shuffle();
        model.getGame().getGoldDeck().shuffle();
        model.getGame().getStartDeck().shuffle();
        model.getGame().getObjDeck().shuffle();
        model.getGame().sort();
        model.getGame().addCommonObjs();


        for (Player player : model.getGame().getPlayers()) {
            // the creation of the hand consists also in giving the two possible objective cards
            player.createHand(model.getGame().getResDeck(), model.getGame().getGoldDeck(), model.getGame().getObjDeck(), model.getGame().getStartDeck());
        }
        model.getGame().setCurrentPlayer();
        this.model.getGame().getMarket().setMarket(this.model.getGame().getResDeck(), this.model.getGame().getGoldDeck());
    }

    /**
     * Method to flip the cards of the player
     * @param username is the username of the player that wants to flip the cards
     */
    public void flipCards(String username) {
        Player player = model.getGame().getPlayerByUsername(username);
        for (PlayingCard handCard : player.getHand())
            handCard.setState(!handCard.isFront());
        model.notifyAllListeners(null,username);
    }

    /**
     * Method to set the starter card of the player
     * @param username is the username of the player that chose the front of the starter card
     * @param front is the front of the starter card, true if it's front, back if it's false
     */
    public void setStarterCard(String username, boolean front) {
        Player player = model.getGame().getPlayerByUsername(username);
        player.getStation().getCards().get(0).setState(front);
        player.starterCardStateConfirmed();

        player.setReadyToPlay();
        int temp = 0;
        for (Player p : model.getGame().getPlayers())
            if (p.getReadyToPlay())
                temp += 1;
        if (temp == model.getGame().getMaxNumPlayers()) // sure that can't be more players than MaxNum
            model.getGame().setState(State.RUNNING);

        model.notifyAllListeners(null,username);
    }

}
