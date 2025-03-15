package it.polimi.ingsw.View;
import it.polimi.ingsw.EventHandling.Event;
import it.polimi.ingsw.Listener.ViewListener;
import it.polimi.ingsw.ModelView.GameView;

import java.io.IOException;
import java.util.ArrayList;


/**
 * UI class, is used to represent the user interface.
 * It's abstract because it's extended by the CLI and GUI classes.
 */
public abstract class UI1 {

    public abstract ViewListener getViewListener();

    /**
     * Method to refresh the game.
     * @param gameview is the GameView of the game.
     * @param username is the nickname of the player that generated the event
     */
    public abstract void handleRefresh(GameView gameview, String username);

    /**
     * Method to add listener to the UI.
     * @param listener is the listener that will be added to the UI
     */
    public void addListener (ViewListener listener) { // nel main chiamo questo metodo e ci metto in input il controller
    }

    /**
     * Method to handle the creators of the games.
     * @param creators is the list of creators of the games
     */
    public void handleCreators(ArrayList<String> creators) {
    }

    /**
     * Method to handle the chat.
     * @param message is the message that the player wants to send
     * @param username is the nickname of the player that sent the message
     */
    public void handleChat(String message, String username) {
    }

    /**
     * Method to handle the error events.
     * @param event type of error event
     * @param username is the nickname of the player that generated the event
     */
    public void handleError(Event event, String username) {
    }

    /**
     * Method tu run the UI
     * @param clientId is the id of the client
     * @throws InterruptedException the exception is thrown when a thread is waiting, sleeping, or otherwise occupied, and the thread is interrupted, either before or during the activity.
     * @throws IOException Signals that an I/O exception of some sort has occurred.
     */
    public abstract void run(String clientId) throws InterruptedException, IOException ;

    /**
     * Method to exit from the game.
     */
     public void exitGame() { }
}
