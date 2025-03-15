package it.polimi.ingsw.Model;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * ResourceDeck class, is used to represent a deck of resource cards.
 */
public class ResourceDeck extends Deck<ResourceCard> implements Serializable {
    /**
     * Constructor of the class.
     * @param cards is the list of cards in the deck.
     */
    public ResourceDeck(ArrayList <ResourceCard> cards){
        super(cards);

    }
}
