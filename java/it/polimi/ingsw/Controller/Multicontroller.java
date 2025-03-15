package it.polimi.ingsw.Controller;

import it.polimi.ingsw.EventHandling.Event;
import it.polimi.ingsw.EventHandling.InvalidUsernameEvent1;
import it.polimi.ingsw.EventHandling.JoinEvent1;
import it.polimi.ingsw.Listener.GameListener;
import it.polimi.ingsw.Model.State;

import java.util.*;


/**
 * This class is used to manage multiple games at the same time.
 */
public class Multicontroller {
    /**
     * Attributes of the class.
     * controllers is a map that binds the creator of a game with its controller.
     * listeners is a map that binds a player of a game with its listener.
     */
    Map<String, GameController1> controllers;
    Map<String, GameListener> listeners;

    /**
     * Constructor of the class.
     * It initializes the maps.
     */
    public Multicontroller(){
        controllers = new HashMap<>();
        listeners = new HashMap<>();
    }

    /**
     * Method to add a controller to the map controllers.
     * @param controller is the controller of the game
     * @param creator is the creator of the game
     */
    public void addController(GameController1 controller, String creator){
        controllers.put(creator,controller);
    }

    /**
     * Method to remove a listener from the map listeners.
     * @param player is a player of a game
     */
    public void removeListener(String player) {
        listeners.remove(player);
    }


    /**
     * Method to get the controller of a game by its creator.
     * @param creator is the creator of the game
     * @return the controller of the game
     */
    public GameController1 getControllerByName(String creator){
        return controllers.get(creator);
    }

    /**
     * Method to add a listener to the map listeners.
     * @param id is the id of the listener
     * @param listener is the listener to add
     */
    public void addListener(String id,GameListener listener){
        listeners.put(id, listener);
    }

    /**
     * Method to notify a listener.
     * @param event is the event that generates the notify
     * @param id is the player to notify
     */
    public void notifyListener(Event event, String id){
        listeners.get(id).handleMessage(event);
    }

    /**
     * Method to get the creators of the games-
     * @return the list of the creators of the games.
     */
    public synchronized ArrayList<String> getCreators() {
        Set<String> keys = controllers.keySet();
        ArrayList<String> keysList = new ArrayList<>(keys);

        Iterator<String> iterator = keysList.iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            if (!(controllers.get(key).getModel().getGame().getState().equals(State.WAITING))) {
                iterator.remove();
            }
        }

        return keysList;
    }

    /**
     * @param joinEvent is the event that contains the username of the player that wants to join a game
     *                  controls that the username is unique.
     * @return
     */
    public Boolean uniqueUsername(JoinEvent1 joinEvent){
        for (GameController1 gameController: this.getControllers()){
            if (gameController.getModel().getGame().getPlayerByUsername(joinEvent.getUsername()) != null){
                this.notifyListener(new InvalidUsernameEvent1(joinEvent.getUsername()), joinEvent.getId());
                return false;
            }
        }
        return true;
    }

    /**
     * Method to get the listeners by its name.
     * @param id is the id of the listener
     * @return the listener with that id
     */
    public GameListener getListenerByName(String id){
        return listeners.get(id);
    }

    /**
     * Method to get the controllers of the games.
     * @return the list of the controllers of the games.
     */
    public ArrayList<GameController1> getControllers(){
        ArrayList<GameController1> controllersList = new ArrayList<>();
        for(GameController1 controller: controllers.values()){
            controllersList.add(controller);
        }
        return controllersList;
    }

    /**
     * Method to remove a controller from the map controllers.
     * @param creator is the creator of the game
     */
    public void removeController(String creator){
        controllers.remove(creator);
    }

}