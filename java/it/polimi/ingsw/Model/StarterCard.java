package it.polimi.ingsw.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.fusesource.jansi.Ansi;

import java.io.Serializable;
import java.util.ArrayList;

import static org.fusesource.jansi.Ansi.ansi;

/**
 * StarterCard class, is used to represent a starter card.
 * A starter card is a type of card that the player places first on the board.
 * It's different from the PlayingCard because it can have more than one permanent resource and also the back corners aren't always empty..
 */
public class StarterCard extends Card implements Serializable {
    /**
     * Attributes of the class.
     * permanentRes is an array list of resources that are permanent on the card.
     * backCorners is an array of corners that are on the back of the card.
     */
    private ArrayList<Resource> permanentRes;
    private Corner backCorners[];

    /**
     * Constructor of the class.
     * Set the attributes of the card reading from the JSON file
     * @param cor0 is the resource of the first corner.
     * @param cor1 is the resource of the second corner.
     * @param cor2 is the resource of the third corner.
     * @param cor3 is the resource of the fourth corner.
     * @param resources is the array list of permanent resources.
     * @param corback0 is the resource of the first back corner.
     * @param corback1 is the resource of the second back corner.
     * @param corback2 is the resource of the third back corner.
     * @param corback3 is the resource of the fourth back corner.
     * @param numCard is the number of the card.
     */
    public StarterCard(@JsonProperty("cor0")Resource cor0, @JsonProperty("cor1")Resource cor1,
                       @JsonProperty("cor2")Resource cor2, @JsonProperty("cor3")Resource cor3,
                       @JsonProperty("resources")ArrayList<Resource> resources,
                       @JsonProperty("corback0")Resource corback0, @JsonProperty("corback1")Resource corback1,
                       @JsonProperty("corback2")Resource corback2, @JsonProperty("corback3")Resource corback3,
                       @JsonProperty("numCard")String numCard){
        super(cor0,cor1,cor2,cor3,numCard);

        backCorners = new Corner[4];
        for (int i = 0; i < 4; i++)
            this.backCorners[i] = new Corner();
        backCorners[0].setRes(corback0);
        backCorners[1].setRes(corback1);
        backCorners[2].setRes(corback2);
        backCorners[3].setRes(corback3);
        this.permanentRes = resources;

        this.setPos(40,40);
    }

    /**
     * Method to get the wanted corner of the back of the card.
     * @param numCorner is the number of the corner you want to know.
     * @return the corner requested
     */
    public Corner getBackCorner(int numCorner){
        return this.backCorners[numCorner];
    }

    /**
     * Method to get the array list of permanent resources.
     * @return the array list of permanent resources.
     */
    public ArrayList<Resource> getPermanentRes() {
        return this.permanentRes;
    }

    /**
     * Method to print the card in the console.
     * The card is printed in yellow color.
     * It uses emojis to represent the resources on the card.
     * If it is front call the toStringFront method, if it is back call the toStringBack method.
     * @return a string that represents the card.
     */
    @Override
    public String toString() {
        String card = null;

        if (this.isFront()) {
            card = toStringFront();
        } else {
            card = toStringBack();
        }

        return card;
    }

    /**
     * Method to print the front of the card in the console.
     * @return a string that represents the front of the card.
     */
    public String toStringFront() {
        String card = null;

        String cor0 = switch (this.getCorner(0).getRes().getResName()) {
            case "Animal" -> "🐺";
            case "Plant" -> "🌿";
            case "Insect" -> "🦋";
            case "Fungi" -> "🍄";
            case "Empty" -> "⬜️";
            case "Hidden" -> "❌";
            default -> "❓";
        };
        String cor1 = switch (this.getCorner(1).getRes().getResName()) {
            case "Animal" -> "🐺";
            case "Plant" -> "🌿";
            case "Insect" -> "🦋";
            case "Fungi" -> "🍄";
            case "Empty" -> "⬜️";
            case "Hidden" -> "❌";
            default -> "❓";
        };
        String cor2 = switch (this.getCorner(2).getRes().getResName()) {
            case "Animal" -> "🐺";
            case "Plant" -> "🌿";
            case "Insect" -> "🦋";
            case "Fungi" -> "🍄";
            case "Empty" -> "⬜️";
            case "Hidden" -> "❌";
            default -> "❓";
        };
        String cor3 = switch (this.getCorner(3).getRes().getResName()) {
            case "Animal" -> "🐺";
            case "Plant" -> "🌿";
            case "Insect" -> "🦋";
                case "Fungi" -> "🍄";
                case "Empty" -> "⬜️";
                case "Hidden" -> "❌";
                default -> "❓";
            };

            card = ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.YELLOW).a("╓─────────────────────╖").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString() + "<br>" +
                    ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.YELLOW).a(" " + cor0 + "                 " + cor1+" ").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString() + "<br>" +
                    ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.YELLOW).a("                       ").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString() + "<br>" +
                    ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.YELLOW).a(" " + cor3 + "                 " + cor2+" ").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString() + "<br>" +
                    ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.YELLOW).a("╙──────────F──────────╜").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString();
        return card;
    }

    /**
     * Method to print the back of the card in the console.
     * @return a string that represents the back of the card.
     */
    public String toStringBack() {
        String card = null;

        if(permanentRes.size()==1){
            if(permanentRes.get(0).getResName().equals("Insect")){
                card=   ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.YELLOW).a("╓─────────────────────╖").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString()+"<br>"+
                        ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.YELLOW).a(" ").bg(Ansi.Color.WHITE).a("  ").bg(Ansi.Color.YELLOW).a("                 🌿 ").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString()+"<br>"+
                        ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.YELLOW).a("           🦋          ").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString()+"<br>"+
                        ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.YELLOW).a(" 🦋                 ").bg(Ansi.Color.WHITE).a("  ").bg(Ansi.Color.YELLOW).a(" ").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString()+"<br>"+
                        ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.YELLOW).a("╙──────────B──────────╜").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString();
            }else{
                card = ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.YELLOW).a("╓─────────────────────╖").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString()+"<br>"+
                        ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.YELLOW).a(" 🐺                 ").bg(Ansi.Color.WHITE).a("  ").bg(Ansi.Color.YELLOW).a(" ").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString()+"<br>"+
                        ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.YELLOW).a("           🍄          ").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString()+"<br>"+
                        ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.YELLOW).a(" ").bg(Ansi.Color.WHITE).a("  ").bg(Ansi.Color.YELLOW).a("                 🍄 ").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString()+"<br>"+
                        ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.YELLOW).a("╙──────────B──────────╜").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString();
            }
        }
        if(permanentRes.size()==2){
            // because there are only 2 starterCards that have 2 permanentRes
            // and the resources are mutually exclusive, in one there is animal and insect
            // in the other there is plant and fungi
            if(permanentRes.get(0).getResName().equals("Animal") || permanentRes.get(1).getResName().equals("Animal")){
                card = ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.YELLOW).a("╓─────────────────────╖").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString()+"<br>"+
                        ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.YELLOW).a(" ").bg(Ansi.Color.WHITE).a("  ").bg(Ansi.Color.YELLOW).a("        🐺       ").bg(Ansi.Color.WHITE).a("  ").bg(Ansi.Color.YELLOW).a(" ").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString()+"<br>"+
                        ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.YELLOW).a("           🦋          ").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString()+"<br>"+
                        ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.YELLOW).a(" ").bg(Ansi.Color.WHITE).a("  ").bg(Ansi.Color.YELLOW).a("                 ").bg(Ansi.Color.WHITE).a("  ").bg(Ansi.Color.YELLOW).a(" ").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString()+"<br>"+
                        ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.YELLOW).a("╙──────────B──────────╜").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString();
            }else{
                card = ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.YELLOW).a("╓─────────────────────╖").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString()+"<br>"+
                        ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.YELLOW).a(" ").bg(Ansi.Color.WHITE).a("  ").bg(Ansi.Color.YELLOW).a("        🌿       ").bg(Ansi.Color.WHITE).a("  ").bg(Ansi.Color.YELLOW).a(" ").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString()+"<br>"+
                        ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.YELLOW).a("           🍄          ").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString()+"<br>"+
                        ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.YELLOW).a(" ").bg(Ansi.Color.WHITE).a("  ").bg(Ansi.Color.YELLOW).a("                 ").bg(Ansi.Color.WHITE).a("  ").bg(Ansi.Color.YELLOW).a(" ").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString()+"<br>"+
                        ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.YELLOW).a("╙──────────B──────────╜").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString();
            }

        }
        if(permanentRes.size()==3){
            if(permanentRes.get(0).getResName().equals("Fungi") || permanentRes.get(1).getResName().equals("Fungi") || permanentRes.get(2).getResName().equals("Fungi")) {
                card = ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.YELLOW).a("╓─────────────────────╖").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString()+"<br>"+
                        ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.YELLOW).a(" ").bg(Ansi.Color.WHITE).a("  ").bg(Ansi.Color.YELLOW).a("        🌿      ").bg(Ansi.Color.WHITE).a("  ").bg(Ansi.Color.YELLOW).a("  ").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString()+"<br>"+
                        ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.YELLOW).a("           🐺          ").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString()+"<br>"+
                        ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.YELLOW).a(" ").bg(Ansi.Color.RED).a("XX").bg(Ansi.Color.YELLOW).a("        🍄      ").bg(Ansi.Color.RED).a("XX").bg(Ansi.Color.YELLOW).a("  ").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString()+"<br>"+
                        ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.YELLOW).a("╙──────────B──────────╜").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString();
            }else{
                card = ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.YELLOW).a("╓─────────────────────╖").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString()+"<br>"+
                        ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.YELLOW).a(" ").bg(Ansi.Color.WHITE).a("  ").bg(Ansi.Color.YELLOW).a("        🐺       ").bg(Ansi.Color.WHITE).a("  ").bg(Ansi.Color.YELLOW).a(" ").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString()+"<br>"+
                        ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.YELLOW).a("           🦋          ").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString()+"<br>"+
                        ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.YELLOW).a(" ").bg(Ansi.Color.RED).a("XX").bg(Ansi.Color.YELLOW).a("        🌿       ").bg(Ansi.Color.RED).a("XX").bg(Ansi.Color.YELLOW).a(" ").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString()+"<br>"+
                        ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.YELLOW).a("╙──────────B──────────╜").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString();

            }
        }

        return card;
    }
}


