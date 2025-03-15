package it.polimi.ingsw.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.fusesource.jansi.Ansi;

import java.io.Serializable;

import static org.fusesource.jansi.Ansi.ansi;

/**
 * ResObjective class, is used to represent a resource objective card.
 * A resource objective card is a type of objective card that requires the player to have a certain number of resources.
 * The resources can be animals, plants, insects or fungi.
 * The player can have the resources in any combination, the only important thing is that the player has the right number of resources.
 * The class extends the ObjectiveCard class.
 */
public class ResObjective extends ObjectiveCard implements Serializable {
    /**
     * Attributes of the class.
     * resNeeded is the resource needed to complete the objective.
     * numCard is the number of the card.
     */
    private Resource resNeeded;
    private String numCard;

    /**
     * Constructor of the class.
     * Set the attributes of the card reading from the JSON file
     * @param points is the number of points that the player will gain if he completes the objective.
     * @param resNeeded is the resource needed to complete the objective.
     * @param numCard is the number of the card.
     */
    public ResObjective(@JsonProperty("points")int points, @JsonProperty("resNeeded")Resource resNeeded,@JsonProperty("numCard") String numCard) {
        super(points);
        this.resNeeded = resNeeded;
        this.numCard = numCard;
    }

    /**
     * Method to get the number of the card.
     * @return the number of the card.
     */
    public String getNumCard() {
        return this.numCard;
    }

    /**
     * Method to print the card in the console.
     * The card is printed in the color of the resource needed.
     * @return the toString of the card
     */
    @Override
    public String toString() {
        String card = null;
        switch (resNeeded.getResName()) {
            case "Animal" : card =
                    ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.BLUE).a("╓────────────────────╖").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString()+"<br>" +
                            ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.BLUE).a("               🐺     ").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString()+"<br>" +
                            ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.BLUE).a("        2 x  🐺  🐺   ").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString()+"<br>" +
                            ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.BLUE).a("                      ").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString()+"<br>" +
                            ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.BLUE).a("╙────────────────────╜").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString();
                break;
            case "Plant" :card =
                    ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.GREEN).a("╓────────────────────╖").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString()+"<br>" +
                            ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.GREEN).a("               🌿     ").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString()+"<br>" +
                            ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.GREEN).a("        2 x  🌿 🌿    ").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString()+"<br>" +
                            ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.GREEN).a("                      ").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString()+"<br>" +
                            ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.GREEN).a("╙────────────────────╜").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString();
                break;
            case "Insect" :card =
                    ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.MAGENTA).a("╓────────────────────╖").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString()+"<br>" +
                            ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.MAGENTA).a("               🦋     ").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString()+"<br>" +
                            ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.MAGENTA).a("        2 x  🦋  🦋   ").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString()+"<br>" +
                            ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.MAGENTA).a("                      ").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString()+"<br>" +
                            ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.MAGENTA).a("╙────────────────────╜").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString();
                break;
            case "Fungi" : card =
                    ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.RED).a("╓────────────────────╖").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString()+"<br>" +
                            ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.RED).a("               🍄     ").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString()+"<br>" +
                            ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.RED).a("        2 x  🍄  🍄   ").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString()+"<br>" +
                            ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.RED).a("                      ").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString()+"<br>" +
                            ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.RED).a("╙────────────────────╜").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString();
                break;
        }
        return card;
    }

    /**
     * Method to count how many times the objective is completed.
     * @param station is the board of the player
     * @return the number of times that the objective is completed.
     */
    @Override
    public int timesCompleted(Station station){
        return station.getResNum(this.resNeeded) / 3;
    }
}
