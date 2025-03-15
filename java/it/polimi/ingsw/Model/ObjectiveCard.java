package it.polimi.ingsw.Model;

import java.io.Serializable;

/**
 * ObjectiveCard class, is used to represent an objective card.
 * An objective card is a type of card that the player can complete one or more times during the match
 * There are different types of objective so the class is abstract, each of them have the same properties but
 * different algorithms to check if the objective is completed.
 */
public  abstract class ObjectiveCard implements Serializable {
    /**
     * Attributes of the class.
     * points is the number of points that the player will gain if he completes the objective.
     * It is protected so that the subclasses can access it.
     */
    protected int points;

    /**
     * Constructor of the class
     * @param points is the number of points that the player will gain if he completes the objective.
     */
    public ObjectiveCard(int points) {
        this.points = points;
    }

    /**
     * Method to get the number of points that the player will gain if he completes the objective.
     * @return the number of points that the player will gain if he completes the objective.
     */
    public int getPoints(){return this.points;}

    /**
     * Method to check how many times the objective is completed, it is abstract because each objective has a different algorithm.
     * @param station is the board of the player
     * @return the number of times the objective is completed
     */
    abstract public int timesCompleted(Station station);

    /**
     * Method to get the number of the card
     * @return the number of the card
     */
    abstract public String getNumCard();
}
