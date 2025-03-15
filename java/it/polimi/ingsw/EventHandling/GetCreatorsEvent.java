package it.polimi.ingsw.EventHandling;

import java.util.ArrayList;

/**
 * Event to get the list of creators
 */
public class GetCreatorsEvent extends Event{
    private final ArrayList<String> creators ;

    /**
     * Constructor of the class
     * @param username is the nickname of the player that generated the event (null because the event is generated by the server)
     * @param creators is the list of creators of the games
     */
    public GetCreatorsEvent( String username, ArrayList<String> creators) {
        super(EventType.GETCREATORS, username);
        this.creators = creators;
    }

    /**
     * Method to get the list of creators of the games
     * @return the list of creators of the games
     */
    public ArrayList<String> getCreators() {
        return creators;
    }
}
