package it.polimi.ingsw.EventHandling;

/**
 * Event that is generated when a player quits the game and others players have to be notified.
 */
public class TerminateGameEvent extends Event{
    /**
     * Constructor of the class.
     * @param type is the type of the event
     * @param username is the nickname of the player that generated the event
     */
    public TerminateGameEvent(EventType type, String username) {
        super(type, username);
    }
}
