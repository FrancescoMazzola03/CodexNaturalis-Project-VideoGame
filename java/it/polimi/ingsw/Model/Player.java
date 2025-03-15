package it.polimi.ingsw.Model;

import it.polimi.ingsw.Model.Exceptions.InsufficientResourcesException;
import it.polimi.ingsw.Model.Exceptions.InvalidPositionException;
import it.polimi.ingsw.Model.Exceptions.NoCornersException;

import javax.swing.text.PlainView;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Player class, is used to represent a player.
 * A player is a person that plays the game.
 * The player has a hand of cards, a station, a number of points, a name and a number of objectives completed.
 * The player can play cards, draw cards, place cards in his station, complete objectives and get points.
 * The player can also see his station and his hand.
 * The player can also see the station of the other players.
 */
public class Player implements Serializable {
    /**
     * Attributes of the class.
     * hand is the hand of the player, it contains the cards of a specific turn
     * station is the station of the player, it contains the cards that the player has placed
     * points is the number of points that the player has.
     * name is the name of the player.
     * objective contains the objectives card of the player, it has to choose only one.
     * NumObjsCompleted is the number of objectives completed by the player.
     * ReadyToPlay is a boolean that is true if the player is ready to play, false otherwise.
     */
    private ArrayList<PlayingCard> hand;
    private ArrayList<ObjectiveCard> objective;
    private Station station;
    private int points;
    private String name;
    private int NumObjsCompleted;
    private boolean ReadyToPlay;

    /**
     * Constructor of the class.
     * @param name is the name of the player.
     */
    public Player(String name) {
        this.hand = new ArrayList<>();
        this.station = new Station();
        this.points = 0;
        this.name = name;
        objective = new ArrayList<>();
        this.NumObjsCompleted = 0;
        this.ReadyToPlay = false;
    }

    /*******************************
     ****** GETTERS & SETTERS ******
     ******************************/

    /**
     * Method to get the boolean ReadyToPlay
     * @return the boolean ReadyToPlay
     */
    public boolean getReadyToPlay() {
        return ReadyToPlay;
    }

    /**
     * Method to set the boolean ReadyToPlay to true
     */
    public void setReadyToPlay() {
        this.ReadyToPlay = true;
    }

    /**
     * Method to get the number of points of the player
     * @return the number of points of the player
     */
    public int getPoints() {
        return this.points;
    }

    /**
     * Method to get the name of the player
     * @return the name of the player
     */
    public String getName() {
        return this.name;
    }

    /**
     * Method to get the hand of the player
     * @return the hand of the player
     */
    public ArrayList<PlayingCard> getHand() {
        return this.hand;
    }

    /**
     * Method to get the objective card of the player
     * @return the objective card of the player
     */
    public ObjectiveCard getObjectiveCard() {
        return this.objective.get(0);
    }

    /**
     * Method to get the number of objectives completed by the player
     * @return the number of objectives completed by the player
     */
    public int getNumObjsCompleted() {
        return this.NumObjsCompleted;
    }

    /**
     * Method to get the station of the player
     * @return the station of the player
     */
    public Station getStation() {
        return this.station;
    }

    /**
     * Method to add points to the player
     * @param points is the number of points that the player has just earned.
     */
    public void setPoints(int points) {
        this.points = this.points + points;
    }

    /**
     * Method to set the objective card of the player
     * @param chosen is a number that represents the objective card that the player has chosen
     */
    public void setObjectiveCard (int chosen) {
        // let's think in a "positive" way: he wants card 1, I eliminate card pos 1 (which would be 2)
        // analogous reasoning in reverse
        if (chosen == 1 )
            objective.remove(1);
        else
            objective.remove(0);
    }

    public ArrayList<ObjectiveCard> possibleObjectives(){
        return this.objective;
    }

    public void createHand (ResourceDeck resourceDeck, GoldenDeck goldenDeck, ObjectiveDeck objectiveDeck, StarterDeck starterDeck) {
        // adding 3 cards to the hand of the player
        hand.add(resourceDeck.pop());
        hand.add(resourceDeck.pop());
        hand.add(goldenDeck.pop());
        // adding the two objective cards of which only one will remain
        objective.add(objectiveDeck.pop());
        objective.add(objectiveDeck.pop());
        // adding the starter card, it will be in position 0
        // nb: the starter card by default is in position 40,40
        // nb: when building the station, it sets the position 40,40 to 0
        this.station.addCard(starterDeck.pop());
        // incremento già le risorse
        // this.addStarterResource((StarterCard) this.station.getCards().get(0));
    }

    /**
     * Method to place a card in the station of the player.
     * The method checks if the position is valid, if the player has enough resources to place the card and if there is a corner to place the card.
     * If the position is valid, the player has enough resources and there is a corner to place the card, the card is placed in the station of the player.
     * @param row row of the matrix of the station where the card will be placed
     * @param column column of the matrix of the station where the card will be placed
     * @param handCardPos position of the card in the hand of the player
     * @throws InvalidPositionException The position is not valid
     * @throws InsufficientResourcesException The player doesn't have enough resources to place the card
     * @throws NoCornersException There isn't either a corner to place the card
     */
    public void placeCard(int row, int column, int handCardPos) throws InvalidPositionException, InsufficientResourcesException, NoCornersException{
        int[][] cardsPos = station.getCardPositions();
        try {
            station.isPlaceable(row, column, handCardPos, this);

            if (!hand.get(handCardPos - 1).isFront())    // Added this thing that was missing: when a player presses to confirm card placement, if the card he wants to place is set on the back,
                for (int i = 0; i < 4; i++)              // its corners are all set to empty (we are talking about playing cards, the starter is different )
                    hand.get(handCardPos - 1).setCorner(i, Resource.EMPTY);

            station.setNumCard(row, column, station.getCards().size());
            station.getCards().add(hand.get(handCardPos - 1));   // the -1 is used because the player chooses from 1 to 3 while the hand goes from 0 to 2
            if (cardsPos[row - 1][column - 1] != -1)
                station.decrementResNum(station.getCards().get(cardsPos[row - 1][column - 1]).getCorner(2).getRes());
            if (cardsPos[row + 1][column - 1] != -1)
                station.decrementResNum(station.getCards().get(cardsPos[row + 1][column - 1]).getCorner(1).getRes());
            if (cardsPos[row - 1][column + 1] != -1)
                station.decrementResNum(station.getCards().get(cardsPos[row - 1][column + 1]).getCorner(3).getRes());
            if (cardsPos[row + 1][column + 1] != -1)
                station.decrementResNum(station.getCards().get(cardsPos[row + 1][column + 1]).getCorner(0).getRes());

            if (this.hand.get(handCardPos - 1).isFront()) {
                for (int i = 0; i < 4; i++)
                    station.incrementResNum(this.hand.get(handCardPos - 1).getCorner(i).getRes());
            } else
                this.station.incrementResNum(this.hand.get(handCardPos - 1).getPermanentRes());

            if (this.hand.get(handCardPos - 1).isFront())
                this.points += this.hand.get(handCardPos - 1).addPoints(this, row, column);
            this.hand.remove(handCardPos - 1);
        } catch (InvalidPositionException | InsufficientResourcesException | NoCornersException e) {
            throw e;
        }

    }

    /**
     * Method to draw a card from the market or from golden/resource decks.
     * @param res deck of resources
     * @param gold deck of golden cards
     * @param market market
     * @param choice choice of the player
     */
    public void drawCard(ResourceDeck res, GoldenDeck gold, Market market, int choice) {

        if (choice == 0 || choice == 1 || choice == 2 || choice == 3) {
            hand.add(market.getMarketCard(choice));
            market.update(choice, res, gold);
        } else if (choice == 4) {
            hand.add(res.pop());
        } else if (choice == 5) {
            hand.add(gold.pop());
        }
    }

    /**
     * Method to manage the corners of the starter card.
     */
    public void starterCardStateConfirmed() {
        // if the starter card isn't front, put in corners the used corners
        // increment the resource number of the player
        if (!(this.station.getCards().get(0).isFront())) {
            for (int i = 0; i < 4; i++) {
                Resource tempRes = ((StarterCard) this.station.getCards().get(0)).getBackCorner(i).getRes();
                this.station.getCards().get(0).getCorner(i).setRes(tempRes);
            }
        }
        addStarterResource((StarterCard) this.station.getCards().get(0));
    }

    /**
     * Method to add the resources of the starter card to the player's station.
     * @param starter starter card
     */
    public void addStarterResource(StarterCard starter) {
        // after choose the side of the starterCard, increment the resource number of the player
        for(int i = 0; i < 4; i++)
            this.station.incrementResNum(starter.getCorner(i).getRes());
        // if front is false, consider also the permanent resources
        if (!(this.station.getCards().get(0).isFront())) {
            for (Resource r : ((StarterCard) this.station.getCards().get(0)).getPermanentRes())
                this.station.incrementResNum(r);
        }
    }

    /**
     * Method to increment the number of objectives completed by the player.
     */
    public void incrementNumObjsCompleted() {
        this.NumObjsCompleted = this.NumObjsCompleted + 1;
    }

    /**
     * Method to get the player's starter card.
     * @return the player's starter card
     */
    public StarterCard getPlayerStarterCard() {
        return (StarterCard) this.station.getCards().get(0);
    }


}




