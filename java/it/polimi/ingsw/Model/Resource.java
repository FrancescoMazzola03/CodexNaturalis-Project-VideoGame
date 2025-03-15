package it.polimi.ingsw.Model;

import java.io.Serializable;

/**
 * Resource class, is used to represent a resource.
 * A resource is a type of object that can be found in a corner of a card.
 */
public enum Resource implements Serializable {
    /**
     * Attributes of the class.
     * resName is the name of the resource.
     */
    // the idea is to use a single enum class to refer to the type of angle you have in it there can be a resource (4) or an object (3) or
    // it can be empty (neither resource nor object) or have something (or not) but be covered
    FUNGI("Fungi"),
    INSECT("Insect"),
    PLANT("Plant"),
    ANIMAL("Animal"),
    HIDDEN("Hidden"), // there is no box in which there is corner
    EMPTY("Empty"),  //corner where there is no resource
    QUILL("Quill"),
    INKWELL ("Inkwell"),
    MANUSCRIPT("Manuscript");
    private final String resName;

    /**
     * Constructor of the class.
     * @param resName is the name of the resource.
     */
    Resource(String resName){
        this.resName= resName;
    }

    /**
     * Method to get the name of the resource.
     * @return the name of the resource.
     */
    public String getResName(){
        return this.resName;
    }
}
