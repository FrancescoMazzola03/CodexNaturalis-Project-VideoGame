package it.polimi.ingsw.EventHandling;

import java.io.Serializable;

/**
 * Event that is generated when a player pings the server
 */
public class Ping extends Event implements Serializable {
    long currentTimeMillis;
    /**
     * Constructor of the class
     * @param type type of the event
     * @param username the username of the player that has pinged the server
     */
    public Ping(EventType type, String username, long currentTimeMillis) {
        super(type, username);
        this.currentTimeMillis = currentTimeMillis;
    }

    public long getCurrentTimeMillis() {
        return currentTimeMillis;
    }
}
