package it.polimi.ingsw.Model;

import java.io.Serializable;

/**
 * State class, is used to represent the state of the game.
 * The state of the game can be WAITING, RUNNING, LAST_TURN, ENDED, WAITINGTOPLAY.
 * The state of the game is used to know what the game is doing.
 */
public enum State implements Serializable {
    WAITING,
    RUNNING,
    LAST_TURN,
    ENDED,
    WAITINGTOPLAY
}
