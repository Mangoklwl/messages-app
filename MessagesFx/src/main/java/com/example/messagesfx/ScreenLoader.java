package com.example.messagesfx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ScreenLoader {
    public static  void loadScreen(String viewpath, Stage stage) throws IOException {
        Parent view1 = FXMLLoader.load(ScreenLoader.class.getResource(viewpath));
        Scene viewScene = new Scene(view1);
        stage.setScene(viewScene);
        stage.show();
    }

    public static  void loadScreenWithUserImg(String viewpath, Stage stage, String image) throws IOException {
        Parent view1 = FXMLLoader.load(ScreenLoader.class.getResource(viewpath));
        Scene viewScene = new Scene(view1);
        stage.setScene(viewScene);
        stage.setUserData(image);
        stage.show();
    }

}
