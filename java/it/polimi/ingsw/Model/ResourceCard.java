package it.polimi.ingsw.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.fusesource.jansi.Ansi;

import java.io.Serializable;

import static org.fusesource.jansi.Ansi.ansi;

/**
 * ResourceCard class, is used to represent a resource card.
 * A resource card is a type of card that the player can place on the board.
 * It's different from the golden one because this one doesn't need resources to be placed
 */
public class ResourceCard extends PlayingCard implements Serializable {

    /**
     * Constructor of the class.
     * Set the attributes of the card reading from the JSON file
     * @param cor0 is the resource of the first corner
     * @param cor1 is the resource of the second corner
     * @param cor2 is the resource of the third corner
     * @param cor3 is the resource of the fourth corner
     * @param permanentRes is the permanent resource of the card
     * @param bonusPoints is the number of bonus points that the card gives
     * @param numCard is the number of the card
     */
    public ResourceCard(@JsonProperty("cor0")Resource cor0, @JsonProperty("cor1")Resource cor1,
                        @JsonProperty("cor2")Resource cor2,@JsonProperty("cor3") Resource cor3,
                        @JsonProperty("permanentRes")Resource permanentRes, @JsonProperty("bonusPoints")int bonusPoints,
                        @JsonProperty("numCard") String numCard){
        super(cor0, cor1, cor2, cor3, permanentRes, bonusPoints,numCard);
    }

    /**
     * Method to print the card in the console.
     * The card is printed in the color of the permanent resource.
     * @return a string that represents the card
     */
    @Override
    public String toString(){
        String card = null;
        String color = switch (this.getPermanentRes().getResName()) {
            case "Animal" -> "BLUE";
            case "Plant" -> "GREEN";
            case "Insect" -> "MAGENTA";
            case "Fungi" -> "RED";
            default -> "WHITE";
        };
        if(this.isFront()){
            String cor0 = switch (this.getCorner(0).getRes().getResName()) {
                case "Animal" -> "🐺";
                case "Plant" -> "🌿";
                case "Insect" -> "🦋";
                case "Fungi" -> "🍄";
                case "Empty" -> "  ";
                case "Hidden" -> "XX";
                case "Inkwell" -> "🫙";
                case "Quill" -> "🪶";
                case "Manuscript" -> "📜";
                default -> "❓";
            };
            String colorCor0 = switch (this.getCorner(0).getRes().getResName()) {
                case "Hidden" -> "RED";
                case "Empty" -> "WHITE";
                default -> color;
            };
            String cor1 = switch (this.getCorner(1).getRes().getResName()) {
                case "Animal" -> "🐺";
                case "Plant" -> "🌿";
                case "Insect" -> "🦋";
                case "Fungi" -> "🍄";
                case "Empty" -> "  ";
                case "Hidden" -> "XX";
                case "Inkwell" -> "🫙";
                case "Quill" -> "🪶";
                case "Manuscript" -> "📜";
                default -> "❓";
            };
            String colorCor1 = switch (this.getCorner(1).getRes().getResName()) {
                case "Hidden" -> "RED";
                case "Empty" -> "WHITE";
                default -> color;
            };
            String cor2 = switch (this.getCorner(2).getRes().getResName()) {
                case "Animal" -> "🐺";
                case "Plant" -> "🌿";
                case "Insect" -> "🦋";
                case "Fungi" -> "🍄";
                case "Empty" -> "  ";
                case "Hidden" -> "XX";
                case "Inkwell" -> "🫙";
                case "Quill" -> "🪶";
                case "Manuscript" -> "📜";
                default -> "❓";
            };
            String colorCor2 = switch (this.getCorner(2).getRes().getResName()) {
                case "Hidden" -> "RED";
                case "Empty" -> "WHITE";
                default -> color;
            };
            String cor3 = switch (this.getCorner(3).getRes().getResName()) {
                case "Animal" -> "🐺";
                case "Plant" -> "🌿";
                case "Insect" -> "🦋";
                case "Fungi" -> "🍄";
                case "Empty" -> "  ";
                case "Hidden" -> "XX";
                case "Inkwell" -> "🫙";
                case "Quill" -> "🪶";
                case "Manuscript" -> "📜";
                default -> "❓";
            };
            String colorCor3 = switch (this.getCorner(3).getRes().getResName()) {
                case "Hidden" -> "RED";
                case "Empty" -> "WHITE";
                default -> color;
            };
            if(this.getBonusPoints()==1) {
                card =
                        ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.valueOf(color)).a("╓──────────").bg(Ansi.Color.YELLOW).a("1").bg(Ansi.Color.valueOf(color)).a("──────────╖").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString()  + "<br>"+
                                ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.valueOf(color)).a(" " ).bg(Ansi.Color.valueOf(colorCor0)).a(cor0).bg(Ansi.Color.valueOf(color)).a("                 ").bg(Ansi.Color.valueOf(colorCor1)).a(cor1).bg(Ansi.Color.valueOf(color)).a(" ").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString() + "<br>"+
                                ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.valueOf(color)).a("                       ").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString()  + "<br>"+
                                ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.valueOf(color)).a(" ").bg(Ansi.Color.valueOf(colorCor3)).a(cor3).bg(Ansi.Color.valueOf(color)).a("                 ").bg(Ansi.Color.valueOf(colorCor2)).a(cor2).bg(Ansi.Color.valueOf(color)).a(" ").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString()  + "<br>"+
                                ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.valueOf(color)).a("╙──────────F──────────╜").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString();

            }else {
                card =
                        ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.valueOf(color)).a("╓───────────").bg(Ansi.Color.valueOf(color)).a("──────────╖").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString()  + "<br>"+
                                ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.valueOf(color)).a(" " ).bg(Ansi.Color.valueOf(colorCor0)).a(cor0).bg(Ansi.Color.valueOf(color)).a("                 ").bg(Ansi.Color.valueOf(colorCor1)).a(cor1).bg(Ansi.Color.valueOf(color)).a(" ").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString() + "<br>"+
                                ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.valueOf(color)).a("                       ").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString()  + "<br>"+
                                ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.valueOf(color)).a(" ").bg(Ansi.Color.valueOf(colorCor3)).a(cor3).bg(Ansi.Color.valueOf(color)).a("                 ").bg(Ansi.Color.valueOf(colorCor2)).a(cor2).bg(Ansi.Color.valueOf(color)).a(" ").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString()  + "<br>"+
                                ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.valueOf(color)).a("╙──────────F──────────╜").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString();
            }
        }else{
            String Res = switch (this.getPermanentRes().getResName()) {
                case "Animal" -> "🐺";
                case "Plant" -> "🌿";
                case "Insect" -> "🦋";
                case "Fungi" -> "🍄";
                default -> "❓";
            };
            card =
                    ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.valueOf(color)).a("╓─────────────────────╖").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString()  + "<br>"+
                            ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.valueOf(color)).a(" ").bg(Ansi.Color.WHITE).a("  ").bg(Ansi.Color.valueOf(color)).a("                 ").bg(Ansi.Color.WHITE).a("  ").bg(Ansi.Color.valueOf(color)).a(" ").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString() + "<br>"+
                            ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.valueOf(color)).a("           "+Res+"          ").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString()  + "<br>"+
                            ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.valueOf(color)).a(" ").bg(Ansi.Color.WHITE).a("  ").bg(Ansi.Color.valueOf(color)).a("                 ").bg(Ansi.Color.WHITE).a("  ").bg(Ansi.Color.valueOf(color)).a(" ").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString()  + "<br>"+
                            ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.valueOf(color)).a("╙──────────B──────────╜").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString();
        }
        return card;
    }
}
