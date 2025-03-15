package it.polimi.ingsw.Model;

import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.polimi.ingsw.EventHandling.Event;
import it.polimi.ingsw.EventHandling.RefreshEvent;
import it.polimi.ingsw.Listener.GameListener;
import it.polimi.ingsw.ModelView.GameView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Game class, is used to represent a match.
 * It contains all the information about the match.
 */
public class Game {
    /**
     * Attributes of the class.
     * players is the list of players in the match.
     * creator is the player that created the match.
     * commonObjs is the list of common objectives.
     * resDeck is the deck of resource cards.
     * golDeck is the deck of golden cards.
     * startDeck is the deck of starter cards.
     * objDeck is the deck of objective cards.
     * market is the market of the match.
     * leader is the player that has more points during the match.
     * MaxNumPlayers is the maximum number of players in the match.
     * state is the state of the match.
     * currentPlayer is the player that is playing.
     * listeners is the list of listeners of the match.
     */
    private ArrayList<Player> players;
    private Player creator;
    private ArrayList<ObjectiveCard> commonObjs;
    private ResourceDeck resDeck;
    private GoldenDeck golDeck;
    private StarterDeck startDeck;
    private ObjectiveDeck objDeck;
    private Market market;
    private Player leader;
    private int MaxNumPlayers;
    private State state;
    private Player currentPlayer;
    private ArrayList <GameListener> listeners;
    private String[] pawnOwners;

    /**
     * Constructor of the class.
     * It creates a new match with the maximum number of players.
     * Fills the decks reading them from the JSON files.
     * @param MaxNumPlayers is the maximum number of players in the match.
     */
    public Game(int MaxNumPlayers) {
        players = new ArrayList<>();
        listeners = new ArrayList<>();
        ArrayList<GoldenCard> goldCards = new ArrayList<>();
        ArrayList<ResourceCard> resCards = new ArrayList<>();
        ArrayList<ObjectiveCard> objCards = new ArrayList<>();
        ArrayList<StarterCard> startCards = new ArrayList<>();
        pawnOwners = new String[5];
        for (int i = 0; i < 5; i++)
            pawnOwners[i] = "none";
        ObjectMapper objectMapper = new ObjectMapper();
        this.state = State.WAITING;
        this.MaxNumPlayers = MaxNumPlayers;

        try {
            InputStream goldCardStream = getClass().getResourceAsStream("/Json/goldCard.json");
            goldCards = objectMapper.readValue(goldCardStream, new TypeReference<ArrayList<GoldenCard>>() {});
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            InputStream resCardStream = getClass().getResourceAsStream("/Json/resCard.json");
            resCards = objectMapper.readValue(resCardStream, new TypeReference<ArrayList<ResourceCard>>() {});
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            InputStream startCardStream = getClass().getResourceAsStream("/Json/startCard.json");
            startCards = objectMapper.readValue(startCardStream, new TypeReference<ArrayList<StarterCard>>() {});
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            InputStream patternObjStream = getClass().getResourceAsStream("/Json/patternObj.json");
            ArrayList<PatternObjective> patternObjectives = objectMapper.readValue(patternObjStream, new TypeReference<ArrayList<PatternObjective>>() {});

            InputStream resObjStream = getClass().getResourceAsStream("/Json/resObj.json");
            ArrayList<ResObjective> resObjectives = objectMapper.readValue(resObjStream, new TypeReference<ArrayList<ResObjective>>() {});

            InputStream itemObjStream = getClass().getResourceAsStream("/Json/itemObj.json");
            ArrayList<ItemObjective> itemObjectives = objectMapper.readValue(itemObjStream, new TypeReference<ArrayList<ItemObjective>>() {});

            objCards.addAll(patternObjectives);
            objCards.addAll(resObjectives);
            objCards.addAll(itemObjectives);
        } catch (IOException e) {
            e.printStackTrace();
        }
        resDeck = new ResourceDeck(resCards);
        golDeck = new GoldenDeck(goldCards);
        objDeck = new ObjectiveDeck(objCards);
        startDeck = new StarterDeck(startCards);
        market = new Market();
        commonObjs = new ArrayList<>();
    }

    /**
     * Method to get the list of owners of the pawns
     * @return the owners of the pawns
     */
    public String[] getPawnOwners(){
        return pawnOwners;
    }

    /**
     * Method do set the owner of the pawn
     * @param choice is the choice of the pawn
     * @param username is the username of the player
     */
    public void setPawnOwner(int choice, String username){
        pawnOwners[choice - 1] = username;
    }

    /**
     * Method to set the creator of the match
     */
    public void setCreator(){
        creator = players.get(0);
    }

    /**
     * Method to get the creator of the match
     * @return the creator of the match
     */
    public Player getCreator(){
        return creator;
    }

    /**
     * Method to add the common objs to the match
     * It pops two cards from the objDeck and adds them to the commonObjs
     */
    public void addCommonObjs(){
        commonObjs.add(objDeck.pop());
        commonObjs.add(objDeck.pop());
    }

    /**
     * Method to get the current player
     * @return the player that is current playing
     */
    public Player getCurrentPlayer(){
        return this.currentPlayer;
    }

    /**
     * Method to set the current player
     */
    public void setCurrentPlayer(){
        if (players.size() == 1 || this.currentPlayer == null)
            currentPlayer = players.get(0);
        else if (players.indexOf(currentPlayer) == players.size() - 1 ) {
            currentPlayer = players.get(0);
        }
        else
            currentPlayer = players.get(players.indexOf(currentPlayer) + 1);
    }

    /**
     * Method to set the state of the match
     * @param state is the state of the match
     */
    public  void setState (State state){
        this.state = state;
        // notificare i vari cambiamenti
    }

    /**
     * Method to get the state of the match
     * @return the state of the match
     */
    public State getState(){
        return this.state;
    }

    /**
     * Method to get the resource deck
     * @return the resource deck
     */
    public ResourceDeck getResDeck(){
        return this.resDeck;
    }

    /**
     * Method to get the golden deck
     * @return the golden deck
     */
    public GoldenDeck getGoldDeck(){
        return this.golDeck;
    }

    /**
     * Method to get the market
     * @return the market
     */
    public Market getMarket(){ return this.market;}

    /**
     * Method to get the starter deck
     * @return the starter deck
     */
    public StarterDeck getStartDeck(){ return this.startDeck;}

    /**
     * Method to get the objective deck
     * @return the objective deck
     */
    public ObjectiveDeck getObjDeck(){return this.objDeck;}

    /**
     * Method to add a player to the match
     * @param player is the player that has to be added to the match
     */
    public void addPlayer(Player player){ players.add(player);}

    /**
     * Method to get the players of the match
     * @return the list of players in the match
     */
    public ArrayList<Player> getPlayers() {
        return this.players;
    }

    /**
     * Method to sort randomly the order of the players
     */
    public void sort() {
        Collections.shuffle(players);
    }

    /**
     * Method to get the common objectives of the match
     * @return the common objectives of the match
     */
    public ArrayList<ObjectiveCard> getCommonObjs(){
        return this.commonObjs;
    }

    /**
     * Method to find a winner or winners of the match
     * @return the player or players that won the match
     */
    public ArrayList<Player> findWinners() {

        for (Player player : players) {

            int objPointsPrivate = 0, objPointsPublic = 0, objPointsPublic2 = 0;
            int timesPrivate = player.getObjectiveCard().timesCompleted(player.getStation());
            int timesPublic = commonObjs.get(0).timesCompleted(player.getStation());
            int timesPublic2 = commonObjs.get(1).timesCompleted(player.getStation());
            // points of private objective
            if (timesPrivate > 0) {
                objPointsPrivate = timesPrivate * player.getObjectiveCard().points;
                player.incrementNumObjsCompleted();
            }
            // points of first public objective
            if (timesPublic > 0) {
                objPointsPublic = timesPublic * commonObjs.get(0).points;
                player.incrementNumObjsCompleted();
            }
            // points of second public objective
            if (timesPublic2 > 0) {
                objPointsPublic2 = timesPublic2 * commonObjs.get(1).points;
                player.incrementNumObjsCompleted();
            }

            player.setPoints(objPointsPrivate + objPointsPublic + objPointsPublic2);
        }

        leader = players.stream().max(Comparator.comparingInt(Player::getPoints)).orElse(null);

        ArrayList<Player> leaders = players.stream().filter(player -> player.getPoints() == leader.getPoints()).collect(Collectors.toCollection(ArrayList::new));

        int maxobj = leaders.stream().mapToInt(Player::getNumObjsCompleted).max().orElse(0);

        return leaders.stream().filter(player -> player.getNumObjsCompleted() == maxobj).collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * @return the max number of players of the match
     */
    public int getMaxNumPlayers() {
        return MaxNumPlayers;
    }

    /**
     * Method to get the leader of the match, so the player with more points
     * @return the leader of the match
     */
    public Player getLeader() {
        return leader;
    }

    /**
     * Method to get the player by it username
     * @param username is the username of a player
     * @return the player with the username passed as parameter
     */
    public Player getPlayerByUsername (String username) {
        for (Player player : players){
            if (player.getName().equals(username))
                return player;
        }
        return null;
    }

}