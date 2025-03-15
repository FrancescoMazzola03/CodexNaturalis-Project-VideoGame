package it.polimi.ingsw.EventHandling;

import java.io.Serializable;

/**
 * ChatEvent1 class, is used to represent a chat event, so to manage when a player receive/send a message.
 */
public class ChatEvent1 extends Event implements Serializable {
    /**
     * Attributes of the class.
     * message is the message that has been sent.
     * receiver is the player that will receive the message.
     * creator is the creator of the game.
     */
    String message;
    String receiver;
    String creator;

    /**
     * Constructor of the class.
     * @param username player that sent the message
     * @param message message that has been sent
     * @param receiver player that will receive the message
     * @param creator creator of the game
     */
    public ChatEvent1(String username, String message, String receiver, String creator) {
        super(EventType.CHAT,username);
        this.message = message;
        this.receiver = receiver;
        this.creator = creator;

    }

    /**
     * Method to get the message that has been sent.
     * @return the message that has been sent.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Method to get the player that will receive the message.
     * @return the player that will receive the message.
     */
    public String getReceiver(){ return this.receiver; }

    /**
     * Method to get the creator of the game.
     * @return the creator of the game
     */
    public String getCreator(){ return this.creator; }
}