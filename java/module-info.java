module it.polimi.ingsw {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.rmi;
    requires java.net.http;
    requires org.fusesource.jansi;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires java.desktop;
    requires jdk.jdi;


    opens it.polimi.ingsw.View to javafx.fxml;
    exports it.polimi.ingsw.View;
    exports it.polimi.ingsw.Network to java.rmi;
    opens it.polimi.ingsw.Model to com.fasterxml.jackson.databind;
    exports it.polimi.ingsw.Model to com.fasterxml.jackson.databind;
    exports it.polimi.ingsw.View.Controller to javafx.fxml;
    opens it.polimi.ingsw.View.Controller to javafx.fxml;
}