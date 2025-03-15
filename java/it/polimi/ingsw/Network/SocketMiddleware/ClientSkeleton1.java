package it.polimi.ingsw.Network.SocketMiddleware;



import it.polimi.ingsw.EventHandling.Event;
import it.polimi.ingsw.EventHandling.RegisterEvent;
import it.polimi.ingsw.Network.Client1;
import it.polimi.ingsw.Network.Server1;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.RemoteException;

/**
 * This class is the skeleton of the client that manages communication between the server and client
 * using input and output streams to serialize and deserialize Event objects.
 * It implements the Client1 interface.
 */
public class ClientSkeleton1 implements Client1 {

    private final ObjectOutputStream oos;
    private final ObjectInputStream ois;
    private String name;

    /**
     * Constructor of the class.
     * @param socket is the socket used to communicate with the client
     * @throws RemoteException if the output stream cannot be created
     */
    public ClientSkeleton1(Socket socket) throws RemoteException {
        this.name = null;
        try {
            this.oos = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            throw new RemoteException("Cannot create output stream", e);
        }
        try {
            this.ois = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            throw new RemoteException("Cannot send message, client have disconnected.", e);
        }
    }

    /**
     * This method serializes the Event object and sends it to the client via ObjectOutputStream..
     * @param event is the event to be sent to the client
     * @throws RemoteException if the event cannot be sent
     */
    @Override
    public void handleMessage(Event event) throws RemoteException {
        try {
            oos.writeObject(event);
            oos.reset();
            oos.flush();
        } catch (IOException e) {
            throw new RemoteException("Cannot send model view");
        }
    }

    /**
     * This method retrieves the name of the client.
     * @return the name of the client
     * @throws RemoteException if the name cannot be retrieved
     */
    @Override
    public String getName() throws RemoteException {
        return this.name;
    }


    /**
     * Receives an event from the client and passes it to the server for processing.
     * This method reads an Event from the client's input stream.
     * If the event is an instance of RegisterEvent, sets the client name with the value obtained from the event.
     * It then passes the received event and reference to itself to the server's handleMessage method.
     * @param server the reference to the server that manages the Event
     * @throws RemoteException if an error occurs while receiving the event or deserializing the object.
     */
    public void receive(Server1 server) throws RemoteException {
        Event event;
        try {
            event = (Event) ois.readObject();
            if (event instanceof RegisterEvent){
                this.name = event.getUsername();
            }
        } catch (IOException e) {
            throw new RemoteException("Client " + name + " does not respond. Client will be removed");
        } catch (ClassNotFoundException e) {
            throw new RemoteException("Cannot deserialize choice from client", e);
        }
        server.handleMessage(event, this);
    }

}
