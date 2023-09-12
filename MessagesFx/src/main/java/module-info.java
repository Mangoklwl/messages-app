module com.example.messagesfx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.google.gson;
    requires java.desktop;
    opens com.example.messagesfx to javafx.fxml;
    exports com.example.messagesfx;
    exports com.example.messagesfx.responses;
    exports com.example.messagesfx.models;
    opens com.example.messagesfx.responses to javafx.fxml;
    exports com.example.messagesfx.requests;
    opens com.example.messagesfx.requests to javafx.fxml;
    exports com.example.messagesfx.services;
    opens com.example.messagesfx.services to javafx.fxml;
    exports com.example.messagesfx.controllers;
    opens com.example.messagesfx.controllers to javafx.fxml;
}