package it.polimi.ingsw.Model;

import it.polimi.ingsw.EventHandling.Event;
import it.polimi.ingsw.EventHandling.EventType;
import it.polimi.ingsw.EventHandling.RefreshEvent;
import it.polimi.ingsw.Listener.GameListener;
import it.polimi.ingsw.ModelView.GameView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Model1 class, is used to bind the model with other components of the MVC pattern.
 * It contains the game and the listeners.
 *
 */
public class Model1 {
    /**
     * Attributes of the class.
     * listeners is a map that contains for each player (string) a GameListener.
     * game is the match.
     */
    private Map<String, GameListener> listeners = new HashMap<>();
    private Game game;

    /**
     * Method to set the game.
     * @param game is the match.
     */
    public void setGame(Game game) {
        this.game = game;
    }

    /**
     * Method to get the game.
     * @return the match.
     */
    public Game getGame() {
        return game;
    }

    /**
     * Method to add a listener to a specific player of the map listeners.
     * @param username the username of the player
     * @param listener the listener of the player
     */
    public void addListener (String username, GameListener listener){
        listeners.put(username,listener);
    }

    /**
     * Method to remove a listener of a player from the map listeners.
     * @param username the username of the player
     */
    public void removeListener(String username) {
        listeners.remove(username);
    }

    /**
     * Method to notify all the listeners that there's an event.
     * @param event the event that generates the notify.
     * @param author the player that's the author of the event
     */
    public  synchronized void notifyAllListeners(Event event, String author){
        for (GameListener listener : listeners.values())
            if (listener != null)
                notifyListener(listener,event,author);
    }

    /**
     * Method to notify the player's listener that there's an event that involves it.
     * @param listener the listener to notify
     * @param event the event that generates the notify
     * @param author the player that's the author of the event
     */
    public void notifyListener(GameListener listener, Event event, String author){
        if(event == null)
            listener.handleMessage(new RefreshEvent(new GameView(this.game),author));
        else {
            listener.handleMessage(event);
        }
    }

    /**
     * Method to get the listener by the nickname of a player
     * @param username the username of the player
     * @return the listener of a specific player
     */
    public GameListener getListenerByName(String username){
         return listeners.get(username);
    }

    public Map<String,GameListener> getListeners(){return this.listeners;}
}
