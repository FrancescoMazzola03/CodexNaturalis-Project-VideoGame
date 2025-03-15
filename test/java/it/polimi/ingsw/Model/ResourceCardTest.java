package it.polimi.ingsw.Model;

import junit.framework.TestCase;

public class ResourceCardTest extends TestCase {
        Game game =new Game(4);
     ResourceDeck resDeckTest = game.getResDeck();
    public void testtoString(){
        for(int i = 0 ;i <40 ; i++) {
            System.out.println(resDeckTest.getCards().get(i).toString());
            resDeckTest.getCards().get(i).setState(false);
            System.out.println(resDeckTest.getCards().get(i).toString());
        }
    }
}