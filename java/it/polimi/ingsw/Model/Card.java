package it.polimi.ingsw.Model;
import it.polimi.ingsw.ModelView.*;

import java.io.Serializable;

/**
 * Card class, is used to represent a card of the game.
 * A card has 4 corners {@link Corner} and can be placed front or back.
 * The card has a number, so you can bind the real Card to the JSON card.
 * The card has a position in the matrix of the game.
 * The card has a resource in each corner.
 */
public class Card implements Serializable {
    private Corner corners [];
    private int posX, posY; // coordinates of the card in the matrix
    private boolean front; // true if it's front,false otherwise
    private String numCard;

    /**
     * Constructor of the class.
     * Assume that the card is front.
     * @param cor0 resource of the first corner
     * @param cor1 resource of the second corner
     * @param cor2 resource of the third corner
     * @param cor3 resource of the fourth corner
     * @param numCard number of the card
     */
    public Card(Resource cor0, Resource cor1, Resource cor2, Resource cor3,String numCard) {
        corners = new Corner[4];
        for (int i = 0; i < 4; i++)
            this.corners[i] = new Corner();
        corners[0].setRes(cor0);
        corners[1].setRes(cor1);
        corners[2].setRes(cor2);
        corners[3].setRes(cor3);
        this.front = true;
        this.numCard = numCard;
    }

    /**
     * Method to get the number of the card.
     * @return the number of the card
     */
    public String getNumCard() {
        return this.numCard;
    }

    /**
     * Method to get the  corner.
     * @param numCorner the number of the corner to know
     * @return a specific corner of the card
     */
    public Corner getCorner(int numCorner){
        return this.corners[numCorner];
    }

    /**
     * Method to set the state of the card.
     * @param front true if the card is front, false otherwise
     */
    public void setState(boolean front){
        this.front= front;
    }

    /**
     * Method to know if the card is front or back.
     * @return true if the card is front, false otherwise
     */
    public boolean isFront(){
        return this.front;
    }

    /**
     * Method to get the position of the card in the matrix.
     * @return the position of the card in the matrix
     */
    public int[] getPos(){
        int[] pos = {this.posX,this.posY};
        return pos;
    }

    /**
     * Method to set the position of the card in the matrix.
     * @param x the x coordinate of the card
     * @param y the y coordinate of the card
     */
    public void setPos(int x, int y){
        this.posX = x;
        this.posY = y;
    }


}
