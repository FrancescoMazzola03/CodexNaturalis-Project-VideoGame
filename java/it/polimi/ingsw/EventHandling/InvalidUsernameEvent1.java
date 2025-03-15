package it.polimi.ingsw.EventHandling;

import java.io.Serializable;
/**
 * InvalidUsernameEvent class, is used to represent an invalid username event.
 * An invalid username event is an event that is generated when a player tries to connect to the server with a username that is already taken.
 */
public class InvalidUsernameEvent1 extends Event implements Serializable {
    /**
     * Constructor of the class.
     * @param username is the nickname of the player that generated the event
     */
    public InvalidUsernameEvent1(String username){
        super (EventType.INVALIDUSER, username);
    }

}
