package it.polimi.ingsw.Network;

import it.polimi.ingsw.EventHandling.Event;

import java.rmi.Remote;
import java.rmi.RemoteException;
/**
 * The Server1 interface defines the methods that a remote server must implement
 * to support client registration and event handling in a distributed application.
 */
public interface Server1 extends Remote {
    /**
     * Registers a new client with the server.
     * @param client the client to register.
     * @throws RemoteException if a remote communication error occurs during registration.
     */
    void register(Client1 client) throws RemoteException;
    /**
     * Handles an event sent from a client.
     * @param event the event to be handled.
     * @param client the client that sent the event.
     * @throws RemoteException if a remote communication error occurs while handling the event.
     */
    void handleMessage(Event event, Client1 client) throws RemoteException;
}


