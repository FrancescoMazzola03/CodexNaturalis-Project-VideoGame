package it.polimi.ingsw.Listener;

import it.polimi.ingsw.EventHandling.Event;
import it.polimi.ingsw.ModelView.GameView;

/**
 * Interface that represents the listener of the Model.
 * It is used to handle the events that are sent by the server or by the controller.
 * A listener is used to notify the client that something has changed in the Model.
 */
public interface GameListener {
    /**
     * Method to handle the event that is sent by the server or by the controller.
     * @param event is the event that the listener has to handle.
     */
    void handleMessage(Event event);
}
