package it.polimi.ingsw.EventHandling;

import java.io.Serializable;
/**
 * Event that is generated when a player tries to place a card in an invalid position.
 */
public class InvalidPositionEvent1 extends Event implements Serializable {
    String reason;
    /**
     * Constructor for the event.
     * @param reason the reason why the position is invalid
     * @param username the player that generated the event
     */
    public InvalidPositionEvent1(String reason, String username) {
        super(EventType.INVALIDPOS,username);
        this.reason = reason;
    }
    /**
     * Getter for the reason why the position is invalid.
     * @return the reason why the position is invalid
     */
    public String getReason() {
        return reason;
    }
}
