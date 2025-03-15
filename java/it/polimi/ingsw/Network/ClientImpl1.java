package it.polimi.ingsw.Network;


import it.polimi.ingsw.EventHandling.*;
import it.polimi.ingsw.Listener.ViewListener;
import it.polimi.ingsw.ModelView.GameView;
import it.polimi.ingsw.View.*;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
/**
 * ClientImpl1 is an implementation of the Client1 interface and Runnable.
 * It represents a client in a distributed application with both a text-based and graphical user interface.
 */
public  class ClientImpl1 extends UnicastRemoteObject implements Client1, Runnable{
    /**
     * The chosen user interface (UI) for this client, either text-based (TUI1) or graphical (GUI).
     * The server to which this client is connected.
     * The username of this client.
     */
    UI1 chosenUi;
    final Server1 server;
    String username;
    Timer timer;

    /**
     * Constructor of the class
     * @param server the server to connect to.
     * @param i an integer to determine which UI to use (1 or 2 for TUI, otherwise GUI).
     * @throws RemoteException if a remote communication error occurs.
     */
    public ClientImpl1(Server1 server, int i) throws RemoteException {
        if(i==1 || i==2)
            chosenUi = new TUI1();
        else
            chosenUi = new GUI();
        username = UUID.randomUUID().toString();
        this.server = server;
        initialize(server);
    }

    /**
     * Initializes the client by registering it with the server and adding a listener to the chosen UI.
     * @param server the server to connect to.
     * @throws RemoteException if a remote communication error occurs.
     */
    private void initialize(Server1 server) throws RemoteException {
        server.register(this);
        chosenUi.addListener(new ViewListener() {
            @Override
            public void handleMessage(Event event) {
                try {
                    server.handleMessage(event, ClientImpl1.this);
                } catch (RemoteException e) {
                    System.err.println("Unable to update the server: " + e.getMessage() + ". Skipping the update...");
                }
            }
        });
    }
    /**
     * Retrieves the name of the client.
     * @return the username of the client.
     * @throws RemoteException if a remote communication error occurs.
     */
    @Override
    public String getName() throws RemoteException {
        return username;
    }

    /**
     * Resets the timer for the client.
     */
    public void resetTimer() {
        if (timer != null)
            timer.cancel();
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("\n" + "[DISCONNECTION] terminating client...");
                System.exit(0);
            }
        }, 18500);
    }



    /**
     * Handles an event sent from the server to the client.
     * @param event the event to be handled.
     * @throws RemoteException if a remote communication error occurs.
     */
    @Override
    public void handleMessage(Event event) throws RemoteException {
        switch (event.getType()) {
            case PING:
                // System.out.println("Ping received by server. Sending pong");
                resetTimer();
                server.handleMessage(new Pong(EventType.PONG, username), this);
                break;
            case TERMINATEEVENT:
                chosenUi.exitGame();
                break;
            case GETCREATORS:
                GetCreatorsEvent getCreatorsEvent = (GetCreatorsEvent) event;
                chosenUi.handleCreators(getCreatorsEvent.getCreators());
                break;
            case REFRESH:
                RefreshEvent refreshEvent = (RefreshEvent) event;
                GameView gameView = refreshEvent.getGameView();
                chosenUi.handleRefresh(gameView, event.getUsername());
                //System.out.println("Refreshed");
                break;
            case CHAT:
                ChatEvent1 chatEvent = (ChatEvent1) event;
                String message = chatEvent.getMessage();
                String username = chatEvent.getUsername();
                chosenUi.handleChat(message, username);
                break;
            case NOPAWN:
                chosenUi.handleError(event, event.getUsername());
                break;
            case NOJOIN:
            case INVALIDPOS:
            case NOCREATE:
            case INVALIDUSER:
                chosenUi.handleError(event, event.getUsername());
                break;


        }
    }

    /**
     * Runs the chosen UI with the client's username.
     */
    public void run() {
        try {
            chosenUi.run(username);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

