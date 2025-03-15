package it.polimi.ingsw.EventHandling;

import java.io.Serializable;

/**
 * FlipEvent1 class, is used to represent a flip event, so to manage when a player flips a card.
 */
public class FlipEvent1 extends Event implements Serializable {
    /**
     * Attributes of the class.
     * creator is the creator of the game.
     */
    String creator;

    /**
     * Constructor of the class.
     * @param username player that flipped the card
     * @param creator creator of the game
     */
    public FlipEvent1(String username, String creator) {
        super(EventType.FLIP, username);
        this.creator = creator;

    }

    /**
     * Method to get the creator of the game.
     * @return the creator of the game
     */
    public String getCreator(){ return this.creator; }

}
