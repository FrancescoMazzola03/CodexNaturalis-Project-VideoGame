package it.polimi.ingsw.EventHandling;

import it.polimi.ingsw.ModelView.GameView;

import java.io.Serializable;

/**
 * Event that is generated when the game view needs to be refreshed.
 */
public class RefreshEvent extends Event implements Serializable {
    /**
     * Attributes of the class.
     * gameView is the game view that needs to be refreshed.
     */
    GameView gameView;

    /**
     * Constructor of the class
     * @param gameView is the game view that needs to be refreshed
     * @param Refresher is the nickname of the player that generated the event
     */
    public RefreshEvent(GameView gameView, String Refresher) {
        super(EventType.REFRESH,Refresher);
        this.gameView = gameView;

    }

    /**
     * Method to get the game view that needs to be refreshed.
     * @return the game view that needs to be refreshed
     */
    public GameView getGameView() {
        return gameView;
    }

}
