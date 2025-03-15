package it.polimi.ingsw.Model;

import junit.framework.TestCase;

public class ObjectiveDeckTest extends TestCase {

    public void testtoString() {
        Game game = new Game(4);
        ObjectiveDeck objectiveDeck = game.getObjDeck();
        for (int i = 0; i < 16; i++) {
            System.out.println(objectiveDeck.getCards().get(i).toString());
        }
    }

}