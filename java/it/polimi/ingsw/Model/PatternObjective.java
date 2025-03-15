package it.polimi.ingsw.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.fusesource.jansi.Ansi;

import java.io.Serializable;

import static org.fusesource.jansi.Ansi.ansi;

/**
 * PatternObjective class, is used to represent a pattern objective card.
 * A pattern objective card is a type of objective card that requires the player to place the cards in a specific pattern.
 */
public class PatternObjective extends ObjectiveCard implements Serializable {
    /**
     * Attributes of the class.
     * pattern is the pattern that the player has to follow to complete the objective. -1 Stands for left, 0 for up, +1 for right. Each of these is to be considered from the bottom card to the top card
     * firstRes is the first resource that represents the type (animal, fungi, insect, plant) of the first card that the player has to place in the pattern.
     * secondRes is the second resource that represents the type (animal, fungi, insect, plant) of the second card that the player has to place in the pattern.
     * thirdRes is the third resource that represents the type (animal, fungi, insect, plant) of the third card that the player has to place in the pattern.
     * numCard is the number of the card.
     */
    private int[] pattern; //-1 left, 0 up, +1 right
    private Resource firstRes;
    private Resource secondRes;
    private Resource thirdRes;
    private String numCard;

    /**
     * Constructor of the class.
     * Set the attributes of the card reading from the JSON file
     * @param points is the number of points that the player will gain if he completes the objective.
     * @param pattern is the pattern that the player has to follow to complete the objective.
     * @param first is the first resource that represents the type (animal, fungi, insect, plant) of the first card that the player has to place in the pattern.
     * @param second is the second resource that represents the type (animal, fungi, insect, plant) of the second card that the player has to place in the pattern.
     * @param third is the third resource that represents the type (animal, fungi, insect, plant) of the third card that the player has to place in the pattern.
     * @param numCard is the number of the card.
     */
    public PatternObjective(@JsonProperty("points") int points, @JsonProperty("pattern") int[] pattern,@JsonProperty("first") Resource first,
                            @JsonProperty("second")Resource second, @JsonProperty("third") Resource third,@JsonProperty("numCard") String numCard) {
        super(points);

        this.pattern = pattern;
        this.firstRes = first;
        this.secondRes = second;
        this.thirdRes = third;
        this.numCard = numCard;
    }

    /**
     * Method to get the number of the card
     * @return the number of the card
     */
    public String getNumCard() {
        return this.numCard;
    }

    /**
     * Method to print the PatternObjective card in the console.
     * The background color is based on the real card.
     * The method prints the pattern using the emojis, these in addiction to representing the pattern, also represent the type (animal, fungi, insect, plant) of the card that the player has to place in the pattern.
     * @return the String that represent the card.
     */
    @Override
    public String toString() {
        String card = null;
        StringBuilder finalString = new StringBuilder("Pattern Objective\n");
        if(this.firstRes.equals(this.secondRes) && this.secondRes.equals(this.thirdRes)) {
            if(this.pattern[0]==-1){
                if(this.firstRes.getResName().equals("Insect")){
                    card = ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.MAGENTA).a("╓──────────2──────────╖").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString()+"<br>"+
                            ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.MAGENTA).a("         ").bg(Ansi.Color.WHITE).a("🦋").bg(Ansi.Color.MAGENTA).a("            ").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString()+"<br>"+
                            ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.MAGENTA).a("            ").bg(Ansi.Color.WHITE).a("🦋").bg(Ansi.Color.MAGENTA).a("         ").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString()+"<br>"+
                            ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.MAGENTA).a("               ").bg(Ansi.Color.WHITE).a("🦋").bg(Ansi.Color.MAGENTA).a("      ").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString()+"<br>"+
                            ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.MAGENTA).a("╙─────────────────────╜").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString();
                }
                else {
                    card = ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.GREEN).a("╓──────────2──────────╖").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString() + "<br>" +
                            ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.GREEN).a("          ").bg(Ansi.Color.WHITE).a("🌿").bg(Ansi.Color.GREEN).a("           ").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString() + "<br>" +
                            ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.GREEN).a("            ").bg(Ansi.Color.WHITE).a("🌿").bg(Ansi.Color.GREEN).a("         ").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString() + "<br>" +
                            ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.GREEN).a("              ").bg(Ansi.Color.WHITE).a("🌿").bg(Ansi.Color.GREEN).a("       ").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString() + "<br>" +
                            ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.GREEN).a("╙─────────────────────╜").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString();
                }
            }else{
                if(this.firstRes.getResName().equals("Fungi")) {
                    card = ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.RED).a("╓──────────2──────────╖").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString() + "<br>" +
                            ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.RED).a("               ").bg(Ansi.Color.WHITE).a("🍄").bg(Ansi.Color.RED).a("      ").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString() + "<br>" +
                            ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.RED).a("            ").bg(Ansi.Color.WHITE).a("🍄").bg(Ansi.Color.RED).a("         ").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString() + "<br>" +
                            ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.RED).a("         ").bg(Ansi.Color.WHITE).a("🍄").bg(Ansi.Color.RED).a("            ").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString() + "<br>" +
                            ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.RED).a("╙─────────────────────╜").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString();

                }else{
                    card = ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.RED).a("╓──────────2──────────╖").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString() + "<br>" +
                            ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.RED).a("               ").bg(Ansi.Color.WHITE).a("🐺").bg(Ansi.Color.RED).a("      ").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString() + "<br>" +
                            ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.RED).a("            ").bg(Ansi.Color.WHITE).a("🐺").bg(Ansi.Color.RED).a("         ").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString() + "<br>" +
                            ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.RED).a("         ").bg(Ansi.Color.WHITE).a("🐺").bg(Ansi.Color.RED).a("            ").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString() + "<br>" +
                            ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.RED).a("╙─────────────────────╜").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString();
                }
            }
        }else{
            if(this.firstRes.getResName().equals("Animal")){
                card = ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.BLUE).a("╓──────────3──────────╖").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString() + "<br>" +
                        ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.BLUE).a("             ").bg(Ansi.Color.WHITE).a("🍄").bg(Ansi.Color.BLUE).a("        ").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString() + "<br>" +
                        ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.BLUE).a("          ").bg(Ansi.Color.WHITE).a("🐺").bg(Ansi.Color.BLUE).a("           ").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString() + "<br>" +
                        ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.BLUE).a("          ").bg(Ansi.Color.WHITE).a("🐺").bg(Ansi.Color.BLUE).a("           ").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString() + "<br>" +
                        ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.BLUE).a("╙─────────────────────╜").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString();
            }
            else if(this.firstRes.getResName().equals("Plant")){
                card = ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.RED).a("╓──────────3──────────╖").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString() + "<br>" +
                        ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.RED).a("         ").bg(Ansi.Color.WHITE).a("🍄").bg(Ansi.Color.RED).a("            ").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString() + "<br>" +
                        ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.RED).a("         ").bg(Ansi.Color.WHITE).a("🍄").bg(Ansi.Color.RED).a("            ").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString() + "<br>" +
                        ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.RED).a("            ").bg(Ansi.Color.WHITE).a("🌿").bg(Ansi.Color.RED).a("         ").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString() + "<br>" +
                        ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.RED).a("╙─────────────────────╜").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString();
            }
            else if(this.firstRes.getResName().equals("Insect") &&  this.thirdRes.getResName().equals("Animal")){
                card = ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.MAGENTA).a("╓──────────3──────────╖").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString() + "<br>" +
                        ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.MAGENTA).a("         ").bg(Ansi.Color.WHITE).a("🐺").bg(Ansi.Color.MAGENTA).a("            ").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString() + "<br>" +
                        ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.MAGENTA).a("            ").bg(Ansi.Color.WHITE).a("🦋").bg(Ansi.Color.MAGENTA).a("         ").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString() + "<br>" +
                        ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.MAGENTA).a("            ").bg(Ansi.Color.WHITE).a("🦋").bg(Ansi.Color.MAGENTA).a("         ").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString() + "<br>" +
                        ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.MAGENTA).a("╙─────────────────────╜").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString();
            }else{
                card = ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.GREEN).a("╓──────────3──────────╖").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString() + "<br>" +
                        ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.GREEN).a("             ").bg(Ansi.Color.WHITE).a("🌿").bg(Ansi.Color.GREEN).a("        ").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString() + "<br>" +
                        ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.GREEN).a("             ").bg(Ansi.Color.WHITE).a("🌿").bg(Ansi.Color.GREEN).a("        ").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString() + "<br>" +
                        ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.GREEN).a("          ").bg(Ansi.Color.WHITE).a("🦋").bg(Ansi.Color.GREEN).a("           ").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString() + "<br>" +
                        ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.GREEN).a("╙─────────────────────╜").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString();
            }
        }

        return card;
    }

    /**
     * Method to get how many times the objective is completed.
     * @param station is the board of the player
     * @return the number of times the objective is completed
     */
    public int timesCompleted(Station station){
        int counter = 0;
        int helPattern = 0; //It is needed because if there is a zero in the pattern you have to go back 2 rows in the matrix instead of just 1

        for(int i = 1; i < station.getCards().size(); i++) { // the for starts from 1 because 0 contains the initial card, which is not useful for completing the objective
            try {
                PlayingCard firstCard = (PlayingCard) station.getCards().get(i);
                Resource first = firstCard.getPermanentRes();
                if (first.equals(this.firstRes) && !firstCard.isUsed()) {
                    if(this.pattern[0]==0){
                        helPattern = -1;
                    }

                    int secondCardX = firstCard.getPos()[0] - 1 + helPattern;  // Since we always start from the lowest card and move upwards, the X will always be -1 of the card we consider
                    int secondCardY = firstCard.getPos()[1] + this.pattern[0]; // X always decreases, while the pattern depends on Y
                    int secondCardPosition = station.getCardPositions()[secondCardX][secondCardY];
                    PlayingCard secondCard = (PlayingCard) station.getCards().get(secondCardPosition);
                    Resource second = secondCard.getPermanentRes();
                    helPattern = 0; // reset it to zero otherwise it will affect the thirdCard

                    if (second.equals(this.secondRes) && !secondCard.isUsed()) {
                        if(this.pattern[1]==0){
                            helPattern = -1;
                        }

                        int thirdCardX = secondCard.getPos()[0] - 1 + helPattern;
                        int thirdCardY = secondCard.getPos()[1] + this.pattern[1];
                        int thirdCardPosition = station.getCardPositions()[thirdCardX][thirdCardY];
                        PlayingCard thirdCard = (PlayingCard) station.getCards().get(thirdCardPosition);
                        Resource third = thirdCard.getPermanentRes();
                        if (third.equals(this.thirdRes) && !thirdCard.isUsed()) {
                            firstCard.setUsed(true);            // set the isUsed flag of the cards used in the objective so that they are not used multiple times
                            secondCard.setUsed(true);
                            thirdCard.setUsed(true);
                            counter++;
                        }
                    }

                }
            }catch(IndexOutOfBoundsException | ClassCastException e){
                continue;
            }
        }
        for(int i = 1; i < station.getCards().size(); i++)  // set the isUsed flag back to false so if I have another PatternObjective I can use all the cards again
            ((PlayingCard)station.getCards().get(i)).setUsed(false);
        return counter;
    }
}
