package it.polimi.ingsw.EventHandling;

import java.io.Serializable;

/**
 * Event that is generated when a player sets the starter card.
 */
public class SetStarterCardEvent1 extends Event implements Serializable {
    /**
     * Attributes of the class.
     * front is true if the card is front, false if it is back.
     * creator is the creator of the game.
     */
    private final boolean front;
    private final String creator;


    /**
     * Constructor of the class
     * @param nickname is the nickname of the player that generated the event
     * @param front is true if the card is front, false if it is back
     * @param creator is the creator of the game
     */
    public SetStarterCardEvent1(String nickname, boolean front, String creator) {
        super(EventType.STARTERCARD,nickname);
        this.front = front;
        this.creator = creator;

    }

    /**
     * Method to get if the card is front or back.
     * @return true if the card is front, false if it is back
     */
    public boolean getFront() {
        return front;
    }

    /**
     * Method to get the creator of the game.
     * @return the creator of the game
     */
    public String getCreator() {
        return creator;
    }


}
