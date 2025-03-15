package it.polimi.ingsw.View;

import it.polimi.ingsw.EventHandling.*;
import it.polimi.ingsw.Listener.ViewListener;
import it.polimi.ingsw.Model.State;
import it.polimi.ingsw.ModelView.GameView;
import javafx.application.Platform;

import java.io.IOException;
import java.util.ArrayList;

import static javafx.application.Application.launch;

/**
 * GUI class, it's mansion is to manage the GUI of the game.
 *
 */
public class GUI extends UI1 {
    /**
     * Attributes of the class.
     * guiApplication is the GUIApplication that will be used to set the scene.
     * nickname is the nickname of the player.
     * game is the GameView of the game.
     * clientId is the id of the client.
     * eventreceived is the event received.
     * creators is the list of the creators of the games.
     * viewListener is the listener of the view.
     * creatorChosen is the creator of the game.
     * numPlayers is the number of players.
     * lock is the lock used to synchronize the threads.
     */
    GUIApplication guiApplication;
    String nickname;
    GameView game;
    String clientId;
    EventType eventreceived;
    ArrayList<String> creators;
    ViewListener viewListener;
    String creatorChosen;
    int numPlayers;
    private final Object lock = new Object();

    @Override
    public ViewListener getViewListener() {
        return viewListener;
    }

    /**
     * Method to get the creators of the games.
     * @return the ArrayList of the creators of the games.
     */
    public ArrayList<String> getCreators(){
        return creators;
    }

    /**
     * Method to run the GUI.
     * @param clientId is the id of the client.
     */
    @Override
    public void run(String clientId) {
        GUIApplication.setGui(this);
        this.clientId = clientId;
        GUIApplication.run();
        this.guiApplication = GUIApplication.getInstance();
        System.out.println(guiApplication + "registrata");
        this.quitGame();
    }

    /**
     * Method to get the nickname of the player.
     * @return the nickname of the player.
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Method to get the GameView of the game.
     * @return the GameView of the game.
     */
    public GameView getGameView() {
        return this.game;
    }

    /**
     * Method to use JAVAFX thread to run the scenes
     * @param r the runnable to run
     */
    public void requestPlatformRunLater(Runnable r) {
        Platform.runLater(r);
    }

    /**
     * Method to send a message between players.
     * @param selectedPLayer is the player that will receive the message.
     * @param message is the message to send.
     */
    public void sendMessage(String selectedPLayer, String message){
        ChatEvent1 chatEvent = new ChatEvent1(nickname, message, selectedPLayer, creatorChosen);
        notify(chatEvent);
    }


    /**
     * Method to show the error.
     * @param caseError is the type of error to show.
     */
    public void showError(int caseError) {

            requestPlatformRunLater(() -> this.guiApplication.openPopup(SceneEnum.ERROR));
            requestPlatformRunLater(() -> this.guiApplication.setError(caseError));

    }

    /**
     * Method to close the error.
     */
    public void closeError(){
        requestPlatformRunLater(() -> this.guiApplication.closePopUpStage());
    }

    /**
     * Method to set Nickname and join the game
     * @param number number of players of the game
     * @param creator creator of the game
     */
    public void showSetNicknameAndJoin(int number,String creator)  {
            numPlayers = number; // in case of joined game this is pre-set to 0
            creatorChosen = creator; // in case of created-game this is null, then will be equals to nickname
            requestPlatformRunLater(() -> this.guiApplication.setActiveScene(SceneEnum.NICKNAME));
        }

    /**
     * Method to show the scene for choose the number of players.
     */
        public void showChoiceNumberPlayer() {
        guiApplication = GUIApplication.getInstance();
        requestPlatformRunLater(() -> this.guiApplication.setActiveScene(SceneEnum.NUMPLAYER));
    }

    /**
     * Method to quit the game
     */
    public void quitGame(){
        notify(new QuitEvent(EventType.QUITEVENT, creatorChosen, nickname));
        System.exit(0);
    }

    /**
     * Method to show the scene in which the player waits for other players to join the game.
     * @param nickname is the nickname of the player.
     */

    public void showWaitingForPlayers(String nickname)  {
        this.nickname = nickname;
        if (numPlayers!=0)
            this.creatorChosen = nickname;
        JoinEvent1 joinEvent = new JoinEvent1(nickname, creatorChosen, clientId, numPlayers);
        eventreceived = EventType.WAIT;
        notify(joinEvent);

        synchronized (lock) {
            while (eventreceived.equals(EventType.WAIT)) {
                try {

                    lock.wait(); // Wait until notified
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException(e);
                }
            }
        }
        if (!eventreceived.equals(EventType.INVALIDUSER)){
            new Thread(() ->{requestPlatformRunLater(() -> this.guiApplication.setActiveScene(SceneEnum.WAITPLAYERS));}).start();
            eventreceived = EventType.WAIT;}
        else{
            this.showError(0);
        }

    }

    /**
     * Method to show to the player the games in which he can join
     */
    public void showAvailableGame() {
        this.guiApplication = GUIApplication.getInstance();

        eventreceived = EventType.WAIT;
        notify(new WantToJoinEvent(clientId));
        synchronized (lock) {
            while (eventreceived.equals(EventType.WAIT)) {
                try {
                    lock.wait(); // Wait until notified
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException(e);
                }
            }
        }
        if (creators.size() == 0) {
            requestPlatformRunLater(() -> this.guiApplication.setActiveScene(SceneEnum.NUMPLAYER));                                                                                 //distinguere i diversi tipi di errori per stampare cose diverse e associare azioni diverse al press di ok
            this.showError(4);

        } else {
            requestPlatformRunLater(() -> this.guiApplication.setActiveScene(SceneEnum.JOINGAME));
        }
    }


    /**
     * Method to show that the game is ended.
     */
    public void showEndGame(){
        requestPlatformRunLater(() -> this.guiApplication.setActiveScene(SceneEnum.ENDGAME));
        GameEndedEvent gameEndedEvent = new GameEndedEvent(nickname, creatorChosen);
        notify(gameEndedEvent);
    }

    /**
     * Method to show that it's the last turn of the game.
     */
    public void showlastturn(){
        requestPlatformRunLater(() ->this.guiApplication.openPopup(SceneEnum.LASTTURN));
        requestPlatformRunLater(() -> this.guiApplication.showLastTurn());

    }

    /**
     * Method to flip of the cards
     */
    public void flipCards(){
        FlipEvent1 flipEvent = new FlipEvent1(nickname, creatorChosen);
        notify(flipEvent);
    }


    /**
     * Method to show the station of others player.
     * @param playersName is the list of the players' nickname.
     */
    public void showOtherStations(ArrayList<String> playersName) {
        if(this.guiApplication.getPopup() != null) {
            if (this.guiApplication.getPopup().isShowing()) {
                this.guiApplication.closePopUpStage();
                if (this.guiApplication.getPopup().getTitle().equals("Player:  " + playersName.get(0) + "'s station")) {
                    showStationPlayer(playersName.get(0));
                }
                if (playersName.size() == 2 && this.guiApplication.getPopup().getTitle().equals("Player:  " + playersName.get(1) + "'s station")) {
                    showStationPlayer(playersName.get(1));
                }
                if (playersName.size() == 3 && this.guiApplication.getPopup().getTitle().equals("Player:  " + playersName.get(2) + "'s station")) {
                    showStationPlayer(playersName.get(2));
                }
            }
        }
    }

    /**
     * Method to complete the process of showing the station of others players.
     * @param playerNickname is the nickname of the player.
     */
    public void showStationPlayer(String playerNickname) {
        if(this.guiApplication.getPopup() != null){
            this.guiApplication.getPopup().close();
        }
        requestPlatformRunLater(() -> this.guiApplication.openPopup(SceneEnum.OTHERSTATION));
        requestPlatformRunLater(() -> this.guiApplication.setOtherStation(playerNickname));

    }

    /**
     * Method to show the wait scene if others players are choosing the objective card.
     * @param objectiveCard is the number of the objective card chosen.
     * @param starterCard is the boolean that indicates if the card is front or back.
     */
    public void showwaiting4obj(int objectiveCard, boolean starterCard) {
        ChooseObjEvent1 chooseObjEvent = new ChooseObjEvent1(nickname,objectiveCard, creatorChosen);
        notify(chooseObjEvent);
        SetStarterCardEvent1 setStarterCardEvent = new SetStarterCardEvent1(nickname, starterCard, creatorChosen);
        notify(setStarterCardEvent);
        if (!game.getState().equals(State.RUNNING))
            requestPlatformRunLater(() -> this.guiApplication.setActiveScene(SceneEnum.WAITOBJ));
        else
            showBoard();
    }

    /**
     * Method to show the board
     */
    public void showBoard(){
        requestPlatformRunLater(() -> this.guiApplication.setActiveScene(SceneEnum.BOARD));

    }

    /**
     * Method to show the scene in which the player can choose the pawn.
     */
    public void showPawnChoice(){
        requestPlatformRunLater(() -> this.guiApplication.setActiveScene(SceneEnum.PAWNCHOICE));
    }

    /**
     * Method to show the objective cards.
     * @param i is the number of the pawn chosen.
     */
    public void showObjectiveCards(int i) {
        ChoosePawnEvent choosePawnEvent = new ChoosePawnEvent( i, creatorChosen, nickname);
        notify(choosePawnEvent);
        requestPlatformRunLater(() -> this.guiApplication.setActiveScene(SceneEnum.OBJECTIVE));

    }


    /**
     * Method to notify that the player drew a card.
     * @param choice is the choice of the card drew by the player.
     */
    public void showDrawCard(int choice) {
        DrawEvent1 drawCardEvent = new DrawEvent1(choice, nickname, creatorChosen);
        notify(drawCardEvent);
    }


    /**
     * Method to exit the game
     */
    @Override
    public void exitGame() {
        requestPlatformRunLater(() -> this.guiApplication.openPopup(SceneEnum.EXIT));
        requestPlatformRunLater(() -> this.guiApplication.caseExit());


    }

    /**
     * Method to notify that the player wants to place a card.
     * @param col column of the matrix where place the card.
     * @param row row of the matrix where place the card.
     * @param chosenCard is the card chosen by the player to place.
     */
    public void placeCard(int col, int row, int chosenCard){
        PlaceEvent1 placeEvent = new PlaceEvent1(col, row, chosenCard, nickname, creatorChosen);
        notify(placeEvent);
    }


    /**
     * Method to receive the event of the creators of the games
     * @param creators is the list of the creators of the games.
     */
    public void handleCreators(ArrayList<String> creators) {
        synchronized (lock) {

            eventreceived = EventType.GETCREATORS;
            this.creators = creators;
            lock.notifyAll();

        }
    }

    /**
     * Method to notify the event
     * @param event is the event to notify
     */
    public void notify(Event event) {
        this.viewListener.handleMessage(event);
    }

    /**
     * Method to add listener to the view.
     * @param listener is the listener of the view.
     */
    public void addListener(ViewListener listener) { // nel main chiamo questo metodo e ci metto in input il controller
        this.viewListener = listener;
    }

    /**
     * Method to refresh the game.
     * @param gameview is the GameView of the game.
     * @param username is the nickname of the player.
     */
    @Override
    public void handleRefresh(GameView gameview, String username) {
        synchronized (lock) {
            this.game = gameview;
            if (nickname == null || this.nickname.equals(username))   // username = null to manage create game in which there ins't an username yet
                eventreceived = EventType.REFRESH;
            guiApplication.refresh();
            lock.notifyAll(); // Notify all waiting threads

        }}

    /**
     * Method to handle the error.
     * @param event is the error event to handle
     * @param username is the nickname of the player
     */
    public void handleError (Event event, String username){
        synchronized (lock){
            if (event.getType() == EventType.INVALIDUSER)
                eventreceived = EventType.INVALIDUSER;


            else if (event.getType() == EventType.INVALIDPOS){
                eventreceived = EventType.INVALIDPOS;
                event = (InvalidPositionEvent1) event;
                guiApplication.handleInvalidUser(((InvalidPositionEvent1) event).getReason());
                System.out.println(((InvalidPositionEvent1) event).getReason());
            }
            lock.notifyAll();}}

    /**
     * Method to handle the chat.
     * @param message is the message to show
     * @param username is the nickname of the player that sent the message
     */
    public void handleChat(String message, String username){
    guiApplication.showMessage(message, username);
    }
}
