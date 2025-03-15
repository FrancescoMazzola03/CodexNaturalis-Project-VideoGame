package it.polimi.ingsw.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.fusesource.jansi.Ansi;

import java.io.Serializable;

import static org.fusesource.jansi.Ansi.ansi;

/**
 * ItemObjective class, is used to represent an item objective card.
 * It extends the ObjectiveCard class.
 * An item objective card is a type of objective card that requires the player to have a certain number of items.
 * The items can be manuscripts, quills or inkwells.
 * The player can have the items in any combination, the only important thing is that the player has the right number of items.
 *
 */
public class ItemObjective extends ObjectiveCard implements Serializable {

    /**
     * Attributes of the class.
     * numManuscript is the number of manuscripts required to complete the objective.
     * numQuill is the number of quills required to complete the objective.
     * numInkwell is the number of inkwells required to complete the objective.
     * numCard is the number of the card.
     */
    private int numManuscript;
    private int numQuill;
    private int numInkwell;
    private String numCard;

    /**
     * Constructor of the class.
     * Set the attributes of the card reading from the JSON file
     * @param points is the number of points that the player will gain if he completes the objective.
     * @param numManuscript is the number of manuscripts required to complete the objective.
     * @param numQuill is the number of quills required to complete the objective.
     * @param numInkwell is the number of inkwells required to complete the objective.
     * @param numCard is the number of the card.
     */
    public ItemObjective(@JsonProperty("points")int points, @JsonProperty("manuscripts")int numManuscript, @JsonProperty("quills") int numQuill,@JsonProperty("inkwells") int numInkwell,
                         @JsonProperty("numCard") String numCard){
        super(points);
        this.numInkwell = numInkwell;
        this.numManuscript = numManuscript;
        this.numQuill = numQuill;
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
     * Method to print the card in the console.
     * The card is printed in cyan color.
     * It uses emojis to represent the item required to complete the objective.
     * @return the String that represent the card.
     */
    @Override
    public String toString() {
        String card = null;
        if (this.getPoints() == 2) {
            if (this.numQuill > 0) {
                card = ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.CYAN).a("╓────────────────────╖").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString() + "<br>" +
                        ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.CYAN).a("                      ").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString() + "<br>" +
                        ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.CYAN).a("          2 x 🪶🪶    ").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString() + "<br>" +
                        ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.CYAN).a("                      ").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString() + "<br>" +
                        ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.CYAN).a("╙────────────────────╜").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString();
            }
            if (this.numInkwell > 0) {
                card = ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.CYAN).a("╓────────────────────╖").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString() + "<br>" +
                        ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.CYAN).a("                      ").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString() + "<br>" +
                        ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.CYAN).a("          2 x 🫙🫙    ").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString() + "<br>" +
                        ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.CYAN).a("                      ").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString() + "<br>" +
                        ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.CYAN).a("╙────────────────────╜").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString();
            }
            if (this.numManuscript > 0) {
                card = ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.CYAN).a("╓────────────────────╖").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString() + "<br>" +
                        ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.CYAN).a("                      ").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString() + "<br>" +
                        ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.CYAN).a("          2 x 📜📜    ").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString() + "<br>" +
                        ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.CYAN).a("                      ").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString() + "<br>" +
                        ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.CYAN).a("╙────────────────────╜").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString();
            }

        }else {
            card = ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.CYAN).a("╓────────────────────╖").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString() + "<br>"+
                    ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.CYAN).a("               🫙     ").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString() + "<br>"+
                    ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.CYAN).a("          3 x  📜     ").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString() + "<br>"+
                    ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.CYAN).a("               🪶     ").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString() + "<br>"+
                    ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.CYAN).a("╙────────────────────╜").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString();
        }
        return card;

    }

    /**
     * Method to get the number of times a player completed the objective.
     * @param station is the board of the player.
     * @return the number of times that the objective is completed.
     */
    @Override
    public int timesCompleted(Station station) {
        if (this.numInkwell == 2)
            return station.getResNum(Resource.INKWELL) / 2;
        else if (this.numQuill == 2)
            return station.getResNum(Resource.QUILL) / 2;
        else if (this.numManuscript == 2)
            return station.getResNum(Resource.MANUSCRIPT) / 2;
        else{
            int smallest = station.getResNum(Resource.INKWELL); // look for the minimum of the three item types and then return that as the number of times the objective has been completed
            if (smallest > station.getResNum(Resource.MANUSCRIPT)) smallest = station.getResNum(Resource.MANUSCRIPT);
            if (smallest > station.getResNum(Resource.QUILL)) smallest = station.getResNum(Resource.QUILL);
            return smallest;
        }
    }
}
