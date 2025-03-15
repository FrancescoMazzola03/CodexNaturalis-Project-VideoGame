package it.polimi.ingsw.EventHandling;

import java.io.Serializable;

/**
 * Event that is generated when a player responds to a ping.
 */
public class Pong extends Event implements Serializable {
    /**
     * Constructor of the class
     * @param type is the type of the event
     * @param username is the nickname of the player that generated the event
     */
    public Pong(EventType type, String username) {
        super(type, username);
    }
}
