package it.polimi.ingsw.Network;

import it.polimi.ingsw.Controller.GameController1;
import it.polimi.ingsw.Controller.Multicontroller;
import it.polimi.ingsw.EventHandling.*;
import it.polimi.ingsw.Model.Exceptions.InsufficientResourcesException;
import it.polimi.ingsw.Model.Exceptions.InvalidPositionException;
import it.polimi.ingsw.Model.Exceptions.NoCornersException;
import it.polimi.ingsw.Model.Model1;

import java.rmi.RemoteException;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Implementation of the Server1 interface, managing client registration,
 * event handling, and heartbeat monitoring for active clients.
 */
public class ServerImpl1 implements Server1 {
    /**
     * Attributes of the class.
     * controller The Multicontroller that manages game controllers and listeners.
     * uniqueuser A flag indicating if a username is unique.
     * heartbeatThread The thread responsible for sending heartbeat messages to clients.
     * activeClients A list of active clients connected to the server.
     * clientCreators A map of clients to their respective creators of the games.
     * clientNames A map of clients to their respective names.
     * clientIds A map of clients to their respective IDs.
     * lastEvent The type of the last event received.
     * isRunning A flag indicating whether the server is running.

     */
    private Multicontroller controller;
    private boolean uniqueuser;
    private Thread heartbeatThread;
    private List<Client1> activeClients;
    private List<String> disconnectedIds;
    private Map <Client1, String> clientCreators;
    private Map <Client1, String> clientNames;
    private Map <Client1, String> clientIds;
    // private EventType lastEvent = null;
    // private Boolean isRunning = true;
    // private Map <Client1, Boolean> clientIsPinged;
    private int numberOfThreads = 0;
    // private Object lock = new Object();


    public ServerImpl1(){
        this.controller = new Multicontroller();
        activeClients = new <Client1> ArrayList();
        disconnectedIds = new ArrayList<>();
        clientCreators = new HashMap<>();
        clientNames = new HashMap<>();
        clientIds = new HashMap<>();
        // clientIsPinged = new HashMap<>();
        // startHeartBeat(); // tunz tunz tunz
    }
    /**
     * Registers a new client with the server.
     * @param client the client to register.
     * @throws RemoteException if a remote communication error occurs during registration.
     */
    public  void register (Client1 client) throws RemoteException {
        synchronized (activeClients) {
            activeClients.add(client);
            clientIds.put(client, client.getName());
            startClientPing(client, numberOfThreads);
            numberOfThreads++;
        }

        String id = client.getName();
        System.out.println("Client registered: " + id);
        this.controller.addListener( id,(arg) -> { // metodo multi-controller: a ogni client (id) associamo un listener
            try {
                client.handleMessage(arg);
                // System.out.println("Number of listeners: " + this.controller.getListeners().size());
            } catch (RemoteException e) {
                System.err.println("Unable to update the client: " + id + ". Removing client.");
            }
        });

    }


    /**
     * Starts a new thread to ping a client.
     * @param client the client to ping.
     * @param threadNumber the number of the thread.
     * @throws RemoteException if a remote communication error occurs while pinging the client.
     */
    void startClientPing(Client1 client, int threadNumber) throws RemoteException {
        new Thread( () -> {

            while (!Thread.currentThread().isInterrupted()) {

                String clientName = clientNames.get(client);
                String clientCreator = clientCreators.get(client);
                String clientId = clientIds.get(client);

                AtomicBoolean isSleeping = new AtomicBoolean(false);

                try {
                    Thread.sleep(7500);
                } catch (InterruptedException e) {
                    break;
                }

                System.out.println("I am Thread " + threadNumber + " and starting a new pinging session on client " + clientId);

                ScheduledExecutorService timeScheduler = Executors.newSingleThreadScheduledExecutor();
                timeScheduler.execute(() -> {
                    try {
                        Thread.currentThread().sleep(15000); // Dormo per 15s
                        isSleeping.set(true);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                });

                try {
                    if (isSleeping.get()) {
                        System.err.println("2. I am Thread " + threadNumber + " and client " + clientId + " seems to have been disconnected. Removing " + clientId + ".");

                        synchronized (client) {
                            activeClients.remove(client);
                            unregister(clientId, clientName, clientCreator);
                        }

                        Thread.currentThread().interrupt();
                    }

                    System.out.println("I am Thread " + threadNumber + " and I will ping right now " + clientId);
                    client.handleMessage(new Ping(EventType.PING, null, System.currentTimeMillis()));
                    System.out.println("I am Thread " + threadNumber + " and everything is ok. Shutting down the timer. ");
                    timeScheduler.shutdownNow();
                } catch (RemoteException e) {
                    System.err.println("1. I am Thread " + threadNumber + " and client " + clientId + " seems to have been disconnected. Removing " + clientId + ".");

                    synchronized (client) {
                        activeClients.remove(client);
                        unregister(clientId, clientName, clientCreator);
                    }

                    timeScheduler.shutdownNow();
                    Thread.currentThread().interrupt();
                }


            }

        }).start();
    }


    /**
     * Handles an event sent from a client.
     * @param event  the event to be handled.
     * @param client the client that sent the event.
     * @throws RemoteException if a remote communication error occurs while handling the event.
     */
    @Override
    public synchronized void handleMessage(Event event, Client1 client) throws RemoteException {
        switch (event.getType()) {
            case QUITEVENT:
                QuitEvent quitEvent = (QuitEvent) event;
                String creatore = quitEvent.getCreator(); // username è il creator
                if (controller.getControllerByName(creatore) != null){
                    controller.getControllerByName(creatore).rageQuitGame(quitEvent.getUsername());
                    controller.removeController(creatore);}
                break;
            case WANTOJOIN:
                WantToJoinEvent wantToJoinEvent = (WantToJoinEvent) event;
                controller.notifyListener(new GetCreatorsEvent(null, controller.getCreators()), wantToJoinEvent.getUsername()); // questo è l'username del client, non del player, null nel primo campo perchè l'username non serve
                String username = wantToJoinEvent.getUsername();
                break;
            case USERNAME:
                JoinEvent1 joinEvent = (JoinEvent1) event;
                if (joinEvent.getUsername().equals(joinEvent.getCreator()) && controller.uniqueUsername(joinEvent)){      //se il nome del giocatore è uguale a quello del creator, allora è lui a creare la partita, a meno che non si chiamino// allo stesso modo (motivo della seconda condizione
                    GameController1 newController = new GameController1(new Model1());
                    newController.getModel().addListener(joinEvent.getUsername(),(arg) -> { // associamo listeners ai players (nel model)
                        this.controller.getListenerByName(joinEvent.getId()).handleMessage(arg);
                    });

                    // client.setCreator(joinEvent.getCreator());
                    clientCreators.put(client, joinEvent.getCreator());
                    clientIds.put(client, client.getName());
                    clientNames.put(client, joinEvent.getUsername());


                    newController.CreateGameAndJoin(joinEvent.getPlayers(), joinEvent.getUsername());
                    this.controller.addController(newController, joinEvent.getCreator());
                }
                else {
                    uniqueuser = true;

                    if (controller.uniqueUsername(joinEvent)) {
                        GameController1 controller = this.controller.getControllerByName(joinEvent.getCreator());   // prendo il controller di cui il creator fa parte e ci aggiungo il giocatore

                        controller.getModel().addListener(joinEvent.getUsername(), (arg) -> {
                            this.controller.getListenerByName(joinEvent.getId()).handleMessage(arg);
                            //System.out.println("Number of listeners: " + controller.getModel().getListeners().size());
                        });
                        // client.setCreator(joinEvent.getCreator());
                        clientCreators.put(client, joinEvent.getCreator());
                        clientIds.put(client, client.getName());
                        clientNames.put(client, joinEvent.getUsername());
                        controller.playerJoin(joinEvent.getUsername());
                    }
                    else
                        this.controller.notifyListener(new InvalidUsernameEvent1(joinEvent.getUsername()), joinEvent.getId());
                }
                break;
            case STARTERCARD:
                SetStarterCardEvent1 setStarterCardEvent = (SetStarterCardEvent1) event;
                boolean front = setStarterCardEvent.getFront();
                String creator = setStarterCardEvent.getCreator();
                if (controller.getControllerByName(creator) != null)
                    controller.getControllerByName(creator).setStarterCard(setStarterCardEvent.getUsername(), front);
                break;

            case CHOOSEOBJ:
                ChooseObjEvent1 chooseObjEvent = (ChooseObjEvent1) event;
                int obj = chooseObjEvent.getObjCard();
                if (controller.getControllerByName(chooseObjEvent.getCreator()) != null)
                    controller.getControllerByName(chooseObjEvent.getCreator()).chosenObjective(obj, chooseObjEvent.getUsername());
                break;
            case CHAT:
                ChatEvent1 chatEvent1 = (ChatEvent1) event;
                if (controller.getControllerByName(chatEvent1.getCreator()) != null){
                    if (chatEvent1.getReceiver().equals("all"))
                        controller.getControllerByName(chatEvent1.getCreator()).getModel().notifyAllListeners(event, null);
                    else{
                        GameController1 controller = this.controller.getControllerByName(chatEvent1.getCreator());
                        controller.getModel().notifyListener(controller.getModel().getListenerByName(chatEvent1.getReceiver()),event,chatEvent1.getUsername());
                    }}
                break;
            case PLACE:
                PlaceEvent1 placeEvent = (PlaceEvent1) event;
                int row = placeEvent.getRow();
                int column = placeEvent.getColumn();
                int cardPosition = placeEvent.getChosenCard();
                //String username = placeEvent.getUsername(); verificare se avere l'username può essere utile o meno
                if (controller.getControllerByName(placeEvent.getCreator()) != null){
                    try{
                        controller.getControllerByName(placeEvent.getCreator()).placeCard(row, column, cardPosition, placeEvent.getUsername());}
                    catch (InvalidPositionException | InsufficientResourcesException | NoCornersException e){
                        GameController1 controller = this.controller.getControllerByName(placeEvent.getCreator());
                        controller.getModel().notifyListener(controller.getModel().getListenerByName(placeEvent.getUsername()),new InvalidPositionEvent1(e.getMessage(), event.getUsername()),null);
                    }}

                break;
            case DRAW:
                DrawEvent1 drawEvent =(DrawEvent1) event;
                int card = drawEvent.getChosencard();
                if (controller.getControllerByName(drawEvent.getCreator()) != null)
                    controller.getControllerByName(drawEvent.getCreator()).drawCard(card,drawEvent.getUsername());
                break;

            case FLIP:
                FlipEvent1 flipEvent = (FlipEvent1) event;
                String user = flipEvent.getUsername();
                if (controller.getControllerByName(flipEvent.getCreator()) != null)
                    controller.getControllerByName(flipEvent.getCreator()).flipCards(user);
                break;
            case CHOOSEPAWN:
                ChoosePawnEvent choosePawnEvent = (ChoosePawnEvent) event;
                int pawn = choosePawnEvent.getPawn();
                if (controller.getControllerByName(choosePawnEvent.getCreator()) != null)
                    controller.getControllerByName(choosePawnEvent.getCreator()).setPawn(pawn, choosePawnEvent.getUsername());
                break;
            case GAMEENDED:
                GameEndedEvent gameEndedEvent = (GameEndedEvent) event;
                if (controller.getControllerByName(gameEndedEvent.getCreator()) != null)
                    controller.removeController(gameEndedEvent.getCreator());
                break;
            case PONG:
                // lastEvent = EventType.PONG;
                Pong pong = (Pong) event;
                String clientPonged = pong.getUsername();
                // System.out.println("Pong received by client" + clientPonged);
                break;
        }
    }

    /**
     * Removes a client and its associated resources from the server.
     * This method synchronizes access to ensure thread safety.
     * @param clientid       The ID of the client to unregister.
     * @param clientname     The name of the client to unregister.
     * @param clientcreator  The creator of the client's game session.
     */
    public synchronized void unregister(String clientid, String clientname, String clientcreator) {
        GameController1 gameController = this.controller.getControllerByName(clientcreator);

        if (gameController != null) {
            gameController.getModel().removeListener(clientname);
            controller.removeListener(clientid);
            gameController.rageQuitGame(clientname);
            controller.removeController(clientcreator);
        }

    }
    public Multicontroller getController(){return this.controller;}
}