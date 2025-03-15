package it.polimi.ingsw.Model;
import it.polimi.ingsw.Model.Exceptions.InsufficientResourcesException;
import it.polimi.ingsw.Model.Exceptions.InvalidPositionException;
import it.polimi.ingsw.Model.Exceptions.NoCornersException;
import it.polimi.ingsw.Model.Exceptions.ZeroResourceException;
import org.fusesource.jansi.Ansi;

import java.io.Serializable;
import java.util.ArrayList;

import static org.fusesource.jansi.Ansi.ansi;

/**
 * Station class, is used to represent the board of a player (board and station are synonymous).
 * The board save the cards that a player place and the resources that gains placing the cards.
 * A player place a Card on the board and this one is saved in the matrix at a specific row and column,
 * the position of the card in the cards ArrayList is saved in the cell.
 * So for example the first card that the player place is saved in the cell (40,40) and the position of the card in the cards ArrayList is 0,
 * so the cell (40,40) contains the number 0.
 */
public class Station implements Serializable {
    /**
     * Attributes of the class.
     * numAnimals is the number of animals that the player has.
     * numPlants is the number of plants that the player has.
     * numInsects is the number of insects that the player has.
     * numFungi is the number of fungi that the player has.
     * numInkwells is the number of inkwells that the player has.
     * numManuscripts is the number of manuscripts that the player has.
     * numQuills is the number of quills that the player has.
     * cardPositions is the matrix that contains the cards
     * cards is the ArrayList that contains the cards that the player placed.
     */
    private int numAnimals;
    private int numPlants;
    private int numInsects;
    private int numFungi;
    private int numInkwells;
    private int numManuscripts;
    private int numQuills;
    private int[][] cardPositions = new int[82][82];
    private ArrayList<Card> cards;

    /**
     * Constructor of the class.
     * Initialize the matrix cardPositions with -1 because the first card is 0.
     * The -1 value means that in the cell there isn't a card.
     * Initialize the ArrayList cards.
     * The first card is the StarterCard and is placed in the cell (40,40).
     */
    public Station() {
        for (int i = 0; i < 82; i++) {
            for (int j = 0; j < 82; j++) {
                cardPositions[i][j] = -1;
            }
        }
        cardPositions[40][40] = 0; // at (40,40) there is the starterCard
        cards = new ArrayList<>();
    }

    /**
     * Method to get the ArrayList cards.
     * @return the ArrayList cards.
     */
    public ArrayList<Card> getCards() {
        return this.cards;
    }

    /**
     * Method to get the matrix cardPositions.
     * @return the matrix cardPositions.
     */
    public int[][] getCardPositions() {
        return this.cardPositions;
    }

    /**
     * Method to set the number of the card in the cell (x,y) of the matrix cardPositions.
     * @param x row of the matrix
     * @param y column of the matrix
     * @param numCard number of the card of the ArrayList
     */
    public void setNumCard(int x, int y, int numCard) {
        this.cardPositions[x][y] = numCard;
    }

    /**
     * Method to get the number of the resource res that the player has.
     * @param res Resource
     * @return the number of the resource res that the player has.
     */
    public int getResNum(Resource res) {
        switch (res) {
            case ANIMAL:
                return numAnimals;
            case PLANT:
                return numPlants;
            case INSECT:
                return numInsects;
            case FUNGI:
                return numFungi;
            case INKWELL:
                return numInkwells;
            case MANUSCRIPT:
                return numManuscripts;
            case QUILL:
                return numQuills;
            default:
                return 0;
        }
    }

    /**
     * Method to increment the number of the resource res that the player has.
     * @param res Resource
     */
    public void incrementResNum(Resource res) {
        switch (res) {
            case ANIMAL:
                numAnimals++;
                break;
            case PLANT:
                numPlants++;
                break;
            case INSECT:
                numInsects++;
                break;
            case FUNGI:
                numFungi++;
                break;
            case INKWELL:
                numInkwells++;
                break;
            case MANUSCRIPT:
                numManuscripts++;
                break;
            case QUILL:
                numQuills++;
                break;
            default:
                break;
        }
    }

    /**
     * Method to decrement the number of the resource res that the player has.
     * @param res Resource
     * @throws ZeroResourceException if the player has 0 resources of the type res
     */
    public void decrementResNum(Resource res) throws ZeroResourceException {
        if(this.getResNum(res) == 0 && !res.equals(Resource.HIDDEN) && !res.equals(Resource.EMPTY))
            throw new ZeroResourceException(res);
        switch (res) {
            case ANIMAL:
                numAnimals--;
                break;
            case PLANT:
                numPlants--;
                break;
            case INSECT:
                numInsects--;
                break;
            case FUNGI:
                numFungi--;
                break;
            case INKWELL:
                numInkwells--;
                break;
            case MANUSCRIPT:
                numManuscripts--;
                break;
            case QUILL:
                numQuills--;
                break;
            default:
                break;
        }
    }

    /**
     * Method to add a card to the ArrayList cards.
     * @param card Card
     */
    public void addCard(Card card) {
        cards.add(card);
    }

    /**
     * Method to check if the player has enough resources to place the card.
     * @param chosenHandCard it's the card the player wants to place
     * @return boolean, true if the player has enough resources to place the card, false otherwise
     */
    public boolean hasResourceNeeded(PlayingCard chosenHandCard) {
        if (chosenHandCard.isFront() && chosenHandCard instanceof GoldenCard)
        {
            if (((GoldenCard) chosenHandCard).getNumAnimals() <= numAnimals &&
                    ((GoldenCard) chosenHandCard).getNumInsects() <= numInsects &&
                    ((GoldenCard) chosenHandCard).getNumPlants() <= numPlants &&
                    ((GoldenCard) chosenHandCard).getNumFungi() <= numFungi)
            {
                return true;
            } else {
                return false;
            }
        }
        return true;
    }

    /**
     * Method to check if a card can be placed.
     * @param row row of the matrix cardPositions where the player wants to place the card
     * @param column column of the matrix cardPositions where the player wants to place the card
     * @param handCardPos position of the card in the hand of the player
     * @param player player that wants to place the card
     * @return boolean, true if the card can be placed in the position (row,column), false otherwise
     * @throws NoCornersException If there isn't a corner in which the card can be placed
     * @throws InvalidPositionException If the position (row,column) is invalid
     * @throws InsufficientResourcesException If the player doesn't have enough resources to place the card
     */
    public boolean isPlaceable(int row, int column, int handCardPos, Player player) throws NoCornersException,InvalidPositionException,InsufficientResourcesException{
        if(cardPositions[row][column] != -1 || (
                cardPositions[row + 1][column + 1] == -1 && cardPositions[row - 1][column - 1] == -1 &&
                        cardPositions[row + 1][column - 1] == -1 && cardPositions[row - 1][column + 1] == -1) ||
                row >= 81 || column >= 81 || row < 0 || column < 0)
        {System.out.println("row:"+row + "column:"  + column );
            System.out.println(cardPositions[40][40]);
            System.out.println(cardPositions[row][column]);
            System.out.println(cardPositions[41][39]);
            throw new InvalidPositionException();}
        else if(!this.hasResourceNeeded(player.getHand().get(handCardPos - 1))
                && player.getHand().get(handCardPos - 1).isFront()){               //the -1 is used because the player chooses a card from 1 to 3 in the hand but
            throw new InsufficientResourcesException();                           // the arraylist has indices from 0 to 2
        }
        else if(!enoughCorners(row,column))
                throw new NoCornersException();
        return true;
    }

    /**
     * Method to check if there is a corner in which the card can be placed.
     * @param row row of the matrix cardPositions where the player wants to place the card
     * @param column column of the matrix cardPositions where the player wants to place the card
     * @return boolean, true if there is a corner in which the card can be placed, false otherwise.
     */
    public boolean enoughCorners(int row, int column){
        if(cardPositions[row + 1][column + 1] != -1 && !cards.get(cardPositions[row + 1][column + 1]).getCorner(0).getRes().equals(Resource.HIDDEN))
            return true;
        else if( cardPositions[row + 1][column - 1] != -1 && !cards.get(cardPositions[row + 1][column - 1]).getCorner(1).getRes().equals(Resource.HIDDEN))
            return true;
        else if( cardPositions[row - 1][column + 1] != -1 && !cards.get(cardPositions[row - 1][column + 1]).getCorner(3).getRes().equals(Resource.HIDDEN))
            return true;
        else if( cardPositions[row - 1][column - 1] != -1 && !cards.get(cardPositions[row - 1][column - 1]).getCorner(2).getRes().equals(Resource.HIDDEN))
            return true;
        return false;
    }

    /**
     * Method used to check if a card is placeable in the GUI.
     * @param row row of the matrix cardPositions where the player wants to place the card
     * @param column column of the matrix cardPositions where the player wants to place the card
     * @param handCardPos position of the card in the hand of the player
     * @param player player that wants to place the card
     * @return boolean, true if the card can be placed in the position (row,column), false otherwise
     */
    public boolean fastIsPlaceable(int row, int column, int handCardPos, Player player) {
        if (cardPositions[row][column] != -1 || (
                cardPositions[row + 1][column + 1] == -1 && cardPositions[row - 1][column - 1] == -1 &&
                        cardPositions[row + 1][column - 1] == -1 && cardPositions[row - 1][column + 1] == -1) ||
                row >= 81 || column >= 81 || row < 0 || column < 0)
            return false;
        else if(!enoughCorners(row,column))
            return false;
        return true;
    }

    /**
     * Method to print the matrix cardPositions.
     * It prints with white color the possible position to place a card and with colored numbers the cards that the player placed
     * @return prints the matrix cardPositions in the console.
     */
    @Override
    public String toString(){

        int startRow = -1;
        int endRow = -1;
        int startCol = -1;
        int endCol = -1;
        boolean hasNonMinusOneRow = false;
        boolean hasNonMinusOneCol = false;
        StringBuilder ris = new StringBuilder();


        for (int i = 0; i < 81 ; i++) {
            for (int j = 0 ; j < 81; j++) {
                if(cardPositions[i][j] != -1 && !hasNonMinusOneRow){
                    startRow = i ;
                    hasNonMinusOneRow = true;
                }
                if(cardPositions[i][j] != -1 && !hasNonMinusOneCol){
                    startCol = j;
                    hasNonMinusOneCol = true;
                }
                if(cardPositions[i][j] != -1 && i < startRow ){
                    startRow = i;
                }
                if(cardPositions[i][j] != -1 && j < startCol){
                    startCol = j;
                }
                if(cardPositions[i][j] != -1 && i > endRow){
                    endRow = i;
                }
                if(cardPositions[i][j] != -1 && j > endCol){
                    endCol = j;
                }
            }
        }

        if(startRow > 1){
            startRow -= 1;
        }
        if(startCol > 1){
            startCol -= 1;
        }
        if(getCards().size()<100){
            endRow += 1;
            endCol += 1;
        }




        for (int i = startRow; i <= endRow; i++) {
            for (int j = startCol; j <= endCol; j++) {
                if(cardPositions[i][j] != -1) {
                    if(i == 40 && j == 40){
                        ris.append(ansi().fg(Ansi.Color.YELLOW).a("   ").a(cardPositions[i][j]).a("   ").reset());
                    }
                    else {
                        PlayingCard playingCard = (PlayingCard) cards.get(cardPositions[i][j]);
                        switch (playingCard.getPermanentRes().getResName()) {
                            case "Fungi":
                                ris.append(ansi().fg(Ansi.Color.RED).a("   ").a(cardPositions[i][j]).a("   ").reset());
                                break;
                            case "Animal":
                                ris.append(ansi().fg(Ansi.Color.BLUE).a("   ").a(cardPositions[i][j]).a("   ").reset());
                                break;
                            case "Plant":
                                ris.append(ansi().fg(Ansi.Color.GREEN).a("   ").a(cardPositions[i][j]).a("   ").reset());
                                break;
                            case "Insect":
                                ris.append(ansi().fg(Ansi.Color.MAGENTA).a("   ").a(cardPositions[i][j]).a("   ").reset());
                                break;

                        }
                    }
                }
                else{
                    ris.append(String.format("(%d,%d)", i, j));
                }
                ris.append("   "); // Add space between columns
            }
            ris.append("\n"); // Carry out after each line
        }

        return ris.toString();
    }
}
