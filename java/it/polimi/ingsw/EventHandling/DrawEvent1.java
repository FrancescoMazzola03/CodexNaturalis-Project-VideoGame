package it.polimi.ingsw.EventHandling;

import java.io.Serializable;

/**
 * Event that is generated when a player draws a card
 */
public class DrawEvent1 extends Event implements Serializable {
    /**
     * Attributes of the class
     * chosencard is the card that the player has drawn
     * creator is the creator of the game
     */
    private final int chosencard;
    private final String creator;

    /**
     * Constructor of the class
     * @param card the card that the player has drawn
     * @param username the username of the player that has drawn the card
     * @param creator the creator of the game
     */
    public DrawEvent1(int card, String username, String creator) {
        super(EventType.DRAW,username);
        this.chosencard = card;
        this.creator = creator;

    }

    /**
     * Method that returns the card that the player has drawn
     * @return the card that the player has drawn
     */
    public int getChosencard() {
        return chosencard;
    }


    /**
     * Method that returns the creator of the game
     * @return the creator of the game
     */
    public String getCreator(){ return this.creator; }
}
