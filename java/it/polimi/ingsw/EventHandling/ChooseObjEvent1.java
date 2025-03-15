package it.polimi.ingsw.EventHandling;

import java.io.Serializable;

/**
 * Event that is generated when a player chooses an objective card
 */
public class ChooseObjEvent1 extends Event implements Serializable {
    /**
     * Attributes of the class
     * objCard is the number of the objective card that the player has chosen
     * creator is the creator of the game
     */
    int objCard;
    String creator;

    /**
     * Constructor of the class
     * @param username is the player that has chosen the objective card
     * @param objCard is the number of the objective card that the player has chosen
     * @param creator is the creator of the game
     */
    public ChooseObjEvent1(String username, int objCard, String creator){
        super(EventType.CHOOSEOBJ,username);
        this.objCard = objCard;
        this.creator = creator;
    }

    /**
     * Method that returns the number of the objective card that the player has chosen
     * @return the number of the objective card that the player has chosen
     */
    public int getObjCard() {
        return objCard;
    }

    /**
     * Method that returns the creator of the game
     * @return the creator of the game
     */
    public String getCreator(){ return this. creator; }

}
