package it.polimi.ingsw.Model.Exceptions;


/**
 * Exception designed to suppress detailed information about the exception
 */
    public class SilentRuntimeException extends RuntimeException {

    /**
     * Default constructor
     * @param cause the cause of the exception
     */
        public SilentRuntimeException(Throwable cause) {
            super(null, null, true, false);

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
            return this;
        }

    /**
     * Used to suppress detailed information about the exception
     * @return the message of the exception
     */
        public String getMessage() {
            return "";
        }

    /**
     * Used to suppress detailed information about the exception
     * @return an empty string
     */
        public String toString() {
            return "";
        }





    }
