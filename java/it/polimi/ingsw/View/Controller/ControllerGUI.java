package it.polimi.ingsw.View.Controller;


import it.polimi.ingsw.View.GUI;

/**
 * ControllerGUI class, it's the generic controller for the Scenes that will be displayed on the GUI.
 */
public abstract class ControllerGUI {
    /**
     * GUI attribute to call the methods of the GUI class.
     */
    protected GUI gui;

    /**
     * Method to set the GUI class.
     * @param gui is the GUI class that will be used to call the methods of the GUI class.
     */
    public void setGui(GUI gui) {
        this.gui = gui;
    }


    /**
     * Method to load the scenes.
     */
    public void load() {
    }


    /**
     * Method to refresh the scenes.
     */
    public void refresh(){

    }

    /**
     * Method to set the text of the error popup.
     * @param caseError is the type of error that will be shown in the popup.
     */
    public void setTextError(int caseError) {
    }

    /**
     * Method to show a message that a player sent in the chat
     * @param message the message to show
     * @param nickname the nickname of the player that sent the message
     */
    public void showMessage(String message, String nickname){
    }
    /**
     * Method to manage when the plauer wants to place a card in an invalid position
     * @param message message that says that the position that player chose is invalid
     */
    public void handleInvalidPosition(String message){

    }


    /**
     * Method to see other players' stations.
     * @param nickname is the nickname of the player that the station will be shown.
     */
    public void load(String nickname) {
    }
}

