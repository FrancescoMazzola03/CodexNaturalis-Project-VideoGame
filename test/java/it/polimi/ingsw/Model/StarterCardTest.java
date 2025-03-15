package it.polimi.ingsw.Model;

import junit.framework.TestCase;

import java.util.ArrayList;

public class StarterCardTest extends TestCase {

    public void testGetBackCorner() {

            Game game = new Game(4);
            StarterDeck starterDeck = game.getStartDeck();

            assertEquals(starterDeck.getCards().get(0).getBackCorner(0).getRes(), Resource.EMPTY);
            assertEquals(starterDeck.getCards().get(1).getBackCorner(0).getRes(), Resource.ANIMAL);

    }
    public void testtoString(){
        Game game = new Game(4);
        StarterDeck starterDeck = game.getStartDeck();
        for(int i = 0 ; i < 6 ; i++) {
            System.out.println(starterDeck.getCards().get(i).toString());
            starterDeck.getCards().get(i).setState(false);
            System.out.println(starterDeck.getCards().get(i).toString());
        }
    }

    public void testGetPermanentRes(){
        ArrayList<Resource> permanentRes = new ArrayList<>();
        permanentRes.add(Resource.ANIMAL);
        permanentRes.add(Resource.INSECT);
        StarterCard starterCard = new StarterCard(Resource.ANIMAL,Resource.ANIMAL,Resource.ANIMAL,Resource.ANIMAL,
                permanentRes,Resource.ANIMAL,Resource.ANIMAL,Resource.ANIMAL,Resource.ANIMAL,"001");
        assertEquals(starterCard.getPermanentRes().get(0),Resource.ANIMAL);
        assertEquals(starterCard.getPermanentRes().get(1),Resource.INSECT);
    }

}
