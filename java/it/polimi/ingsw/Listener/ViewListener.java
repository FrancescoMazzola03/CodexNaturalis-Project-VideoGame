package it.polimi.ingsw.Listener;

import it.polimi.ingsw.EventHandling.Event;

/**
 * Interface that represents the listener of the View.
 * It is used to handle the events that are sent by the server or by the controller.
 * A listener is used to notify the client that something has changed in the game.
 */
public interface ViewListener {
    /**
     * Method to handle the event that is sent by the server or by the controller.
     * @param event is the event that the listener has to handle.
     */
    void handleMessage(Event event);
}
