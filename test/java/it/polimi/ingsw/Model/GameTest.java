package it.polimi.ingsw.Model;

import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.jar.JarOutputStream;

public class GameTest extends TestCase {
    Game game;
    Player player1,player2,player3,player4;

    @Before
    public void setUp() {
        game = new Game(4);
        player1 = new Player("giorgio1");
        player2 = new Player("giorgio2");
        player3 = new Player("giorgio3");
        player4 = new Player("giorgio4");
        game.addPlayer(player1);
        game.addPlayer(player2);
        game.addPlayer(player3);
        game.addPlayer(player4);

    }

    @Test
    public void testGetResDeck() {
        assertEquals(game.getResDeck().getCards().size(), 40);
    }

    @Test
    public void testGetGoldDeck() {
        assertEquals(game.getResDeck().getCards().size(),40);

    }
    public void testAddCommonObjs(){
        ObjectiveCard card1 = game.getObjDeck().getCards().get(game.getObjDeck().getCards().size() - 1);
        ObjectiveCard card2 = game.getObjDeck().getCards().get(game.getObjDeck().getCards().size() - 2);
        game.addCommonObjs();
        assertEquals(game.getCommonObjs().get(0),card1);
        assertEquals(game.getCommonObjs().get(1),card2);
    }

    @Test
    public void testGetMarket() {
        ArrayList<ResourceCard> resCards = new ArrayList<>();
        resCards.addAll(game.getResDeck().getCards());
        ArrayList<GoldenCard> goldCards = new ArrayList<>();
        goldCards.addAll(game.getGoldDeck().getCards());
        game.getMarket().setMarket(game.getResDeck(),game.getGoldDeck());

        assertTrue(resCards.contains(game.getMarket().getResourceMarket()[0]));
        assertTrue(resCards.contains(game.getMarket().getResourceMarket()[1]));
        assertTrue(goldCards.contains(game.getMarket().getGoldenMarket()[0]));
        assertTrue(goldCards.contains(game.getMarket().getGoldenMarket()[1]));

    }

    @Test
    public void testGetStartDeck() {assertEquals(game.getStartDeck().getCards().size(), 6);
    }

    @Test
    public void testGetObjDeck() {assertEquals(game.getObjDeck().getCards().size(), 16);}

    @Test
    public void testAddPlayer() {
        game.addPlayer(player1);
        assertTrue(game.getPlayers().contains(player1));
    }

    @Test
    public void testGetPlayers() {
    }

    @Test
    public void testSort() {
        ArrayList <Player> beforeShuffle = new ArrayList<>();
        beforeShuffle.addAll(game.getPlayers());
        game.sort();
        ArrayList <Player> afterShuffle = new ArrayList<>();
        afterShuffle.addAll(game.getPlayers());
        assertNotSame(beforeShuffle,afterShuffle);
    }

    @Test
    public void testSetWinner() {
    }

    @Test
    public void testGetPlayerByUsername(){
        assertEquals(game.getPlayerByUsername("giorgio1"),player1);
        assertEquals(game.getPlayerByUsername("dssdsvd"),null);
    }

    @Test
    public void testSetCurrentPlayer(){
        game.setCurrentPlayer();
        assertEquals(game.getCurrentPlayer(),player1);
        game.setCurrentPlayer();
        assertEquals(game.getCurrentPlayer(),player2);
        game.setCurrentPlayer();
        game.setCurrentPlayer();
        game.setCurrentPlayer();
        assertEquals(game.getCurrentPlayer(),player1);
    }

    @Test
    public void testFindWinners(){
        game.getCommonObjs().add(new ResObjective(2,Resource.INSECT,"040"));
        game.getCommonObjs().add(new ResObjective(2,Resource.FUNGI,"060"));
        player1.createHand(game.getResDeck(),game.getGoldDeck(),game.getObjDeck(),game.getStartDeck());
        player1.setObjectiveCard(1);
        player2.createHand(game.getResDeck(),game.getGoldDeck(),game.getObjDeck(),game.getStartDeck());
        player2.setObjectiveCard(1);
        player2.getStation().incrementResNum(Resource.INSECT);
        player2.getStation().incrementResNum(Resource.INSECT);
        player2.getStation().incrementResNum(Resource.INSECT);
        player3.createHand(game.getResDeck(),game.getGoldDeck(),game.getObjDeck(),game.getStartDeck());
        player3.possibleObjectives().remove(0);
        player3.possibleObjectives().remove(0);
        player3.possibleObjectives().add(new ResObjective(2,Resource.ANIMAL,"020"));
        player3.possibleObjectives().add(new ResObjective(2,Resource.FUNGI,"030"));
        player3.setObjectiveCard(1);
        player3.getStation().incrementResNum(Resource.FUNGI);
        player3.getStation().incrementResNum(Resource.FUNGI);
        player3.getStation().incrementResNum(Resource.FUNGI);
        player4.createHand(game.getResDeck(),game.getGoldDeck(),game.getObjDeck(),game.getStartDeck());
        player4.possibleObjectives().remove(0);
        player4.possibleObjectives().remove(0);
        player4.possibleObjectives().add(new ResObjective(2,Resource.ANIMAL,"010"));
        player4.possibleObjectives().add(new ResObjective(2,Resource.FUNGI,"015"));
        player4.setObjectiveCard(1);
        player4.getStation().incrementResNum(Resource.ANIMAL);
        player4.getStation().incrementResNum(Resource.ANIMAL);
        player4.getStation().incrementResNum(Resource.ANIMAL);
        player1.setPoints(0);
        player2.setPoints(0);
        player3.setPoints(0);
        player4.setPoints(0);
        assertEquals(game.findWinners().get(0),player2);
        assertEquals(game.findWinners().get(1),player3);
        assertEquals(game.findWinners().get(2),player4);
    }

    @Test
    public void testGetMaxNumPlayers(){
        assertEquals(game.getMaxNumPlayers(),4);
    }
    @Test
    public void testSetCreator(){
        game.setCreator();
        assertEquals(game.getCreator(),player1);
    }
    @Test
    public void testGetCreator(){
        game.setCreator();
        assertEquals(game.getCreator(),player1);
    }
    @Test
    public void testSetState(){
        game.setState(State.WAITING);
        assertEquals(game.getState(),State.WAITING);
    }
}