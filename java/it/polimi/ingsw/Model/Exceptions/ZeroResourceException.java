package it.polimi.ingsw.Model.Exceptions;

import it.polimi.ingsw.Model.Resource;

/**
 * Exception thrown when the system want to decrement a resource that is already zero
 */
public class ZeroResourceException extends RuntimeException{
    /**
     * Default constructor
     * @param res the resource that is already zero
     */
    public ZeroResourceException(Resource res) {
        super("You have zero " + res.getResName() + ", can't have less");
    }

}
