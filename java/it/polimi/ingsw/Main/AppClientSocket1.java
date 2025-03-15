package it.polimi.ingsw.Main;



import it.polimi.ingsw.Network.ClientImpl1;
import it.polimi.ingsw.Network.SocketMiddleware.ServerStub1;

import java.rmi.RemoteException;

/**
 * This class is the main class of the client side of the application.
 * It is used to start the client with Socket connection.
 */
public class AppClientSocket1
{
    /**
     * This is the main method of the client side of the application.
     * @param args the command line arguments
     * @param choice the choice of the user interface
     * @param ip the ip address of the server
     * @throws RemoteException exception thrown when a remote operation fails
     */
    public static void main(String[] args, int choice, String ip) throws RemoteException {
        ServerStub1 serverStub = new ServerStub1(ip, 1235);
        ClientImpl1 client = new ClientImpl1(serverStub, choice);
        new Thread() {
            @Override
            public void run() {
                while(true) {
                    try {
                        serverStub.receive(client);
                    } catch (RemoteException e) {
                        System.err.println("Cannot receive from server. Stopping...");
                        try {
                            serverStub.close();
                        } catch (RemoteException ex) {
                            System.err.println("Cannot close connection with server. Halting...");
                        }
                        System.exit(1);
                    }
                }
            }
        }.start();

        client.run();
    }
}
