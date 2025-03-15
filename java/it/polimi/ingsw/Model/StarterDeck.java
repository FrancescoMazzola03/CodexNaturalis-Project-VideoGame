package it.polimi.ingsw.Model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * StarterDeck class, is used to represent the deck of starter cards.
 */
public class StarterDeck extends Deck<StarterCard> implements Serializable {
    /**
     * Constructor of the class.
     * @param cards is the list of cards in the deck.
     */
    public StarterDeck(ArrayList<StarterCard> cards){
        super(cards);

    }
}


