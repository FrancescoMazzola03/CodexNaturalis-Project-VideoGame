package it.polimi.ingsw.Model;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class MarketTest extends TestCase {
    Game game;
    Market market;
    @Before
    public void setUp(){
        game = new Game(4);
        market = new Market();
    }

    @Test
    public void testUpdate() {
        ResourceCard resCard;
        GoldenCard goldCard;
        market.setMarket(game.getResDeck(),game.getGoldDeck());
        resCard = game.getResDeck().getCards().get(game.getResDeck().getCards().size() - 1);
        market.update(0,game.getResDeck(),game.getGoldDeck());
        assertEquals(resCard,market.getResourceMarket()[0]);
        resCard = game.getResDeck().getCards().get(game.getResDeck().getCards().size() - 1);
        market.update(1,game.getResDeck(),game.getGoldDeck());
        assertEquals(resCard,market.getResourceMarket()[1]);
        goldCard = game.getGoldDeck().getCards().get(game.getGoldDeck().getCards().size() - 1);
        market.update(2,game.getResDeck(),game.getGoldDeck());
        assertEquals(goldCard,market.getGoldenMarket()[0]);
        goldCard = game.getGoldDeck().getCards().get(game.getGoldDeck().getCards().size() - 1);
        market.update(3,game.getResDeck(),game.getGoldDeck());
        assertEquals(goldCard,market.getGoldenMarket()[1]);
    }

    @Test
    public void testSetMarket() {
        ResourceCard lastResCard,secondLastResCard;
        GoldenCard lastGoldCard,secondLastGoldCard;
        lastResCard = game.getResDeck().getCards().get(39);
        secondLastResCard = game.getResDeck().getCards().get(38);
        lastGoldCard = game.getGoldDeck().getCards().get(39);
        secondLastGoldCard = game.getGoldDeck().getCards().get(38);
        market.setMarket(game.getResDeck(),game.getGoldDeck());
        assertEquals(secondLastResCard,market.getResourceMarket()[1]);
        assertEquals(lastResCard,market.getResourceMarket()[0]);
        assertEquals(secondLastGoldCard,market.getGoldenMarket()[1]);
        assertEquals(lastGoldCard,market.getGoldenMarket()[0]);
    }

    @Test
    public void testGetResourceMarket() {
        ResourceCard firstResCard = game.getResDeck().getCards().get(39);
        ResourceCard secondResCard = game.getResDeck().getCards().get(38);
        market.setMarket(game.getResDeck(),game.getGoldDeck());
        assertEquals(firstResCard,market.getResourceMarket()[0]);
        assertEquals(secondResCard,market.getResourceMarket()[1]);
    }

    @Test
    public void testGetGoldenMarket() {
        GoldenCard firstGoldenCard = game.getGoldDeck().getCards().get(39);
        GoldenCard secondGoldenCard = game.getGoldDeck().getCards().get(38);
        market.setMarket(game.getResDeck(),game.getGoldDeck());
        assertEquals(firstGoldenCard,market.getGoldenMarket()[0]);
        assertEquals(secondGoldenCard,market.getGoldenMarket()[1]);
    }

    @Test
    public void testGetMarketCard() {
        ResourceCard firstResCard = game.getResDeck().getCards().get(39);
        ResourceCard secondResCard = game.getResDeck().getCards().get(38);
        GoldenCard firstGoldenCard = game.getGoldDeck().getCards().get(39);
        GoldenCard secondGoldenCard = game.getGoldDeck().getCards().get(38);
        assertEquals(market.getMarketCard(5),null);
        market.setMarket(game.getResDeck(),game.getGoldDeck());
        assertEquals(firstResCard,market.getMarketCard(0));
        assertEquals(secondResCard,market.getMarketCard(1));
        assertEquals(firstGoldenCard,market.getMarketCard(2));
        assertEquals(secondGoldenCard,market.getMarketCard(3));
    }

    @Test
    public void testToString(){
        game.getMarket().setMarket(game.getResDeck(),game.getGoldDeck());
        StringBuilder test = new StringBuilder();
        List<String> cards = new ArrayList<>();
        cards.add(game.getMarket().getResourceMarket()[0].toString());
        cards.add(game.getMarket().getResourceMarket()[1].toString());
        cards.add(game.getMarket().getGoldenMarket()[0].toString());
        cards.add(game.getMarket().getGoldenMarket()[1].toString());
        int numberOfLines = game.getMarket().getResourceMarket()[0].toString().split("<br>").length;
        for (int i = 0; i < numberOfLines; i++) {
            for (String card : cards) {
                String[] cardLines = card.split("<br>");
                test.append(cardLines[i]).append("  ");
            }
            test.append("\n");
        }
        assertEquals(test.toString(),game.getMarket().toString());
    }
}