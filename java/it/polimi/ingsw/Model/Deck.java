package it.polimi.ingsw.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Deck class, is used to represent a deck of cards.
 * @param <T> is the type of the deck, it is a generic because different
 *  decks contain different types of cards.
 */
public class Deck<T> implements Serializable {
    /**
     * Attributes of the class.
     * cards is the list of cards in the deck, for the same reason as the deck,
     * in the game exist different types of cards, so it is a generic list.
     * It is protected because it is used in the subclasses.
     */
    protected ArrayList<T> cards;

    /**
     * Constructor of the class.
     * @param cards is the list of cards in the deck.
     */
    public Deck(ArrayList<T> cards) {
        this.cards = cards;
    }

    /**
     * Method to shuffle the deck.
     */
    public void shuffle(){
        Collections.shuffle(cards);
    }

    /**
     * Method to remove the card in the last position of the deck and return it.
     * @return the card in the last position of the deck.
     */
    public T pop(){
        if (!cards.isEmpty()) {
            return cards.remove(cards.size() - 1);
        }
        return null;
    }

    /**
     * Method to get the cards in the deck.
     * @return the list of cards in the deck.
     */
    public ArrayList<T> getCards(){
        return this.cards;
    }

    /**
     * Method to know if the deck is empty or not..
     * @return true if the deck is empty, false otherwise.
     */
    public boolean isEmpty() {
        return cards.isEmpty();
    }
}

