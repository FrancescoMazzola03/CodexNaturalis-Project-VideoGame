package it.polimi.ingsw.Network.SocketMiddleware;



import it.polimi.ingsw.EventHandling.Event;
import it.polimi.ingsw.EventHandling.RegisterEvent;
import it.polimi.ingsw.Network.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.RemoteException;

/**
 * A stub implementation of the Server1 interface.
 * This class acts as a client-side representation of the server, enabling communication with the server
 * using sockets and object streams.
 */
public class ServerStub1 implements Server1 {
    /**
     * The IP address of the server.
     * The port number of the server.
     * The output stream for sending events to the server.
     * The input stream for receiving events from the server.
     * The socket used to connect to the server.
     */
    String ip;
    int port;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private Socket socket;
    /**
     * Constructor of the class.
     * @param ip   the IP address of the server.
     * @param port the port number of the server.
     */
    public ServerStub1(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }
    /**
     * Registers a client with the server.
     * This method establishes a socket connection to the server using the provided IP address and port.
     * It initializes the output and input streams for communication. If the connection or stream
     * initialization fails, a RemoteException is thrown.
     * After establishing the connection, this method sends a {@code RegisterEvent} to the server to
     * register the client
     * @param client the client to be registered.
     * @throws RemoteException if the server cannot be reached, or if the input/output streams cannot be created.
     */
    @Override
    public void register(Client1 client) throws RemoteException {
        try {
            this.socket = new Socket(ip, port);
            try {
                this.oos = new ObjectOutputStream(socket.getOutputStream());
            } catch (IOException e) {
                throw new RemoteException("Cannot create output stream", e);
            }
            try {
                this.ois = new ObjectInputStream(socket.getInputStream());
            } catch (IOException e) {
                throw new RemoteException("Cannot create input stream", e);
            }
        } catch (IOException e) {
            throw new RemoteException("Unable to connect to the server", e);
        }
        handleMessage(new RegisterEvent(client.getName()), client); // create the event to register the client
    }

    /**
     * Handles an event and sends it to the specified client.
     * @param event  the event to be sent.
     * @param client the client to which the event is to be sent.
     * @throws RemoteException if an I/O error occurs while sending the event.
     */
    @Override
    public void handleMessage(Event event, Client1 client) throws RemoteException {
        try {
            oos.writeObject(event);
            oos.reset();
            oos.flush();
        } catch (IOException e) {
            throw new RemoteException("Cannot send event", e);
        }
    }
    /**
     * Receives an event from the specified client.
     * This method reads an event from the input stream and then calls the client's
     * handleMessage method with the received event.
     * @param client the client from which the event is to be received.
     * @throws RemoteException if an I/O error occurs while receiving the event, or if the event cannot be deserialized.
     */
    public void receive(Client1 client) throws RemoteException {
        Event event;
        try {
            event = (Event) ois.readObject();
        } catch (IOException e) {
            throw new RemoteException("Cannot receive model view from client", e);
        } catch (ClassNotFoundException e) {
            throw new RemoteException("Cannot deserialize model view from client", e);
        }
        client.handleMessage(event);
    }
    /**
     * Closes the socket connection.
     * @throws RemoteException if an I/O error occurs while closing the socket.
     */
    public void close() throws RemoteException {
        try {
            socket.close();
        } catch (IOException e) {
            throw new RemoteException("Cannot close socket", e);
        }
    }
}
