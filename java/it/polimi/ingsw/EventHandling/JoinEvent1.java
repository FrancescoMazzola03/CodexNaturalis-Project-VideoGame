package it.polimi.ingsw.EventHandling;

import java.io.Serializable;

/**
 * Event that is generated when a player joins the game.
 */
public class JoinEvent1 extends Event implements Serializable {
    /**
     * Attributes of the class.
     * creator is the creator of the game.
     * id is the id of the client.
     * players is the number of players of the game.
     */
    String creator;
    String id;
    int players = 0;


    /**
     * Constructor of the class
     * @param username is the nickname of the player that generated the event
     * @param creator is the creator of the game
     * @param id is the id of the client
     * @param players is the number of players of the game
     */
    public JoinEvent1(String username,String creator, String id, int players) {
        super(EventType.USERNAME,username);
        this.creator = creator;
        this.id = id;
        this.players = players;
    }

    /**
     * Method to get the creator of the game.
     * @return the creator of the game
     */
    public String getCreator() {
        return creator;
    }

    /**
     * Method to get the id of the client.
     * @return the id of the client
     */
    public String getId(){
        return this.id;
    }

    /**
     * Method to get the number of players of the game.
     * @return the number of players of the game
     */
    public int getPlayers(){
        return this.players;
    }

}
