package it.polimi.ingsw.Model;

import it.polimi.ingsw.Model.Exceptions.InsufficientResourcesException;
import it.polimi.ingsw.Model.Exceptions.InvalidPositionException;
import it.polimi.ingsw.Model.Exceptions.NoCornersException;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertThrows;

public class PlayerTest extends TestCase {
    Game game;
    Player player1,player2;
    ResourceDeck resDeck;
    GoldenDeck goldDeck;
    ObjectiveDeck objDeck;
    StarterDeck startDeck;
    Market market;
    StarterCard testStarter;

    @Before
    public void setUp(){
        game = new Game(4);
        player1 = new Player("ForzaNapoli");
        player2 = new Player("ForzaCatania");
        resDeck = game.getResDeck();
        goldDeck = game.getGoldDeck();
        objDeck = game.getObjDeck();
        startDeck = game.getStartDeck();
        market = game.getMarket();
        market.setMarket(resDeck,goldDeck);
        game.addPlayer(player1);
        ArrayList <Resource> resources = new ArrayList<>();
        resources.add(Resource.ANIMAL);
        resources.add(Resource.ANIMAL);
        testStarter = new StarterCard(Resource.HIDDEN,Resource.ANIMAL,Resource.ANIMAL,Resource.ANIMAL,resources,
                Resource.INSECT,Resource.EMPTY,Resource.INKWELL,Resource.FUNGI,"081");
        player1.getStation().incrementResNum(Resource.ANIMAL);
        player1.getStation().incrementResNum(Resource.ANIMAL);
        player1.getStation().incrementResNum(Resource.ANIMAL);
        player1.getStation().incrementResNum(Resource.ANIMAL);
        player2.getStation().incrementResNum(Resource.ANIMAL);
        player2.getStation().incrementResNum(Resource.ANIMAL);
        player2.getStation().incrementResNum(Resource.ANIMAL);
        player2.getStation().incrementResNum(Resource.ANIMAL);
    }

    /*public void testGetPoints() {
    }

    public void testSetPoints() {
    }

    public void testTestGetName() {
    }

    public void testGetStation() {
    }*/

    @Test
    public void testDrawCard() {
        PlayingCard tempCard;
        tempCard = market.getMarketCard(0);
        player1.drawCard(resDeck,goldDeck,market,0);
        assertEquals(tempCard, player1.getHand().get(player1.getHand().size() - 1));
        tempCard = market.getMarketCard(1);
        player1.drawCard(resDeck,goldDeck,market,1);
        assertEquals(tempCard, player1.getHand().get(player1.getHand().size() - 1));
        tempCard = market.getMarketCard(2);
        player1.drawCard(resDeck,goldDeck,market,2);
        assertEquals(tempCard, player1.getHand().get(player1.getHand().size() - 1));
        tempCard = market.getMarketCard(3);
        player1.drawCard(resDeck,goldDeck,market,3);
        assertEquals(tempCard, player1.getHand().get(player1.getHand().size() - 1));
        tempCard = resDeck.getCards().get(resDeck.getCards().size() - 1);
        player1.drawCard(resDeck,goldDeck,market,4);
        assertEquals(tempCard, player1.getHand().get(player1.getHand().size() - 1));
        tempCard = goldDeck.getCards().get(goldDeck.getCards().size() - 1);
        player1.drawCard(resDeck,goldDeck,market,5);
        assertEquals(tempCard, player1.getHand().get(player1.getHand().size() - 1));
    }

    @Test
    public void testSetObjectiveCard() {
        Player player3 = new Player("gigio");
        Player player4 = new Player("gigione");
        player3.createHand(resDeck,goldDeck,objDeck,startDeck);
        player4.createHand(resDeck,goldDeck,objDeck,startDeck);
        ArrayList<ObjectiveCard> cards3 = new ArrayList<>();
        cards3.add(player3.possibleObjectives().get(0));
        cards3.add(player3.possibleObjectives().get(1));
        player3.setObjectiveCard(1);
        assertEquals(player3.getObjectiveCard(),cards3.get(0));
        ArrayList<ObjectiveCard> cards4 = new ArrayList<>();
        cards4.add(player4.possibleObjectives().get(0));
        cards4.add(player4.possibleObjectives().get(1));
        player4.setObjectiveCard(2);
        assertEquals(player4.getObjectiveCard(),cards4.get(1));
    }

    @Test
    public void testGetHand() {
    }

    @Test
    public void testGetObjectiveCard() {
    }

    @Test
    public void testPlaceCard() {

        player1.getStation().getCards().add(testStarter);
        System.out.println(player1.getStation().getCards().get(0).equals(testStarter));
        GoldenCard testGolden = goldDeck.pop();
        player1.getHand().add(testGolden);
        System.out.println("ANIMALS: " + testGolden.getNumAnimals());
        System.out.println("INSECTS: " + testGolden.getNumInsects());
        System.out.println("PLANTS: " + testGolden.getNumPlants()); //a questa carta in particolare servono 3 plants
        System.out.println("FUNGI: " + testGolden.getNumFungi());   //per essere piazzata
        try {
            player1.placeCard(45, 45, 1); //questa non mi piazza la carta perchè è una InvalidPosition, quindi devo avere -1 nella posizione
        } catch (InvalidPositionException e) {
            System.out.println("InvalidPositionException");
        }
        player1.getHand().add(testGolden);
        try {
            player1.placeCard(41, 41, 1); //anche questa mi da -1 perchè non ho abbastanza risorse per piazzare la carta
        } catch (InsufficientResourcesException e) {
            System.out.println("InsufficientResourcesException");
        }
        assertEquals(player1.getStation().getCardPositions()[41][41], -1);
        player1.getStation().incrementResNum(Resource.PLANT);
        player1.getStation().incrementResNum(Resource.PLANT);
        player1.getStation().incrementResNum(Resource.PLANT);//ora mi può piazzare la carta perchè ho 3 plants
        player1.placeCard(41, 41, 1);
        assertEquals(player1.getStation().getCardPositions()[41][41], 1);
        player1.getHand().add(testGolden);
        try {
            player1.placeCard(39, 39, 1);
        }
        catch (NoCornersException e )
        {
            System.out.println("NoCornersException");
        }
        assertEquals(player1.getStation().getCardPositions()[39][39], -1);

    }



    @Test
    public void testStartCardStateConfirmed() {
        Player player3 = new Player("cc");
        testStarter.setState(false); //così mi setta la carta per metterla con il retro
        player3.getStation().getCards().add(testStarter);
        System.out.println(player1.getStation().getCards().size());
        player3.starterCardStateConfirmed();
        assertEquals(player3.getStation().getCards().get(0).getCorner(0).getRes(),Resource.INSECT);
        assertEquals(player3.getStation().getCards().get(0).getCorner(1).getRes(),Resource.EMPTY);
        assertEquals(player3.getStation().getCards().get(0).getCorner(2).getRes(),Resource.INKWELL);
        assertEquals(player3.getStation().getCards().get(0).getCorner(3).getRes(),Resource.FUNGI);
    }

    @Test
    public void testIncrementNumObjsCompleted() {
        assertEquals(player1.getNumObjsCompleted(),0);
        player1.incrementNumObjsCompleted();
        assertEquals(player1.getNumObjsCompleted(),1);
    }


    @Test
    public void testGetReadyToPlay(){
        assertEquals(player1.getReadyToPlay(),false);
        player1.setReadyToPlay();
        assertEquals(player1.getReadyToPlay(),true);
    }

    @Test
    public void testSetReadyToPlay(){
        player1.setReadyToPlay();
        assertEquals(player1.getReadyToPlay(),true);
    }

    @Test
    public void testGetNumObjsCompleted() {
    }

    public void testGetPlayerStarterCard() {
        Player player3 = new Player("nono");
        player3.getStation().getCards().add(testStarter);
        assertEquals(player3.getPlayerStarterCard(),testStarter);
    }
    @Test
    public void testtoString(){
        ResourceCard card1 = new ResourceCard(Resource.ANIMAL,Resource.PLANT,Resource.INSECT,Resource.FUNGI,Resource.ANIMAL,0,"001");
        ResourceCard card2 = new ResourceCard(Resource.ANIMAL,Resource.PLANT,Resource.INSECT,Resource.FUNGI,Resource.ANIMAL,0,"001");
        ResourceCard card3 = new ResourceCard(Resource.ANIMAL,Resource.PLANT,Resource.INSECT,Resource.FUNGI,Resource.ANIMAL,0,"001");
        player1.getHand().add(0,card1);
        player1.getHand().add(1,card2);
        player1.getHand().add(2,card3);
        System.out.println(player1.toString());
    }
}