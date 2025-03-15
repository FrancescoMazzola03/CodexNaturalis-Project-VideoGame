package it.polimi.ingsw.Network;

import it.polimi.ingsw.EventHandling.Event;

import java.rmi.Remote;
import java.rmi.RemoteException;
/**
 * Client1 is a remote interface representing a client in a distributed application.
 * It extends the Remote interface to support remote method invocation.
 */
public interface Client1 extends Remote {
    /**
     * Handles an event sent from the server to the client.
     * @param event the event to be handled.
     * @throws RemoteException if a remote communication error occurs.
     */
    void handleMessage(Event event) throws RemoteException;
    /**
     * Retrieves the name of the client.
     * @return the name of the client.
     * @throws RemoteException if a remote communication error occurs.
     */
    String getName() throws RemoteException;

}

