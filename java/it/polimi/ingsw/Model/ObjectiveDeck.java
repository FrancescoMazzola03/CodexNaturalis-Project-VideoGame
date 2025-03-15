package it.polimi.ingsw.Model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * ObjectiveDeck class, is used to represent the deck of objective cards.
 */
public class ObjectiveDeck extends Deck<ObjectiveCard> implements Serializable {
    /**
     * Constructor of the class.
     * @param cards is the list of cards that the deck will contain.
     */
    public ObjectiveDeck(ArrayList<ObjectiveCard> cards){
        super(cards);
    }
}
