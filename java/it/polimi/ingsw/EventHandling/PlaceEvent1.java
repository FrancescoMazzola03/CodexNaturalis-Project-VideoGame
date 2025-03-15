package it.polimi.ingsw.EventHandling;

import java.io.Serializable;

/**
 * PlaceEvent1 class, is used to represent a place event.
 * A place event is an event that is generated when a player places a card on the board.
 */
public class PlaceEvent1 extends Event implements Serializable {
    /**
     * Attributes of the class.
     * row is the row where the card is placed.
     * column is the column where the card is placed.
     * chosenCard is the card that the player has chosen to place.
     * creator is the creator of the game.
     */
    private final int row;
    private final int column;
    private final int chosenCard;
    private final String creator;

    /**
     * Constructor of the class.
     * @param column column where the card is placed
     * @param row row where the card is placed
     * @param chosenCard card that the player has chosen to place
     * @param username username of the player that has placed the card
     * @param creator creator of the game
     */
    public PlaceEvent1(int column, int row, int chosenCard, String username, String creator) {
        super(EventType.PLACE, username);
        this.row = row;
        this.column = column;
        this.chosenCard = chosenCard;
        this.creator = creator;

    }

    /**
     * Method to get the row where the card is placed.
     * @return the row where the card is placed
     */
    public int getRow() {
        return row;
    }

    /**
     * Method to get the column where the card is placed.
     * @return the column where the card is placed
     */
    public int getColumn() {
        return column;
    }

    /**
     * Method to get the card that the player has chosen to place.
     * @return the card that the player has chosen to place
     */
    public int getChosenCard() {
        return chosenCard;
    }

    /**
     * Method to get the creator of the game.
     * @return the creator of the game
     */
    public String getCreator(){ return this.creator; }


}
