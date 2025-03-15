package it.polimi.ingsw.Model.Exceptions;

/**
 * Exception thrown when the player tries to perform an action that requires more resources than he has
 */
public class InsufficientResourcesException extends RuntimeException{
    /**
     * Default constructor
     * It calls the RuntimeException constructor with the message "Not enough resources, try again"
     */
    public InsufficientResourcesException() {
        super("Not enough resources, try again");
    }
}
