package it.polimi.ingsw.Model;

import java.io.Serializable;

/**
 * PlayingCard class, is used to represent a playing card. so a card that can be placed.
 * It extends the Card class because it's a card.
 */
public class PlayingCard extends Card implements Serializable {
    /**
     * Attributes of the class.
     * permanentRes is the resource that the player will gain if he places the card on the back.
     * isUsed is a flag to check if the card has been used or not to the count of a PatternObjective.
     * bonusPoints is the number of points that the card has.
     */
    private Resource permanentRes;
    private boolean isUsed;
    private int bonusPoints;

    /**
     * Constructor of the class.
     * @param cor0 the resource of the first corner
     * @param cor1 the resource of the second corner
     * @param cor2 the resource of the third corner
     * @param cor3 the resource of the fourth corner
     * @param permanentRes the resource that the player will gain if he places the card on the back
     * @param bonusPoints the number of points that the card has
     * @param numCard the number of the card
     */
    public PlayingCard(Resource cor0, Resource cor1, Resource cor2, Resource cor3,Resource permanentRes,int bonusPoints,String numCard){
        super(cor0, cor1, cor2, cor3,numCard);
        this.bonusPoints = bonusPoints;
        this.permanentRes = permanentRes;
        this.isUsed = false;
    }

    /**
     * Method to get the permanent resource of the card.
     * @return the permanent resource of the card
     */
    public Resource getPermanentRes()
    {
        return this.permanentRes;
    }

    /**
     * Method to set a resource for a corner.
     * @param i the index of the corner
     * @param resource the resource to set
     */
    public void setCorner (int i,Resource resource){
        this.getCorner(i).setRes(resource);
    }

    /**
     * Method to get the number of points that the card has.
     * @return the number of points that the card has
     */
    public int getBonusPoints()
    {
        return this.bonusPoints;
    }

    /**
     * Method to get the flag.
     * @return true if the card has been used for the objective, false otherwise
     */
    public boolean isUsed(){return isUsed;}

    /**
     * Method to set the flag.
     * @param flag the flag to set
     */
    public void setUsed(boolean flag){isUsed = flag;}

    /**
     * Method to get how many points has a card.
     * It seems that it's a copy of getBonusPoints method, but it isn't, it's goal is to be overridden by the subclasses.
     * @param player the player that will gain the points
     * @param row the row of the matrix where the card is placed
     * @param column the column of the matrix where the card is placed
     * @return the number of points that the player will gain
     */
    public int addPoints(Player player, int row, int column){
        return this.bonusPoints;
    }
}
