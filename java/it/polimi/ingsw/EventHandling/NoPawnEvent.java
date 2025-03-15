package it.polimi.ingsw.EventHandling;

import java.io.Serializable;

/**
 * NoPawnEvent class, is used to represent an event that is generated when a player choose a pawn already chosen by another player
 */
public class NoPawnEvent extends Event implements Serializable {
    /**
     * Attributes of the class
     * pawnOwner is the username of the player that has already chosen the pawn
     */
    String pawnOwner;

    /**
     * Constructor of the class
     * @param username the username of the player that has chosen the pawn
     * @param pawnOwner the username of the player that has already chosen the pawn
     */
    public NoPawnEvent(String username, String pawnOwner) {
        super(EventType.NOPAWN, username);
        this.pawnOwner = pawnOwner;
    }

    /**
     * Method that returns the username of the player that has already chosen the pawn
     * @return the username of the player that has already chosen the pawn
     */
    public String getPawnOwner() {
        return pawnOwner;
    }
}


