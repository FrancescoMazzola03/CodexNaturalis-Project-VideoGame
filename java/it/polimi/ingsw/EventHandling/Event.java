package it.polimi.ingsw.EventHandling;

import java.io.Serializable;

/**
 * Event class, is used to represent an event.
 * For example, it's an event when a player draw a card,create a game etc.
 * It is serializable so that it can be sent through the network.
 * It is used to communicate in the MVC pattern.
 * it is used to communicate between the client and the server.
 */
public class Event implements Serializable {
    /**
     * Attributes of the class.
     * type is the type of the event.
     * username is the username of the player that has generated the event or an event that involves it.
     */
    private final String username;
    private EventType type;

    /**
     * Constructor of the class.
     * @param type is the type of the event.
     * @param username is the username of the player that has generated the event or an event that involves it.
     */
    public Event(EventType type,String username){
        this.type = type;
        this.username = username;
    }

    /**
     * Method to get the type of the event.
     * @return the type of the event.
     */
    public EventType getType(){
        return this.type;
    }

    /**
     * Method to get the username of the player that has generated the event or an event that involves it.
     * @return the username of the player that has generated the event or an event that involves it.
     */
    public String getUsername(){
        return this.username;
    }
}
