package it.polimi.ingsw.Model;


import java.io.Serializable;

/**
 * Corner class, is used to represent a corner of a card.
 * A corner has a resource {@link Resource} and can be covered or covering another corner.
 */
public class Corner implements Serializable {
    /**
     * Attributes of the class.
     */
    private boolean covered = false;
    private boolean covering = false;
    private Resource resType;
    // private PlayingCard coveringCard;

    /**
     * Method to get the know if the corner is covered or not.
     * @return true if the corner is covered, false otherwise
     */
    public boolean getCovered(){
        return this.covered;
    }


    /**
     * Method to set the corner as covered.
     */
    public void setCovered() {
        this.covered = true;
    }

    /**
     * Method to get the know if the corner is covering another corner.
     * @return true if the corner is covering another corner, false otherwise
     */
    public boolean getCovering(){
        return this.covering;
    }

    /**
     * Method to set the corner as covering another corner.
     */
    public void setCovering(){
        this.covering= true;
    }

    /**
     * Method to get the resource of the corner.
     * @return the resource of the corner
     */
    public Resource getRes(){
        return this.resType;
    }

    /**
     * Method to set the resource of the corner.
     * @param res the resource to set
     */
    public void setRes(Resource res) {
        this.resType = res;
    }

    /**
     * Method to obtain the string representation of the corner.
     * @return the string representation of the corner
     */
    public String toString(){
        return "This corner has this Resource " +  this.resType.toString();
    }



}