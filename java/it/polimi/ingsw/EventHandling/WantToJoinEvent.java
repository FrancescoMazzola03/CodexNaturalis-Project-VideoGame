package it.polimi.ingsw.EventHandling;

/**
 * Event that is generated when a player wants to join the game.
 */
public class WantToJoinEvent extends Event{
    /**
     * Attributes of the class.
     * username is the nickname of the player that wants to join the game.
     */
    private final String username;

    /**
     * Constructor of the class.
     * @param username is the nickname of the player that wants to join the game
     */
    public WantToJoinEvent(String username) {
        super(EventType.WANTOJOIN, username);
        this.username = username;
    }

    /**
     * Method to get the nickname of the player that wants to join the game.
     * @return the nickname of the player that wants to join the game
     */
    public String getUsername() {
        return username;
    }

}
