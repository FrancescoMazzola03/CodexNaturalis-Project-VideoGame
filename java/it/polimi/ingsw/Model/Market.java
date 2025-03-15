package it.polimi.ingsw.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Market class, is used to represent the market of the game.
 * The market is composed by 4 cards, 2 resource cards and 2 golden cards.
 * The market is used to buy cards.
 *
 */
public class Market implements Serializable {
        /**
        * Attributes of the class.
        * resourceMarket is the list of resource cards in the market.
        * goldenMarket is the list of golden cards in the market.
        */
     private ResourceCard []resourceMarket;
     private GoldenCard []goldenMarket;

     /**
      * Constructor of the class.
      * It initializes the market with 4 cards, 2 resource cards and 2 golden cards.
      */
     public Market(){
          this.resourceMarket = new ResourceCard[2];
          this.goldenMarket = new GoldenCard[2];
     }

     /**
      * Method to update the market after a player buys a card.
      * It removes the card that the player bought and adds a new card to the market.
      * @param marketChoice it's the choice of the player about which card he wants to buy.
      * @param res it's the deck of resource cards.
      * @param gold it's the deck of golden cards.
      */
     public void update(int marketChoice,ResourceDeck res, GoldenDeck gold){
          if (marketChoice<2){
               resourceMarket[marketChoice] = res.pop();
          }
          else if(marketChoice<4){
               goldenMarket[marketChoice - 2] = gold.pop();
          }
     }

     /**
      * Method to initialize the market using the decks provided in input.
      * @param res it's the deck of resource cards.
      * @param gold it's the deck of golden cards.
      */
     public void setMarket(ResourceDeck res, GoldenDeck gold){
          resourceMarket[0]= res.pop();
          resourceMarket[1]= res.pop();
          goldenMarket[0]= gold.pop();
          goldenMarket[1]= gold.pop();
     }

     /**
      * Method to get the resource market.
      * @return the resource market
      */
     public ResourceCard[] getResourceMarket() {
          return this.resourceMarket;
     }

     /**
      * Method to get the golden market.
      * @return the golden market
      */
     public GoldenCard[] getGoldenMarket() {
          return this.goldenMarket;
     }


     /**
      * Method to print the market in the console, it calls the toString of each card in the market.
      * @return the String that represents the market
      */
     @Override
     public String toString(){
          StringBuilder combinedCards = new StringBuilder();
          List<String> cards = new ArrayList<>();
          cards.add(this.resourceMarket[0].toString());
          cards.add(this.resourceMarket[1].toString());
          cards.add(this.goldenMarket[0].toString());
          cards.add(this.goldenMarket[1].toString());
          System.out.println("Here is the Market");
          int numberOfLines = this.resourceMarket[0].toString().split("<br>").length;
          for (int i = 0; i < numberOfLines; i++) {
               for (String card : cards) {
                    String[] cardLines = card.split("<br>");
                    combinedCards.append(cardLines[i]).append("  ");
               }
               combinedCards.append("\n");
          }
          return combinedCards.toString();
     }

     /**
      * Method to get the card that the player wants to buy.
      * @param marketChoice it's the choice of the player about which card he wants to buy.
      * @return the card that the player wants to buy.
      */
     public PlayingCard getMarketCard(int marketChoice){
          if (marketChoice<2){
               return resourceMarket[marketChoice];
          }
          else if(marketChoice<4){
               return goldenMarket[marketChoice-2];
          }
         return null;
     }
}
