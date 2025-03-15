package it.polimi.ingsw.EventHandling;

import java.io.Serializable;

/**
 * Event that is generated when the game ends
 */
public class GameEndedEvent extends Event implements Serializable {
    /**
     * The creator of the game
     */
    String creator;

    /**
     * Constructor for the GameEndedEvent
     * @param username The username of the player that the event is addressed to
     * @param creator The creator of the game
     */
    public GameEndedEvent(String username, String creator){
        super(EventType.GAMEENDED, username);
    this.creator = creator;
    }


    /**
     * Getter for the creator of the game
     * @return The creator of the game
     */
    public String getCreator(){ return this.creator; }

}
