package it.polimi.ingsw.Main;


import it.polimi.ingsw.Network.ClientImpl1;
import it.polimi.ingsw.Network.Server1;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


/**
 * This class is the main class of the client side of the application.
 * It is used to start the client with RMI connection.
 */
public class AppClientRMI1 {
    /**
     * This is the main method of the client side of the application.
     * @param args the command line arguments
     * @param i the choice of the user interface
     * @param ip the ip address of the server
     * @throws RemoteException exception thrown when a remote operation fails
     * @throws NotBoundException exception thrown when a remote object is not bound
     */
    public static void main(String[] args, int i, String ip) throws RemoteException, NotBoundException {

        Registry registry = LocateRegistry.getRegistry(ip, 1234);
        Server1 server = (Server1) registry.lookup("VirtualServer");

        ClientImpl1 client = new ClientImpl1(server, i);
        client.run();

    }
}
