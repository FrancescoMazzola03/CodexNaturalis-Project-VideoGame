package it.polimi.ingsw.Model.Exceptions;

/**
 * Exception thrown when there isn't anu corner to place the card.
 */
public class NoCornersException extends RuntimeException{
    /**
     * Default constructor
     * It calls the RuntimeException constructor with the message "No corners, try again"
     */
    public NoCornersException() {
        super("No corners, try again");
    }
}
