package it.polimi.ingsw.Model.Exceptions;

/**
 * Exception designed to suppress detailed information about the exception
 */
public class NoCauseException extends InterruptedException{
    /**
     * Default constructor
     */
    public NoCauseException() {
        super("");

    }

    /**
     * Used to suppress detailed information about the exception
     * @return an empty string
     */
    public String toString(){
        return "";
    }
    /**
     * Used to print the stack trace
     */

    @Override
    public void printStackTrace() {
        System.out.println("");
    }
    /**
     * Used to suppress the stack trace
     * @return the stack trace
     */
    public synchronized Throwable fillInStackTrace() {
        return this; // Suppress the stack trace
    }
    /**
     * Used to suppress detailed information about the exception
     * @return the message of the exception
     */
    public String getMessage() {
        return "";
    }

}
