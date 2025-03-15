package it.polimi.ingsw.EventHandling;

import java.io.Serializable;

/**
 * Event that is generated when a player chooses a pawn
 */
public class ChoosePawnEvent extends Event implements Serializable{
    /**
     * Attributes of the class
     * pawn is the pawn that the player has chosen
     * creator is the creator of the game
     */
    int pawn;
    String creator;

    /**
     * Constructor of the class
     * @param pawn the pawn that the player has chosen
     * @param creator the creator of the game
     * @param username the username of the player that has chosen the pawn
     */
    public ChoosePawnEvent(int pawn, String creator, String username){
        super(EventType.CHOOSEPAWN, username);
        this.pawn = pawn;
        this.creator = creator;
    }

    /**
     * Method that returns the pawn that the player has chosen
     * @return the pawn that the player has chosen
     */
    public int getPawn() {
        return pawn;
    }

    /**
     * Method that returns the creator of the game
     * @return the creator of the game
     */
    public String getCreator() {
        return creator;
    }
}




