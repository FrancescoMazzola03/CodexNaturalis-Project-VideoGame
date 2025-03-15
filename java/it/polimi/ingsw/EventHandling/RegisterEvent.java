package it.polimi.ingsw.EventHandling;

/**
 * Event that is generated when a player registers to the game.
 */
public class RegisterEvent extends Event{

    /**
     * Constructor of the class
     * @param username is the nickname of the player that generated the event
     */
    public RegisterEvent(String username) {
        super(EventType.REGISTER, username);

    }

}
