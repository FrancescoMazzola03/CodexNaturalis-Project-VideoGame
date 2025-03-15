
package it.polimi.ingsw.Main;

import it.polimi.ingsw.Network.Server1;
import it.polimi.ingsw.Network.ServerImpl1;
import it.polimi.ingsw.Network.SocketMiddleware.ClientSkeleton1;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;


/**
 * This class is the main class of the server side of the application.
 * It is used to start the server side of the application.
 * It starts the RMI server and the Socket server.
 * The RMI server is used to handle the RMI connection with the clients.
 * The Socket server is used to handle the Socket connection with the clients.
 */

public class AppServer1{
 public static void main(String[] args) throws RemoteException {

  Server1 server = new ServerImpl1();
  // Start RMI server in a new thread
  new Thread(() -> {

   try {
    String name = "VirtualServer";
       // System.setProperty("java.rmi.server.hostname","172.20.10.13");

       Server1 stub = (Server1) UnicastRemoteObject.exportObject(server, 0);
    Registry registry = LocateRegistry.createRegistry(1234);
    registry.rebind(name, stub);
    System.out.println("RMI Server bound");
   } catch (RemoteException e) {
    System.err.println("Cannot start RMI server: " + e.getMessage());
   }
  }).start();

  // Start Socket server in a new thread
  new Thread(() -> {
   try (ServerSocket serverSocket = new ServerSocket(1235)) {
    while (true) {
     try {
      Socket socket = serverSocket.accept();
      new Thread(() -> {
        try {

         ClientSkeleton1 clientSkeleton = new ClientSkeleton1(socket);
         while (clientSkeleton.getName() == null)
           clientSkeleton.receive(server);
         server.register(clientSkeleton);
         while (true) {
          clientSkeleton.receive(server);
        }
       } catch (RemoteException e) {
            System.err.println("[SOCKET ERROR] " + e.getMessage());
            try {
             socket.close();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
      }).start();
     } catch (IOException e) {
        System.err.println("Socket failed: " + e.getMessage() + ". Closing connection and waiting for a new one...");
     }
    }
   } catch (IOException e) {
    System.err.println("Cannot create server socket: " + e.getMessage());
   }
  }).start();
 }

    }






