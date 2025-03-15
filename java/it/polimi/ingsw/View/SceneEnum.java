package it.polimi.ingsw.View;


/**
 * SceneEnum class, it is used to save the FXML files of the scenes.
 */
public enum SceneEnum {
    /**
     * Attributes of the class
     * value is the path of the FXML file
     */
    MENU("/menu.fxml"),
    BOARD("/board.fxml"),
    JOINGAME("/joiningGame.fxml"),
    NICKNAME("/nickname.fxml"),
    OBJECTIVE("/chooseObjective.fxml"),
    NUMPLAYER("/choiceNumberPlayer.fxml"),
    WAITPLAYERS("/waitingPlayers.fxml"),
    ERROR("/Error.fxml"),
    OTHERSTATION("/otherStation.fxml"),
    PAWNCHOICE("/Pawn.fxml"),
    ENDGAME("/endGame.fxml"),
    LASTTURN("/lasturn.fxml"),
    WAITOBJ ("/waitobj.fxml"),
    EXIT ("/Exit.fxml");

    private final String value;

    /**
     * Constructor of the class
     * @param value is the path of the FXML file
     */
    SceneEnum(final String value) {
        this.value = value;
    }

    /**
     * Method to get the path of the FXML file
     * @return the path of the FXML file
     */
    public String value() {
        return value;
    }

}

