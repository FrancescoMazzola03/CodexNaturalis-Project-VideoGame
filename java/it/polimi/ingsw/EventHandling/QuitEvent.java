package it.polimi.ingsw.EventHandling;

import java.io.Serializable;

/**
 * Event that is generated when a player quits the game.
 */
public class QuitEvent extends Event implements Serializable {
    /**
     * Attributes of the class.
     * creator is the creator of the game.
     */
    private String creator;

    /**
     * Constructor of the class
     * @param type is the type of the event
     * @param creator is the creator of the game
     * @param username is the nickname of the player that generated the event
     */
    public QuitEvent(EventType type, String creator,String username) {
        super(type, username);
        this.creator = creator;

    }

    /**
     * Method to get the creator of the game.
     * @return the creator of the game
     */
    public String getCreator() {
        return creator;
    }
}
