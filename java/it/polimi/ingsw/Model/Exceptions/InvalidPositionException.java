package it.polimi.ingsw.Model.Exceptions;

/**
 * Exception thrown when the player tries to perform an action on an invalid position
 */
public class InvalidPositionException extends RuntimeException{
    /**
     * Default constructor
     * It calls the RuntimeException constructor with the message "Invalid position chosen, try again"
     */
    public InvalidPositionException() {
        super("Invalid position chosen, try again");
    }
}
