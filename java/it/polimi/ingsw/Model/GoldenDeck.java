package it.polimi.ingsw.Model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * GoldenDeck class, is used to represent the deck that contains golden cards.
 */
public class GoldenDeck extends Deck<GoldenCard> implements Serializable {
    /**
     * Constructor of the class.
     * @param cards is the list of golden cards in the deck.
     */
    public GoldenDeck(ArrayList<GoldenCard> cards){
        super(cards);

    }
}
