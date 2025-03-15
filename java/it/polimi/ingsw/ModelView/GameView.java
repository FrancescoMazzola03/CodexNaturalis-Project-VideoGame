package it.polimi.ingsw.ModelView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.polimi.ingsw.Model.*;
import it.polimi.ingsw.Model.Exceptions.InsufficientResourcesException;
import it.polimi.ingsw.Model.Exceptions.InvalidPositionException;
import it.polimi.ingsw.Model.Exceptions.NoCornersException;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.stream.Collectors;

/**
 * GameView class, it's a copy of the class "game" of Model.
 * It's used to send to the client only the necessary information without modifying the original class.
 *
 */
public class GameView implements Serializable {
    /**
     * Attributes of the class.
     * players is the list of the players of the game.
     * commonObjs is the list of the common objectives of the game.
     * resDeck is the resource deck of the game.
     * golDeck is the golden deck of the game.
     * startDeck is the starter deck of the game.
     * objDeck is the objective deck of the game.
     * market is the market of the game.
     * leader is the leader of the game.
     * maxNumPlayers is the maximum number of players of the game.
     * state is the state of the game.
     * currentPlayer is the current player of the game.
     * station is the station of the game.
     * cards is the list of the cards of the game.
     * pawnOwners is the list of the owners of the pawns of the game.
     */
    private ArrayList<Player> players;
    private ArrayList<ObjectiveCard> commonObjs;
    private ResourceDeck resDeck;
    private GoldenDeck golDeck;
    private StarterDeck startDeck;
    private ObjectiveDeck objDeck;
    private Market market;
    private Player leader;
    private int maxNumPlayers;
    private State state;
    private Player currentPlayer;
    private String[] pawnOwners;

    /**
     * Constructor of the class.
     * @param game is the game to copy.
     */
    public GameView(Game game) {
        players = game.getPlayers();
        commonObjs = game.getCommonObjs();
        resDeck = game.getResDeck();
        golDeck = game.getGoldDeck();
        startDeck = game.getStartDeck();
        objDeck = game.getObjDeck();
        market = game.getMarket();
        maxNumPlayers = game.getMaxNumPlayers();
        state = game.getState();
        currentPlayer = game.getCurrentPlayer();
        leader = game.getLeader();
        pawnOwners = game.getPawnOwners();
    }

    /**
     * Method to get which player is playing.
     * @return the player that's playing.
     */
    public Player getCurrentPlayer(){
        return this.currentPlayer;
    }

    /**
     * Method to get the list of owners of the pawns
     * @return the list of owners of the pawns
     */
    public String[] getPawnOwners(){
        return this.pawnOwners;
    }

    /**
     * Method to get the state of the game.
     * @return the state of the game
     */
    public State getState(){
        return this.state;
    }

    /**
     * Method to get the resource deck of the game.
     * @return the resource deck of the game
     */
    public ResourceDeck getResDeck(){
        return this.resDeck;
    }

    /**
     * Method to get the golden deck of the game.
     * @return the golden deck of the game
     */
    public GoldenDeck getGoldDeck(){
        return this.golDeck;
    }

    /**
     * Method to get the market of the game.
     * @return the market of the game
     */
    public Market getMarket(){ return this.market;}

    /**
     * Method to get the players of the game.
     * @return the players of the game
     */
    public ArrayList<Player> getPlayers() {
        return this.players;
    }

    /**
     * Method to get the max number of players of the game
     * @return the max number of players of the game
     */
    public int getMaxNumPlayers() {
        return maxNumPlayers;
    }

    /**
     * Method to get the leader of the game.
     * @return the leader of the game
     */
    public Player getLeader(){
        return this.leader;}

    /**
     * Method to get the common objectives of the game.
     * @return the common objectives of the game
     */
    public ArrayList<ObjectiveCard> getCommonObjs(){
        return this.commonObjs;
        }

    /**
     * Method to get the player by its username.
     * @param username is the username of the player
     * @return the player with the username passed by parameter
     */
    public Player getPlayerByUsername (String username) {
        for (Player player : players){
            if (player.getName().equals(username))
                return player;
        }
        return null;
    }

    /**
     * Method to get the winners of the game.
     * @return the winners of the game
     */
    public ArrayList<Player> findWinners() {

        for (Player player : players) {

            int objPointsPrivate = 0, objPointsPublic = 0, objPointsPublic2 = 0;
            int timesPrivate = player.getObjectiveCard().timesCompleted(player.getStation());
            int timesPublic = commonObjs.get(0).timesCompleted(player.getStation());
            int timesPublic2 = commonObjs.get(1).timesCompleted(player.getStation());

            // points of private objective
            if (timesPrivate > 0) {
                objPointsPrivate = timesPrivate * player.getObjectiveCard().getPoints();
                player.incrementNumObjsCompleted();
            }
            // point of first public objective
            if (timesPublic > 0) {
                objPointsPublic = timesPublic * commonObjs.get(0).getPoints();
                player.incrementNumObjsCompleted();
            }
            // point of second public objective
            if (timesPublic2 > 0) {
                objPointsPublic2 = timesPublic2 * commonObjs.get(1).getPoints();
                player.incrementNumObjsCompleted();
            }

            player.setPoints(objPointsPrivate + objPointsPublic + objPointsPublic2);
        }

        leader = players.stream().max(Comparator.comparingInt(Player::getPoints)).orElse(null);

        ArrayList<Player> leaders = players.stream().filter(player -> player.getPoints() == leader.getPoints()).collect(Collectors.toCollection(ArrayList::new));

        int maxobj = leaders.stream().mapToInt(Player::getNumObjsCompleted).max().orElse(0);

        return leaders.stream().filter(player -> player.getNumObjsCompleted() == maxobj).collect(Collectors.toCollection(ArrayList::new));
    }


}



