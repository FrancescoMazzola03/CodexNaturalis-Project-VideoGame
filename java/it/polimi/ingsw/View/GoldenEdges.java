package it.polimi.ingsw.View;
import javafx.scene.image.ImageView;


/**
 * The GoldenEdges is used to represent where a card can be placed in the board
 * when using the GUI.
 * Extended from the ImageView class, it adds two coordinates to save the position in
 * the matrix that represent the player's board
 */
public class GoldenEdges extends ImageView{
    int x,y;

    /**
     * class constructor
     *
     * @param string path to the image
     * @param i the row of the goldenedge in the matrix
     * @param j the column of the goldenedge in the matrix
     */
    public GoldenEdges(String string,int i,int j){
        super(string);
        this.x = i;
        this.y = j;
    }

    /**
     * Method to get the row of the goldenedge
     * @return the row of the goldenedge
     */
    public int getCoordinateX(){
        return this.x;
    }

    /**
     * Method to get the column of the goldenedge
     * @return the column of the goldenedge
     */
    public int getCoordinateY(){
        return this.y;
    }

}
