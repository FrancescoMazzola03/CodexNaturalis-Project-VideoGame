package it.polimi.ingsw.Model;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.fusesource.jansi.Ansi;

import java.io.Serializable;

import static org.fusesource.jansi.Ansi.ansi;

/**
 * GoldenCard class, is used to represent a golden card.
 * It extends the class PlayingCard because this card can be placed on the station
 */
public class GoldenCard extends PlayingCard implements Serializable {
    /**
     * Attributes of the class.
     * numAnimals is the number of animals in the card.
     * numPlants is the number of plants in the card.
     * numInsects is the number of insects in the card.
     * numFungi is the number of fungi in the card.
     */
    private int numAnimals;
    private int numPlants;
    private int numInsects;
    private int numFungi;

    /**
     * Constructor of the class.
     * Set the attributes of the card reading from the JSON file
     * @param cor0 is the first corner of the card.
     * @param cor1 is the second corner of the card.
     * @param cor2 is the third corner of the card.
     * @param cor3 is the fourth corner of the card.
     * @param permanentRes is the permanent resource of the card.
     * @param bonusPoints is the bonus points of the card.
     * @param numAnimals is the number of animals in the card.
     * @param numPlants is the number of plants in the card.
     * @param numInsects is the number of insects in the card.
     * @param numFungi is the number of fungi in the card.
     * @param numCard is the number of the card.
     */
    public GoldenCard (@JsonProperty("cor0") Resource cor0, @JsonProperty("cor1")Resource cor1, @JsonProperty("cor2")Resource cor2, @JsonProperty("cor3")Resource cor3,
                       @JsonProperty("permanentRes") Resource permanentRes, @JsonProperty("bonusPoints")int bonusPoints, @JsonProperty("numAnimals")int numAnimals, @JsonProperty("numPlants")int numPlants, @JsonProperty("numInsects")int numInsects, @JsonProperty("numFungi")int numFungi,
                        @JsonProperty("numCard") String numCard){
        super(cor0, cor1, cor2, cor3, permanentRes,bonusPoints,numCard);
        this.numAnimals = numAnimals;
        this.numFungi = numFungi;
        this.numInsects = numInsects;
        this.numPlants = numPlants;
    }

    /**
     * Method to get the number of animals in the card.
     * @return the number of animals in the card.
     */
    public int getNumAnimals() {
        return this.numAnimals;
    }

    /**
     * Method to get the number of plants in the card.
     * @return the number of plants in the card.
     */
    public int getNumPlants() {
        return this.numPlants;
    }

    /**
     * Method to get the number of insects in the card.
     * @return the number of insects in the card.
     */
    public int getNumInsects() {
        return this.numInsects;
    }

    /**
     * Method to get the number of fungi in the card.
     * @return the number of fungi in the card.
     */
    public int getNumFungi() {
        return this.numFungi;
    }

    /**
     * Method to get the number of points the player obtain placing this card
     * The algorithm is different for each type of bonus points.
     * @param player is the player that is placing the card.
     * @param row is the row of them matrix where the card is placed.
     * @param column is the column where of the matrix the card is placed.
     * @return the number of points the player obtain placing this card
     */

    @Override
    public int addPoints(Player player, int row, int column) {
        if (this.getBonusPoints() == 3 || this.getBonusPoints() == 5)
            return getBonusPoints();
        else if (this.getBonusPoints() == 1) {
            for (int i = 0; i < 4; i++) {
                if (!this.getCorner(i).getRes().equals(Resource.HIDDEN) &&
                        !this.getCorner(i).getRes().equals(Resource.EMPTY))
                    return player.getStation().getResNum(this.getCorner(i).getRes());
            }
            return 0;
        } else if (this.getBonusPoints() == 2) {
            int incrementBonus = 0;
            if (player.getStation().getCardPositions()[row - 1][column - 1] != -1)
                incrementBonus += 2;
            if (player.getStation().getCardPositions()[row + 1][column - 1] != -1)
                incrementBonus += 2;
            if (player.getStation().getCardPositions()[row - 1][column + 1] != -1)
                incrementBonus += 2;
            if (player.getStation().getCardPositions()[row + 1][column + 1] != -1)
                incrementBonus += 2;
            return incrementBonus;
        } else
            return 0;

    }


    /**
     * Method to print the card in the console.
     * The card is printed in color, the color depends on the type of the card (animal, plant, fungi, insect).
     * Based on the type of the corners there are different emojis
     * @return the String that represent the card
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
            String res = "";
            String downRes = "";
            String downRes1 = "";

            String cor0 = switch (this.getCorner(0).getRes().getResName()) {
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
            switch (this.getPermanentRes().getResName()) {
                case "Plant" : downRes = "🌿";  break;
                case "Animal" : downRes = "🐺"; break;
                case "Insect" : downRes = "🦋"; break;
                case "Fungi" : downRes = "🍄";  break;
            };

            if(this.getNumAnimals() > 0 && !this.getPermanentRes().getResName().equals("Animal")){
                downRes1 = "🐺";
            }
            if(this.getNumPlants() > 0 && !this.getPermanentRes().getResName().equals("Plant")){
                downRes1 = "🌿";
            }
            if(this.getNumInsects() > 0 && !this.getPermanentRes().getResName().equals("Insect")){
                downRes1 = "🦋";
            }
            if(this.getNumFungi() > 0 && !this.getPermanentRes().getResName().equals("Fungi")){
                downRes1 = "🍄";
            }

            if(this.getBonusPoints()==1){
                for (int i = 0; i < 4; i++)
                    if (!this.getCorner(i).getRes().equals(Resource.EMPTY) && !this.getCorner(i).getRes().equals(Resource.HIDDEN) )
                        res = switch (this.getCorner(i).getRes().getResName()) {
                            case "Inkwell" -> "🫙";
                            case "Quill" -> "🪶";
                            case "Manuscript" -> "📜";
                            default -> "❓";
                        };

                card =
                        ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.valueOf(color)).a("╓────────").bg(Ansi.Color.YELLOW).a("1 x "+res).bg(Ansi.Color.valueOf(color)).a("───────╖").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString()+"<br>" +
                                ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.valueOf(color)).a(" ").bg(Ansi.Color.valueOf(colorCor0)).a(cor0).bg(Ansi.Color.valueOf(color)).a("                ").bg(Ansi.Color.valueOf(colorCor1)).a(cor1).bg(Ansi.Color.valueOf(color)).a("  ").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString()+"<br>"+
                                ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.valueOf(color)).a("                       ").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString()  + "<br>"+
                                ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.valueOf(color)).a(" ").bg(Ansi.Color.valueOf(colorCor3)).a(cor3).bg(Ansi.Color.valueOf(color)).a("     ").bg(Ansi.Color.WHITE).a("2x"+downRes+"|"+"1x" +downRes1).bg(Ansi.Color.valueOf(color)).a("   ").bg(Ansi.Color.valueOf(colorCor2)).a(cor2).bg(Ansi.Color.valueOf(color)).a(" ").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString()+"<br>"+
                                ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.valueOf(color)).a("╙──────────F──────────╜").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString();
            }
            else if(this.getBonusPoints()==2){
                card =
                        ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.valueOf(color)).a("╓────────").bg(Ansi.Color.YELLOW).a("2 x ⏹").bg(Ansi.Color.valueOf(color)).a("────────╖").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString()+"<br>"+
                                ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.valueOf(color)).a(" ").bg(Ansi.Color.valueOf(colorCor0)).a(cor0).bg(Ansi.Color.valueOf(color)).a("                 ").bg(Ansi.Color.valueOf(colorCor1)).a(cor1).bg(Ansi.Color.valueOf(color)).a(" ").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString()+"<br>"+
                                ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.valueOf(color)).a("                       ").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString()  + "<br>"+
                                ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.valueOf(color)).a(" ").bg(Ansi.Color.valueOf(colorCor3)).a(cor3).bg(Ansi.Color.valueOf(color)).a("️    ").bg(Ansi.Color.WHITE).a("3x"+downRes+"|"+"1x" +downRes1).bg(Ansi.Color.valueOf(color)).a("    ").bg(Ansi.Color.valueOf(colorCor2)).a(cor2).bg(Ansi.Color.valueOf(color)).a(" ").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString()+"<br>"+
                                ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.valueOf(color)).a("╙──────────F──────────╜").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString();
            }
            else if(this.getBonusPoints()==3){
                card =
                        ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.valueOf(color)).a("╓──────────").bg(Ansi.Color.YELLOW).a("3").bg(Ansi.Color.valueOf(color)).a("──────────╖").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString()+"<br>"+
                                ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.valueOf(color)).a(" ").bg(Ansi.Color.valueOf(colorCor0)).a(cor0).bg(Ansi.Color.valueOf(color)).a("                 ").bg(Ansi.Color.valueOf(colorCor1)).a(cor1).bg(Ansi.Color.valueOf(color)).a(" ").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString()+"<br>"+
                                ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.valueOf(color)).a("                       ").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString()  + "<br>"+
                                ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.valueOf(color)).a(" ").bg(Ansi.Color.valueOf(colorCor3)).a(cor3).bg(Ansi.Color.valueOf(color)).a("     ").bg(Ansi.Color.WHITE).a(" 3 x "+downRes).bg(Ansi.Color.valueOf(color)).a("     ").bg(Ansi.Color.valueOf(colorCor2)).a(cor2).bg(Ansi.Color.valueOf(color)).a(" ").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString()+"<br>"+
                                ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.valueOf(color)).a("╙──────────F──────────╜").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString();
            }
            else if(this.getBonusPoints()==5){
                card =
                        ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.valueOf(color)).a("╓──────────").bg(Ansi.Color.YELLOW).a("5").bg(Ansi.Color.valueOf(color)).a("──────────╖").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString()+"<br>"+
                                ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.valueOf(color)).a(" ").bg(Ansi.Color.valueOf(colorCor0)).a(cor0).bg(Ansi.Color.valueOf(color)).a("                 ").bg(Ansi.Color.valueOf(colorCor1)).a(cor1).bg(Ansi.Color.valueOf(color)).a(" ").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString()+"<br>"+
                                ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.valueOf(color)).a("                       ").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString()+"<br>"+
                                ansi().fg(Ansi.Color.BLACK).bg(Ansi.Color.valueOf(color)).a(" ").bg(Ansi.Color.valueOf(colorCor3)).a(cor3).bg(Ansi.Color.valueOf(color)).a("     ").bg(Ansi.Color.WHITE).a(" 5 x "+downRes).bg(Ansi.Color.valueOf(color)).a("     ").bg(Ansi.Color.valueOf(colorCor2)).a(cor2).bg(Ansi.Color.valueOf(color)).a(" ").fg(Ansi.Color.DEFAULT).bg(Ansi.Color.DEFAULT).toString()+"<br>"+
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

        return  card;
    }


}


