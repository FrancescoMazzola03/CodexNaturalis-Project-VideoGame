package it.polimi.ingsw.Model;

import junit.framework.TestCase;

import java.util.ArrayList;

public class GoldenDeckTest extends TestCase {
    Game game = new Game(4);

    public void testShuffle() {
        ArrayList <GoldenCard> beforeshuffle = new ArrayList<>();
        beforeshuffle.addAll(game.getGoldDeck().getCards());
        game.getGoldDeck().shuffle();
        ArrayList <GoldenCard> aftershuffle = new ArrayList<>();
        aftershuffle.addAll(game.getGoldDeck().getCards());
        assertNotSame(beforeshuffle,aftershuffle);
    }

    public void testPop() {
        ArrayList <GoldenCard> beforePop = new ArrayList<>();            // verificare se a noi piace questo funzionamento: la pop rimuove l'ultimo elemento del deck, non il primo
                                                                         // in caso possiamo creare un nuovo metodo pop dentro il deck che chiama il metodo remove(0) degli arraylist
        beforePop.addAll(game.getGoldDeck().getCards());
        game.getGoldDeck().pop();
        ArrayList <GoldenCard> afterPop = new ArrayList<>();
        afterPop.addAll(game.getGoldDeck().getCards());
        assertEquals(beforePop.get(beforePop.size()-2),afterPop.get(afterPop.size()-1));
        assertEquals(beforePop.size(),afterPop.size() + 1);

    }

    public void testGetCards() {
        for (GoldenCard card : game.getGoldDeck().getCards())
            System.out.println(card.toString());
    }

    public void testIsEmpty() {
        for (int i = 0; i < 40; i++)    // ho fatto ciclo fino a 10 perchè al momento ci sono solo le mie carte, quando aggiungete le vostre dovete aumentare.
            game.getGoldDeck().pop();   // tra l'altro forse ci sta gestire il mazzo vuoto con un'eccezione
    assertEquals(game.getGoldDeck().isEmpty(), true);
    }
}